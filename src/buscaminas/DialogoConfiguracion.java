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
 * configuración.
 * 
 * @author Javier Fuentes
 */
public class DialogoConfiguracion extends JDialog {

    private static final long serialVersionUID = 1L;

    // Componentes que tedrá el diálogo
    VentanaPrincipal ventana;
    JTextField tituloPrincipal;
    JTextField tituloTablero;
    JTextField tituloMinas;
    JFormattedTextField tamanoTablero;
    JFormattedTextField numMinas = new JFormattedTextField();
    JButton botonAceptar;
    int tamano = 10;
    int minas = 20;

    /**
     * Constructor del Dialogo
     * 
     * @param ventana recibe la ventana principal del buscaminas
     */
    public DialogoConfiguracion(VentanaPrincipal ventana) {
        super();
        setModal(true);
        setBounds(0, 0, 300, 400);
        setLocationRelativeTo(ventana.ventana);
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
     * Inicializa el listener del botonAceptar
     */
    public void iniciarListener() {

        botonAceptar.addActionListener((e) -> {

            tamano = (Integer) tamanoTablero.getValue();
            minas = (Integer) numMinas.getValue();

            if (tamano == 10 && minas ==20) {
                JOptionPane.showMessageDialog(this, "Vas a jugar con la configuración por defecto",
                        "Información", JOptionPane.CLOSED_OPTION);
            }

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
