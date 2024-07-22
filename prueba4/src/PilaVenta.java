class NodoVenta {
    int idProducto;
    String nombreProducto;
    String categoria;
    int cantidadVendida;
    double precioTotal;
    String cedulaComprador;
    String nombreComprador;
    String apellidoComprador;
    NodoVenta siguiente;

    NodoVenta(int idProducto, String nombreProducto, String categoria, int cantidadVendida, double precioTotal, String cedulaComprador, String nombreComprador, String apellidoComprador) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.cantidadVendida = cantidadVendida;
        this.precioTotal = precioTotal;
        this.cedulaComprador = cedulaComprador;
        this.nombreComprador = nombreComprador;
        this.apellidoComprador = apellidoComprador;
    }
}

class PilaVentas {
    NodoVenta cima;

    void registrarVenta(int idProducto, String nombreProducto, String categoria, int cantidadVendida, double precioTotal, String cedulaComprador, String nombreComprador, String apellidoComprador) {
        NodoVenta nueva = new NodoVenta(idProducto, nombreProducto, categoria, cantidadVendida, precioTotal, cedulaComprador, nombreComprador, apellidoComprador);
        nueva.siguiente = cima;
        cima = nueva;
    }
}

