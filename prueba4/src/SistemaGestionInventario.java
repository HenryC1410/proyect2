import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SistemaGestionInventario {

    public static void main(String[] args) {
        ListaInventario inventario = new ListaInventario();
        PilaVentas ventas = new PilaVentas();
        ColaPedidos pedidos = new ColaPedidos();
        ArbolBPlus arbol = new ArbolBPlus(6); // Grado 6 para el ejemplo

        while (true) {
            
            String opcion = JOptionPane.showInputDialog(
                "Sistema de Gestión de Inventario\n" +
                    "1. Agregar Producto\n" +
                    "2. Registrar Venta\n" +
                    "3. Mostrar Inventario\n" +
                    "4. Buscar Producto\n" +
                    "5. Modificar Producto\n" +
                    "6. Agregar Pedido\n" +
                    "7. Historial de Pedidos\n" +
                    "8. Historial de Ventas\n" +
                    "9. Salir\n" +
                    "Ingrese una opción:");

            switch (opcion) {
                case "1":
                    agregarProducto(inventario, arbol);
                    break;
                case "2":
                    registrarVenta(inventario, ventas, arbol);
                    break;
                case "3":
                    mostrarInventario(inventario);
                    break;
                case "4":
                    buscarProducto(arbol);
                    break;
                case "5":
                    modificarProducto(inventario, arbol);
                    break;
                case "6":
                    agregarPedido(pedidos);
                    break;
                case "7":
                    mostrarHistorialPedidos(pedidos);
                    break;
                case "8":
                    mostrarHistorialVentas(ventas);
                    break;
                case "9":
                   JOptionPane.showMessageDialog(null, "Saliendo del sistema.");
                   System.exit(0);
                   break;
                    
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        }
    }

    private static void agregarProducto(ListaInventario inventario, ArbolBPlus arbol) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto:"));
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String categoria = JOptionPane.showInputDialog("Ingrese la categoría del producto:");
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad en stock:"));
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto:"));

        inventario.agregarProducto(id, nombre, categoria, cantidad, precio);
        arbol.insertar(id, nombre, categoria, cantidad, precio);
        JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
    }

    private static void registrarVenta(ListaInventario inventario, PilaVentas ventas, ArbolBPlus arbol) {
        // Validar la cédula del comprador
        String cedulaComprador;
        while (true) {
            cedulaComprador = JOptionPane.showInputDialog("Ingrese la cédula del comprador:");
            if (cedulaComprador.matches("\\d{10}")) {
                break; // Salir del bucle si la cédula es válida
            } else {
                JOptionPane.showMessageDialog(null, " Intente nuevamente.");
            }
        }
    
        String nombreComprador = JOptionPane.showInputDialog("Ingrese el nombre del comprador:");
        String apellidoComprador = JOptionPane.showInputDialog("Ingrese el apellido del comprador:");
    
        boolean agregarMasProductos = true;
    
        while (agregarMasProductos) {
            boolean productoValido = false;
            NodoProducto producto = null;
            int cantidad = 0;
    
            while (!productoValido) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a vender:"));
                producto = inventario.buscarProducto(id);
    
                if (producto == null || producto.cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado o sin stock. Intente nuevamente.");
                } else {
                    productoValido = true;
    
                    // Solicitar la cantidad solo si el producto es válido
                    while (true) {
                        cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad vendida:"));
    
                        if (producto.cantidad >= cantidad) {
                            producto.cantidad -= cantidad;
                            NodoProducto productoEnArbol = arbol.buscar(id);
                            if (productoEnArbol != null) {
                                productoEnArbol.cantidad = producto.cantidad;
                            }
    
                            ventas.registrarVenta(id, producto.nombre, producto.categoria, cantidad, producto.precio * cantidad,
                                    cedulaComprador, nombreComprador, apellidoComprador);
                            JOptionPane.showMessageDialog(null, "Venta registrada exitosamente.");
                            break;
                        } else {
                            JOptionPane.showMessageDialog(null, "Cantidad insuficiente en stock. Intente nuevamente.");
                        }
                    }
                }
            }
    
            int continuar = JOptionPane.showConfirmDialog(null, "¿Desea agregar más productos a esta venta?", "Agregar Más", JOptionPane.YES_NO_OPTION);
            agregarMasProductos = (continuar == JOptionPane.YES_OPTION);
        }
    }
    

    private static void mostrarInventario(ListaInventario inventario) {
        NodoProducto temp = inventario.primero;
        if (temp == null) {
            JOptionPane.showMessageDialog(null, "No hay ningún producto en el inventario.");
            return;
        }

        StringBuilder inventarioStr = new StringBuilder();
        while (temp != null) {
            inventarioStr.append("\n ID: ").append(temp.id)
                    .append("\n Nombre: ").append(temp.nombre)
                    .append("\n Categoría: ").append(temp.categoria)
                    .append("\n Cantidad: ").append(temp.cantidad)
                    .append("\n Precio: ").append(temp.precio).append(" $")
                    .append("\n------------------------------------\n");
            temp = temp.siguiente;
        }
        JTextArea textArea = new JTextArea(inventarioStr.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void buscarProducto(ArbolBPlus arbol) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a buscar:"));
        NodoProducto producto = arbol.buscar(id);
        if (producto != null) {
            JOptionPane.showMessageDialog(null, "ID: " + producto.id +
                    "\nNombre: " + producto.nombre +
                    "\nCategoría: " + producto.categoria +
                    "\nCantidad: " + producto.cantidad +
                    "\nPrecio: " + producto.precio + " $");
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
        }
    }

    private static void modificarProducto(ListaInventario inventario, ArbolBPlus arbol) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a modificar/eliminar:"));
        NodoProducto producto = inventario.buscarProducto(id);
        if (producto != null) {
            int opcion = JOptionPane.showOptionDialog(null, "¿Qué desea hacer?", "Modificar/Eliminar Producto",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Modificar", "Eliminar"}, "Modificar");

            if (opcion == 0) {
                producto.nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre del producto:", producto.nombre);
                producto.categoria = JOptionPane.showInputDialog("Ingrese la nueva categoría del producto:", producto.categoria);
                producto.cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad en stock:", producto.cantidad));
                producto.precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo precio del producto:", producto.precio));
                NodoProducto productoEnArbol = arbol.buscar(id);
                if (productoEnArbol != null) {
                    productoEnArbol.nombre = producto.nombre;
                    productoEnArbol.categoria = producto.categoria;
                    productoEnArbol.cantidad = producto.cantidad;
                    productoEnArbol.precio = producto.precio;
                }
                JOptionPane.showMessageDialog(null, "Producto modificado exitosamente.");
            } else if (opcion == 1) {
                inventario.eliminarProducto(id);
                arbol.eliminar(id);
                JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
        }
    }

    private static void agregarPedido(ColaPedidos pedidos) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del producto a pedir:"));
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad a pedir:"));
        String nombreProducto = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String categoria = JOptionPane.showInputDialog("Ingrese la categoría del producto:");

        pedidos.agregarPedido(id, cantidad, nombreProducto, categoria);
        JOptionPane.showMessageDialog(null, "Pedido agregado exitosamente.");
    }

    private static void mostrarHistorialPedidos(ColaPedidos pedidos) {
        NodoPedido temp = pedidos.frente;
        if (temp == null) {
            JOptionPane.showMessageDialog(null, "No hay registro de pedidos.");
            return;
        }

        StringBuilder pedidosStr = new StringBuilder();
        while (temp != null) {
            pedidosStr.append("\n ID Producto: ").append(temp.idProducto)
                    .append("\n Nombre Producto: ").append(temp.nombreProducto)
                    .append("\n Categoría: ").append(temp.categoria)
                    .append("\n Cantidad: ").append(temp.cantidad)
                    .append("\n-------------------------\n");
            temp = temp.siguiente;
        }
        JTextArea textArea = new JTextArea(pedidosStr.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Historial de Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarHistorialVentas(PilaVentas ventas) {
        NodoVenta temp = ventas.cima;
        if (temp == null) {
            JOptionPane.showMessageDialog(null, "No hay registro de ventas.");
            return;
        }

        StringBuilder ventasStr = new StringBuilder();
        while (temp != null) {
            ventasStr.append("\n ID Producto: ").append(temp.idProducto)
                    .append("\n Nombre Producto: ").append(temp.nombreProducto)
                    .append("\n Categoría: ").append(temp.categoria)
                    .append("\n Cantidad Vendida: ").append(temp.cantidadVendida)
                    .append("\n Precio Total: ").append(temp.precioTotal).append(" $")
                    .append("\n Cédula Comprador: ").append(temp.cedulaComprador)
                    .append("\n Nombre Comprador: ").append(temp.nombreComprador)
                    .append("\n Apellido Comprador: ").append(temp.apellidoComprador)
                    .append("\n----------------------------------------\n");
            temp = temp.siguiente;
        }
        JTextArea textArea = new JTextArea(ventasStr.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Historial de Ventas", JOptionPane.INFORMATION_MESSAGE);
    }
}