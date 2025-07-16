 
package ikode;
 
import java.util.ArrayList;
import java.util.List;

public class HistorialRecientes {
    private NodoHistorial cabeza;
    private int tamaño = 0;
    private final int MAX = 10;

    public void agregar(String nombreArchivo, String rutaCompleta) {
        // Evitar duplicados
        eliminar(nombreArchivo);

        NodoHistorial nuevo = new NodoHistorial(nombreArchivo, rutaCompleta);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoHistorial actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }

        tamaño++;
        if (tamaño > MAX) {
            eliminarPrimero();
        }
    }

    public void eliminar(String nombreArchivo) {
        NodoHistorial actual = cabeza, anterior = null;
        while (actual != null) {
            if (actual.nombreArchivo.equals(nombreArchivo)) {
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamaño--;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public void eliminarPrimero() {
        if (cabeza != null) {
            cabeza = cabeza.siguiente;
            tamaño--;
        }
    }

    public List<NodoHistorial> obtenerTodos() {
        List<NodoHistorial> lista = new ArrayList<>();
        NodoHistorial actual = cabeza;
        while (actual != null) {
            lista.add(actual);
            actual = actual.siguiente;
        }
        return lista;
    }
}
