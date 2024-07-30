package ar.edu.unlu.backgammon.modelo.dados;

import java.util.ArrayList;

public class Cubilete {
	
	private ArrayList<Dado> dados;
	
	public Cubilete() {
		this.dados = new ArrayList<>();
		for (int i = 0; i<2 ; i++) {
			this.dados.add(new Dado());
		}
	}
	
	public void tirarDados() {
		for (Dado dado : this.dados) {
			dado.tirar();
		}
		if (this.dados.get(0).getValor()==this.dados.get(1).getValor()) {
			for (Dado dado : this.dados) {
				dado.incrementarUsos();
			}
		}
	}
	
	public int getUsosRestantes() {
		int usos = 0;
		for (Dado dado : this.dados) {
			usos = usos+dado.getUsos();
		}
		return usos;
	}
	
	public void usarDado(Dado dado) {
		dado.usarDado();
	}

	public int getValorDado(Dado dado) {
		return dado.getValor();
	}

	public int getUsosDado(Dado dado) {
		return dado.getUsos();
	}
	
	public Dado getDado(int indice) {
		return this.dados.get(indice);
	}
	
}
