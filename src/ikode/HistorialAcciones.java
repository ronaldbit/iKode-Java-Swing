package ikode;

import java.util.*;

/**
 * Gestiona el historial de acciones realizadas en el sistema.
 * Usa pila para deshacer/rehacer y una lista para visualizar.
 */
public class HistorialAcciones {
    private Stack<NodoAccion> pilaDeshacer = new Stack<>();
    private Stack<NodoAccion> pilaRehacer = new Stack<>();
    private List<NodoAccion> historial = new ArrayList<>();

    // Registrar nueva acción
    public void registrar(NodoAccion accion) {
        pilaDeshacer.push(accion);
        historial.add(accion);
        pilaRehacer.clear(); // No se puede rehacer después de una nueva acción
    }

    // Deshacer última acción
    public NodoAccion deshacer() {
        if (!pilaDeshacer.isEmpty()) {
            NodoAccion acc = pilaDeshacer.pop();
            pilaRehacer.push(acc);
            return acc;
        }
        return null;
    }

    // Rehacer última deshecha
    public NodoAccion rehacer() {
        if (!pilaRehacer.isEmpty()) {
            NodoAccion acc = pilaRehacer.pop();
            pilaDeshacer.push(acc);
            return acc;
        }
        return null;
    }

    // Obtener todas las acciones
    public List<NodoAccion> getHistorial() {
        return historial;
    }

    // Ir hacia una acción anterior específica
public List<NodoAccion> retrocederHasta(int index) {
    List<NodoAccion> acciones = new ArrayList<>();
    while (pilaDeshacer.size() > index) {
        NodoAccion acc = deshacer();
        if (acc != null) {
            acc.desactivar();
            acciones.add(acc);
        }
    }
    return acciones;
}

}
