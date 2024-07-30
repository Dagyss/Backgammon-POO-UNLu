package ar.edu.unlu.backgammon.vista.grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import ar.edu.unlu.backgammon.controlador.ClickListener;
import ar.edu.unlu.backgammon.controlador.Controlador;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;

public class ClickBoton implements ActionListener {
	
	private ClickListener clickListener;
	private Color colorUsuario;
		
	public ClickBoton(Controlador controlador, Color color) {
		this.setClickListener(controlador);
		this.colorUsuario=color;
	}

	public void setClickListener(ClickListener clickListener) {
		this.clickListener=clickListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
    	String buttonText = (String) clickedButton.getClientProperty("id");
		if (clickListener != null) {
		       clickListener.onButtonClick(buttonText, colorUsuario);
		   }
	}
	
}