package ar.edu.unlu.backgammon.vista.grafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import ar.edu.unlu.backgammon.controlador.Controlador;
import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;
import ar.edu.unlu.backgammon.vista.Estado;
import ar.edu.unlu.backgammon.vista.IVista;
import ar.edu.unlu.backgammon.vista.Mensajes;


public class VistaGrafica implements Mensajes, IVista{

	//Iconos de fichas y dados
	private Images imagenes = new Images();
	//Ventana
	private JFrame frame;
	private JDialog espera;
	private JButton btnPasar;
	private JPanel[] panel;
	private JButton[] btnColumna;
	private JButton btnFichasComidasBlancas = new JButton("");
	private JButton btnFichasComidasNegras = new JButton("");
	private JButton btnDado1 = new JButton("");
	private JButton btnDado2 = new JButton("");
	private JTextArea log;
	//Fin ventana
	private Controlador controlador;
	private Jugador jugadorAsociado; 
	private Estado estado;

	public VistaGrafica(Controlador controlador) {
		this.controlador=controlador;
	}

	@Override
	public void iniciarVista() {
		estado = Estado.INICIO;
		while (this.menu()== 1) {
			
			this.setJugador(this.controlador.conectarJugador(jugadorAsociado));
			if (estado!=Estado.USUARIO_INVALIDO) {
				Reglas reglas = new Reglas();
				//Frame
				frame = new JFrame();
				frame.setMinimumSize(new Dimension(815, 700));
				frame.setResizable(false);
				frame.setBounds(100, 100, 450, 300);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				//Panel menu
				JPanel pnlMenu = new JPanel();
				frame.getContentPane().add(pnlMenu, BorderLayout.NORTH);
				pnlMenu.setLayout(new BorderLayout());
				JMenuBar menuBar = new JMenuBar();
				JMenu fileMenu = new JMenu("Menu");
				JMenu ayudaMenu = new JMenu("Ayuda");
				JMenuItem ayudaMenuItem = new JMenuItem("Ver Reglas");
				ayudaMenuItem.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				    	reglas.verReglas();
				    }
				});
				ayudaMenu.add(ayudaMenuItem);
				JMenuItem exitMenuItem = new JMenuItem("Salir");
				exitMenuItem.addActionListener(new ActionListener() {
				    @Override
				    public void actionPerformed(ActionEvent e) {
				        cerrarJuego();
				    }
				});
				fileMenu.add(exitMenuItem);
				menuBar.add(fileMenu);
				menuBar.add(ayudaMenu);
				pnlMenu.add(menuBar, BorderLayout.WEST);
				//Panel jugador
				JPanel pnlJugador = new JPanel();
				frame.getContentPane().add(pnlJugador, BorderLayout.SOUTH);
				JLabel lblJugador = new JLabel(jugadorAsociado.getNombre());
				pnlJugador.add(lblJugador);
				//Boton pasar
				btnPasar = new JButton("Pasar");
				btnPasar.putClientProperty("id", "Pasar");
				btnPasar.addActionListener(new ClickBoton(controlador, jugadorAsociado.getColorFichas()));
				pnlJugador.add(btnPasar);
				btnPasar.setEnabled(false);
				//Panel tablero
				ImagePanel pnlTablero = new ImagePanel(new ImageIcon("images/TableroBG.jpg").getImage());
				frame.getContentPane().add(pnlTablero, BorderLayout.CENTER);
				//Botones tablero
				panel = new JPanel[24];
				btnColumna = new JButton[24];
				int x = 666;
				for (int i = 0; i < panel.length; i++) {
				    if (i == 6) {
				        x = 335;
				    } else if (i == 18) {
				        x = 366;
				    }
				    panel[i] = new JPanel();
				    panel[i].setOpaque(false);
				    panel[i].setLayout(new GridLayout(0, 1, 0, 0));
				    btnColumna[i] = new JButton("");
				    btnColumna[i].putClientProperty("id", "Columna " + (i + 1));
				    if (i < 12) {
				    	panel[i].setBounds(x, 20, 50, 225);
				        x -= 50;
				    } else {
				        x += 50;
				        panel[i].setBounds(x, 346, 50, 225);
				    }
				    panel[i].add(btnColumna[i]);
				    pnlTablero.add(panel[i]);
				    btnColumna[i].addActionListener(new ClickBoton(controlador, jugadorAsociado.getColorFichas()));
				    this.eliminarFondoBoton(btnColumna[i], "Vacio", 0);
	
				}
				// Botones comidas
				inicializarBoton(btnFichasComidasBlancas, "ComidasBlancas", 736, 346);
				pnlTablero.add(btnFichasComidasBlancas);
				inicializarBoton(btnFichasComidasNegras, "ComidasNegras", 736, 20);
				pnlTablero.add(btnFichasComidasNegras);
				//Panel dados
				JPanel panelDados = new JPanel();
				panelDados.setBounds(85, 276, 150, 40);
				pnlTablero.add(panelDados);
				panelDados.setLayout(new BoxLayout(panelDados, BoxLayout.X_AXIS));
				inicializarBoton(btnDado1, "dado1");
				panelDados.add(btnDado1);
				inicializarBoton(btnDado2, "dado2");
				panelDados.add(btnDado2); 
				//Log
				log = new JTextArea(10, 30);
				log.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(log);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				//Panel log
				JPanel pnlLog = new JPanel();
				pnlLog.setLayout(new BorderLayout());
				pnlLog.add(scrollPane, BorderLayout.CENTER);
				//Fin panel log
				frame.getContentPane().add(pnlLog, BorderLayout.EAST);
				this.frame.pack();
				this.frame.setVisible(true);
				frame.addWindowListener(new WindowAdapter() {
		            @Override
		            public void windowClosing(WindowEvent e) {
		                int response = JOptionPane.showConfirmDialog(
		                    frame,
		                    "Seguro? Contara como derrota si esta jugando una partida!",
		                    "Confirmar",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.QUESTION_MESSAGE
		                );
		                
		                if (response == JOptionPane.YES_OPTION) {
		                    cerrarJuego();
		                }
		            }
				});
				
				mostrarSalaEspera();
			} else JOptionPane.showMessageDialog(null, Mensajes.USUARIO_REPETIDO);	
		}
	}
	
	private void inicializarBoton(JButton boton, String id, int x, int y) {
	    boton.addActionListener(new ClickBoton(controlador, jugadorAsociado.getColorFichas()));
	    boton.putClientProperty("id", id);
	    boton.setBounds(x, y, 50, 225);
	}
	
	private void inicializarBoton(JButton boton, String id) {
	    boton.addActionListener(new ClickBoton(controlador, jugadorAsociado.getColorFichas()));
	    boton.setIcon(imagenes.getDadoXImage(1));
	    boton.putClientProperty("id", id);
	    boton.setBorderPainted(false);
	    boton.setContentAreaFilled(false);
	}
	
	private int crearUsuario() {
		int status = 0;
		String usuario = JOptionPane.showInputDialog(null, "Ingrese su nombre de Usuario");
		//El check de usuario == null esta para verificar si el usuario presiona la X o cancelar para salir del programa
		if (usuario==null) {
	    	status = -1;
	    	return status;
		} 
		while (usuario.trim().isBlank() ) {
	    	JOptionPane.showMessageDialog(null, "El usuario no puede estar vacio");
	    	usuario = JOptionPane.showInputDialog(null, "Ingrese su nombre de Usuario");
	    	if (usuario==null) {
		    	status = -1;
		    	JOptionPane.showMessageDialog(null, "Saliendo...");	
		    	return status;
			}
	    }
		jugadorAsociado = this.controlador.crearJugador(usuario);
		return status;
	}
	
	
	private int menu() {
		int status = 0;
		if (estado != Estado.ESPERA && estado != Estado.MOVIMIENTO) {
			estado = Estado.MENU_PRINCIPAL;
			status = crearUsuario();
		    if (status == 0) {
			    Object[] options = {"Buscar Partida", "Estadisticas"};
			    while (status == 0) {
				    int choice = JOptionPane.showOptionDialog(null, "Bienvenido, " + jugadorAsociado.getNombre() + "!", "Menu Principal",
			                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			        if (choice == JOptionPane.YES_OPTION) {
			        	estado = Estado.INICIO_SESION;
			        	status = 1;
			        	
			        } else if (choice == JOptionPane.NO_OPTION){
			        	mostrarTop(this.controlador.getTop(5));
			        	status = 0;
			        } else {
			        	JOptionPane.showMessageDialog(null, "Saliendo...");	
			        	status = -1;
			        }
			    }
		    }
		}
	    return status;
	}

	private void mostrarSalaEspera() {
		estado = Estado.ESPERA;
		espera = new JDialog();
        espera.setUndecorated(true);
        JPanel panelEspera = new JPanel(new BorderLayout());
        panelEspera.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel mensajeEspera = new JLabel("Buscando partida...");
        panelEspera.add(mensajeEspera, BorderLayout.CENTER);
        espera.getContentPane().add(panelEspera);
        espera.pack();
        espera.setLocationRelativeTo(null);	
        espera.setVisible(true);
        this.controlador.verificarInicio();
	}
	
	private void mostrarTop(List<Jugador> ranking) {
	    JFrame frame = new JFrame("Top Jugadores");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setSize(500, 300);
	    String[] columnNames = {"Ranking", "Nombre", "Ganadas", "Perdidas"};
	    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	    JTable table = new JTable(model);
	    int rank = 1;
	    for (Jugador jugador : ranking) {
	        Object[] rowData = {
	            rank++,
	            jugador.getNombre(),
	            jugador.getGanadas(),
	            jugador.getPerdidas()
	        };
	        model.addRow(rowData);
	    }
	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setVisible(true);
	    frame.setAlwaysOnTop(true);
	    JOptionPane.showMessageDialog(null, "Presione OK para volver al menu principal");
	    frame.setVisible(false);
	    
	}
	
	@Override
	public void usuarioConectado() {
		if (estado == Estado.ESPERA) {
			this.updateLog(Mensajes.USUARIO_CONECTADO);
		}
	}
	
	
	@Override
	public void usuarioRepetido() {
		if (estado == Estado.INICIO_SESION) {
			estado = Estado.USUARIO_INVALIDO;
		}
	}
	
	@Override
	public void partidaLlena() {
		this.updateLog(Mensajes.PARTIDA_LLENA);	
	}
	
	@Override
	public void dibujarTablero(String estadoTablero) {
		String[] columnas = estadoTablero.split("\n");
		String tipo;
		int cantidad;
		for (int i=0;i<=23;i++) {
			if (columnas[i].startsWith("0")){
				tipo = "Vacio";
				cantidad = 0;
			}else {
				cantidad = Integer.parseInt(columnas[i].substring(1));
				if (columnas[i].startsWith("B")) { 
						tipo = "Blanca";
					} else tipo = "Negra";
				};
			this.eliminarFondoBoton(btnColumna[i], tipo, cantidad);
		}
		this.eliminarFondoBoton(btnFichasComidasBlancas, "Blanca", Integer.parseInt(columnas[24].trim()));
		this.eliminarFondoBoton(btnFichasComidasNegras, "Negra", Integer.parseInt(columnas[25].trim()));
	}
	
	private void eliminarFondoBoton(JButton boton, String tipo, int cantidad) {
		if (cantidad>=1) {
			boton.setEnabled(true);
			if (tipo == "Negra") {
				boton.setIcon(imagenes.getNegraXImage(cantidad));
			} else {
				boton.setIcon(imagenes.getGrisXImage(cantidad));
			}
		} else {
			boton.setIcon(imagenes.getFichaX0Image());
			boton.setEnabled(false);
		}
		boton.setBorderPainted(false);
		boton.setContentAreaFilled(false);
	}
	
	@Override
	public void actualizarDados(int valorDado1, int usosDado1, int valorDado2, int usosDado2, Color color) {
		if (color == this.jugadorAsociado.getColorFichas()) {
			btnDado1.setIcon(imagenes.getDadoXImage(valorDado1));
			btnDado2.setIcon(imagenes.getDadoXImage(valorDado2));
			btnDado1.setEnabled(usosDado1>0);
			btnDado2.setEnabled(usosDado2>0);
		}
	}
	
	@Override
	public void turnoInicial(Color color) {
		estado = Estado.MOVIMIENTO;
		espera.setVisible(false);
		boolean turno = (color == this.jugadorAsociado.getColorFichas());
		this.updateLog("Turno del jugador: "+ color);
		this.updateLog("Usted es el jugador "+ this.jugadorAsociado.getColorFichas());
		btnPasar.setEnabled(turno);
		btnDado1.setEnabled(turno);
		btnDado2.setEnabled(turno);
		if (turno) {
			this.updateLog(Mensajes.TU_TURNO);
			this.controlador.tirarDados();
		}
	}

	@Override
	public void movimientoInvalido() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.MOVIMIENTO_INCORRECTO);
		}
	}
	
	@Override
	public void hayFichasComidas() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.HAY_FICHAS_COMIDAS);
		}
	}

	@Override
	public void turnoIncorrecto() {
		if (controlador.getColorTurno() != this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.TURNO_INCORRECTO);
		}
	}

	@Override
	public void dadoSinUsos() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.DADO_SIN_USOS);
		}
	}
	
	@Override
	public void dadoNoSeleccionado() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.DADO_NO_SELECCIONADO);
		}
	}
	
	@Override
	public void fichaIncorrecta() {
		if (controlador.getColorTurno() == this.jugadorAsociado.getColorFichas()) {
			this.updateLog(Mensajes.FICHA_INCORRECTA);
		}
	}
	
	@Override
	public void finPartida(Color colorGanador) {
		estado = Estado.FIN_PARTIDA;
		btnPasar.setEnabled(false);
		btnDado1.setEnabled(false);
		btnDado2.setEnabled(false);
		if (colorGanador==Color.NEGRO) {
			this.updateLog(Mensajes.FIN_PARTIDA_NEGRO);
		} else if (colorGanador == Color.BLANCO) {
			this.updateLog(Mensajes.FIN_PARTIDA_BLANCO);
		}
	}
	
	private void cerrarJuego() {
		if (estado == Estado.MOVIMIENTO) {
			this.controlador.desconectar(jugadorAsociado);
		}
		frame.dispose();
		System.exit(0);
	}
	
	private void updateLog(String mensaje) {
	    log.append(mensaje + "\n");
	}
	
	@Override
	public void setJugador(Jugador jugador) {
		this.jugadorAsociado=jugador;
		
	}
		
}
	
