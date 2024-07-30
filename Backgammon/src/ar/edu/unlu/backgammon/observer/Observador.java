package ar.edu.unlu.backgammon.observer;

import ar.edu.unlu.backgammon.modelo.enumerados.Estado_Tablero;

public interface Observador {
	void actualizar(Estado_Tablero estado);
}
