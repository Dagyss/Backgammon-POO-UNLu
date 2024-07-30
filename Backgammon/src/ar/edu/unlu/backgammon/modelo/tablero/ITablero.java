package ar.edu.unlu.backgammon.modelo.tablero;

import java.rmi.RemoteException;
import java.util.List;

import ar.edu.unlu.backgammon.modelo.Jugador;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;
import ar.edu.unlu.rmimvc.observer.IObservableRemoto;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;

public interface ITablero extends IObservableRemoto {

	Color turnoInicial() throws RemoteException;
	
	void pasarTurno() throws RemoteException;

	void tirarDados(Color color) throws RemoteException;

	void realizarMovimiento(Color colorJugador, int nroColumna, int dadoNro) throws RemoteException;
	
	void realizarMovimientoComidas(Color colorJugador, int dadoNro) throws RemoteException;

	Jugador crearJugador(String nombre) throws RemoteException;
	
	Jugador conectarJugador(Jugador jugador)throws RemoteException;
	
	void partidaLista() throws RemoteException;
	
	Color getColorJugador(Jugador jugador) throws RemoteException;
	
	String getValoresDados() throws RemoteException;
	
	Color getColorTurno() throws RemoteException;
	
	String getEstadoColumnas() throws RemoteException;

	List<Jugador> getTop(int limite) throws RemoteException;

	void desconectar(Jugador jugador, IObservadorRemoto controlador) throws RemoteException;

}