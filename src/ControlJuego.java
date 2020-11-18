import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {
	final static int MINA = -1;
	private int minasIniciales = 20;
	private int ladoTablero = 10;

	private int [][] tablero;
	private int puntuacion;
	
	public ControlJuego(int minas_iniciales,int lado_tablero){

		minasIniciales = minas_iniciales;
		ladoTablero = lado_tablero;

		tablero = new int[ladoTablero][ladoTablero];
		
		//Inicializamos una nueva partida
		inicializarPartida();

		depurarTablero();

	}


	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[ladoTablero][ladoTablero];
		
		//Inicializamos una nueva partida
		inicializarPartida();

		depurarTablero();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){

		//TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior, lo pongo todo a cero para inicializarlo.

		// ArrayList que guardará todas las posiciones del tablero.
		ArrayList<Integer> posiciones = new ArrayList<>();
		for (int i = 0; i < ladoTablero*ladoTablero; i++) {
			posiciones.add(i);
		}

		// Se saca una posicion aleatoria del ArrayList y se coloca una mina en ella.
		// Despues se borra esa posción para que no vuelva a salir para colocar una mina.
		Random rd = new Random();
		int indice,posicion,x , y;
		for (int i = 0; i < minasIniciales; i++) {
			indice = rd.nextInt(posiciones.size());
			posicion = posiciones.get(indice);
			x = posicion % ladoTablero;
			y = posicion / ladoTablero;
			tablero[x][y] = MINA;
			posiciones.remove(indice);
		}
		
		
		
		//Al final del m�todo hay que guardar el n�mero de minas para las casillas que no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA){
					tablero[i][j] = calculoMinasAdjuntas(i,j);
				}
			}
		}
	}
	
	/**Cálculo de las minas adjuntas: 
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a rellenar
	 * @param j: posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j){
		int contadorMina = 0;

		// Posicion de donde se empieza a buscar.
		int iInicial = Math.max(0, (i-1));
		int jInicial = Math.max(0, (j-1));

		// Posicion de donde se termina de buscar.
		int iFinal = Math.min(ladoTablero-1, (i+1));
		int jFinal = Math.min(ladoTablero-1, (j+1));

		for (int vertical = iInicial; vertical <= iFinal; vertical++) {
			for (int horizontal = jInicial; horizontal <= jFinal; horizontal++) {
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
		boolean noHayMina = false;
		if(getMinasAlrededor(i, j)!=MINA){
			noHayMina = true;
			this.puntuacion++;
		}
		return noHayMina;
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

	public int getMinasIniciales(){
		return minasIniciales;
	}

	public int getLadoTablero(){
		return ladoTablero;
	}
	
}
