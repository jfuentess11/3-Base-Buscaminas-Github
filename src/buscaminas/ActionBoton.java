package buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

/**
 * Clase que implementa los listener de los botones del Buscaminas. Estos
 * botones tendrán una acción por defecto que es abrir el botón y una acción
 * secundaria que es por ner una bandera haciendo click derecho.
 * 
 * @author Javier Fuentes
 * @since 1.0
 * @version 1.0
 * @see VentanaPrincipal
 */
public class ActionBoton extends MouseInputAdapter implements ActionListener {
	/**
	 * {@link VentanaPrincipal} que recibirá por parámetros para acceder al control
	 * de juego
	 */
	VentanaPrincipal ventana;
	/** Columna en la que se encuentra el botón */
	int i;
	/** Fila en la que se encuentra el botón */
	int j;
	/** Nos indicará si el botón tiene puesta una bandera, por defecto no la tiene */
	boolean tieneBandera = false;
	/** Icono de la bandera */
	Icon bandera = new ImageIcon(getClass().getResource("img/bandera.png"));

	/**
	 * Constructor. Actualiza los atributos de la clase con los que recibe por
	 * parámetros.
	 * 
	 * @param ventana {@link VentanaPrincipal} en la que se encuentra el botón
	 * @param i       entero que nos indica la columna en la que se encuentra el
	 *                botón
	 * @param j       entero que nos indica la fila en la que se encuentra el botón
	 */
	public ActionBoton(VentanaPrincipal ventana, int i, int j) {
		this.ventana = ventana;
		this.i = i;
		this.j = j;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si no tiene bandera
		if (!tieneBandera) {
			boolean esMina = ventana.getJuego().abrirCasilla(i, j);
			// Si no es una mina
			if (!esMina) {
				abrirCasilla(i, j);
				// Si se ha llegado al maximo de puntuación
				if (ventana.getJuego().esFinJuego()) {
					ventana.mostrarFinJuego(false);
				}
			} else {
				ventana.panelesJuego[i][j].setBackground(Color.RED);
				ventana.mostrarFinJuego(true);
			}
		}
	}

	/**
	 * Método que se utilizará para abrir una casilla del buscaminas
	 * 
	 * @param i: posición vertical de la casilla a abrir
	 * @param j: posición horizontal de la casilla a abrir
	 */
	public void abrirCasilla(int i, int j) {
		ventana.mostrarNumMinasAlrededor(i, j);
		if (ventana.getJuego().getMinasAlrededor(i, j) == 0) {
			abrirMasCasillas(i, j);
		}

	}

	/**
	 * El funcianamiento es similar al método {@link calcularMinasAdjuntas} de la
	 * clase {@link ControlJuego}. Se recorre las casillas adyacentes a la que se
	 * pulsó para buscar más casillas libres de mina. En el caso de encontarr otra
	 * casilla que no tenga ninguna mina adyacente se hará de nuevo todo el proceso.
	 * Para controlar que no se salga del tablero como mucho la i y la j valdrán
	 * LADO_TABLERO-1. y, como poco la i y la j valdrán 0.
	 * 
	 * @param i: posición vertical de la casilla a abrir
	 * @param j: posición horizontal de la casilla a abrir
	 */
	public void abrirMasCasillas(int i, int j) {
		/** Posicion de i en la que empezará a buscar */
		int iInicial = Math.max(0, (i - 1));
		/** Posición de j en la que empezará a buscar */
		int jInicial = Math.max(0, (j - 1));
		/** Posición de i en la que terminará de buscar */
		int iFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (i + 1));
		/** Posición de j en la que terminrá de buscar */
		int jFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (j + 1));

		for (int vertical = iInicial; vertical <= iFinal; vertical++) {
			for (int horizontal = jInicial; horizontal <= jFinal; horizontal++) {
				// Si no es una mina
				if (ventana.getJuego().getMinasAlrededor(vertical, horizontal) != ControlJuego.MINA) {
					// Si hay un boton en ese panel
					if (ventana.panelesJuego[vertical][horizontal].getComponent(0) instanceof JButton) {
						// Si no tiene un icono (la bandera)
						if (ventana.botonesJuego[vertical][horizontal].getIcon() == null) {
							ventana.getJuego().abrirCasilla(vertical, horizontal);
							abrirCasilla(vertical, horizontal);
						}

					}
				}
			}
		}
	}

	/**
	 * Acción que ocurrirá cuando hacemos click derecho con el ratón en uno de los botones
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		boolean clicDerecho = (e.getButton() == MouseEvent.BUTTON3);

		// Si ha hecho click derecho
		if (clicDerecho) {
			// Si no tiene bandera
			if (!tieneBandera) {
				ventana.botonesJuego[i][j].setIcon(bandera);
				ventana.actualizarMinas(ventana.getMinas() - 1);
				tieneBandera = true;
			}else {
				ventana.botonesJuego[i][j].setIcon(null); // Se quita el icono de la bandera
				ventana.actualizarMinas(ventana.getMinas() + 1);
				tieneBandera = false;
			}
		}
	}
}