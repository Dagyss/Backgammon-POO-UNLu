package ar.edu.unlu.backgammon.apps;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ar.edu.unlu.backgammon.controlador.Controlador;
import ar.edu.unlu.backgammon.vista.IVista;
import ar.edu.unlu.backgammon.vista.consola.VistaConsolaSwing;
import ar.edu.unlu.backgammon.vista.grafica.VistaGrafica;
import ar.edu.unlu.rmimvc.RMIMVCException;
import ar.edu.unlu.rmimvc.Util;
import ar.edu.unlu.rmimvc.cliente.Cliente;

public class AppCliente {

	public static void main(String[] args) throws RemoteException {
		ArrayList<String> ips = Util.getIpDisponibles();
		String ip = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la que escuchar� peticiones el cliente", "IP del cliente", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				ips.toArray(),
				null
		);
		String port = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que escuchar� peticiones el cliente", "Puerto del cliente", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				9999
		);
		String ipServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione la IP en la corre el servidor", "IP del servidor", 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				null,
				null
		);
		String portServidor = (String) JOptionPane.showInputDialog(
				null, 
				"Seleccione el puerto en el que corre el servidor", "Puerto del servidor", 
				JOptionPane.QUESTION_MESSAGE,
				null,
				null,
				8888
		);
		Cliente c = new Cliente(ip, Integer.parseInt(port), ipServidor, Integer.parseInt(portServidor));
		Object[] options = {"Consola", "Grafica"};
        int choice = JOptionPane.showOptionDialog(null, "Seleccione su interfaz de preferencia:", "Interfaz", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Selecciono la interfaz de Consola!");
            Controlador controlador = new Controlador();
            IVista vista = new VistaConsolaSwing(controlador);
            controlador.setVista(vista);
            try {
				c.iniciar(controlador);
				vista.iniciarVista();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (RMIMVCException e) {	
				e.printStackTrace();
			} 
        } else if (choice == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Selecciono la interfaz Grafica!");
            EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					Controlador controlador = new Controlador();
    		            IVista vista = new VistaGrafica(controlador);
    		            controlador.setVista(vista);
    			    	try {
    						c.iniciar(controlador);
    						vista.iniciarVista();
    					} catch (RemoteException e) {
    						e.printStackTrace();
    					} catch (RMIMVCException e) {   						
    						e.printStackTrace();
    					}					
    				} catch (Exception e) {
    					e.printStackTrace();
    				}    			
    			}
    		});           
        }        
	}
}
