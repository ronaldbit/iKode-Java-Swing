package ikode;

/**
 * Representa una acción del sistema (abrir, cerrar, guardar, etc.)
 */
public class NodoAccion {
    public enum Tipo { ABRIR, CERRAR }

    private Tipo tipo;
    private String archivo;
    private String ruta;
    
    private boolean activa = true;


    public NodoAccion(Tipo tipo, String archivo, String ruta) {
        this.tipo = tipo;
        this.archivo = archivo;
        this.ruta = ruta;
    }

    public void desactivar() {
    activa = false;
}

public boolean estaActiva() {
    return activa;
}
    public Tipo getTipo() { return tipo; }
    public String getArchivo() { return archivo; }
    public String getRuta() { return ruta; }

    @Override
    public String toString() {
        return tipo + ": " + archivo;
    }
}
