package ar.edu.unlu.backgammon.vista;

public interface Mensajes {

	final String BACKGAMMON=
			  "########################### \n"
			+ "# ┳┓┏┓┏┓┓┏┓┏┓┏┓┳┳┓┳┳┓┏┓┳┓ # \n"
			+ "# ┣┫┣┫┃ ┃┫ ┃┓┣┫┃┃┃┃┃┃┃┃┃┃ # \n"
			+ "# ┻┛┛┗┗┛┛┗┛┗┛┛┗┛ ┗┛ ┗┗┛┛┗ # \n"
			+ "########################### \n";
	final String INICIO=
			"Ingrese su nombre de usuario!";
	final String MENU_PRINCIPAL= 
			"Bienvenido a BACKGAMMON \n"
			+ "1) Buscar Partida \n"
			+ "2) Ver Reglas \n"
			+ "3) Ver Estadisticas Usuarios \n"
			+ "0) SALIR \n";
	final String BUSCANDO_PARTIDA=
			"BUSCANDO PARTIDA...";
	final String USUARIO_CONECTADO=
			"UN USUARIO SE CONECTO CORRECTAMENTE!";
	final String PARTIDA_LLENA=
			"LA PARTIDA ESTA LLENA, NO FUE POSIBLE UNIRSE";
	final String MOVIENDO_FICHA= 
			"MOVIENDO FICHA";
	final String MOVIMIENTO_CORRECTO= 
			"MOVIMIENTO OK";
	final String MOVIMIENTO_INCORRECTO= 
			"MOVIMIENTO INVALIDO";
	final String FICHA_INCORRECTA=
			"ESA NO ES SU FICHA";
	final String HAY_FICHAS_COMIDAS= 
			"HAY FICHAS EN LA COLUMNA DE FICHAS COMIDAS! DEBE MOVER ESAS PRIMERO";
	final String DADO_SIN_USOS=
			"EL DADO SELECCIONADO NO TIENE MAS USOS";
	final String DADO_NO_SELECCIONADO=
			"NO SELECCIONO UN DADO";
	final String TU_TURNO=
			"ES TU TURNO!";
	final String TURNO_INCORRECTO=
			"ESTE NO ES TU TURNO";
	final String FIN_PARTIDA_BLANCO=
			"SE TERMINO LA PARTIDA, EL GANADOR ES EL JUGADOR BLANCO";
	final String FIN_PARTIDA_NEGRO=
			"SE TERMINO LA PARTIDA, EL GANADOR ES EL JUGADOR NEGRO";
	final String USUARIO_REPETIDO=
			"EL NOMBRE DE USUARIO SELECCIONADO YA SE ENCUENTRA EN PARTIDA, POR FAVOR SELECCIONE OTRO!";

}
