package presentacion;

import datos.GestorPersistencia;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import logica.Cooperativa;

/**
 * Ventana principal del sistema. Desde aquí se abren las ventanas de cada
 * módulo. Guarda los datos al cerrar.
 */
public class VentanaPrincipal extends JFrame {

    private final Cooperativa cooperativa;
    private JLabel lblResumen;

    /**
     * Crea la ventana principal.
     *
     * @param cooperativa cooperativa con la información cargada
     */
    public VentanaPrincipal(Cooperativa cooperativa) {
        super("CoopeTica R.L. - Sistema de gestión");
        this.cooperativa = cooperativa;
        construirInterfaz();
    }

    /**
     * Obtiene la cooperativa con la que trabaja la ventana.
     *
     * @return la cooperativa
     */
    public Cooperativa getCooperativa() {
        return cooperativa;
    }

    /**
     * Actualiza el texto que muestra la cantidad de sucursales y asociados.
     */
    public void actualizarResumen() {
        lblResumen.setText("Sucursales: " + cooperativa.listarSucursales().size()
                + "   |   Asociados: " + cooperativa.listarAsociados().size());
    }

    private void construirInterfaz() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarYSalir();
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel titulo = new JLabel(cooperativa.getNombre(), SwingConstants.CENTER);
        lblResumen = new JLabel("", SwingConstants.CENTER);

        JButton btnGuardar = new JButton("Guardar datos");
    
        JButton btnSalir = new JButton("Guardar y salir");
        btnSalir.addActionListener(e -> guardarYSalir());
        btnGuardar.addActionListener(e -> {
        if (guardar()) {
        JOptionPane.showMessageDialog(this, "Los datos se guardaron correctamente.",
                "Guardar datos", JOptionPane.INFORMATION_MESSAGE);
    }
});
        panel.add(titulo);
        panel.add(lblResumen);
        panel.add(btnGuardar);
        panel.add(btnSalir);

        setContentPane(panel);
        actualizarResumen();
        pack();
        setLocationRelativeTo(null);
    }

    private boolean guardar() {
        try {
            GestorPersistencia.getInstancia().guardarTodo(cooperativa);
            return true;
        } catch (IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudieron guardar los datos:\n" + ex.getMessage(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void guardarYSalir() {
        if (guardar()) {
            dispose();
            System.exit(0);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea salir sin guardar?", "Salir",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
