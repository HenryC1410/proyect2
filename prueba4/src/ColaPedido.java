import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class NodoPedido {
    int idProducto;
    int cantidad;
    String nombreProducto;
    String categoria;
    NodoPedido siguiente;

    NodoPedido(int idProducto, int cantidad, String nombreProducto, String categoria) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
    }
}

class ColaPedidos {
    NodoPedido frente, finalNodo;

    void agregarPedido(int idProducto, int cantidad, String nombreProducto, String categoria) {
        NodoPedido nuevo = new NodoPedido(idProducto, cantidad, nombreProducto, categoria);
        if (finalNodo != null) {
            finalNodo.siguiente = nuevo;
        } else {
            frente = nuevo;
        }
        finalNodo = nuevo;
    }

    void mostrarPedidos() {
        if (frente == null) {
            JOptionPane.showMessageDialog(null, "No hay registro de pedidos.");
            return;
        }
        StringBuilder pedidosStr = new StringBuilder();
        NodoPedido temp = frente;
        while (temp != null) {
            pedidosStr.append("\n ID Producto: ").append(temp.idProducto)
                    .append("\n Nombre Producto: ").append(temp.nombreProducto)
                    .append("\n Categoría: ").append(temp.categoria)
                    .append("\n Cantidad: ").append(temp.cantidad)
                    .append("\n----------------------------\n");
            temp = temp.siguiente;
        }
        JTextArea textArea = new JTextArea(pedidosStr.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane, "Historial de Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }
}

