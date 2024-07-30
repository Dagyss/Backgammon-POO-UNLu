package ar.edu.unlu.backgammon.persistencia;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import ar.edu.unlu.backgammon.modelo.Jugador;

public class PersistenciaJugador {
	public final static File jugadoresPath = new File("jugadores.dat");
	private Serializador serializadorJugadores = new Serializador(jugadoresPath.getAbsolutePath());

	public boolean existeJugadoresFile() {
		return jugadoresPath.exists();
	}
 
	public void guardar(Jugador jugador) {
		List<Jugador> jugadores = obtenerJugadores();
		jugadores.add(jugador);
		if (jugadores.size() >= 1) {
			serializadorJugadores.writeOneObject(jugadores.get(0));
			for (int i = 1; i < jugadores.size(); i++) {
				serializadorJugadores.addOneObject(jugadores.get(i));
			}
		}
	}

	public List<Jugador> obtenerJugadores() {
		LinkedList<Jugador> jugadores = new LinkedList<Jugador>();
		Object[] recuperado = serializadorJugadores.readObjects();
		if (recuperado.length > 0) {
			for (Object jugador : recuperado) {
				jugadores.add((Jugador) jugador);
			}
		}
		return jugadores;
	}

	public Jugador obtenerJugadorPorNombre(String nombre) {
		return obtenerJugadores().stream().filter(jugador -> jugador.getNombre().equals(nombre)).findFirst()
				.orElse(null);
	}

	public void guardar(List<Jugador> jugadores) {
		if (jugadores.size() >= 1) {
			serializadorJugadores.writeOneObject(jugadores.get(0));
			for (int i = 1; i < jugadores.size(); i++) {
				serializadorJugadores.addOneObject(jugadores.get(i));
			}
		}
	}

}
