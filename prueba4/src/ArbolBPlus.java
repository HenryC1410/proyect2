class NodoBPlus {
    int[] llaves;
    NodoProducto[] productos;
    NodoBPlus[] hijos;
    boolean esHoja;
    int numLlaves;

    NodoBPlus(int grado) {
        llaves = new int[grado - 1];
        productos = new NodoProducto[grado - 1];
        hijos = new NodoBPlus[grado];
        esHoja = true;
        numLlaves = 0;
    }
}

class ArbolBPlus {
    private NodoBPlus raiz;
    private int grado;

    public ArbolBPlus(int grado) {
        this.grado = grado;
        this.raiz = new NodoBPlus(grado);
    }

    public void insertar(int id, String nombre, String categoria, int cantidad, double precio) {
        NodoBPlus r = raiz;
        if (r.numLlaves == grado - 1) {
            NodoBPlus s = new NodoBPlus(grado);
            raiz = s;
            s.esHoja = false;
            s.hijos[0] = r;
            dividirHijo(s, 0, r);
            insertarNoLleno(s, id, nombre, categoria, cantidad, precio);
        } else {
            insertarNoLleno(r, id, nombre, categoria, cantidad, precio);
        }
    }

    private void dividirHijo(NodoBPlus x, int i, NodoBPlus y) {
        int t = (grado - 1) / 2;
        NodoBPlus z = new NodoBPlus(grado);
        z.esHoja = y.esHoja;
        z.numLlaves = t;

        for (int j = 0; j < t; j++) {
            z.llaves[j] = y.llaves[j + t + 1];
            z.productos[j] = y.productos[j + t + 1];
        }

        if (!y.esHoja) {
            for (int j = 0; j < t + 1; j++) {
                z.hijos[j] = y.hijos[j + t + 1];
            }
        }

        y.numLlaves = t;
        for (int j = x.numLlaves; j >= i + 1; j--) {
            x.hijos[j + 1] = x.hijos[j];
        }
        x.hijos[i + 1] = z;

        for (int j = x.numLlaves - 1; j >= i; j--) {
            x.llaves[j + 1] = x.llaves[j];
            x.productos[j + 1] = x.productos[j];
        }

        x.llaves[i] = y.llaves[t];
        x.productos[i] = y.productos[t];
        x.numLlaves++;
    }

    private void insertarNoLleno(NodoBPlus x, int id, String nombre, String categoria, int cantidad, double precio) {
        int i = x.numLlaves - 1;
        if (x.esHoja) {
            while (i >= 0 && id < x.llaves[i]) {
                x.llaves[i + 1] = x.llaves[i];
                x.productos[i + 1] = x.productos[i];
                i--;
            }
            x.llaves[i + 1] = id;
            x.productos[i + 1] = new NodoProducto(id, nombre, categoria, cantidad, precio);
            x.numLlaves++;
        } else {
            while (i >= 0 && id < x.llaves[i]) {
                i--;
            }
            i++;
            if (x.hijos[i].numLlaves == grado - 1) {
                dividirHijo(x, i, x.hijos[i]);
                if (id > x.llaves[i]) {
                    i++;
                }
            }
            insertarNoLleno(x.hijos[i], id, nombre, categoria, cantidad, precio);
        }
    }

    public NodoProducto buscar(int id) {
        return buscar(raiz, id);
    }

    private NodoProducto buscar(NodoBPlus x, int id) {
        int i = 0;
        while (i < x.numLlaves && id > x.llaves[i]) {
            i++;
        }
        if (i < x.numLlaves && id == x.llaves[i]) {
            return x.productos[i];
        }
        if (x.esHoja) {
            return null;
        } else {
            return buscar(x.hijos[i], id);
        }
    }

    public void eliminar(int id) {
        eliminar(raiz, id);
    }

    private void eliminar(NodoBPlus x, int id) {
        int i = 0;
        while (i < x.numLlaves && id > x.llaves[i]) {
            i++;
        }
        if (i < x.numLlaves && id == x.llaves[i]) {
            if (x.esHoja) {
                for (int j = i; j < x.numLlaves - 1; j++) {
                    x.llaves[j] = x.llaves[j + 1];
                    x.productos[j] = x.productos[j + 1];
                }
                x.numLlaves--;
            } else {
                // Implementar eliminación en nodo interno
            }
        } else {
            if (!x.esHoja) {
                eliminar(x.hijos[i], id);
            }
        }
    }
}

