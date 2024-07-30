package ar.edu.unlu.backgammon.controlador;

import ar.edu.unlu.backgammon.modelo.enumerados.Color;

public interface ClickListener {
	void onButtonClick(String buttonText, Color colorUsuario);
}
