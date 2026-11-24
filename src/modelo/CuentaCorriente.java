package modelo;

/**
 * Representa una cuenta corriente asociada a un {@link Asociado},
 * con límite de sobregiro y comisión por sobregiro.
 */
public class CuentaCorriente extends Cuenta {

    private final double limiteSobregiro;
    private final double comisionSobregiro;

    /**
     * Crea una cuenta corriente validando su límite y comisión de sobregiro.
     *
     * @param numeroCuenta identificador único de la cuenta
     * @param titular asociado dueño de la cuenta
     * @param saldoInicial saldo inicial de la cuenta
     * @param limiteSobregiro monto máximo permitido de sobregiro (no negativo)
     * @param comisionSobregiro comisión aplicada cuando el saldo está en sobregiro (no negativa)
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public CuentaCorriente(String numeroCuenta, Asociado titular, double saldoInicial,
                           double limiteSobregiro, double comisionSobregiro) {
        super(numeroCuenta, titular, saldoInicial);
        if (limiteSobregiro < 0) {
            throw new IllegalArgumentException("El límite de sobregiro no puede ser negativo.");
        }
        if (comisionSobregiro < 0) {
            throw new IllegalArgumentException("La comisión por sobregiro no puede ser negativa.");
        }
        this.limiteSobregiro = limiteSobregiro;
        this.comisionSobregiro = comisionSobregiro;
    }

    /**
     * Obtiene el límite de sobregiro de la cuenta corriente.
     *
     * @return límite de sobregiro
     */
    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    /**
     * Obtiene la comisión por sobregiro de la cuenta corriente.
     *
     * @return comisión por sobregiro
     */
    public double getComisionSobregiro() {
        return comisionSobregiro;
    }

    /**
     * Retira un monto positivo permitiendo usar hasta el límite de sobregiro.
     *
     * @param monto monto a retirar
     * @throws IllegalArgumentException si el monto no es positivo o supera saldo + límite de sobregiro
     */
    @Override
    public void retirar(double monto) {
        validarMontoPositivo(monto, "retirar");
        double disponibleTotal = getSaldo() + limiteSobregiro;
        if (monto > disponibleTotal) {
            throw new IllegalArgumentException("El monto excede el saldo disponible y el límite de sobregiro.");
        }
        setSaldo(getSaldo() - monto);
    }

    /**
     * Cobra la comisión por sobregiro si el saldo actual es menor que cero.
     */
    @Override
    public void cobrarComisiones() {
        if (getSaldo() < 0) {
            setSaldo(getSaldo() - comisionSobregiro);
        }
    }

    @Override
    public String toString() {
        return "CuentaCorriente{" +
                "numeroCuenta='" + getNumeroCuenta() + '\'' +
                ", titular=" + getTitular().getCedula() +
                ", saldo=" + getSaldo() +
                ", limiteSobregiro=" + limiteSobregiro +
                ", comisionSobregiro=" + comisionSobregiro +
                '}';
    }
}