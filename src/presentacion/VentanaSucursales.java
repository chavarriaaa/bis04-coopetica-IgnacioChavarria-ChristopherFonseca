package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import logica.Cooperativa;
import modelo.Sucursal;

/**
 * Ventana para registrar y listar las sucursales de la cooperativa.
 */
public class VentanaSucursales extends JDialog {

    private final Cooperativa cooperativa;
    private final VentanaPrincipal principal;
    private final JTextField txtCodigo = new JTextField(10);
    private final JTextField txtNombre = new JTextField(20);
    private final DefaultListModel<Sucursal> modeloLista = new DefaultListModel<>();

    /**
     * Crea la ventana de sucursales.
     *
     * @param principal ventana principal, que se actualiza al registrar
     * @param cooperativa cooperativa en la que se registran las sucursales
     */
    public VentanaSucursales(VentanaPrincipal principal, Cooperativa cooperativa) {
        super(principal, "Sucursales", true);
        this.principal = principal;
        this.cooperativa = cooperativa;
        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        formulario.add(new JLabel());
        formulario.add(btnRegistrar);

        JScrollPane lista = new JScrollPane(new JList<>(modeloLista));
        lista.setPreferredSize(new Dimension(380, 160));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.add(btnCerrar);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(new EmptyBorder(15, 15, 15, 15));
        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(lista, BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);
        setContentPane(contenido);

        cargarLista();
        pack();
        setLocationRelativeTo(principal);
    }

    private void registrar() {
        try {
            cooperativa.registrarSucursal(new Sucursal(txtCodigo.getText(), txtNombre.getText()));
            txtCodigo.setText("");
            txtNombre.setText("");
            cargarLista();
            principal.actualizarResumen();
            txtCodigo.requestFocus();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cargarLista() {
        modeloLista.clear();
        for (Sucursal s : cooperativa.listarSucursales()) {
            modeloLista.addElement(s);
        }
    }
}