package logica;

import modelo.Asociado;
import modelo.Sucursal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import org.junit.Test;



/**
 * Pruebas de la clase Cooperativa.
 */
public class CooperativaTest {
     private Cooperativa crearSucursal(){
     Cooperativa c = new Cooperativa ("Coopetica");
     c.registrarSucursal(new Sucursal("S01","Centro"));
     return c;
     }

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
    @Test
    public void registrarYBuscarAsociado(){
    Cooperativa c = crearSucursal();
    Sucursal s = c.buscarSucursal("S01");
    c.registrarAsociado(new Asociado("1-111-1111", "Ana Mora", "A001", s));
    assertNotNull(c.buscarAsociado("1-111-1111"));
    
    }
    @Test
    public void cedulaDuplicadaFalla() {
        Cooperativa c = crearSucursal();
        Sucursal s = c.buscarSucursal("S01");
        c.registrarAsociado(new Asociado("1-1111-1111", "Ana Mora", "A001", s));
        Asociado repetido = new Asociado("1-1111-1111", "Otra", "A002", s);
        assertThrows(IllegalArgumentException.class, () -> c.registrarAsociado(repetido));
    
}
     @Test
    public void sucursalNoRegistradaFalla() {
        Cooperativa c = crearSucursal();
        Asociado a = new Asociado("2-2222-2222", "Luis Rojas", "A003",
                new Sucursal("S99", "Fantasma"));
        assertThrows(IllegalArgumentException.class, () -> c.registrarAsociado(a));
    }
    
    @Test
    public void depositarAporteActualizaElSaldo() {
        Cooperativa c = crearSucursal();
        Sucursal s = c.buscarSucursal("S01");
        c.registrarAsociado(new Asociado("1-1111-1111", "Ana Mora", "A001", s));
        c.depositarAporte("1-1111-1111", 3000);
        assertEquals(3000, c.buscarAsociado("1-1111-1111").getSaldoAportes(), 0.001);
    }

    @Test
    public void aporteAUnAsociadoInexistenteFalla() {
        Cooperativa c = crearSucursal();
        assertThrows(IllegalArgumentException.class, () -> c.depositarAporte("9-9999-9999", 100));
    }
}