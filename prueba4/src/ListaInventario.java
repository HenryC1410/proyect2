class NodoProducto {
    int id;
    String nombre;
    String categoria;
    int cantidad;
    double precio;
    NodoProducto siguiente;

    NodoProducto(int id, String nombre, String categoria, int cantidad, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
    }
}

class ListaInventario {
    NodoProducto primero;

    void agregarProducto(int id, String nombre, String categoria, int cantidad, double precio) {
        NodoProducto nuevo = new NodoProducto(id, nombre, categoria, cantidad, precio);
        nuevo.siguiente = primero;
        primero = nuevo;
    }

    NodoProducto buscarProducto(int id) {
        NodoProducto temp = primero;
        while (temp != null) {
            if (temp.id == id) {
                return temp;
            }
            temp = temp.siguiente;
        }
        return null;
    }

    void eliminarProducto(int id) {
        if (primero == null) return;

        if (primero.id == id) {
            primero = primero.siguiente;
            return;
        }

        NodoProducto temp = primero;
        while (temp.siguiente != null && temp.siguiente.id != id) {
            temp = temp.siguiente;
        }

        if (temp.siguiente != null) {
            temp.siguiente = temp.siguiente.siguiente;
        }
    }
}

