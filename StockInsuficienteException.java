/**
 * Se lanza cuando se intenta descontar más unidades de las disponibles en stock.
 */
public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
