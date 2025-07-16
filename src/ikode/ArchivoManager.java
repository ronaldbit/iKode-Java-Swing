package ikode;

import java.util.Arrays;

public class ArchivoManager {
    private static final int MAX_ARCHIVOS = 100;
    private String[] archivos = new String[MAX_ARCHIVOS];
    private int cantidad = 0;

    // Agrega archivo si no existe aún
    public boolean agregar(String nombre) {
        if (cantidad >= MAX_ARCHIVOS || buscar(nombre) != -1) return false;
        archivos[cantidad++] = nombre;
        ordenar(); // mantener ordenado
        return true;
    }

    // Elimina lógicamente (deja null y reordena)
    public boolean eliminar(String nombre) {
        int pos = buscar(nombre);
        if (pos == -1) return false;
        for (int i = pos; i < cantidad - 1; i++)
            archivos[i] = archivos[i + 1];
        archivos[--cantidad] = null;
        return true;
    }

    // Ordenamiento alfabético simple (burbuja por simplicidad)
    private void ordenar() {
        Arrays.sort(archivos, 0, cantidad);
    }

    // Búsqueda binaria (solo dentro de los no-nulos)
    public int buscar(String nombre) {
        int izq = 0, der = cantidad - 1;
        while (izq <= der) {
            int mid = (izq + der) / 2;
            int cmp = archivos[mid].compareTo(nombre);
            if (cmp == 0) return mid;
            if (cmp < 0) izq = mid + 1;
            else der = mid - 1;
        }
        return -1;
    }

    public String[] listar() {
        return Arrays.copyOfRange(archivos, 0, cantidad);
    }
}
