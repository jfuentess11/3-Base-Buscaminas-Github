package buscaminas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author Javier Fuentes
 * @since 1.0
 * @version 1.0
 */
public class ControlJuego {
	/** Entero asociado que indica que es una Mina */
	final static int MINA = -1;
	/** Entero que indica las minas inicales de la partida */
	private int minasIniciales = 20;
	/** Entero que indica el tamaño del tablero */
	private int ladoTablero = 10;
	/** Visión lógica del tablero de juego */
	private int [][] tablero;
	/** Entero que nos idica la puntuación */
	private int puntuacion;
	/** ArrayList que guardará un entero que es la casilla en la que se encuentra una mina */
	private ArrayList<Integer> minas = new ArrayList<>();
	
	/**
	 * Constructor parametrizado que inicializa una partida con los datos recibidos.
	 * 
	 * @param minasIniciales entero que indica las minas con las que se empieza la partida
	 * @param ladoTablero entero qe indica el tamaño del tablero
	 */
	public ControlJuego(int minasIniciales,int ladoTablero){
		this.minasIniciales = minasIniciales;
		this.ladoTablero = ladoTablero;
		tablero = new int[ladoTablero][ladoTablero];
		inicializarPartida();
	}

	/**
	 * Constructor por defecto que inicilaiza una partida con los valores predefinidos
	 */
	public ControlJuego() {
		tablero = new int[ladoTablero][ladoTablero];
		inicializarPartida();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){
		// ArrayList que guardará todas las posiciones del tablero.
		ArrayList<Integer> posiciones = new ArrayList<>();
		for (int i = 0; i < ladoTablero*ladoTablero; i++) {
			posiciones.add(i);
		}
		/** Número aleatoio nque se irá generando */
		Random rd = new Random();
		/** Entero que indicará la posición en la que está mirando el ArrayList*/
		int indice;
		/** Posición del tablero  */
		int posicion;
		/** Columna en la que se encuentra esa posicón */
		int columna;
		/** Fila en la que se encuentra esa posición */
		int fila;
		// Se saca una posicion aleatoria del ArrayList y se coloca una mina en ella.
		// Despues se borra esa posción para que no vuelva a salir para colocar una mina.
		for (int i = 0; i < minasIniciales; i++) {
			indice = rd.nextInt(posiciones.size());
			posicion = posiciones.get(indice);
			columna = posicion % ladoTablero;
			fila = posicion / ladoTablero;
			tablero[columna][fila] = MINA;
			posiciones.remove(indice);
		}
		//Al final del método hay que guardar el número de minas para las casillas que no son mina:
		//Si es una mina añade su posición en el Arraylist para luego mostrarlas al final del juego.
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				// Si no es una mina
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}else{
					posicion = (ladoTablero)*j+i;
					minas.add(posicion);
				}
			}
		}
	}
	
	/**
	 * Cálculo de las minas adjuntas: 
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		/** Contador de minas que tiene adyacentes */
		int contadorMina = 0;
		/** Posicion de i en la que empezará a buscar */
		int iInicial = Math.max(0, (i-1));
		/** Posición de j en la que empezará a buscar */
		int jInicial = Math.max(0, (j-1));
		/** Posición de i en la que terminará de buscar */
		int iFinal = Math.min(ladoTablero-1, (i+1));
		/** Posición de j en la que terminrá de buscar */
		int jFinal = Math.min(ladoTablero-1, (j+1));

		for (int vertical = iInicial; vertical <= iFinal; vertical++) {
			for (int horizontal = jInicial; horizontal <= jFinal; horizontal++) {
				// Si es una mina
				if(tablero[vertical][horizontal]==MINA){
					contadorMina++;
				}
			}
		}
		return contadorMina;
	}
	
	/**
	 * Método que nos permite abrir una casilla. Devuelve verdadero y suma un punto si no hay mina.  
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		/** Indicará que esa casilla es o no una mina */
		boolean esMina = true;
		// Si no es una mina
		if(getMinasAlrededor(i, j)!=MINA){
			esMina = false;
			this.puntuacion++;
		}
		return esMina;
	}
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		// Si la puntuación es igual al número de celdas que no son minas ha ganado. (true)
		return getPuntuacion()==(ladoTablero*ladoTablero-minasIniciales);
	}
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return this.puntuacion;
	}

	/**
	 * Metodo que devuelve las minas iniciales
	 * @return Entero con las minas iniciales
	 */
	public int getMinasIniciales(){
		return minasIniciales;
	}

	/**
	 * Método que devuelve el tamaño del tablero
	 * @return Un entero con el lado del tablero
	 */
	public int getLadoTablero(){
		return ladoTablero;
	}

	/**
	 * Método que devuelve un ArrayList con la posción de las minas del tablero.
	 * @return ArrayList de enteros.
	 */
	public ArrayList<Integer> getPosicionesMinas(){
		return minas;
	}
	
}
