package ar.edu.unlu.backgammon.vista.consola;

public class ReglasConsola {
	public String printReglas() {
        String reglas = 
        		"\nObjetivo del juego\n"
                + "===================\n"
                + "Se trata de un juego de mesa cuyo objetivo inmediato es sacar todas las fichas del tablero antes que el \n"
                + "adversario. \n"
                + "Para ello sus fichas deben realizar un recorrido sobre las casillas del tablero, las de uno en sentido de las \n"
                + "agujas del reloj y las del otro en sentido contrario, de modo que los recorridos de uno y otro se cruzan.\n\n"
                
                + "Desarrollo del juego\n"
                + "====================\n"
                + "Inicio del juego\n"
                + "----------------\n"
                + "Cada jugador dispone de dos dados pero, excepcionalmente en el primer lanzamiento, cada uno sólo lanza uno \n"
                + "de ellos. El que obtenga el valor más alto es el que comienza a jugar, moviendo sus fichas con el valor \n"
                + "de los dos dados lanzados (uno de cada jugador).\n\n"
                + "A partir de ahí alternan el turno entre uno y otro para los movimientos sucesivos.\n\n"
                
                + "Movimientos\n"
                + "-----------\n"
                + "Salvo en ese primer lanzamiento, en su turno, cada jugador debe lanzar sus dos dados y mueve sus fichas \n"
                + "tantas casillas como indiquen los mismos. Puede mover dos piezas (una pieza por lo que indica cada dado) \n"
                + "o puede mover una sola pieza en dos movimientos consecutivos. Un movimiento es válido siempre que termine \n"
                + "en una casilla vacía, en una casilla con otras fichas propias o en una casilla con única ficha del \n"
                + "adversario. En este último caso la pieza del adversario es capturada y se coloca sobre la barra.\n\n"
                + "Nunca puede moverse una pieza a una casilla ocupada por dos o más piezas del adversario.\n\n"
                
                + "Dobles\n"
                + "------\n"
                + "Cuando un jugador en el momento de lanzar consigue un doble (los dos dados con el mismo valor), debe \n"
                + "duplicar a su vez el movimiento, es decir, puede realizar cuatro movimientos por el valor que hubiera \n"
                + "salido en los dados.\n\n"
                
                + "Piezas capturadas\n"
                + "-----------------\n"
                + "Si un jugador tiene alguna pieza capturada (sobre la barra) sólo podrá realizar en su turno el \n"
                + "movimiento o movimientos correspondientes metiendo en el tablero estas piezas capturadas, y no podrá \n"
                + "realizar un movimiento con otras piezas hasta tener todas las piezas en juego.\n\n"
                + "Una pieza capturada entra en juego contando su primer movimiento desde la primera casilla de las 24 \n"
                + "de su recorrido total.\n\n"
                
                + "Pasar\n"
                + "-----\n"
                + "En caso de no poder realizar ningún movimiento, el jugador se verá obligado a pasar el turno. Esta \n"
                + "situación se da con frecuencia cuando el jugador tiene alguna pieza capturada.\n\n"
                
                + "Final del juego\n"
                + "---------------\n"
                + "El juego finaliza cuando uno de los jugadores consigue sacar del tablero todas sus piezas.\n\n"
                + "Para poder empezar a realizar movimientos que permitan ir sacando piezas, un jugador debe previamente haber \n"
                + "colocado todas ellas en las casillas del último cuadrante de su recorrido.\n\n"
                + "Las piezas deben salir del tablero utilizando el número exacto necesitado para ello. Solo podrá utilizarse un \n"
                + "número más alto del preciso para sacar una pieza cuando no quede ninguna otra en ninguna de las casillas \n"
                + "anteriores.\n"
                + "---------------\n"
                ;
        
        return reglas;
	}
}
