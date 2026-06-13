package src;

import java.util.*;

public class Inventario {
    private Map<String, Object[]> productos = new HashMap<>();

    public void registrarIngreso(String id, String nombre, int cantidad, double precio) {
        if (productos.containsKey(id)) {
            Object[] p = productos.get(id);
            int stockActual = (int) p[2];
            p[2] = stockActual + cantidad;
        } else {
            productos.put(id, new Object[]{nombre, precio, cantidad});
        }
    }

    public void registrarSalida(String id, int cantidad) {
        if (!productos.containsKey(id)) {
            System.out.println("Error: Producto no existe");
            return;
        }
        Object[] p = productos.get(id);
        int stockActual = (int) p[2];
        if (stockActual < cantidad) {
            System.out.println("Error: Stock insuficiente");
            return;
        }
        p[2] = stockActual - cantidad;
    }

    public void mostrarInventario() {
        if (productos.isEmpty()) {
            System.out.println("Inventario vacío");
            return;
        }
        for (String id : productos.keySet()) {
            Object[] p = productos.get(id);
            System.out.println("ID: " + id + " | " + p[0] + " | Stock: " + p[2] + " | Precio: S/" + p[1]);
        }
    }

    public void consultarProducto(String id) {
        if (productos.containsKey(id)) {
            Object[] p = productos.get(id);
            System.out.println("ID: " + id + " | " + p[0] + " | Stock: " + p[2] + " | Precio: S/" + p[1]);
        } else {
            System.out.println("Producto no encontrado");
        }
    }
}