package logica;

import java.util.ArrayList;
import modelo.Sucursal;
import java.util.Iterator; 
import modelo.Asociado;
/**
 * Coordina las operaciones de CoopeTica R.L. Mantiene en memoria las
 * listas de sucursales y asociados.
 */
public class Cooperativa {

    private final String nombre;
    private final ArrayList<Sucursal> sucursales;
    private final ArrayList<Asociado> asociados;
    /**
     * Crea una cooperativa sin sucursales.
     *
     * @param nombre nombre de la cooperativa; no puede estar vacío
     * @throws IllegalArgumentException si el nombre está vacío
     */
    public Cooperativa(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
        this.sucursales = new ArrayList<>();
        this.asociados = new ArrayList<>();        
    }

    /**
     * Obtiene el nombre de la cooperativa.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Registra una sucursal.
     *
     * @param s sucursal a registrar; no puede ser nula
     * @throws IllegalArgumentException si es nula o si ya existe una
     *         sucursal con el mismo código
     */
    public void registrarSucursal(Sucursal s) {
        if (s == null) {
            throw new IllegalArgumentException("La sucursal es obligatoria.");
        }
        if (buscarSucursal(s.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una sucursal con ese código.");
        }
        sucursales.add(s);
    }

    /**
     * Busca una sucursal por su código.
     *
     * @param codigo código a buscar
     * @return la sucursal, o null si no existe
     */
    public Sucursal buscarSucursal(String codigo) {
        for (Sucursal s : sucursales) {
            if (s.getCodigo().equals(codigo)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Lista las sucursales registradas.
     *
     * @return una copia de la lista de sucursales
     */
    public ArrayList<Sucursal> listarSucursales() {
        return new ArrayList<>(sucursales);
    }
       /**
     * Registra un asociado. Su sucursal debe estar registrada y no puede
     * haber otro asociado con la misma cédula.
     *
     * @param a asociado a registrar; no puede ser nulo
     * @throws IllegalArgumentException si es nulo, si su sucursal no está
     *         registrada o si la cédula ya existe
     */
    public void registrarAsociado(Asociado a) {
        if (a == null) {
            throw new IllegalArgumentException("El asociado es obligatorio.");
        }
        if (buscarSucursal(a.getSucursal().getCodigo()) == null) {
            throw new IllegalArgumentException("La sucursal del asociado no está registrada.");
        }
        if (buscarAsociado(a.getCedula()) != null) {
            throw new IllegalArgumentException("Ya existe un asociado con esa cédula.");
        }
        asociados.add(a);
    }
    /**
     * Busca un asociado por su cédula.
     *
     * @param cedula cédula a buscar
     * @return el asociado, o null si no existe
     */
    public Asociado buscarAsociado(String cedula) {
        Iterator<Asociado> it = asociados.iterator();
        while (it.hasNext()) {
            Asociado a = it.next();
            if (a.getCedula().equals(cedula)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Lista todos los asociados.
     *
     * @return una copia de la lista de asociados
     */
    public ArrayList<Asociado> listarAsociados() {
        return new ArrayList<>(asociados);
    }
}
