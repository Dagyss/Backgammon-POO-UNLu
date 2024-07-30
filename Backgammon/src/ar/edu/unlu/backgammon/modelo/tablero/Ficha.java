package ar.edu.unlu.backgammon.modelo.tablero;

import ar.edu.unlu.backgammon.modelo.enumerados.Color;

public class Ficha {
	
	private Color color;
	
	public Ficha(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
