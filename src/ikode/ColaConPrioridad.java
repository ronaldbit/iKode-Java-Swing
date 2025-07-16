 
package ikode;

import java.util.PriorityQueue;
import java.util.function.Consumer;
public class ColaConPrioridad {
    private PriorityQueue<Tarea> cola = new PriorityQueue<>();
    private Consumer<String> procesador;

    public ColaConPrioridad(Consumer<String> procesador) {
        this.procesador = procesador;
    }

    public void agregarTarea(String descripcion, int prioridad) {
        cola.offer(new Tarea(descripcion, prioridad));
    }

    public Tarea procesarTarea() {
        return cola.poll();
    }

    public boolean hayTareas() {
        return !cola.isEmpty();
    }

    public void procesarTodo() {
        while (hayTareas()) {
            Tarea tarea = procesarTarea();
            System.out.println("✅ Procesando: " + tarea);
            
            // Extraer nombre sin "Guardar "
            String desc = tarea.getDescripcion();
            if (desc.startsWith("Guardar ")) {
                String nombreArchivo = desc.substring(8).trim();
                procesador.accept(nombreArchivo);  // 🔁 llamar a guardarArchivo(nombre)
            }
        }
    }
}

