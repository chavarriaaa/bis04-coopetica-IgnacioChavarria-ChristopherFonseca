package presentacion;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import logica.Cooperativa;
import modelo.Asociado;

/**
 * Ventana para consultar el saldo de aportes de un asociado y para
 * depositar o retirar aportes.
 */
public class VentanaAportes extends JDialog {

    private final Cooperativa cooperativa;
    private final JTextField txtCedula = new JTextField(15);
    private final JTextField txtMonto = new JTextField(10);
    private final JLabel lblAsociado = new JLabel("Asociado: -");
    private final JLabel lblSaldo = new JLabel("Saldo de aportes: -");

    /**
     * Crea la ventana de aportes.
     *
     * @param principal ventana principal, sobre la que se centra
     * @param cooperativa cooperativa donde se registran los aportes
     */
    public VentanaAportes(VentanaPrincipal principal, Cooperativa cooperativa) {
        super(principal, "Aportes", true);
        this.cooperativa = cooperativa;
        construirInterfaz(principal);
    }

    private void construirInterfaz(VentanaPrincipal principal) {
        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Cédula del asociado:"));
        formulario.add(txtCedula);
        formulario.add(new JLabel("Monto:"));
        formulario.add(txtMonto);

        JButton btnConsultar = new JButton("Consultar saldo");
        btnConsultar.addActionListener(e -> consultar());
        JButton btnDepositar = new JButton("Depositar");
        btnDepositar.addActionListener(e -> depositar());
        JButton btnRetirar = new JButton("Retirar");
        btnRetirar.addActionListener(e -> retirar());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botones.add(btnConsultar);
        botones.add(btnDepositar);
        botones.add(btnRetirar);

        JPanel resultado = new JPanel(new GridLayout(0, 1, 4, 4));
        resultado.add(lblAsociado);
        resultado.add(lblSaldo);

        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.add(formulario, BorderLayout.NORTH);
        centro.add(botones, BorderLayout.CENTER);
        centro.add(resultado, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.add(btnCerrar);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(new EmptyBorder(15, 15, 15, 15));
        contenido.add(centro, BorderLayout.CENTER);
        contenido.add(pie, BorderLayout.SOUTH);
        setContentPane(contenido);

        pack();
        setLocationRelativeTo(principal);
    }

    private void consultar() {
        try {
            mostrar(obtenerAsociado());
        } catch (IllegalArgumentException ex) {
            avisar(ex.getMessage());
        }
    }

    private void depositar() {
        try {
            Asociado a = obtenerAsociado();
            cooperativa.depositarAporte(a.getCedula(), leerMonto());
            mostrar(a);
            txtMonto.setText("");
            JOptionPane.showMessageDialog(this, "El depósito se registró correctamente.",
                    "Aportes", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            avisar(ex.getMessage());
        }
    }

    private void retirar() {
        try {
            Asociado a = obtenerAsociado();
            cooperativa.retirarAporte(a.getCedula(), leerMonto());
            mostrar(a);
            txtMonto.setText("");
            JOptionPane.showMessageDialog(this, "El retiro se registró correctamente.",
                    "Aportes", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException ex) {
            avisar(ex.getMessage());
        }
    }

    private Asociado obtenerAsociado() {
        String cedula = txtCedula.getText().trim();
        if (cedula.isEmpty()) {
            throw new IllegalArgumentException("Escriba la cédula del asociado.");
        }
        Asociado a = cooperativa.buscarAsociado(cedula);
        if (a == null) {
            throw new IllegalArgumentException("No existe un asociado con esa cédula.");
        }
        return a;
    }

    private double leerMonto() {
        String texto = txtMonto.getText().trim();
        if (texto.isEmpty()) {
            throw new IllegalArgumentException("Escriba el monto.");
        }
        try {
            double monto = Double.parseDouble(texto);
            if (Double.isNaN(monto) || Double.isInfinite(monto)) {
                throw new IllegalArgumentException("El monto debe ser un número válido.");
            }
            return monto;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El monto debe ser un número.");
        }
    }

    private void mostrar(Asociado a) {
        lblAsociado.setText("Asociado: " + a.getNombre() + " (" + a.getNumeroAsociado() + ")");
        lblSaldo.setText("Saldo de aportes: " + String.format("%,.2f", a.getSaldoAportes()));
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aportes", JOptionPane.WARNING_MESSAGE);
    }
}
