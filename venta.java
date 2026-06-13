import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venta {
    private final String id;
    private final LocalDate fecha;
    private final List<DetalleVenta> detalles;
    private double total;

    //delete prueba de rama

    public void agregarDetalle(String prodId, String nombre, int cantidad, double precioUnitario) {
        if (cantidad <= 0 || precioUnitario < 0) {
            throw new EntradaInvalidaException("Detalle de venta inválido: cantidad o precio incorrecto.");
        }

        double subtotal = cantidad * precioUnitario;

        detalles.add(
            new DetalleVenta(
                prodId,
                nombre,
                cantidad,
                precioUnitario,
                subtotal
            )
        );

        this.total += subtotal;
    }
}