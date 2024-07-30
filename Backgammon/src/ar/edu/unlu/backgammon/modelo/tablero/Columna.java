package ar.edu.unlu.backgammon.modelo.tablero;

import java.util.ArrayList;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;

public class Columna {

	private ArrayList<Ficha> fichas;
	private int posicionColumna;
	private Color colorColumna = Color.VACIO;
	
	public Columna(int posicion) {
		this.fichas = new ArrayList<>();
		this.posicionColumna = posicion;
		inicializarColumna();
	}
	
	private void inicializarColumna() {
		switch(this.posicionColumna) {
		case 1:
			this.colorColumna = Color.NEGRO;
			for (int i = 0; i < 2; i++) {
				this.agregarFicha(new Ficha(Color.NEGRO));
			}
			break;
		case 6:
			this.colorColumna = Color.BLANCO;
			for (int i = 0; i < 5; i++) {
				this.agregarFicha(new Ficha(Color.BLANCO));
			}
			break;
		case 8:
			this.colorColumna = Color.BLANCO;
			for (int i = 0; i < 3; i++) {
				this.agregarFicha(new Ficha(Color.BLANCO));
			}
			break;
		case 12:
			this.colorColumna = Color.NEGRO;
			for (int i = 0; i < 5; i++) {
				this.agregarFicha(new Ficha(Color.NEGRO));
			}
			break;
		case 13:
			this.colorColumna = Color.BLANCO;
			for (int i = 0; i < 5; i++) {
				this.agregarFicha(new Ficha(Color.BLANCO));
			}
			break;
		case 17:
			this.colorColumna = Color.NEGRO;
			for (int i = 0; i < 3; i++) {
				this.agregarFicha(new Ficha(Color.NEGRO));
			}
			break;
		case 19:
			this.colorColumna = Color.NEGRO;
			for (int i = 0; i < 5; i++) {
				this.agregarFicha(new Ficha(Color.NEGRO));
			}
			break;
		case 24:
			this.colorColumna = Color.BLANCO;
			for (int i = 0; i < 2; i++) {
				this.agregarFicha(new Ficha(Color.BLANCO));
			}
			break;
		//25 y -1 son los cases para las columnas de fichas comidas
		case 25:
			this.colorColumna = Color.BLANCO;
			break;
		case -1:
			this.colorColumna = Color.NEGRO;
			break;
		}
	}

	public void agregarFicha(Ficha ficha) {
		this.fichas.add(ficha);
		this.colorColumna = ficha.getColor();
	}
	

	public Ficha quitarFicha() {
		Ficha ficha = null;
		if (!this.fichas.isEmpty()){
			ficha = this.fichas.get(this.fichas.size()-1);
			this.fichas.remove(this.fichas.size()-1);
			if (this.fichas.isEmpty()) {
				this.colorColumna = Color.VACIO;
			}
		}
		return ficha;
	}

	public int getCantFichas() {
		return this.fichas.size();
	}
	
	public int getPosicionColumna() {
		return this.posicionColumna;
	}
	
	public Color getColor() {
		return this.colorColumna;
	}
	
	
}
