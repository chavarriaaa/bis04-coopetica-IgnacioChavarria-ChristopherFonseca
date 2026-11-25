package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un préstamo otorgado dentro de CoopeTica junto con su plan de cuotas.
 */
public class Prestamo {

    private int numero;
    private double monto;
    private double tasaInteres;
    private int plazoMeses;
    private double saldoPendiente;
    private List<Cuota> cuotas;

    /**
     * Crea un préstamo con valores iniciales por defecto.
     */
    public Prestamo() {
        this.cuotas = new ArrayList<>();
    }

    /**
     * Crea un préstamo con sus datos principales.
     *
     * @param numero número identificador del préstamo
     * @param monto monto principal solicitado
     * @param tasaInteres tasa de interés aplicada al préstamo
     * @param plazoMeses cantidad de meses del plazo
     * @param saldoPendiente saldo restante por pagar
     */
    public Prestamo(int numero, double monto, double tasaInteres, int plazoMeses, double saldoPendiente) {
        this.numero = numero;
        this.monto = monto;
        this.tasaInteres = tasaInteres;
        this.plazoMeses = plazoMeses;
        this.saldoPendiente = saldoPendiente;
        this.cuotas = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    @Override
    public String toString() {
        return "Prestamo{"
                + "numero=" + numero
                + ", monto=" + monto
                + ", tasaInteres=" + tasaInteres
                + ", plazoMeses=" + plazoMeses
                + ", saldoPendiente=" + saldoPendiente
                + ", cuotas=" + cuotas
                + '}';
    }
}