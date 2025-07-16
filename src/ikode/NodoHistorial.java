 
package ikode;

public class NodoHistorial {
    String nombreArchivo;
    String rutaCompleta;
    NodoHistorial siguiente;

    public NodoHistorial(String nombreArchivo, String rutaCompleta) {
        this.nombreArchivo = nombreArchivo;
        this.rutaCompleta = rutaCompleta;
    }

    @Override
    public String toString() {
        return nombreArchivo;
    }
}
