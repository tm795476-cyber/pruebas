import java.util.*;

public class Main {
    private static final Inventario inventario = new Inventario();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarCatalogoInicial();
        
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcionMenu();
            ejecutarOpcion(opcion);
        } while (opcion != 0);
        
        System.out.println("✅ Sistema cerrado correctamente.");
    }

    private static void mostrarMenu() {
        System.out.println("\n  SISTEMA DE INVENTARIO Y VENTAS (FERRETERÍA)");
        System.out.println("1. Registrar Ingreso");
        System.out.println("2. Registrar Salida");
        System.out.println("3. Ver Inventario");
        System.out.println("4. Consultar Producto");
        System.out.println("5. Registrar Venta");
        System.out.println("6. Ver Historial de Ventas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> registrarIngreso();
                case 2 -> registrarSalida();
                case 3 -> mostrarInventario();
                case 4 -> consultarProducto();
                case 5 -> registrarVenta();
                case 6 -> mostrarHistorial();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } catch (ProductoNoEncontradoException | StockInsuficienteException | EntradaInvalidaException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error inesperado: " + e.getMessage());
        }
    }

    private static void registrarIngreso() {
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        double precio = leerDouble("Precio unitario: ");
        int cantidad = leerIntPositivo("Cantidad: ");
        inventario.registrarIngreso(id, nombre, cantidad, precio);
        System.out.println(" Ingreso registrado.");
    }

    private static void registrarSalida() {
        System.out.print("ID del producto: ");
        String id = scanner.nextLine().trim();
        int cantidad = leerIntPositivo("Cantidad a retirar: ");
        inventario.registrarSalida(id, cantidad);
        System.out.println(" Salida registrada.");
    }

    private static void mostrarInventario() {
        List<Producto> lista = inventario.obtenerInventario();
        if (lista.isEmpty()) {
            System.out.println(" El inventario está vacío.");
            return;
        }
        System.out.println("\n INVENTARIO ACTUAL:");
        System.out.println("ID         | NOMBRE                    | CANTIDAD | PRECIO");
        System.out.println("----------------------------------------------------------------");
        lista.forEach(System.out::println);
    }

    private static void consultarProducto() {
        System.out.print("ID a consultar: ");
        String id = scanner.nextLine().trim();
        Producto p = inventario.buscarPorId(id);
        if (p != null) System.out.println(" " + p);
        else System.out.println(" Producto no encontrado.");
    }

    private static void registrarVenta() {
        System.out.println("\n REGISTRAR VENTA (escriba 'fin' para cerrar pedido)");
        List<Inventario.ItemPedido> items = new ArrayList<>();
        
        while (true) {
            System.out.print("ID producto: ");
            String id = scanner.nextLine().trim();
            if (id.equalsIgnoreCase("fin")) break;
            if (id.isEmpty()) continue;
            
            int cantidad = leerIntPositivo("Cantidad: ");
            items.add(new Inventario.ItemPedido(id, cantidad));
        }

        if (items.isEmpty()) {
            System.out.println(" Venta cancelada: sin productos.");
            return;
        }

        Venta venta = inventario.registrarVenta(items);
        System.out.println("\n VENTA PROCESADA CORRECTAMENTE:");
        System.out.println(venta);
    }

    private static void mostrarHistorial() {
        List<Venta> historial = inventario.obtenerHistorialVentas();
        if (historial.isEmpty()) {
            System.out.println(" No hay ventas registradas.");
            return;
        }
        System.out.println("\n HISTORIAL DE VENTAS:");
        historial.forEach(System.out::println);
    }

    // 🔧 UTILIDADES DE ENTRADA
    private static int leerOpcionMenu() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static int leerIntPositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor > 0) return valor;
                System.out.println(" Debe ser mayor a 0.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                if (valor >= 0) return valor;
                System.out.println(" No puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println(" Ingrese un número válido.");
            }
        }
    }

    private static void cargarCatalogoInicial() {
        System.out.println(" Cargando catálogo de ferretería...");
        inventario.registrarIngreso("F01", "Martillo de Bola", 20, 15.50);
        inventario.registrarIngreso("F02", "Destornillador Plano", 35, 8.75);
        inventario.registrarIngreso("F03", "Destornillador Phillips", 30, 9.20);
        inventario.registrarIngreso("F04", "Llave Inglesa 10\"", 15, 22.00);
        inventario.registrarIngreso("F05", "Cinta Métrica 5m", 40, 12.30);
        inventario.registrarIngreso("F06", "Taladro Percutor 600W", 10, 85.00);
        inventario.registrarIngreso("F07", "Juego de Brocas (10pz)", 25, 18.50);
        inventario.registrarIngreso("F08", "Clavos Acero (Caja 1kg)", 50, 6.40);
        inventario.registrarIngreso("F09", "Tornillos Madera (Caja 100pz)", 45, 9.80);
        inventario.registrarIngreso("F10", "Pintura Vinílica 4L", 12, 35.00);
        inventario.registrarIngreso("F11", "Sierra para Mano", 18, 19.90);
        inventario.registrarIngreso("F12", "Nivel de Burbuja 60cm", 22, 14.50);
        System.out.println(" Catálogo listo.\n");
    }
}
