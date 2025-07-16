package ikode;

import java.util.ArrayList;
import java.util.List;

public class ABBIndice {
    private NodoABB raiz;

    public void insertar(String nombre, int linea) {
        raiz = insertarRec(raiz, nombre, linea);
    }

    private NodoABB insertarRec(NodoABB actual, String nombre, int linea) {
        if (actual == null) return new NodoABB(nombre, linea);
        if (nombre.compareToIgnoreCase(actual.nombre) < 0)
            actual.izquierda = insertarRec(actual.izquierda, nombre, linea);
        else if (nombre.compareToIgnoreCase(actual.nombre) > 0)
            actual.derecha = insertarRec(actual.derecha, nombre, linea);
        return actual; // ignorar duplicados
    }

    public List<NodoABB> enOrden() {
        List<NodoABB> lista = new ArrayList<>();
        recorrer(raiz, lista);
        return lista;
    }
    
    public List<NodoABB> obtenerTodas() {
    return enOrden(); // reutiliza el método que ya tienes
}


    private void recorrer(NodoABB nodo, List<NodoABB> lista) {
        if (nodo != null) {
            recorrer(nodo.izquierda, lista);
            lista.add(nodo);
            recorrer(nodo.derecha, lista);
        }
    }

    public NodoABB buscar(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private NodoABB buscarRec(NodoABB actual, String nombre) {
        if (actual == null) return null;
        int cmp = nombre.compareToIgnoreCase(actual.nombre);
        if (cmp == 0) return actual;
        return cmp < 0 ? buscarRec(actual.izquierda, nombre) : buscarRec(actual.derecha, nombre);
    }
}
