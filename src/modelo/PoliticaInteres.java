package modelo;

/**
 * Define una estrategia para calcular los intereses de un préstamo.
 */
public interface PoliticaInteres {

    /**
     * Calcula el monto total de intereses generados por un préstamo.
     *
     * @param monto capital inicial del préstamo (mayor que cero)
     * @param tasaAnual tasa de interés anual en porcentaje (no negativa)
     * @param plazoMeses plazo en meses (mayor que cero)
     * @return total de intereses calculados
     */
    double calcularInteres(double monto, double tasaAnual, int plazoMeses);
}