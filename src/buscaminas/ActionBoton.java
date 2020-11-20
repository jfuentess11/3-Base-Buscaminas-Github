package buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author jesusredondogarcia
 **
 */
public class ActionBoton implements ActionListener {

	VentanaPrincipal ventana;
	int i;
	int j;

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

		boolean noEsMina = ventana.getJuego().abrirCasilla(i, j);

		if (noEsMina) {

			abrirCasilla(i, j);

		} else {
			ventana.panelesJuego[i][j].setBackground(Color.RED);
			ventana.mostrarFinJuego(true);
		}
	}

	/**
	 * El funcianamiento es similar al método calcularMinasAdjuntas de la clase ControlJuego.
	 * Se recorre las casillas adyacentes a la que se pulsó para buscar más casillas libres de mina.
	 * En el caso de encontarr otra casilla que no tenga ninguna mina adyacente se hará de nuevo todo el proceso.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como poco la i y la j valdrán 0.
	 * @param i: posición vertical de la casilla a abrir
	 * @param j: posición horizontal de la casilla a abrir
	 */
	public void abrirMasCasillas(int i, int j) {

		int iInicial = Math.max(0, (i - 1));
		int jInicial = Math.max(0, (j - 1));

		int iFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (i + 1));
		int jFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (j + 1));

		for (int vertical = iInicial; vertical <= iFinal; vertical++) {
			for (int horizontal = jInicial; horizontal <= jFinal; horizontal++) {
				if (ventana.getJuego().getMinasAlrededor(vertical, horizontal) != ControlJuego.MINA) {
					// Si hay un boton en ese panel lo abre
					if (ventana.panelesJuego[vertical][horizontal].getComponent(0) instanceof JButton) { 
						ventana.getJuego().abrirCasilla(vertical, horizontal);
						abrirCasilla(vertical, horizontal);
					}
				}
			}
		}
	}

	/**
	 * Método que se utilizará para abrir una casilla del buscaminas
	 * @param i: posición vertical de la casilla a abrir
	 * @param j: posición horizontal de la casilla a abrir
	 */
	public void abrirCasilla(int i, int j) {
		ventana.mostrarNumMinasAlrededor(i, j);
		if (ventana.getJuego().getMinasAlrededor(i, j) == 0) {
			abrirMasCasillas(i, j);
		}

		if (ventana.getJuego().esFinJuego()) {
			ventana.mostrarFinJuego(false);
		}
	}
}
