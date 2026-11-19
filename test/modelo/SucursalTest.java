
package modelo;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;

public class SucursalTest {
    
@Test
    public void crearSucursalValida(){
    
    Sucursal s = new Sucursal("S01", "Central");
    assertEquals("S01", s.getCodigo());
    assertEquals("Central", s.getNombre());   
    }
@Test
public void codigoLanzaException(){

assertThrows(IllegalArgumentException.class,() -> new Sucursal("","Centro"));
}

@Test
public void nombreVacioEnSetterLanzaException(){

Sucursal s = new Sucursal ("S01", "Central");
assertThrows(IllegalArgumentException.class, () -> s.setNombre(""));
}
    
}

