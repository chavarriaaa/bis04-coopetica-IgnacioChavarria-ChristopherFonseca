package modelo;

/**
 * Calcula el interés simple para un préstamo.
 */
public class InteresSimple implements PoliticaInteres {

    /**
     * Calcula el interés simple según capital, tasa anual y plazo en meses.
     *
     * @param monto capital inicial del préstamo (mayor que cero)
     * @param tasaAnual tasa de interés anual en porcentaje (no negativa)
     * @param plazoMeses plazo en meses (mayor que cero)
     * @return interés simple total
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    @Override
    public double calcularInteres(double monto, double tasaAnual, int plazoMeses) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del préstamo debe ser mayor que cero.");
        }
        if (tasaAnual < 0) {
            throw new IllegalArgumentException("La tasa de interés no puede ser negativa.");
        }
        if (plazoMeses <= 0) {
            throw new IllegalArgumentException("El plazo en meses debe ser mayor que cero.");
        }
        return monto * (tasaAnual / 100.0) * (plazoMeses / 12.0);
    }

    @Override
    public String toString() {
        return "InteresSimple{}";
    }
}