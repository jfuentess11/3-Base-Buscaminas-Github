package buscaminas;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.GridLayout;

/**
 * Dialogo que aparacerá al inicio del juego si quieres cambiar la
 * configuración. Permite cambiar el tamaño del tablero y el número de minas
 * iniciales ya que modifica los atributos de la clase {@link ControlJuego}
 * 
 * @see ControlJuego
 * @see VentanaPrincipal
 * @author Javier Fuentes
 * @since 1.0
 * @version 1.0
 */
public class DialogoConfiguracion extends JDialog {

    private static final long serialVersionUID = 1L;

    // Componentes que tedrá el diálogo

    /** {@link VentanaPrincipal} del buscaminas */
    VentanaPrincipal ventana;
    JTextField tituloPrincipal;
    JTextField tituloTablero;
    JTextField tituloMinas;
    /** Guardará el tamaño deseado del tablero */
    JFormattedTextField tamanoTablero;
    /** Guardará el número de minas iniciales que se desee */
    JFormattedTextField numMinas;
    /** Botón que al pulsarlo se creará la partida con los datos deseados */
    JButton botonAceptar;
    /**
     * Guardará el tamaño con el que se inicia el tablero, se inicializa con el
     * valor por defecto, pero luego guardará el valor introdocido por el usuario
     * para jugar más partidas
     */
    int tamano = 10;
    /**
     * Guardará el número de minas con las que se inicia, se inicializa con el valor
     * por defecto, pero luego guardará el valor introdocido por el usuario para
     * jugar más partidas
     */
    int minas = 20;

    /**
     * Constructor. Marca el tamaño, la posición y el cierre del diálogo.
     * 
     * @param ventana recibe la ventana principal del buscaminas para poder acceder
     *                a los atributos de {@link ControlJuego}
     */
    public DialogoConfiguracion(VentanaPrincipal ventana) {
        super();
        setModal(true);
        setBounds(0, 0, 300, 400);
        setLocationRelativeTo(ventana.ventana); // Se coloca en el centro del frame de la ventana que recibe
        this.ventana = ventana;
        iniciarComponentes();
        iniciarListener();
    }

    /**
     * Inicia los componentes del diálogo
     */
    public void iniciarComponentes() {
        this.setLayout(new GridLayout(6, 1));

        // JTextField tituloPrincipal
        tituloPrincipal = new JTextField("Configuración de partida");
        tituloPrincipal.setEditable(false);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 20));
        tituloPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloPrincipal);

        // JTextField tituloTablero
        tituloTablero = new JTextField("Tamaño del tablero");
        tituloTablero.setEditable(false);
        tituloTablero.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloTablero);

        // JTextField tamanoTablero
        tamanoTablero = new JFormattedTextField(tamano);
        tamanoTablero.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tamanoTablero);

        // JTextField tituloMinas
        tituloMinas = new JTextField("Minas totales");
        tituloMinas.setEditable(false);
        tituloMinas.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloMinas);

        // JTextField numMinas
        numMinas = new JFormattedTextField(minas);
        numMinas.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(numMinas);

        // JButton botonAceptar
        botonAceptar = new JButton("Aceptar");
        this.add(botonAceptar);

    }

    /**
     * Inicializa el listener del {@link botonAceptar}
     */
    public void iniciarListener() {

        botonAceptar.addActionListener((e) -> {

            tamano = (Integer) tamanoTablero.getValue();
            minas = (Integer) numMinas.getValue();

            // Si no cambia la configuración se jugará con la configuración por defecto
            if (tamano == 10 && minas == 20) {
                JOptionPane.showMessageDialog(this, "Vas a jugar con la configuración por defecto", "Información",
                        JOptionPane.CLOSED_OPTION);
            }

            // Si se quieren poner más minas que casillas tiene el buscaminas saltará este
            // mensaje y tendrá que volver a intoducir los datos
            if (tamano * tamano < minas) {
                JOptionPane.showMessageDialog(this, "Hay más minas que casillas en el tablero, prueba con otros datos",
                        "Error", JOptionPane.CLOSED_OPTION);
            }

            // Inicializa el atributo juego de la ventana con los parámetros introducidos
            ventana.juego = new ControlJuego(minas, tamano);
            this.dispose();
        });
    }
}
