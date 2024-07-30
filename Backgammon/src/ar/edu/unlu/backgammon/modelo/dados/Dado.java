package ar.edu.unlu.backgammon.modelo.dados;

import java.util.Random;

public class Dado {
	private int valor;
	private int usos;
	
	public Dado() {
		super();
		this.usos = 0;
	}
	
	public void tirar() {
		Random random = new Random();
		this.valor = random.nextInt(6)+1;
		this.usos=1;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	public int getUsos() {
		return this.usos;
	}
	
	public void incrementarUsos() {
		this.usos++;
	}
	
	public void usarDado() {
		this.usos--;
	}
}
