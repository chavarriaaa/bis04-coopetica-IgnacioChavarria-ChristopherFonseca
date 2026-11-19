
package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;


public class AsociadoTest {
    
    private Asociado crearAsociado(){
    return new Asociado("119830452", "Ana Mora", "A001", new Sucursal("S01","Central"));
    
    }
    
    @Test
    public void depositarAporteSumaALSaldo(){
    Asociado a = crearAsociado();
    a.depositarAporte(5000);
    assertEquals(5000, a.getSaldoAportes(), 0.001);    
    }
    
    @Test
    public void depositarMontoNegativoFalla(){
    Asociado a = crearAsociado();
assertThrows(IllegalArgumentException.class, () -> a.depositarAporte(-100)); 
    }
    @Test
    public void retirarMasQueElSaldoFalla(){
    Asociado a = crearAsociado();
    a.depositarAporte(1000);
    assertThrows(IllegalArgumentException.class, () -> a.retirarAporte(1500));
    }
    @Test
    public void retiroValidoRestaElsaldo(){
    Asociado a = crearAsociado();
    a.depositarAporte(1000);
    a.retirarAporte(700);
    assertEquals(300, a.getSaldoAportes(), 0.001);
   
    }
       @Test
       public void AsociadosConMismaCedula(){
       Sucursal s = new Sucursal("S01", "Central");
       Asociado a = new Asociado ("1-1111-1111","Ana Mora","A001", s);
       Asociado b = new Asociado ("1-1111-1111","Felipe Rodriguez","A002", s);  
       assertEquals(a,b);

}
           
}
