package modelo;

import java.util.Objects;

/**
 * Representa una cuenta bancaria abstracta asociada a un {@link Asociado}.
 */
public abstract class Cuenta {

    private final String numeroCuenta;
    private final Asociado titular;
    private double saldo;

    /**
     * Crea una cuenta con número, titular y saldo inicial validados.
     *
     * @param numeroCuenta identificador único de la cuenta
     * @param titular asociado dueño de la cuenta
     * @param saldoInicial saldo inicial de la cuenta
     * @throws IllegalArgumentException si los datos son inválidos o el saldo es negativo
     */
    public Cuenta(String numeroCuenta, Asociado titular, double saldoInicial) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException("El número de cuenta es obligatorio.");
        }
        if (titular == null) {
            throw new IllegalArgumentException("El titular de la cuenta es obligatorio.");
        }
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.numeroCuenta = numeroCuenta.trim();
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    /**
     * Obtiene el número de cuenta.
     *
     * @return número de cuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * Obtiene el asociado titular de la cuenta.
     *
     * @return asociado titular
     */
    public Asociado getTitular() {
        return titular;
    }

    /**
     * Consulta el saldo actual de la cuenta.
     *
     * @return saldo disponible
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Actualiza el saldo interno desde las subclases.
     *
     * @param nuevoSaldo nuevo saldo a asignar
     * @throws IllegalArgumentException si el saldo es negativo
     */
    protected void setSaldo(double nuevoSaldo) {
        if (nuevoSaldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.saldo = nuevoSaldo;
    }

    /**
     * Deposita un monto positivo en la cuenta.
     *
     * @param monto monto a depositar
     * @throws IllegalArgumentException si el monto es menor o igual a cero
     */
    public void depositar(double monto) {
        validarMontoPositivo(monto, "depositar");
        this.saldo += monto;
    }

    /**
     * Retira un monto de la cuenta si hay fondos suficientes.
     *
     * @param monto monto a retirar
     * @throws IllegalArgumentException si el monto no es positivo o supera el saldo disponible
     */
    public void retirar(double monto) {
        validarMontoPositivo(monto, "retirar");
        if (monto > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para el retiro.");
        }
        this.saldo -= monto;
    }

    /**
     * Aplica las comisiones correspondientes según el tipo de cuenta.
     */
    public abstract void cobrarComisiones();

    /**
     * Valida que un monto sea estrictamente mayor que cero.
     *
     * @param monto monto a validar
     * @param operacion nombre de la operación
     * @throws IllegalArgumentException si el monto es menor o igual a cero
     */
    protected void validarMontoPositivo(double monto, String operacion) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto para " + operacion + " debe ser mayor que cero.");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", titular=" + titular.getCedula() +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cuenta cuenta)) return false;
        return Objects.equals(numeroCuenta, cuenta.numeroCuenta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroCuenta);
    }
}