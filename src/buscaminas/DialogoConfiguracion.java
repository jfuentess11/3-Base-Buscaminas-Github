package buscaminas;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.GridLayout;


/**
 * Dialogo que aparacerá al inicio del juego si quieres cambiar la configuración.
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
    JTextField tamanoTablero;
    JTextField numMinas;
    JButton botonAceptar;
    int tamano = 10;
    int minas = 20;
    /**
     * Constructor del Dialogo
     * @param ventana recibe la ventana principal del buscaminas
     */
    public DialogoConfiguracion(VentanaPrincipal ventana){
        super();
        setModal(true);
        setBounds(750,300, 300, 400);
        this.ventana = ventana;
        iniciarComponentes();
        iniciarListener();
    }

    /**
     * Inicia los componentes del diálogo
     */
    public void iniciarComponentes(){
        this.setLayout(new GridLayout(6,1));

        // JTextField tituloPrincipal
        tituloPrincipal = new JTextField("Configuración de partida");
        tituloPrincipal.setEditable(false);
        tituloPrincipal.setFont(new Font("Arial",Font.BOLD,20));
        tituloPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloPrincipal);

        // JTextField tituloTablero
        tituloTablero = new JTextField("Tamaño del tablero");
        tituloTablero.setEditable(false);
        tituloTablero.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloTablero);

        // JTextField tamanoTablero
        tamanoTablero = new JTextField(String.valueOf(tamano));
        tamanoTablero.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tamanoTablero);

        // JTextField tituloMinas
        tituloMinas = new JTextField("Minas totales");
        tituloMinas.setEditable(false);
        tituloMinas.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(tituloMinas);

        // JTextField numMinas
        numMinas = new JTextField(String.valueOf(minas));
        numMinas.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(numMinas);

        // JButton botonAceptar
        botonAceptar = new JButton("Aceptar");
        this.add(botonAceptar);

    }

    /**
     * Inicializa el listener del botonAceptar
     */
    public void iniciarListener(){

        botonAceptar.addActionListener((e)->{

            try {
                tamano = Integer.parseInt(this.tamanoTablero.getText());
                minas = Integer.parseInt(this.numMinas.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Has itroducido valores incorrectos, jugaras con la configuración por defecto", "Error", JOptionPane.CLOSED_OPTION);
                tamano = 10;
                minas = 20;
            }

            // Inicializa el atributo juego de la ventana con los parámetros introducidos
            ventana.juego = new ControlJuego(minas, tamano);
            this.dispose();
        });
    }
    
}
