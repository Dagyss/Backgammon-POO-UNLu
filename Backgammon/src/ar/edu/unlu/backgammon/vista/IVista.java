package ar.edu.unlu.backgammon.vista;

import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;

public interface IVista {

	void iniciarVista();

	void dibujarTablero(String estadoTablero);
	
	void setJugador(Jugador jugador);
	
	void turnoInicial(Color color);
	
	void actualizarDados(int valorDado1, int usosDado1, int valorDado2, int usosDado2, Color color);

	void turnoIncorrecto();
	
	void dadoSinUsos();
	
	void dadoNoSeleccionado();
	
	void finPartida(Color colorGanador);
	
	void movimientoInvalido();
	
	void fichaIncorrecta();

	void hayFichasComidas();
	
	void partidaLlena();

	void usuarioConectado();

	void usuarioRepetido();




	

}
