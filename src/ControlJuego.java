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
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
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
		for (int i = 0; i < LADO_TABLERO*LADO_TABLERO; i++) {
			posiciones.add(i);
		}

		// Se saca una posicion aleatoria del ArrayList y se coloca una mina en ella.
		// Despues se borra esa posción para que no vuelva a salir para colocar una mina.
		Random rd = new Random();
		int indice,posicion,x , y;
		for (int i = 0; i < MINAS_INICIALES; i++) {
			indice = rd.nextInt(posiciones.size());
			posicion = posiciones.get(indice);
			x = posicion % LADO_TABLERO;
			y = posicion / LADO_TABLERO;
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
	private int calculoMinasAdjuntas(int x, int y){
		int contadorMina = 0;

		// Posicion de x donde empieza a buscar.
		int xInicial = Math.max(0, (x-1));

		// Posicion de x donde termina de buscar.
		int xFinal = Math.min(LADO_TABLERO-1, (x+1));

		// Posicion de y donde empieza a buscar.
		int yInicial = Math.max(0, (y-1));

		// Posicion de y donde termina de buscar.
		int yFinal = Math.min(LADO_TABLERO-1, (y+1));

		for (int i = xInicial; i <= xFinal; i++) {
			for (int j = yInicial; j <= yFinal; j++) {
				if(tablero[i][j]==MINA){
					contadorMina++;
				}
					
			}
		}
		
		return contadorMina;
	}
	
	/**
	 * Método que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		return false;
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		return false;
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
		return 0;
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return 0;
	}
	
}
