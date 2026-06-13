import java.util.Objects;

/**
 * Representa un producto del inventario de la ferretería.
 * Encapsula id, nombre, stock y precio unitario.
 */
public class Producto {

    // ─────────────────────────────────────────────
    //  Atributos
    // ─────────────────────────────────────────────

    private final String id;       // Identificador único (ej. "F01")
    private String nombre;         // Nombre descriptivo del producto
    private int    stock;          // Cantidad disponible en bodega
    private double precioUnitario; // Precio de venta unitario en S/

    // ─────────────────────────────────────────────
    //  Constructor
    // ─────────────────────────────────────────────

    /**
     * Crea un producto nuevo con validaciones básicas.
     *
     * @param id             Identificador único, no nulo ni vacío
     * @param nombre         Nombre del producto, no nulo ni vacío
     * @param stock          Cantidad inicial, debe ser >= 0
     * @param precioUnitario Precio unitario, debe ser >= 0
     */
    public Producto(String id, String nombre, int stock, double precioUnitario) {
        if (id == null || id.isBlank())
            throw new EntradaInvalidaException("El ID del producto no puede estar vacío.");
        if (nombre == null || nombre.isBlank())
            throw new EntradaInvalidaException("El nombre del producto no puede estar vacío.");
        if (stock < 0)
            throw new EntradaInvalidaException("El stock inicial no puede ser negativo.");
        if (precioUnitario < 0)
            throw new EntradaInvalidaException("El precio unitario no puede ser negativo.");

        this.id             = id.trim().toUpperCase();
        this.nombre         = nombre.trim();
        this.stock          = stock;
        this.precioUnitario = precioUnitario;
    }

    // ─────────────────────────────────────────────
    //  Getters
    // ─────────────────────────────────────────────

    public String getId()             { return id; }
    public String getNombre()         { return nombre; }
    public int    getStock()          { return stock; }
    public double getPrecioUnitario() { return precioUnitario; }

    // ─────────────────────────────────────────────
    //  Setters con validación
    // ─────────────────────────────────────────────

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new EntradaInvalidaException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    public void setPrecioUnitario(double precio) {
        if (precio < 0)
            throw new EntradaInvalidaException("El precio no puede ser negativo.");
        this.precioUnitario = precio;
    }

    // ─────────────────────────────────────────────
    //  Lógica de negocio
    // ─────────────────────────────────────────────

    /**
     * Agrega unidades al stock (ingreso de mercancía).
     *
     * @param cantidad Cantidad a ingresar, debe ser > 0
     */
    public void agregarStock(int cantidad) {
        if (cantidad <= 0)
            throw new EntradaInvalidaException("La cantidad a ingresar debe ser mayor a 0.");
        this.stock += cantidad;
    }

    /**
     * Descuenta unidades del stock (salida o venta).
     *
     * @param cantidad Cantidad a descontar, debe ser > 0 y no superar el stock
     */
    public void descontarStock(int cantidad) {
        if (cantidad <= 0)
            throw new EntradaInvalidaException("La cantidad a descontar debe ser mayor a 0.");
        if (cantidad > this.stock)
            throw new StockInsuficienteException(
                String.format("Stock insuficiente para '%s'. Disponible: %d, solicitado: %d.",
                    nombre, stock, cantidad)
            );
        this.stock -= cantidad;
    }

    /**
     * Calcula el valor total del producto en inventario.
     *
     * @return stock × precioUnitario
     */
    public double calcularValorTotal() {
        return stock * precioUnitario;
    }

    /**
     * Indica si el producto tiene stock disponible.
     */
    public boolean tieneStock() {
        return stock > 0;
    }

    // ─────────────────────────────────────────────
    //  equals / hashCode / toString
    // ─────────────────────────────────────────────

    /** Dos productos son iguales si comparten el mismo ID. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        return id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación tabular alineada con el encabezado impreso en Main.
     * Formato: ID         | NOMBRE                    | CANTIDAD | PRECIO
     */
    @Override
    public String toString() {
        return String.format("%-10s | %-25s | %-8d | S/ %.2f",
            id, nombre, stock, precioUnitario);
    }
}
