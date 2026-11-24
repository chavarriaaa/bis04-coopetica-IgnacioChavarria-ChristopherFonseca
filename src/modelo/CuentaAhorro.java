package modelo;

/**
 * Representa una cuenta de ahorros asociada a un {@link Asociado},
 * con tasa de interés anual y comisión mensual por manejo.
 */
public class CuentaAhorro extends Cuenta {

    private final double tasaInteresAnual;
    private final double comisionMensual;

    /**
     * Crea una cuenta de ahorros validando sus parámetros.
     *
     * @param numeroCuenta identificador único de la cuenta
     * @param titular asociado dueño de la cuenta
     * @param saldoInicial saldo inicial de la cuenta
     * @param tasaInteresAnual tasa de interés anual (no negativa)
     * @param comisionMensual comisión mensual por manejo (no negativa)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public CuentaAhorro(String numeroCuenta, Asociado titular, double saldoInicial,
                        double tasaInteresAnual, double comisionMensual) {
        super(numeroCuenta, titular, saldoInicial);
        if (tasaInteresAnual < 0) {
            throw new IllegalArgumentException("La tasa de interés anual no puede ser negativa.");
        }
        if (comisionMensual < 0) {
            throw new IllegalArgumentException("La comisión mensual no puede ser negativa.");
        }
        this.tasaInteresAnual = tasaInteresAnual;
        this.comisionMensual = comisionMensual;
    }

    /**
     * Obtiene la tasa de interés anual de la cuenta de ahorros.
     *
     * @return tasa de interés anual
     */
    public double getTasaInteresAnual() {
        return tasaInteresAnual;
    }

    /**
     * Obtiene la comisión mensual de la cuenta de ahorros.
     *
     * @return comisión mensual
     */
    public double getComisionMensual() {
        return comisionMensual;
    }

    /**
     * Aplica la comisión mensual sin dejar el saldo por debajo de cero.
     */
    @Override
    public void cobrarComisiones() {
        double nuevoSaldo = getSaldo() - comisionMensual;
        setSaldo(Math.max(0.0, nuevoSaldo));
    }

    @Override
    public String toString() {
        return "CuentaAhorro{" +
                "numeroCuenta='" + getNumeroCuenta() + '\'' +
                ", titular=" + getTitular().getCedula() +
                ", saldo=" + getSaldo() +
                ", tasaInteresAnual=" + tasaInteresAnual +
                ", comisionMensual=" + comisionMensual +
                '}';
    }
}