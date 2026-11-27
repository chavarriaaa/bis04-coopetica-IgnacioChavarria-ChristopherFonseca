package datos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import logica.Cooperativa;
import modelo.Asociado;
import modelo.Sucursal;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas de la clase GestorPersistencia. Usan una carpeta temporal
 * para no modificar los datos reales.
 */
public class GestorPersistenciaTest {

    @Rule
    public TemporaryFolder temporal = new TemporaryFolder();

    private GestorPersistencia gestor;

    @Before
    public void prepararCarpeta() {
        gestor = GestorPersistencia.getInstancia();
        gestor.usarCarpeta(temporal.getRoot().getPath());
    }

    @After
    public void restaurarCarpeta() {
        gestor.usarCarpeta("datos");
    }

    @Test
    public void guardarYCargarConservaLosDatos() throws IOException {
        Cooperativa c = new Cooperativa("CoopeTica R.L.");
        c.registrarSucursal(new Sucursal("S01", "Centro"));
        Sucursal s = c.buscarSucursal("S01");
        c.registrarAsociado(new Asociado("1-1111-1111", "Ana Mora", "A001", s));
        c.depositarAporte("1-1111-1111", 2500);

        gestor.guardarTodo(c);
        Cooperativa cargada = gestor.cargarTodo();

        assertEquals(1, cargada.listarSucursales().size());
        assertEquals("Ana Mora", cargada.buscarAsociado("1-1111-1111").getNombre());
        assertEquals(2500, cargada.buscarAsociado("1-1111-1111").getSaldoAportes(), 0.001);
    }

    @Test
    public void cargarSinArchivosCreaLosArchivosYDevuelveCooperativaVacia() throws IOException {
        Cooperativa cargada = gestor.cargarTodo();
        assertTrue(cargada.listarSucursales().isEmpty());
        assertTrue(new File(temporal.getRoot(), "asociados.txt").exists());
    }

    @Test
    public void saldoInvalidoEnElArchivoLanzaExcepcion() throws IOException {
        Files.writeString(new File(temporal.getRoot(), "sucursales.txt").toPath(), "S01;Centro\n");
        Files.writeString(new File(temporal.getRoot(), "asociados.txt").toPath(),
                "1-1111-1111;Ana Mora;A001;S01;abc\n");
        assertThrows(IOException.class, () -> gestor.cargarTodo());
    }

    @Test
    public void nombreConPuntoYComaNoSeGuarda() {
        Cooperativa c = new Cooperativa("CoopeTica R.L.");
        c.registrarSucursal(new Sucursal("S01", "Centro;Norte"));
        assertThrows(IllegalArgumentException.class, () -> gestor.guardarTodo(c));
    }
}