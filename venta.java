import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Módulo de Ventas.
 * Se adicionó el desglose de IGV (18%).
 */
public class Venta {
    private final String id;
    private final LocalDate fecha;
    private final List<DetalleVenta> detalles;
    private double total;

    // Constante para el impuesto 
    private static final double TASA_IGV = 0.18;

    public Venta(String id, LocalDate fecha) {
        this.id = Objects.requireNonNull(id, "ID de venta no puede ser nulo");
        this.fecha = Objects.requireNonNull(fecha, "Fecha de venta no puede ser nula");
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarDetalle(String prodId, String nombre, int cantidad, double precioUnitario) {
        if (cantidad <= 0 || precioUnitario < 0) {
            throw new IllegalArgumentException("Detalle de venta inválido: cantidad o precio incorrecto.");
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

    public double getSubtotalBase() { return total / (1 + TASA_IGV); }
    public double getMontoIGV() { return total - getSubtotalBase(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("🧾 Venta #").append(id)
          .append(" | Fecha: ")
          .append(fecha)
          .append("\n");

        sb.append("-------------------------------------------------------------\n");
        sb.append(String.format("%-10s | %-20s | %4s | %9s | %9s\n", "ID", "Producto", "Cant", "P. Unit", "Subtotal"));

        for (DetalleVenta d : detalles) {
            sb.append(d).append("\n");
        }

        sb.append("-------------------------------------------------------------\n");
        // AQUÍ ESTÁ TU MEJORA: Solo 3 líneas discretas antes del Total
        sb.append(String.format("   Subtotal:             $%,10.2f\n", getSubtotalBase()));
        sb.append(String.format("   IGV (18%%):            $%,10.2f\n", getMontoIGV()));
        sb.append(String.format("💰 TOTAL:                $%,10.2f\n", total));

        return sb.toString();
    }

    public static class DetalleVenta {
        private final String prodId, nombre;
        private final int cantidad;
        private final double precioUnitario, subtotal;

        public DetalleVenta(String prodId, String nombre, int cantidad, double precioUnitario, double subtotal) {
            this.prodId = prodId;
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.subtotal = subtotal;
        }

        @Override
        public String toString() {
            return String.format("%-10s | %-20s | %4d | $%,8.2f | $%,8.2f",
                prodId, nombre, cantidad, precioUnitario, subtotal);
        }
    }
}