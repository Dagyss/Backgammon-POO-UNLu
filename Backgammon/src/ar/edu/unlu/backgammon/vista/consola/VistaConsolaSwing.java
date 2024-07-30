package ar.edu.unlu.backgammon.vista.consola;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import ar.edu.unlu.backgammon.controlador.Controlador;
import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.vista.Estado;
import ar.edu.unlu.backgammon.vista.IVista;
import ar.edu.unlu.backgammon.vista.Mensajes;

public class VistaConsolaSwing extends JFrame implements IVista, Mensajes {

	private static final long serialVersionUID = 1L;
	//Ventana
	private JTextField campoTexto;
	private JTextArea pantalla;
	private JPanel panelComandos;
	//Fin ventana
	private Controlador controlador;
	private Jugador jugadorAsociado;
	private Estado estado;

	public VistaConsolaSwing(Controlador controlador) {
		// Ventana principal
		this.controlador=controlador;
		setTitle("Consola");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		// Pantalla de la consola
		pantalla = new JTextArea();
		pantalla.setEditable(false);
		pantalla.setBackground(Color.BLACK);
		pantalla.setForeground(Color.WHITE);
		pantalla.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane scrollPantalla = new JScrollPane(pantalla);
		scrollPantalla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// Campo de texto para escribir los comandos
		campoTexto = new JTextField(50);
		campoTexto.setToolTipText("");
		campoTexto.hasFocus();
		campoTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
		campoTexto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (campoTexto.getText().length() >= 20) {
					e.consume();
				}
			}
		});
		campoTexto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String entrada = campoTexto.getText();
				println(entrada);
				campoTexto.setText("");
					switch(estado) {
					case INICIO:
						validarUsuario(entrada);
						break;
					case MENU_PRINCIPAL:
						opcionesPrincipal(entrada);
						break;
					case MOVIMIENTO:
						validarMovimiento(entrada);
						break;
					case FIN_PARTIDA:
						cerrarJuego();
						break;
					case ESPERANDO_MOVIMIENTO:
						turnoIncorrecto();
						break;
					default: 
						break;
					} 
				} 
		});
		//Evento cierre
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cerrarJuego(); 
			}
		});
		panelComandos = new JPanel();
		panelComandos.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelComandos.add(campoTexto);
		getContentPane().add(scrollPantalla, BorderLayout.CENTER);
		getContentPane().add(panelComandos, BorderLayout.SOUTH);
	}
	
	@Override
	public void iniciarVista() {
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		clearScreen();
		println(Mensajes.BACKGAMMON);
		inicio();
	}
	
	private void inicio() {
		println(Mensajes.INICIO);
		this.estado = Estado.INICIO;
	}

	private void opcionesPrincipal(String entrada) {
		switch (entrada) {
		case "1":
			clearScreen();
			estado = Estado.INICIO_SESION;
			this.setJugador(controlador.conectarJugador(jugadorAsociado));
			//Aca reviso si ya esta conectado el mismo nombre a la partida, en cuyo caso
			//aviso que no se puede y pido que cambie el nombre vuelvo al estado inicial
			if (estado == Estado.PARTIDALLENA) {
				estado = Estado.MENU_PRINCIPAL;
				menu();
			} else if (estado == Estado.USUARIO_INVALIDO) {
				inicio();
			} else mostrarSalaEspera();
			break;
		case "2":
			clearScreen();
			ReglasConsola reglas = new ReglasConsola();
			println(reglas.printReglas());
			menu();
			break;
		case "3":
			mostrarTop(this.controlador.getTop(5));
			menu();
			break;
		case "0":
			this.cerrarJuego();
			break;
		default:
			println("ingrese una opcion correcta !");
			break;
		}
	}
	 
	private void menu() {
		//clearScreen();
		println(Mensajes.MENU_PRINCIPAL);
		this.estado = Estado.MENU_PRINCIPAL;
	}
	
	private void mostrarSalaEspera() {
		//clearScreen();
		println(Mensajes.BUSCANDO_PARTIDA);
		estado = Estado.ESPERA;
		this.controlador.verificarInicio();
	}

	private void mostrarTop(List<Jugador> ranking) {
		println("Mejores Jugadores:");
	    println("Ranking\t|Nombre\t\t\t(Ganadas/Perdidas)");
	    for (int i = 0; i < ranking.size(); i++) {
	        Jugador jugador = ranking.get(i);
	        println((i + 1)+"\t|"+jugador.getNombre()+"\t ("+jugador.getGanadas()+"/"+jugador.getPerdidas()+")");
	    }
	    println("\n");
	}
	
	private void validarUsuario(String usuario) {
		if (usuario.trim().isBlank()) {
			println("El usuario no puede estar vacio, reingrese...");
		} else {	
			this.jugadorAsociado = this.controlador.crearJugador(usuario);
			println("Hola "+usuario+"!");
			menu();
		}
		
	}
	
	@Override 
	public void usuarioConectado() {
		if (estado == Estado.ESPERA) {
			println("Un usuario se conecto a la partida");
		}
	}
	
	@Override
	public void usuarioRepetido() {
		if (estado == Estado.INICIO_SESION) {
			println(Mensajes.USUARIO_REPETIDO);
			estado = Estado.USUARIO_INVALIDO;
		}
	}
	
	@Override
	public void partidaLlena() {
		println(Mensajes.PARTIDA_LLENA);
		this.estado = Estado.PARTIDALLENA; 	
	}
	
	@Override
	public void dibujarTablero(String estadoTablero) {
		clearScreen();
		String[] columnas = estadoTablero.split("\n");
		println(  
				  "___12___11___10___9____8____7_________6____5____4____3____2____1____  CN\n"
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |   \n"
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |   \n"
				
				+ "| |"+columnas[11]+"| |"+columnas[10]+"| |"+columnas[9]+"| |"+columnas[8]+"| |"+columnas[7]+"| |"+columnas[6]+"| |  | |"+columnas[5]+"| |"+columnas[4]+"| |"+columnas[3]+"| |"+columnas[2]+"| |"+columnas[1]+"| |"+columnas[0]+"| | "+columnas[25]+"\n"
				
				
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | | \n"
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | | \n"
				+ "|  ()   ()   ()   ()   ()   ()  |  |  ()   ()   ()   ()   ()   ()  | \n"
				+ "|_______________________________|__|_______________________________| \n"
				+ "|_______________________________|__|_______________________________| \n"
				+ "|                               |  |                               | \n"
				+ "|  ()   ()   ()   ()   ()   ()  |  |  ()   ()   ()   ()   ()   ()  | \n"
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | | \n"
				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | | \n"
				
				
				+ "| |"+columnas[12]+"| |"+columnas[13]+"| |"+columnas[14]+"| |"+columnas[15]+"| |"+columnas[16]+"| |"+columnas[17]+"| |  | |"+columnas[18]+"| |"+columnas[19]+"| |"+columnas[20]+"| |"+columnas[21]+"| |"+columnas[22]+"| |"+columnas[23]+"| | "+columnas[24]+" \n"

				+ "| |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |  | |   \n"				
				+ "|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|__|_|   \n"
				+ "   13   14   15   16   17   18        19   20   21   22   23   24    CB");
	}
	
	@Override
	public void turnoInicial(ar.edu.unlu.backgammon.modelo.enumerados.Color color) {
		println("Turno del jugador: "+ color);
		println("Usted es el jugador "+ this.jugadorAsociado.getColorFichas());
		this.estado = Estado.ESPERANDO_MOVIMIENTO;
		
		if (color == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.TU_TURNO);
			this.estado = Estado.MOVIMIENTO;
			controlador.tirarDados();
			movimiento();
		}
	}

	@Override
	public void turnoIncorrecto() {
		if (controlador.getColorTurno() != this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.TURNO_INCORRECTO);	
		}
	}
	
	//---------------MOVIMIENTO---------------//
	
	private void movimiento() {
		println("Ingrese el numero de columna de la ficha que desea mover (1-24 CN o CB [Comidas Negras y Comidas Blancas]), \n"
				+ "y el numero de dado que desea utilizar (1 o 2) o PASAR, para pasar el turno \n"
				+ "EJEMPLO: '6 2'  = Columna 6 Dado nro 2");
	}
	
	private void validarMovimiento(String entrada) {
		String[] entradaFiltrada = entrada.trim().split(" ");
		if (entrada.toUpperCase().equals("PASAR")) {
			controlador.pasarTurno();
		} else if (entradaFiltrada.length == 2) {
			try {
				
				int movimientoDado = Integer.valueOf(entradaFiltrada[1]);
				if (movimientoDado>2) {
					println("Solo hay 2 dados! Usted intento usar el dado numero: "+ entradaFiltrada[1]);
				} else {
					if ((entradaFiltrada[0].equals("CB")&& ar.edu.unlu.backgammon.modelo.enumerados.Color.BLANCO==this.jugadorAsociado.getColorFichas()) ||
							(entradaFiltrada[0].equals("CN")&& ar.edu.unlu.backgammon.modelo.enumerados.Color.NEGRO==this.jugadorAsociado.getColorFichas())) {
						System.out.println("Intentando mover la ficha "+entradaFiltrada[0]+" Del jugador "+this.jugadorAsociado.getColorFichas());
						controlador.movimientoComidas(this.jugadorAsociado.getColorFichas(), movimientoDado);
					} else {
						try {
							int movimientoCol =  Integer.valueOf(entradaFiltrada[0]);
							if (movimientoCol>=1 && movimientoCol<=25) {
								controlador.jugadorMueveFicha(this.jugadorAsociado.getColorFichas(), movimientoCol, movimientoDado);
							} else println("Solo hay 24 columnas! Usted intento mover fichas de la columna: "+ entradaFiltrada[0]);
						} catch (NumberFormatException e) {
							println("No ingreso una columna con sus fichas");
						}
						

					}
				}
				
				
			} catch(NumberFormatException e) {
				println("No ingreso numeros!");
			}
			
		}
	}

	@Override
	public void movimientoInvalido() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.MOVIMIENTO_INCORRECTO);
		}
	}
	
	//-------------FIN MOVIMIENTO-------------//
	
	

	@Override
	public void fichaIncorrecta() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.FICHA_INCORRECTA);	
		}
	}
	
	@Override
	public void hayFichasComidas() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.HAY_FICHAS_COMIDAS);
		}
	}
	
	@Override
	public void actualizarDados(int valorDado1, int usosDado1, int valorDado2, int usosDado2,
			ar.edu.unlu.backgammon.modelo.enumerados.Color color) {
		if (color == this.jugadorAsociado.getColorFichas()) {
			println("Dados disponibles: \n");
			println("Dado 1: |"+valorDado1+ "| Usos disponibles: "+ usosDado1);
			println("Dado 2: |"+valorDado2+ "| Usos disponibles: "+ usosDado2);
		}
		
	}

	@Override
	public void dadoSinUsos() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.DADO_SIN_USOS);	
		}
	}

	@Override
	public void dadoNoSeleccionado() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			println(Mensajes.DADO_NO_SELECCIONADO);
		}
	}	
	
	@Override
	public void finPartida(ar.edu.unlu.backgammon.modelo.enumerados.Color colorGanador) {
		estado = Estado.FIN_PARTIDA; 
		if (colorGanador==ar.edu.unlu.backgammon.modelo.enumerados.Color.NEGRO) {
			println(Mensajes.FIN_PARTIDA_NEGRO);
		} else if (colorGanador == ar.edu.unlu.backgammon.modelo.enumerados.Color.BLANCO) {
			println(Mensajes.FIN_PARTIDA_BLANCO);
		}
	}

	private void println(String texto) {
		pantalla.append("" + texto + "\n");
		pantalla.setCaretPosition(pantalla.getDocument().getLength());
	}

	private void clearScreen() {
		pantalla.setText("");
	}
	
	private void cerrarJuego() {
		if (estado == Estado.MOVIMIENTO || estado == Estado.ESPERANDO_MOVIMIENTO) {
			this.controlador.desconectar(jugadorAsociado);
		}
		this.dispose();
		System.exit(0);
	}

	@Override
	public void setJugador(Jugador jugador) {
		this.jugadorAsociado = jugador;
	}

}
