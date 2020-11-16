import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public ActionBoton(VentanaPrincipal ventana) {
		this.ventana = ventana;
	}

	/**
	 * Acción que ocurrirá cuando pulsamos uno de los botones.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int i = 0;
		int j = 0;

		for (int vertical = 0; vertical < ventana.getJuego().LADO_TABLERO; vertical++) {
			for (int horizontal = 0; horizontal < ventana.getJuego().LADO_TABLERO; horizontal++) {
				if (e.getSource().equals(ventana.botonesJuego[vertical][horizontal])) {
					i = vertical;
					j = horizontal;
				}
			}
		}

		boolean limpio = ventana.getJuego().abrirCasilla(i, j);

		if (limpio) {

			ventana.mostrarNumMinasAlrededor(i, j);
			ventana.actualizarPuntuacion();

			if (ventana.getJuego().esFinJuego()) {
				ventana.mostrarFinJuego(false);
			}

		} else {
			ventana.mostrarFinJuego(true);
		}
	}

}
