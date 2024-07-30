package ar.edu.unlu.backgammon.controlador;

import java.rmi.RemoteException;
import java.util.List;

import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;
import ar.edu.unlu.backgammon.modelo.enumerados.Estado_Tablero;
import ar.edu.unlu.backgammon.vista.IVista;
import ar.edu.unlu.rmimvc.cliente.IControladorRemoto;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.backgammon.modelo.tablero.ITablero;

public class Controlador implements ClickListener, IControladorRemoto {
	
	private ITablero modelo;
	private IVista vista;
	private Color colorTurno;
	private int nroUltimoDadoClick;

	public  <T extends IObservableRemoto> Controlador (T modelo) throws RemoteException {
		this.setModeloRemoto(modelo);
	}

	public Controlador() {
	}
	
	public void setVista ( IVista vista) {
		this.vista = vista;
	}
	
	public <T extends IObservableRemoto> void setModeloRemoto(T modeloRemoto) {
		this.modelo = (ITablero) modeloRemoto; // es necesario castear el modelo remoto 
	}
	
	public void iniciarPartida() throws RemoteException {		
		setColorTurno(modelo.turnoInicial());
	}
	
	public void verificarInicio() {
		try {
			this.modelo.partidaLista();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void tirarDados()  {
		try {
			modelo.tirarDados(colorTurno);
		} catch (RemoteException e) {	
			e.printStackTrace();
		}
	}
	
	private void actualizarDados() throws RemoteException {
		String[] valoresDados =  modelo.getValoresDados().split("\n");
		vista.actualizarDados(Integer.parseInt(valoresDados[0].trim()), Integer.parseInt(valoresDados[1].trim()), Integer.parseInt(valoresDados[2].trim()), Integer.parseInt(valoresDados[3].trim()), colorTurno);
	}

	private void cambioDeTurno() {
		try {
			this.setColorTurno(this.modelo.getColorTurno());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void pasarTurno() {
		try {
			this.modelo.pasarTurno();
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public Jugador conectarJugador(Jugador jugador)  {
		try {
			jugador = this.modelo.conectarJugador(jugador);
			
		} catch (RemoteException e) {
		
			e.printStackTrace();
		}
		return jugador;
	}
	
	public void desconectar(Jugador jugador) {
		try {
			this.modelo.desconectar(jugador, this);
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Jugador crearJugador(String nombre) {
		Jugador jugador = null;
		try {
			jugador = this.modelo.crearJugador(nombre); 
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return jugador;
		
	}
	
	public void jugadorMueveFicha(Color colorJugador,int nroColumna, int nroDado) {
		try {
			modelo.realizarMovimiento(colorJugador, nroColumna, nroDado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void movimientoComidas(Color colorJugador, int nroDado) {
		try {
			modelo.realizarMovimientoComidas(colorJugador, nroDado);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void actualizar(IObservableRemoto observable, Object arg0) throws RemoteException {
		if (arg0 instanceof Estado_Tablero) {
			switch((Estado_Tablero)arg0) {
			case PARTIDA_INICIADA:
				vista.dibujarTablero(modelo.getEstadoColumnas());
				this.iniciarPartida();
				vista.turnoInicial(colorTurno);
				break;
			case DADOS_TIRADOS:
				this.actualizarDados();
				break;
			case MOVIMIENTO_REALIZADO:
				vista.dibujarTablero(modelo.getEstadoColumnas());
				this.actualizarDados();
				break;
			case MOVIMIENTO_REALIZADO_FIN_TURNO:
				vista.dibujarTablero(modelo.getEstadoColumnas());
				this.cambioDeTurno();
				vista.turnoInicial(colorTurno);
				break;
			case TURNO_INCORRECTO:
				vista.turnoIncorrecto();
				break;
			case DADO_SIN_USOS:
				vista.dadoSinUsos();
				break;
			case MOVIMIENTO_INVALIDO:
				vista.movimientoInvalido();
				break;
			case FICHA_INCORRECTA:
				vista.fichaIncorrecta();
				break;
			case HAY_FICHAS_COMIDAS:
				vista.hayFichasComidas();
				break;
			case PARTIDA_FINALIZADA_GANO_BLANCO:
				vista.dibujarTablero(modelo.getEstadoColumnas());
				vista.finPartida(Color.BLANCO);
				break;
			case PARTIDA_FINALIZADA_GANO_NEGRO:
				vista.dibujarTablero(modelo.getEstadoColumnas());
				vista.finPartida(Color.NEGRO);
				break;
			case PARTIDA_LLENA:
				vista.partidaLlena();
				break;
			case USUARIO_CONECTADO:
				vista.usuarioConectado();
				break;
			case USUARIO_REPETIDO:
				vista.usuarioRepetido();
				break;
			default:
			break;
			}
		}
	}

	@Override
	public void onButtonClick(String buttonText, Color colorUsuario) {
		String[] split = buttonText.split(" ");
		int columna;
		try {
			columna=Integer.parseInt(split[1].trim());
			if(nroUltimoDadoClick==0) {
				vista.dadoNoSeleccionado();
			}else {
				this.jugadorMueveFicha(colorUsuario, columna, nroUltimoDadoClick);
				this.nroUltimoDadoClick = 0;
			}
		} catch (Exception e) {
			switch(buttonText) {
			case "dado1":
				this.nroUltimoDadoClick=1;
				break;
			case "dado2":
				this.nroUltimoDadoClick=2;
				break;
			case "Pasar":
				this.pasarTurno();
				break;
			default:
				if (colorTurno == colorUsuario) {
					this.movimientoComidas(colorUsuario, nroUltimoDadoClick);
				}
				break;
			}
		}
	}
	
	private void setColorTurno(Color color) {
		this.colorTurno=color;
	}
	
	public Color getColorTurno() {
		return this.colorTurno;
	}
	
	public List<Jugador> getTop(int i) {
		try {
			return this.modelo.getTop(i);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
