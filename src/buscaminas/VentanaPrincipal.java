package buscaminas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
 * Ventana principal del Buscaminas
 * 
 * @author Javier Fuentes
 */
public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;
	DialogoConfiguracion dialogo = new DialogoConfiguracion(this);

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(600, 100, 600, 800);
		// ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Para que se inicie en
		// pantalla completa
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crearControlJuego();

	}

	/**
	 * Método que nos dejará cambiar la configuracion por defecto del buscaminas. Si
	 * quiere juagr con la configuración predefinida se iniciará juego con un
	 * contructor vacio. Si se quiere jugar con una configuración personalizada se
	 * inicializará juego con el constructor parametrizado.
	 */
	public void crearControlJuego() {

		juego = null; // Se pone nulo para que funcionen correctamente el método.

		int cambiar = JOptionPane.showConfirmDialog(ventana,
				"¿Quieres jugar con los valores por defecto de la configuración?", "Configuración",
				JOptionPane.YES_NO_OPTION);

		if (JOptionPane.NO_OPTION == cambiar) {
			dialogo.setVisible(true);
		}
		// Si se sale de la configuración se iniciará un juego por defecto
		if (juego == null) {
			juego = new ControlJuego();
		}

	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelImagen.setBackground(Color.decode("#FAFFFF"));
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelEmpezar.setBackground(Color.decode("#FAFFFF"));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelPuntuacion.setBackground(Color.decode("#FAFFFF"));
		panelJuego = new JPanel();
		panelJuego.setBackground(Color.decode("#FAFFFF"));
		panelJuego.setLayout(new GridLayout(getJuego().getLadoTablero(), getJuego().getLadoTablero()));

		botonEmpezar = new JButton();
		botonEmpezar.setIcon(new ImageIcon("img/caraFeliz.png"));
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setBackground(Color.decode("#FAFFFF"));
		pantallaPuntuacion.setFont(new FontUIResource("", Font.BOLD, 20));
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.decode("#949BBA"), 1));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.decode("#949BBA"), 1));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
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

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {

		botonEmpezar.addActionListener((e) -> {
			reiniciar();

		});

		for (int i = 0; i < getJuego().getLadoTablero(); i++) {
			for (int j = 0; j < getJuego().getLadoTablero(); j++) {
				botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
			}
		}
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca el
	 * botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto según
	 * la siguiente correspondecia (consultar la variable correspondeciaColor): - 0
	 * : negro - 1 : azul - 2 : verde - 3 : magenta - 4 ó más : rojo
	 * 
	 * @param i: posición vertical de la celda.
	 * @param j: posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		panelesJuego[i][j].removeAll();
		int numMinas = getJuego().getMinasAlrededor(i, j);
		JLabel label = new JLabel();
		if (numMinas == ControlJuego.MINA) {
			label.setIcon(new ImageIcon("img/mina.png"));
		} else {
			if (numMinas != 0) {
				label.setText(String.valueOf(numMinas));
				label.setForeground(correspondenciaColores[Integer.parseInt(label.getText())]);
			}
			panelesJuego[i][j].setBackground(Color.decode("#FAFFFF"));
			actualizarPuntuacion();

		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panelesJuego[i][j].add(label);
		refrescarPantalla();
	}

	/**
	 * Muestra una ventana que indica el fin del juego
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

		new JOptionPane();
		if (porExplosion) {
			botonEmpezar.setIcon(new ImageIcon("img/caraTriste.png"));
			JOptionPane.showMessageDialog(ventana, "¡HAS PERDIDIO!\nHas explotado una bomba...", "Fin del Juego",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(ventana, "¡ENORABUENA!\nHas ganado la partida", "Fin del Juego",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void mostrarMinas() {

		ArrayList<Integer> minas = juego.getPosicionesMinas();

		int x;
		int y;

		for (int i = 0; i < minas.size(); i++) {
			x = minas.get(i) % (juego.getLadoTablero());
			y = minas.get(i) / (juego.getLadoTablero());
			mostrarNumMinasAlrededor(x, y);
		}

	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(String.valueOf(getJuego().getPuntuacion()));
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

}
