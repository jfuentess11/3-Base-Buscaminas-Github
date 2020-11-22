package buscaminas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

/**
 * Ventana principal del Buscaminas. Esta clase será la encargada de crear y
 * modificar la parte gráfica del buscaminas. Se apoya en la clase
 * {@link ControlJuego} para sacar la infromación.
 * 
 * Se inicializa en el método {@link incializar} que ejecuta el siguiente
 * código: {@code
 * 		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
 * }
 * 
 * @see ControlJuego
 * @author Javier Fuentes
*  @since 1.0
 * @version 1.0
 */
public class VentanaPrincipal {
	// La ventana principal, en este caso, guarda todos los componentes:
	/** Frame del buscaminas donde se añadirán el resto de componentes */
	JFrame ventana;
	/** Panel que guardará {@link pantallaMinas} */
	JPanel panelMinas;
	/** Panel que guardará {@link botonEmpezar} */
	JPanel panelEmpezar;
	/** Panel que guardará {@link pantallaPuntuacion} */
	JPanel panelPuntuacion;
	/** Panle que guardará {@link panelesJuego} */
	JPanel panelJuego;
	/**
	 * Diálogo propio {@link DialogoConfiguracion} que dejará modificar la
	 * configuración
	 */
	DialogoConfiguracion dialogo = new DialogoConfiguracion(this);
	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros.
	/**
	 * Paneles que guardarán {@link botonesJuego} en sus respectivas posiciones y
	 * posteriormente pondrán un JLabel descubriendo habia en ese botón
	 */
	JPanel[][] panelesJuego;
	/** Array de botones que contendrá todos los botones del {@link panelesJuego} */
	JButton[][] botonesJuego;
	/** Correspondencia de colores para las minas */
	Color correspondenciaColores[] = { Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };
	/** Color de fondo */
	Color colorFondo = Color.decode("#FAFFFF");
	/** Botón que iniciará o reiniciará la partida */
	JButton botonEmpezar;
	/** Mostrará la puntuación que tiene el usuario */
	JTextField pantallaPuntuacion;
	/**
	 * Mostrará las minas que hay activas en el tablero, se inicializa con el número
	 * de minas que tiene la partida y cada vez que el usuario pone una bandera este
	 * contador decrementará
	 */
	JTextField pantallaMinas;
	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	/** Contendrá toda la lógica del buscaminas */
	ControlJuego juego;

	/**
	 * Constructor. Marca el tamaño, la posición y el cierre del frame, También
	 * llama al método que inicializa {@link juego}
	 */
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(0, 0, 400, 550);
		ventana.setLocationRelativeTo(null); // Para que aparezca en el centro de la pantalla
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crearControlJuego();
	}

	/**
	 * Método que nos dejará cambiar la configuracion por defecto del buscaminas. Si
	 * quiere jugar con la configuración predefinida se iniciará juego con un
	 * constructor vacío. Si se quiere jugar con una configuración personalizada se
	 * inicializará juego con el constructor parametrizado.
	 */
	public void crearControlJuego() {

		juego = null; // Se pone nulo para que funcionen correctamente el método.
		/** Variable que nos idicará si el usuario quiere cambiar la configuración */
		int cambiar;
		cambiar = JOptionPane.showConfirmDialog(ventana,
				"¿Quieres jugar con los valores por defecto de la configuración?", "Configuración",
				JOptionPane.YES_NO_OPTION);
		// Si selecciona NO aparecerá {@link dialogo} para configurar la partida
		if (JOptionPane.NO_OPTION == cambiar) {
			dialogo.setVisible(true);
		}
		// Si se sale de la configuración se iniciará un juego por defecto
		if (juego == null) {
			juego = new ControlJuego();
		}

	}

	/**
	 * Método que inicializa todos los componentes de la clase
	 */
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelMinas = new JPanel();
		panelMinas.setLayout(new GridLayout());
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout());
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout());
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(getJuego().getLadoTablero(), getJuego().getLadoTablero()));

		pantallaMinas = new JTextField(String.valueOf(juego.getMinasIniciales()));
		pantallaMinas.setEditable(false);
		pantallaMinas.setHorizontalAlignment(SwingConstants.CENTER);
		botonEmpezar = new JButton();
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes, colores y fuente:
		/** Fuente de las pantallas */
		Font fuente = new FontUIResource("", Font.BOLD, 20);

		panelMinas.setBorder(BorderFactory.createTitledBorder("Minas"));
		panelMinas.setBackground(colorFondo);
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelEmpezar.setBackground(colorFondo);
		panelPuntuacion.setBorder(BorderFactory.createTitledBorder("Puntuación"));
		panelPuntuacion.setBackground(colorFondo);
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));
		panelJuego.setBackground(colorFondo);

		pantallaMinas.setBackground(colorFondo);
		pantallaMinas.setFont(fuente);
		botonEmpezar.setIcon(new ImageIcon(getClass().getResource("img/caraFeliz.png")));
		pantallaPuntuacion.setBackground(colorFondo);
		pantallaPuntuacion.setFont(fuente);

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelMinas, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Paneles
		panelesJuego = new JPanel[getJuego().getLadoTablero()][getJuego().getLadoTablero()];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[getJuego().getLadoTablero()][getJuego().getLadoTablero()];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton();
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// Pantallas y Botón Empezar
		panelMinas.add(pantallaMinas);
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		// BotonEmpezar
		botonEmpezar.addActionListener((e) -> {
			reiniciar();
		});
		// Botones de jueego
		for (int i = 0; i < getJuego().getLadoTablero(); i++) {
			for (int j = 0; j < getJuego().getLadoTablero(); j++) {
				ActionBoton actionBoton = new ActionBoton(this, i, j);
				botonesJuego[i][j].addActionListener(actionBoton);
				botonesJuego[i][j].addMouseListener(actionBoton);
			}
		}
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda. Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto según
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : azul - 2 : verde - 3 : magenta - 4 ó más : rojo
	 * 
	 * En el caso de que se puse una mina se mostrará el icono de una mina en todas
	 * las posiciones donde las hubiera. Si se tenía puesta una bandera en un mina
	 * esta aparecerá también, pero como desactivada.
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		/** Botón que se va a abrir */
		JButton boton = (JButton) panelesJuego[i][j].getComponent(0);
		/** Indicará las minas que tiene alrededor una casilla o si en una bomba */
		int numMinas = getJuego().getMinasAlrededor(i, j);
		/** Label que se introducirá en el panel en el que se quita el botón */
		JLabel label = new JLabel();
		/** Icono que tiene el botón */
		Icon iconoBoton = boton.getIcon();
		// Si se ha pulsado una mina
		if (numMinas == ControlJuego.MINA) {
			// Si la mina tienen icono (la bandera)
			if (iconoBoton != null) {
				label.setIcon(new ImageIcon(getClass().getResource("img/minaDesactivada.png")));
			} else {
				label.setIcon(new ImageIcon(getClass().getResource("img/mina.png")));
			}
			label.setHorizontalAlignment(SwingConstants.CENTER);
			panelesJuego[i][j].removeAll();
			panelesJuego[i][j].add(label);
			panelesJuego[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Decorativo
		} else {
			// Si la no mina no tiene icono (la bandera)
			if (iconoBoton == null) {
				// Si tiene alguna mina alrededor
				if (numMinas != 0) {
					label.setText(String.valueOf(numMinas));
					label.setForeground(correspondenciaColores[Integer.parseInt(label.getText())]);
				}
				panelesJuego[i][j].setBackground(colorFondo); // Decorativo
				actualizarPuntuacion();
				label.setHorizontalAlignment(SwingConstants.CENTER);
				panelesJuego[i][j].removeAll();
				panelesJuego[i][j].add(label);
			}
		}
		refrescarPantalla();
	}

	/**
	 * Muestra la localización de todas las minas del juego Muestra una ventana que
	 * indica el fin del juego Si se ha perdidio cambiará la cara feliz del
	 * {@link botonEmpezar} por una triste
	 * 
	 * @param porExplosion : Un booleano que indica si es final del juego porque ha
	 *                     explotado una mina (true) o bien porque hemos desactivado
	 *                     todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {

		mostrarMinas();

		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego.length; j++) {
				botonesJuego[i][j].setEnabled(false);
			}
		}
		/** Panel que aparecerá al acabar la partida */
		new JOptionPane();
		// Si ha explotado una mina
		if (porExplosion) {
			botonEmpezar.setIcon(new ImageIcon(getClass().getResource("img/caraTriste.png")));
			JOptionPane.showMessageDialog(ventana, "¡HAS PERDIDIO!\nHas explotado una bomba...", "Fin del Juego",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(ventana, "¡ENORABUENA!\nHas ganado la partida", "Fin del Juego",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Método que mediante un ArrayList de enteros que guarda las posiciones donde
	 * se encuentran las minas calcula su lugar en el tablero y las muestra.
	 */
	public void mostrarMinas() {
		/** Posciones donde están las minas */
		ArrayList<Integer> minas = juego.getPosicionesMinas();
		/** Columna donde se encuentra */
		int i;
		/** Fila donde se encuentra */
		int j;
		for (int k = 0; k < minas.size(); k++) {
			i = minas.get(k) % (juego.getLadoTablero());
			j = minas.get(k) / (juego.getLadoTablero());
			mostrarNumMinasAlrededor(i, j);
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(getJuego().getPuntuacion()));
	}

	/**
	 * Método que muestra la minas que faltan por desactivar por pantalla.
	 * 
	 * @param minas nuevo entero que mostrará
	 */
	public void actualizarMinas(int minas) {
		pantallaMinas.setText(String.valueOf(minas));
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método que se utilizará para reiniciar la partida.
	 */
	public void reiniciar() {
		ventana.getContentPane().removeAll();
		crearControlJuego();
		inicializarComponentes();
		inicializarListeners();
		refrescarPantalla();
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}

	/**
	 * Devuelve las minas que quedan por desactivar
	 * @return un entero con el número de minas que quedan
	 */
	public int getMinas() {
		return Integer.parseInt(pantallaMinas.getText());
	}

}
