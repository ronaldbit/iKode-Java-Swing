 
package ikode;
 
public class NodoArchivo {
    private String nombre;
    private String rutaCompleta;

    public NodoArchivo(String nombre, String rutaCompleta) {
        this.nombre = nombre;
        this.rutaCompleta = rutaCompleta;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }

    @Override
    public String toString() {
        return nombre; // Solo esto se muestra en el JTree
    }
}
