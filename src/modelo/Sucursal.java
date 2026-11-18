
package modelo;

public class Sucursal {
    
  private final String codigo;
    private String nombre;

    /**
     * Crea una sucursal.
     *
     * @param codigo código único de la sucursal; no puede estar vacío
     * @param nombre nombre de la sucursal; no puede estar vacío
     * @throws IllegalArgumentException si el código o el nombre están vacíos
     */
    public Sucursal(String codigo, String nombre) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
    }

    /**
     * Obtiene el código de la sucursal.
     *
     * @return el código
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre de la sucursal.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre de la sucursal.
     *
     * @param nombre nuevo nombre; no puede estar vacío
     * @throws IllegalArgumentException si el nombre está vacío
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Devuelve una descripción de la sucursal.
     *
     * @return texto con código y nombre
     */
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
            
}
