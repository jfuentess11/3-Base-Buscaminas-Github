package buscaminas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.MouseInputAdapter;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 * 
 * @author jesusredondogarcia
 **
 */
public class ActionBoton extends MouseInputAdapter implements ActionListener {

	VentanaPrincipal ventana;
	int i;
	int j;
	boolean tieneBandera = false;

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

		if (!tieneBandera) {
			boolean noEsMina = ventana.getJuego().abrirCasilla(i, j);

			if (noEsMina) {
				abrirCasilla(i, j);
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
	 * El funcianamiento es similar al método calcularMinasAdjuntas de la clase
	 * ControlJuego. Se recorre las casillas adyacentes a la que se pulsó para
	 * buscar más casillas libres de mina. En el caso de encontarr otra casilla que
	 * no tenga ninguna mina adyacente se hará de nuevo todo el proceso. Por lo
	 * tanto, como mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco
	 * la i y la j valdrán 0.
	 * 
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
						if(ventana.botonesJuego[vertical][horizontal].getIcon()==null){
							ventana.getJuego().abrirCasilla(vertical, horizontal);
							abrirCasilla(vertical, horizontal);
						}
						
					}
				}
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

	@Override
	public void mouseReleased(MouseEvent e) {
		boolean clicDerecho = (e.getButton() == MouseEvent.BUTTON3);
		if (!tieneBandera) {
			if (clicDerecho) {
				ventana.botonesJuego[i][j].setIcon(new ImageIcon("img/bandera.png"));
				tieneBandera = true;
				ventana.actualizarMinas(ventana.getMinas()-1);
			}
		} else {
			if (clicDerecho) {
				ventana.botonesJuego[i][j].setIcon(null);
				tieneBandera = false;
				ventana.actualizarMinas(ventana.getMinas()+1);

			}

		}
	}
}
