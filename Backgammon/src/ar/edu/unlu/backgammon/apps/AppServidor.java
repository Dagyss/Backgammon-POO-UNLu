package ar.edu.unlu.backgammon.apps;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ar.edu.unlu.backgammon.modelo.tablero.ITablero;
import ar.edu.unlu.backgammon.modelo.tablero.Tablero;
import ar.edu.unlu.backgammon.persistencia.PersistenciaJugador;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.servidor.Servidor;

public class AppServidor {
	
	public static void main(String[] args) {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchara peticiones el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchara peticiones el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		PersistenciaJugador persistencia = new PersistenciaJugador();
		ITablero modelo = new Tablero(persistencia);
		Servidor servidor = new Servidor(ip, Integer.parseInt(port));
		try {
			servidor.iniciar(modelo);
		} catch (RemoteException e) {	
			e.printStackTrace();
		} catch (RMIMVCException e) {
			e.printStackTrace();
		}
	}
}
