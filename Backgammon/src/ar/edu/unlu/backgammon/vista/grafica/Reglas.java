package ar.edu.unlu.backgammon.vista.grafica;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Reglas {
	public void verReglas() {
        String rules = "<html><body>" +
                "<h1>Objetivo del juego</h1>" +
                "<p>Se trata de un juego de mesa cuyo objetivo inmediato es sacar todas las fichas del tablero antes que el adversario. " +
                "Para ello sus fichas deben realizar un recorrido sobre las casillas del tablero, las de uno en sentido de las agujas del reloj " +
                "y las del otro en sentido contrario, de modo que los recorridos de uno y otro se cruzan.</p>" +
                "<h2>Desarrollo del juego</h2>" +
                "<h3>Inicio del juego</h3>" +
                "<p>Cada jugador dispone de dos dados pero, excepcionalmente en el primer lanzamiento, cada uno sólo lanza uno de ellos. " +
                "El que obtenga el valor más alto es el que comienza a jugar, moviendo sus fichas con el valor de los dos dados lanzados " +
                "(uno de cada jugador).</p>" +
                "<p>A partir de ahí alternan el turno entre uno y otro para los movimientos sucesivos.</p>" +
                "<h3>Movimientos</h3>" +
                "<p>Salvo en ese primer lanzamiento, en su turno, cada jugador debe lanzar sus dos dados y mueve sus fichas tantas casillas " +
                "como indiquen los mismos. Puede mover dos piezas (una pieza por lo que indica cada dado) o puede mover una sola pieza en " +
                "dos movimientos consecutivos. Un movimiento es válido siempre que termine en una casilla vacía, en una casilla con otras " +
                "fichas propias o en una casilla con única ficha del adversario. En este último caso la pieza del adversario es capturada " +
                "y se coloca sobre la barra.</p>" +
                "<p>Nunca puede moverse una pieza a una casilla ocupada por dos o más piezas del adversario.</p>" +
                "<h3>Dobles</h3>" +
                "<p>Cuando un jugador en el momento de lanzar consigue un doble (los dos dados con el mismo valor), debe duplicar a su vez " +
                "el movimiento, es decir, puede realizar cuatro movimientos por el valor que hubiera salido en los dados.</p>" +
                "<h3>Piezas capturadas</h3>" +
                "<p>Si un jugador tiene alguna pieza capturada (sobre la barra) sólo podrá realizar en su turno el movimiento o movimientos " +
                "correspondientes metiendo en el tablero estas piezas capturadas, y no podrá realizar un movimiento con otras piezas hasta " +
                "tener todas las piezas en juego.</p>" +
                "<p>Una pieza capturada entra en juego contando su primer movimiento desde la primera casilla de las 24 de su recorrido total.</p>" +
                "<h3>Pasar</h3>" +
                "<p>En caso de no poder realizar ningún movimiento, el jugador se verá obligado a pasar el turno. Esta situación se da con " +
                "frecuencia cuando el jugador tiene alguna pieza capturada.</p>" +
                "<h3>Final del juego</h3>" +
                "<p>El juego finaliza cuando uno de los jugadores consigue sacar del tablero todas sus piezas.</p>" +
                "<p>Para poder empezar a realizar movimientos que permitan ir sacando piezas, un jugador debe previamente haber colocado " +
                "todas ellas en las casillas del último cuadrante de su recorrido.</p>" +
                "<p>Las piezas deben salir del tablero utilizando el número exacto necesitado para ello. Solo podrá utilizarse un número más " +
                "alto del preciso para sacar una pieza cuando no quede ninguna otra en ninguna de las casillas anteriores.</p>" +
                "</body></html>";

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(rules);
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 600));

        JOptionPane.showMessageDialog(null, scrollPane, "Reglas del Juego", JOptionPane.INFORMATION_MESSAGE);
    }
}

