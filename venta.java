import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Venta {
    private final String id;
    private final LocalDate fecha;
    private final List<DetalleVenta> detalles;
    private double total;

    public Venta(String id, LocalDate fecha) {
        this.id = Objects.requireNonNull(id, "ID de venta no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "Fecha de venta no puede ser nula");
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

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

    public String getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public List<DetalleVenta> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }
}