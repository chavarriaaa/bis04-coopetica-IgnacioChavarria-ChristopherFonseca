package logica;

import modelo.Sucursal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import org.junit.Test;

/**
 * Pruebas de la clase Cooperativa.
 */
public class CooperativaTest {

    @Test
    public void registrarYBuscarSucursal() {
        Cooperativa c = new Cooperativa("CoopeTica");
        c.registrarSucursal(new Sucursal("S01", "Centro"));
        assertNotNull(c.buscarSucursal("S01"));
        assertEquals(1, c.listarSucursales().size());
    }

    @Test
    public void codigoDeSucursalDuplicadoFalla() {
        Cooperativa c = new Cooperativa("CoopeTica");
        c.registrarSucursal(new Sucursal("S01", "Centro"));
        Sucursal repetida = new Sucursal("S01", "Otra");
        assertThrows(IllegalArgumentException.class, () -> c.registrarSucursal(repetida));
    }
}