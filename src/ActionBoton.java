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

		for (int vertical = 0; vertical < ventana.getJuego().getLadoTablero(); vertical++) {
			for (int horizontal = 0; horizontal < ventana.getJuego().getLadoTablero(); horizontal++) {
				if (e.getSource().equals(ventana.botonesJuego[vertical][horizontal])) {
					i = vertical;
					j = horizontal;
				}
			}
		}

		boolean noEsMina = ventana.getJuego().abrirCasilla(i, j);

		if (noEsMina) {

			ventana.mostrarNumMinasAlrededor(i, j);
			ventana.actualizarPuntuacion();

			if (ventana.getJuego().getMinasAlrededor(i, j) == 0) {

				int iInicial = Math.max(0, (i - 1));
				int jInicial = Math.max(0, (j - 1));

				int iFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (i + 1));
				int jFinal = Math.min(ventana.getJuego().getLadoTablero() - 1, (j + 1));

				for (int vertical = iInicial; vertical <= iFinal; vertical++) {
					for (int horizontal = jInicial; horizontal <= jFinal; horizontal++) {
						if (ventana.getJuego().getMinasAlrededor(vertical, horizontal) != ControlJuego.MINA) {
							if(ventana.getJuego().getMinasAlrededor(vertical, horizontal) == 0){
								if(ventana.panelesJuego[vertical][horizontal].getComponent(0) instanceof JButton){
									JButton siguienteBoton = (JButton)ventana.panelesJuego[vertical][horizontal].getComponent(0);
									siguienteBoton.doClick();
								}
							}else{
								ventana.mostrarNumMinasAlrededor(vertical, horizontal);
								ventana.actualizarPuntuacion();	
							}
														
						}

					}
				}
			}

			if (ventana.getJuego().esFinJuego()) {
				ventana.mostrarFinJuego(false);
			}

		} else {
			ventana.mostrarFinJuego(true);
		}
	}
}
