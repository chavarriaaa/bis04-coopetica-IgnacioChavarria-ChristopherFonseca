package presentacion;

import datos.GestorPersistencia;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import logica.Cooperativa;

/**
 * Punto de entrada del sistema CoopeTica R.L.
 */
public class MAIN {

    /**
     * Carga los datos guardados y abre la ventana principal.
     *
     * @param args argumentos de línea de comandos; no se usan
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Cooperativa cooperativa = GestorPersistencia.getInstancia().cargarTodo();
                new VentanaPrincipal(cooperativa).setVisible(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "No se pudieron cargar los datos:\n" + e.getMessage(),
                        "Error al abrir el sistema", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
