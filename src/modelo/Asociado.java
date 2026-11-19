
package modelo;

import java.util.Objects;

/**
 * Se represenra un asociado de Coopetica
 * 
 * La cedula y el numero de asociado no cambian una vez creado el registro 
 * 
*/
public class Asociado {
    private final String cedula;
    private String nombre;
    private final String numeroAsociado;
    private final Sucursal sucursal;
    private double saldoAportes;

    /**
     * Crea un asociado nuevo con saldo de aportes en cero.
     *
     * @param cedula cédula del asociado; no puede estar vacía
     * @param nombre nombre completo; no puede estar vacío
     * @param numeroAsociado número de asociado; no puede estar vacío
     * @param sucursal sucursal a la que pertenece; no puede ser nula
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public Asociado(String cedula, String nombre, String numeroAsociado, Sucursal sucursal) {
        this(cedula, nombre, numeroAsociado, sucursal, 0);
    }

    /**
     * Crea un asociado con un saldo de aportes inicial. Se usa al cargar
     * los datos guardados en archivo.
     *
     * @param cedula cédula del asociado; no puede estar vacía
     * @param nombre nombre completo; no puede estar vacío
     * @param numeroAsociado número de asociado; no puede estar vacío
     * @param sucursal sucursal a la que pertenece; no puede ser nula
     * @param saldoAportes saldo inicial; no puede ser negativo
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public Asociado(String cedula, String nombre, String numeroAsociado,
                    Sucursal sucursal, double saldoAportes) {
        if (cedula == null || cedula.isBlank()) {
            throw new IllegalArgumentException("La cédula es obligatoria.");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (numeroAsociado == null || numeroAsociado.isBlank()) {
            throw new IllegalArgumentException("El número de asociado es obligatorio.");
        }
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal es obligatoria.");
        }
        if (saldoAportes < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.cedula = cedula.trim();
        this.nombre = nombre.trim();
        this.numeroAsociado = numeroAsociado.trim();
        this.sucursal = sucursal;
        this.saldoAportes = saldoAportes;
    }

    /**
     * Obtiene la cédula.
     *
     * @return la cédula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Obtiene el nombre.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del asociado.
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
     * Obtiene el número de asociado.
     *
     * @return el número de asociado
     */
    public String getNumeroAsociado() {
        return numeroAsociado;
    }

    /**
     * Obtiene la sucursal del asociado.
     *
     * @return la sucursal
     */
    public Sucursal getSucursal() {
        return sucursal;
    }

    /**
     * Obtiene el saldo de aportes.
     *
     * @return el saldo de aportes
     */
    public double getSaldoAportes() {
        return saldoAportes;
    }

    /**
     * Deposita un aporte.
     *
     * @param monto monto a depositar; debe ser positivo
     * @throws IllegalArgumentException si el monto es cero o negativo
     */
    public void depositarAporte(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo.");
        }
        saldoAportes += monto;
    }

    /**
     * Retira un aporte.
     *
     * @param monto monto a retirar; debe ser positivo y no mayor al saldo
     * @throws IllegalArgumentException si el monto es cero, negativo o
     *         mayor al saldo de aportes
     */
    public void retirarAporte(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo.");
        }
        if (monto > saldoAportes) {
            throw new IllegalArgumentException("El saldo de aportes es insuficiente.");
        }
        saldoAportes -= monto;
    }

    /**
     * Dos asociados son iguales si tienen la misma cédula.
     *
     * @param o objeto a comparar
     * @return true si es un asociado con la misma cédula
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asociado)) {
            return false;
        }
        Asociado otro = (Asociado) o;
        return cedula.equals(otro.cedula);
    }

    /**
     * Calcula el código hash a partir de la cédula, de forma coherente
     * con {@code equals}.
     *
     * @return el código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    /**
     * Devuelve una descripción del asociado.
     *
     * @return texto con cédula, nombre, número, sucursal y saldo
     */
    @Override
    public String toString() {
        return cedula + " - " + nombre + " (" + numeroAsociado + ") - "
                + sucursal.getNombre() + " - aportes: " + saldoAportes;
    }
}

