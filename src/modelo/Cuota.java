package modelo;

import java.time.LocalDate;

/**
 * Representa una cuota mensual asociada a un préstamo en CoopeTica.
 */
public class Cuota {

    private int numero;
    private double monto;
    private LocalDate fechaVencimiento;
    private boolean pagada;

    /**
     * Crea una cuota con valores iniciales por defecto.
     */
    public Cuota() {
    }

    /**
     * Crea una cuota con sus datos principales.
     *
     * @param numero número de cuota dentro del plan
     * @param monto valor económico de la cuota
     * @param fechaVencimiento fecha límite de pago de la cuota
     * @param pagada indica si la cuota ya fue pagada
     */
    public Cuota(int numero, double monto, LocalDate fechaVencimiento, boolean pagada) {
        this.numero = numero;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.pagada = pagada;
    }

    /**
     * Obtiene el número de cuota.
     *
     * @return número de cuota
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Asigna el número de cuota.
     *
     * @param numero nuevo número de cuota
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el monto de la cuota.
     *
     * @return monto de la cuota
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Asigna el monto de la cuota.
     *
     * @param monto nuevo monto de la cuota
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * Obtiene la fecha de vencimiento de la cuota.
     *
     * @return fecha de vencimiento
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Asigna la fecha de vencimiento de la cuota.
     *
     * @param fechaVencimiento nueva fecha de vencimiento
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Indica si la cuota ya está pagada.
     *
     * @return {@code true} si está pagada, {@code false} en caso contrario
     */
    public boolean isPagada() {
        return pagada;
    }

    /**
     * Marca o desmarca la cuota como pagada.
     *
     * @param pagada estado de pago de la cuota
     */
    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    @Override
    public String toString() {
        return "Cuota{"
                + "numero=" + numero
                + ", monto=" + monto
                + ", fechaVencimiento=" + fechaVencimiento
                + ", pagada=" + pagada
                + '}';
    }
}