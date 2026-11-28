package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import logica.Cooperativa;
import modelo.Asociado;
import modelo.Sucursal;

/**
 * Ventana para registrar, listar y buscar asociados de la cooperativa.
 */
public class VentanaAsociados extends JDialog {

    private final Cooperativa cooperativa;
    private final VentanaPrincipal principal;
    private final JTextField txtCedula = new JTextField(15);
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtNumero = new JTextField(10);
    private final JComboBox<Sucursal> cmbSucursal = new JComboBox<>();
    private final JTextField txtBuscar = new JTextField(12);
    private final DefaultListModel<Asociado> modeloLista = new DefaultListModel<>();

    /**
     * Crea la ventana de asociados.
     *
     * @param principal ventana principal, que se actualiza al registrar
     * @param cooperativa cooperativa en la que se registran los asociados
     */
    public VentanaAsociados(VentanaPrincipal principal, Cooperativa cooperativa) {
        super(principal, "Asociados", true);
        this.principal = principal;
        this.cooperativa = cooperativa;
        construirInterfaz();
    }

    private void construirInterfaz() {
        for (Sucursal s : cooperativa.listarSucursales()) {
            cmbSucursal.addItem(s);
        }

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Cédula:"));
        formulario.add(txtCedula);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Número de asociado:"));
        formulario.add(txtNumero);
        formulario.add(new JLabel("Sucursal:"));
        formulario.add(cmbSucursal);
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        formulario.add(new JLabel());
        formulario.add(btnRegistrar);

        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busqueda.add(new JLabel("Buscar por cédula:"));
        busqueda.add(txtBuscar);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        JButton btnTodos = new JButton("Mostrar todos");
        btnTodos.addActionListener(e -> mostrarTodos());
        busqueda.add(btnBuscar);
        busqueda.add(btnTodos);

        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.add(formulario, BorderLayout.NORTH);
        norte.add(busqueda, BorderLayout.SOUTH);

        JScrollPane lista = new JScrollPane(new JList<>(modeloLista));
        lista.setPreferredSize(new Dimension(520, 180));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.add(btnCerrar);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(new EmptyBorder(15, 15, 15, 15));
        contenido.add(norte, BorderLayout.NORTH);
        contenido.add(lista, BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);
        setContentPane(contenido);

        mostrarTodos();
        pack();
        setLocationRelativeTo(principal);
    }

    private void registrar() {
        Sucursal sucursal = (Sucursal) cmbSucursal.getSelectedItem();
        if (sucursal == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero registre al menos una sucursal.",
                    "Falta una sucursal", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            cooperativa.registrarAsociado(new Asociado(txtCedula.getText(),
                    txtNombre.getText(), txtNumero.getText(), sucursal));
            txtCedula.setText("");
            txtNombre.setText("");
            txtNumero.setText("");
            mostrarTodos();
            principal.actualizarResumen();
            txtCedula.requestFocus();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscar() {
        String cedula = txtBuscar.getText().trim();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escriba la cédula que desea buscar.",
                    "Buscar asociado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Asociado encontrado = cooperativa.buscarAsociado(cedula);
        if (encontrado == null) {
            JOptionPane.showMessageDialog(this, "No existe un asociado con esa cédula.",
                    "Buscar asociado", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modeloLista.clear();
        modeloLista.addElement(encontrado);
    }

    private void mostrarTodos() {
        modeloLista.clear();
        for (Asociado a : cooperativa.listarAsociados()) {
            modeloLista.addElement(a);
        }
    }
}