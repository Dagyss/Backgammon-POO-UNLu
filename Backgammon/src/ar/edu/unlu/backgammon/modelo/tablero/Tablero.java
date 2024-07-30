package ar.edu.unlu.backgammon.modelo.tablero;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.modelo.dados.Cubilete;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;
import ar.edu.unlu.backgammon.modelo.enumerados.Estado_Tablero;
import ar.edu.unlu.backgammon.persistencia.PersistenciaJugador;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;



public class Tablero extends ObservableRemoto implements ITablero {
	private final PersistenciaJugador persistencia;
	private Cubilete cubilete;
	private ArrayList<Columna> columnas;
	private ColumnaCapturas comidasBlanco;
	private ColumnaCapturas comidasNegro;
	private Map<Integer, Jugador> jugadoresJugando = new HashMap <Integer, Jugador>(2);
	private Color colorTurno;
	
	public Tablero(PersistenciaJugador per) {
		this.persistencia = per;
		this.cubilete = new Cubilete();
		this.columnas = new ArrayList<>();
		this.comidasBlanco = new ColumnaCapturas(25);
		this.comidasNegro = new ColumnaCapturas(-1);
		this.colorTurno = Color.VACIO;
		inicializarTablero();
	}
	
	private void inicializarTablero() {
		for (int i=0; i<24; i++) {
			this.columnas.add(new Columna(i+1));
		}
	}
	
	@Override 
	public Jugador crearJugador(String nombre) throws RemoteException {
		if (persistencia.obtenerJugadorPorNombre(nombre) == null) {
			Jugador jugador = new Jugador(nombre);
			jugador.setGanadas(0);
			jugador.setPerdidas(0);
			persistencia.guardar(jugador);
		}
		return persistencia.obtenerJugadorPorNombre(nombre);
	} 
	
	@Override
	public Jugador conectarJugador(Jugador jugador) throws RemoteException {
		if (this.jugadoresJugando.size()==2) {
			notificarObservadores(Estado_Tablero.PARTIDA_LLENA);
		} else if (this.jugadoresJugando.isEmpty()) {
			jugador.setColorFichas(Color.BLANCO);
			jugadoresJugando.put(1, jugador);
			notificarObservadores(Estado_Tablero.USUARIO_CONECTADO);
		} else {
			if (!this.jugadoresJugando.get(1).equals(jugador)) {
				jugador.setColorFichas(Color.NEGRO);
				this.jugadoresJugando.put(2, jugador);
				notificarObservadores(Estado_Tablero.USUARIO_CONECTADO);
			} else notificarObservadores(Estado_Tablero.USUARIO_REPETIDO);
		}
		return jugador;
	}
	
	@Override
	public void desconectar(Jugador jugador, IObservadorRemoto controlador) throws RemoteException{
		Estado_Tablero estado;
		List<Jugador> jugadores = new ArrayList<>(jugadoresJugando.values());
		if (jugador.getColorFichas()==Color.BLANCO) {
			estado = Estado_Tablero.PARTIDA_FINALIZADA_GANO_NEGRO;
			System.out.println("Victoria negro: "+ jugador.getNombre());
			jugadoresJugando.get(2).incGanadas();
			jugadoresJugando.get(1).incPerdidas();
			jugadoresJugando.remove(1);
		} else {
			estado = Estado_Tablero.PARTIDA_FINALIZADA_GANO_BLANCO;
			System.out.println("Victoria blanco: "+ jugador.getNombre());
			jugadoresJugando.get(1).incGanadas();
			jugadoresJugando.get(2).incPerdidas();
			jugadoresJugando.remove(2);
		}
		actualizarEstadisticas(jugadores);
		removerObservador(controlador);
		notificarObservadores(estado);
	}
	
	
	@Override
	public void partidaLista() throws RemoteException {
		if (this.jugadoresJugando.size()==2) {
			notificarObservadores(Estado_Tablero.PARTIDA_INICIADA);
		}
	}
	
	@Override
	public Color turnoInicial() throws RemoteException {
		while (colorTurno == Color.VACIO) {
			cubilete.tirarDados();
			int valorDado1 = cubilete.getValorDado(cubilete.getDado(0));
			int valorDado2 = cubilete.getValorDado(cubilete.getDado(1));
			if (valorDado1>valorDado2){
				this.colorTurno = Color.BLANCO;
			} else {
				if (valorDado2>valorDado1) {
					this.colorTurno = Color.NEGRO;
				}
			}
		}
		return this.colorTurno;
	}
	
	@Override
	public void pasarTurno() throws RemoteException {
		this.cambiarTurno();
		notificarObservadores(Estado_Tablero.MOVIMIENTO_REALIZADO_FIN_TURNO);
	}
	
	@Override
	public void tirarDados(Color color) throws RemoteException {
		if (color == colorTurno) {
			cubilete.tirarDados();
			notificarObservadores(Estado_Tablero.DADOS_TIRADOS);
		}
	}
	
	@Override
	public String getValoresDados() throws RemoteException {
		String valores = this.cubilete.getValorDado(this.cubilete.getDado(0))+" \n"
				+ this.cubilete.getUsosDado(this.cubilete.getDado(0)) +" \n"
				+ this.cubilete.getValorDado(this.cubilete.getDado(1))+" \n"
				+ this.cubilete.getUsosDado(this.cubilete.getDado(1)) +" \n";
		return valores;
	}
	
	//---------------MOVIMIENTO---------------//
	
	@Override
	public void realizarMovimiento(Color colorJugador, int nroColumna, int dadoNro) throws RemoteException {
		Estado_Tablero estado;
		if ((colorJugador==Color.BLANCO && comidasBlanco.getCantFichas()>0) || (colorJugador==Color.NEGRO && comidasNegro.getCantFichas()>0)) {
			estado = Estado_Tablero.HAY_FICHAS_COMIDAS; 
		} else {
			int nroColumnaFisico = nroColumna-1;
			int valor = this.cubilete.getValorDado(this.cubilete.getDado(dadoNro-1));
			int nroColumnaSig = nroColumnaSiguiente(colorJugador,nroColumnaFisico,valor);
			estado = verificacionesPrevias(colorJugador, nroColumnaFisico, dadoNro);
			if (estado==Estado_Tablero.MOVIMIENTO_VALIDO || estado==Estado_Tablero.FICHA_SALE || estado==Estado_Tablero.FICHA_COMIDA) {
				Ficha ficha = columnas.get(nroColumnaFisico).quitarFicha();
				if (estado == Estado_Tablero.FICHA_COMIDA) { 
					Ficha fichaComida = columnas.get(nroColumnaSig).quitarFicha();
					switch (colorJugador) {
					case BLANCO: 
						this.comidasNegro.agregarFicha(fichaComida);
						break;
					case NEGRO: 
						this.comidasBlanco.agregarFicha(fichaComida);
						break;
					default:
						break;
					}
					columnas.get(nroColumnaSig).agregarFicha(ficha);
				} else {
					if (estado!=Estado_Tablero.FICHA_SALE) {
						columnas.get(nroColumnaSig).agregarFicha(ficha);
					} else {
						estado = verificarFinDePartida(colorJugador);
					}
				}
				this.cubilete.usarDado(this.cubilete.getDado(dadoNro-1));
				if (estado != Estado_Tablero.PARTIDA_FINALIZADA_GANO_BLANCO && estado != Estado_Tablero.PARTIDA_FINALIZADA_GANO_NEGRO) {
					if (this.cubilete.getUsosRestantes()==0) {
						this.cambiarTurno();
						estado = Estado_Tablero.MOVIMIENTO_REALIZADO_FIN_TURNO;
					} else	estado = Estado_Tablero.MOVIMIENTO_REALIZADO;
				}					
			}
		}
		notificarObservadores(estado);
	}
	
	@Override
	public void realizarMovimientoComidas(Color colorJugador, int dadoNro) throws RemoteException {
		Estado_Tablero estado = Estado_Tablero.MOVIMIENTO_INVALIDO;
		if((colorJugador==Color.BLANCO && comidasBlanco.getCantFichas()>0) ||(colorJugador==Color.NEGRO && comidasNegro.getCantFichas()>0)) {
			int nroColumna;
			int nroColumnaSiguiente;
			if (colorJugador == Color.BLANCO) {
				nroColumna = 24;
			} else {
				nroColumna = -1;
			}
			int valor = this.cubilete.getValorDado(this.cubilete.getDado(dadoNro-1));
			nroColumnaSiguiente = nroColumnaSiguiente(colorJugador, nroColumna, valor);
			estado = verificacionesPrevias(colorJugador, nroColumna, dadoNro);
			if (estado==Estado_Tablero.MOVIMIENTO_VALIDO || estado==Estado_Tablero.FICHA_COMIDA) {
				Ficha ficha = null;
				Ficha fichaComida;
				switch (colorJugador) {
				case BLANCO:
					ficha=comidasBlanco.quitarFicha();
					if(estado == Estado_Tablero.FICHA_COMIDA){
						fichaComida = columnas.get(nroColumnaSiguiente).quitarFicha();
						this.comidasNegro.agregarFicha(fichaComida);
						
					}
					break;
				case NEGRO:
					ficha=comidasNegro.quitarFicha();
					if(estado == Estado_Tablero.FICHA_COMIDA){
						fichaComida = columnas.get(nroColumnaSiguiente).quitarFicha();
						this.comidasBlanco.agregarFicha(fichaComida);
					}
					break;
				default:
					break;
				}
				columnas.get(nroColumnaSiguiente).agregarFicha(ficha);
				this.cubilete.usarDado(this.cubilete.getDado(dadoNro-1));
				if (this.cubilete.getUsosRestantes()==0) {
					this.cambiarTurno();
					estado = Estado_Tablero.MOVIMIENTO_REALIZADO_FIN_TURNO;
				} else	estado = Estado_Tablero.MOVIMIENTO_REALIZADO;
			}
		}
		notificarObservadores(estado);
		
	}
	
	
	private Estado_Tablero verificacionesPrevias(Color colorJugador,int nroColumnaFisico,int dadoNro) {
		Estado_Tablero estado = Estado_Tablero.MOVIMIENTO_VALIDO;
		int valor = this.cubilete.getValorDado(this.cubilete.getDado(dadoNro-1));
		int nroColumnaSig = nroColumnaSiguiente(colorJugador,nroColumnaFisico,valor);
		if (fichaIncorrecta(colorJugador, nroColumnaFisico)) {
			estado = Estado_Tablero.FICHA_INCORRECTA;
		} else {
			if (colorJugador!=this.colorTurno) {
				estado = Estado_Tablero.TURNO_INCORRECTO;
			} else {
				if (this.cubilete.getDado(dadoNro-1).getUsos()==0) {
					estado = Estado_Tablero.DADO_SIN_USOS;
				} else {
					if (nroColumnaSig<=-1 || nroColumnaSig>=24){
						if (this.hayFichasEnOtroCuadrante(colorJugador)){
							estado = Estado_Tablero.MOVIMIENTO_INVALIDO;
						} else {
							if (nroColumnaSig==-1 || nroColumnaSig==24) { 
								estado = Estado_Tablero.FICHA_SALE;
							} else {
								estado = verificarFichaSale(colorJugador, nroColumnaFisico);
							}
						}
					} else {
						if (this.columnas.get(nroColumnaSig).getColor()!=colorJugador && this.columnas.get(nroColumnaSig).getColor()!=Color.VACIO) {
							if (this.columnas.get(nroColumnaSig).getCantFichas()>1) {
								estado = Estado_Tablero.MOVIMIENTO_INVALIDO;
							} else {
								estado = Estado_Tablero.FICHA_COMIDA;
							} 
						}
					}
				}
			}
		}
		return estado;
	}
	
	private boolean fichaIncorrecta(Color colorJugador, int nroColumnaFisico) {
		boolean resultado = false;
		if (nroColumnaFisico != -1 && nroColumnaFisico!=24) {
			if (colorJugador!=this.columnas.get(nroColumnaFisico).getColor()) {
				resultado = true;
			} 
		}else if (nroColumnaFisico == -1 && colorJugador!=Color.NEGRO){
			resultado = true;
		} else if(nroColumnaFisico == 24 && colorJugador!=Color.BLANCO) {
			resultado = true;
		}
		return resultado;
	}
	
	
	private int nroColumnaSiguiente(Color color, int nroColumna, int valor) {
		int columnaSiguiente = -10;
		switch(color) {
		case BLANCO:
			columnaSiguiente = nroColumna - valor;
			break;
		case NEGRO:
			columnaSiguiente = nroColumna + valor;
			break;		
		default:
			break;
		}
		return columnaSiguiente;
	}
	
	private boolean hayFichasEnOtroCuadrante(Color color) {
		boolean hayFichas = false;
		int i = 0;
		if (color==Color.NEGRO) {
			while (!hayFichas && i<18) {
				if (this.columnas.get(i).getColor()==color && this.columnas.get(i).getCantFichas()>0) {
					hayFichas = true;
				}
				i++;
			}
		}else {
			i=6;
			while (!hayFichas && i<24) {
				if (this.columnas.get(i).getColor()==color && this.columnas.get(i).getCantFichas()>0) {
					hayFichas = true;
				}
				i++;
			}	
		}
		return hayFichas;
	}
	
	private Estado_Tablero verificarFichaSale(Color colorJugador, int nroColumnaFisico) {
		Estado_Tablero estado = Estado_Tablero.FICHA_SALE;
		int i = nroColumnaFisico;
		if (colorJugador==Color.BLANCO) {
			i++;
			while (estado == Estado_Tablero.FICHA_SALE && i<6) {
				if (columnas.get(i).getCantFichas()>0 && columnas.get(i).getColor()==colorJugador) {
					estado = Estado_Tablero.NO_ES_FICHA_MAS_CERCANA;
					break;
				}
				i++;
			}
		}else {
			i--;
			while (estado == Estado_Tablero.FICHA_SALE && i>18) {
				if (columnas.get(i).getCantFichas()>0 && columnas.get(i).getColor()==colorJugador) {
					estado = Estado_Tablero.NO_ES_FICHA_MAS_CERCANA;
					break;
				}
				i--;
			}
		}
		
		return estado;
	}
	
	private Estado_Tablero verificarFinDePartida(Color colorJugador) {
		Estado_Tablero estado = Estado_Tablero.MOVIMIENTO_VALIDO;
		int i = 0;
		if (colorJugador == Color.BLANCO) {
			if (comidasBlanco.getCantFichas()>0) {
				estado = Estado_Tablero.PARTIDA_CONTINUA;
			}
		} else {
			if (comidasNegro.getCantFichas()>0) {
				estado = Estado_Tablero.PARTIDA_CONTINUA;
			}
		}
		while (estado == Estado_Tablero.MOVIMIENTO_VALIDO && i<24) {
			if (columnas.get(i).getCantFichas()>0 && columnas.get(i).getColor()==colorJugador) {
				estado = Estado_Tablero.PARTIDA_CONTINUA;
			}
			i++;
			
		}
		if (estado == Estado_Tablero.MOVIMIENTO_VALIDO ) {
			List<Jugador> jugadores = new ArrayList<>(jugadoresJugando.values());
			if (colorJugador==Color.BLANCO) {
				estado = Estado_Tablero.PARTIDA_FINALIZADA_GANO_BLANCO;
				jugadoresJugando.get(1).incGanadas();
				jugadoresJugando.get(2).incPerdidas();
			} else {
				estado = Estado_Tablero.PARTIDA_FINALIZADA_GANO_NEGRO;
				jugadoresJugando.get(2).incGanadas();
				jugadoresJugando.get(1).incPerdidas();
			}
			actualizarEstadisticas(jugadores);
		}
		return estado;
	}
	
	private void actualizarEstadisticas(List<Jugador> jugadores) {
		List<Jugador> jugadoresPersistencia = persistencia.obtenerJugadores();
		jugadoresPersistencia.replaceAll(jugadorViejo -> jugadores.stream()
				.filter(jugadorNuevo -> jugadorNuevo.equals(jugadorViejo)).findFirst().orElse(jugadorViejo));
		this.persistencia.guardar(jugadoresPersistencia);
	}
	
	private Color cambiarTurno() {
		if (this.colorTurno==Color.BLANCO) {
			this.colorTurno = Color.NEGRO;
		}else {
			this.colorTurno = Color.BLANCO;
		}
		return colorTurno;
	}
	
	//-------------FIN MOVIMIENTO-------------//
	
	@Override
	public Color getColorJugador(Jugador jugador) throws RemoteException {
		for (int nroJugador : jugadoresJugando.keySet()) {
            Jugador jugadorAux = jugadoresJugando.get(nroJugador);
            if (jugadorAux.equals(jugador)) {
                return jugadorAux.getColorFichas();
            }
        }
        return null;
    }

	@Override
	public Color getColorTurno() throws RemoteException {
		return colorTurno;
	}

	@Override
	public String getEstadoColumnas () throws RemoteException{
		String tableroString = "";
		for (Columna columna : columnas) {
			if (columna.getColor()==Color.BLANCO) {
				tableroString = tableroString+"B"+columna.getCantFichas()+"\n";
			}else if (columna.getColor()==Color.NEGRO) {
				tableroString = tableroString+"N"+columna.getCantFichas()+"\n";
			} else {
				tableroString = tableroString + columna.getCantFichas()+" \n";
			}
		}
		tableroString = tableroString + comidasBlanco.getCantFichas()+" \n";
		tableroString = tableroString + comidasNegro.getCantFichas()+" \n";

		return tableroString;
	}

	@Override
	public List<Jugador> getTop(int limite) throws RemoteException {
		List<Jugador> jugadores = persistencia.obtenerJugadores();
	    return jugadores.stream().sorted((j1, j2) -> {
	        int comparacionPorGanadas = Integer.compare(j2.getGanadas(), j1.getGanadas());
	        if (comparacionPorGanadas != 0) {
	            return comparacionPorGanadas;
	        } else {
	            int comparacionPorPerdidas = Integer.compare(j1.getPerdidas(), j2.getPerdidas());
	            return comparacionPorPerdidas;
	        }
	    }).limit(limite).collect(Collectors.toList());
	}
	
}
