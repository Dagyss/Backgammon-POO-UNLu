package ar.edu.unlu.backgammon.observer;

public interface Observable {
	void agregarObservador(Observador observador);
	void eliminarObservador(Observador observador);
	void notificarObservadores();
}
