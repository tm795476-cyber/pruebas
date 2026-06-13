/**
 * Se lanza cuando el usuario ingresa datos con formato o valor incorrecto.
 */
public class EntradaInvalidaException extends RuntimeException {
    public EntradaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
