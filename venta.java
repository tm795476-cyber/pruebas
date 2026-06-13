import java.time.LocalDate;
import java.util.ArrayList;
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
}