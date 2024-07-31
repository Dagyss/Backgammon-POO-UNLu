# Backgammon Online - Proyecto de Programación Orientada a Objetos

## Introducción
Este repositorio contiene un juego de Backgammon en línea desarrollado como proyecto final para la asignatura de Programación Orientada a Objetos en la Universidad Nacional de Luján. El juego se ha implementado utilizando RMI (Remote Method Invocation) y sigue el patrón de diseño MVC (Modelo-Vista-Controlador) para separar la lógica de la aplicación, la interfaz de usuario y el control del flujo del programa.

### Diagrama de Clases
![Diagrama de Clases](https://i.ibb.co/X4PBpRK/diagrama-De-Clases-Backgammon-POO.png)

## Características Principales
- **Juego en línea**: Permite que varios jugadores se conecten y jueguen Backgammon en tiempo real.
- **Arquitectura MVC**: Mejora la organización del código al separar la lógica del negocio, la interfaz de usuario y los controles.
- **Diseño basado en eventos**: Utiliza el patrón Observer para manejar las actualizaciones del juego en tiempo real.
- **Interfaz gráfica y de consola**: Los usuarios pueden elegir entre una interfaz gráfica amigable o una consola basada en texto.
- **Persistencia de datos**: Guarda el estado y los rankings de los jugadores utilizando serialización.

## Reglas del Juego

### Tablero del Juego
![Tablero](https://www.wikihow.com/images/c/c8/Play-Backgammon-Step-18.jpg)

### Objetivo del Juego
El objetivo inmediato del Backgammon es sacar todas las fichas del tablero antes que el adversario. Para ello, las fichas deben realizar un recorrido sobre las casillas del tablero, las de uno en sentido de las agujas del reloj y las del otro en sentido contrario, de modo que los recorridos de uno y otro se cruzan.

### Desarrollo del Juego

#### Inicio del Juego
El tablero se encuentra dividido en dos mitades izquierda y derecha respectivamente. Cada jugador dispone de dos dados pero, excepcionalmente en el primer lanzamiento, cada uno sólo lanza uno de ellos. El que obtenga el valor más alto es el que comienza a jugar. A partir de ahí alternan el turno entre uno y otro para los movimientos sucesivos.

#### Movimientos
En su turno, cada jugador debe lanzar sus dos dados y mover sus fichas tantas casillas como indiquen los mismos. Puede mover dos piezas (una pieza por lo que indica cada dado) o una sola pieza en dos movimientos consecutivos. Un movimiento es válido siempre que termine en una casilla vacía, en una casilla con otras fichas propias o en una casilla con una única ficha del adversario. En este último caso, la pieza del adversario es capturada y se coloca sobre la barra. Nunca puede moverse una pieza a una casilla ocupada por dos o más piezas del adversario.

#### Dobles
Cuando un jugador obtiene un doble (los dos dados con el mismo valor), debe duplicar el movimiento, es decir, puede realizar cuatro movimientos por el valor que hubiera salido en los dados.

#### Piezas Capturadas
Si un jugador tiene alguna pieza capturada (sobre la barra), sólo podrá realizar el movimiento correspondiente introduciendo estas piezas en el tablero, y no podrá mover otras piezas hasta que todas las piezas capturadas estén en juego. Una pieza capturada entra en juego contando su primer movimiento desde la primera casilla de las 24 de su recorrido total.

#### Pasar
En caso de no poder realizar ningún movimiento, el jugador se verá obligado a pasar el turno. Esta situación se da con frecuencia cuando el jugador tiene alguna pieza capturada.

### Final del Juego
El juego finaliza cuando uno de los jugadores consigue sacar del tablero todas sus piezas. Para empezar a sacar piezas, un jugador debe haber colocado todas ellas en las casillas del último cuadrante de su recorrido. Las piezas deben salir del tablero utilizando el número exacto necesario. Solo se podrá utilizar un número más alto del preciso para sacar una pieza cuando no quede ninguna otra en ninguna de las casillas anteriores.

## Reconocimientos
Quiero agradecer a [federico radeljak](https://github.com/federicoradeljak) por proporcionar la librería "librería-rmimvc", que me permitio realizar la implemetacion de RMI en mi proyecto. Para más información sobre esta librería y su código  [pueden visitar el enlace](https://github.com/federicoradeljak/libreria-rmimvc).
