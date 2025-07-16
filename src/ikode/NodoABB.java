 
package ikode;

public class NodoABB {
    public String nombre;   // Nombre de función o clase
    public int linea;       // Línea aproximada
    public NodoABB izquierda, derecha;

    public NodoABB(String nombre, int linea) {
        this.nombre = nombre;
        this.linea = linea;
    }

    @Override
    public String toString() {
        return nombre + " (línea " + linea + ")";
    }
}
