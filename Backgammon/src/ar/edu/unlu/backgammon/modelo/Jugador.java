package ar.edu.unlu.backgammon.modelo;

import java.io.Serializable;
import java.util.Objects;
import ar.edu.unlu.backgammon.modelo.enumerados.Color;


public class Jugador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Color colorFichas;
	private int ganadas;
	private int perdidas;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Color getColorFichas() {
		return colorFichas;
	}
	
	public void setColorFichas(Color colorFichas) {
		this.colorFichas = colorFichas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(nombre, other.nombre);
	}
	 
	
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}
	
	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}
	
	public int getGanadas() {
		return ganadas;
	}
	
	public int getPerdidas() {
		return perdidas;
	}

	public void incGanadas() {
		this.ganadas++;
	}
	
	public void incPerdidas() {
		this.perdidas++;
	}
	
}
 
