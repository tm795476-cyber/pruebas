/**
 * Se lanza cuando se busca un producto por ID y no existe en el inventario.
 */
public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String id) {
        super("Producto con ID '" + id + "' no encontrado en el inventario.");
    }
}
