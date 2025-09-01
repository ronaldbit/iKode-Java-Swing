
# iKode - Editor de Código con Estructuras de Datos

**Curso:** Algoritmos y Estructuras de Datos  
**Proyecto Final**  
**Autor:** [Ronald Ramos]  
**Fecha:** [16.07.2025]

---

## 🎯 Descripción General

**iKode** es un editor de código desarrollado en Java con interfaz gráfica (Swing), que integra estructuras de datos clásicas para gestionar archivos, pestañas y acciones del usuario. El sistema permite abrir carpetas, explorar archivos y editar código fuente en un entorno amigable.

Este proyecto cumple con los requisitos del curso mediante el uso intencional y funcional de diversas estructuras de datos como arreglos, listas enlazadas, pilas, colas, árboles binarios, AVL y un TAD personalizado.

---

## ✅ Funcionalidades Implementadas

### 📁 Explorador de Archivos (JTree)
- Permite abrir cualquier carpeta y visualizar su contenido en un árbol.
- Los archivos se muestran por nombre, pero internamente se conserva la ruta absoluta para su apertura.

### 📝 Apertura de Archivos en Pestañas
- Los archivos se abren en pestañas usando `RSyntaxTextArea`, con soporte para resaltado de sintaxis y plegado de código.
- Si el archivo ya está abierto, se selecciona la pestaña existente sin duplicarla.
- Las pestañas incluyen botón de cierre (❌) y se enfocan correctamente al volver a abrir.

### 📌 Arreglo Unidimensional – Pestañas Abiertas
- Se usa un arreglo de tamaño fijo (`MAX_PESTAÑAS = 50`) para registrar los archivos abiertos.
- Evita duplicados y lleva control del número total de pestañas abiertas.

### 🕘 Lista Enlazada – Archivos Recientes
- Se registran hasta **10 archivos recientemente abiertos**, incluso si la pestaña ya fue cerrada.
- Se usa una lista enlazada para insertar en orden y eliminar el más antiguo si se excede el tope.
- Los archivos recientes se muestran dinámicamente en el menú `Abrir reciente`.
- Al seleccionar uno, se enfoca su pestaña o se abre si no está abierta.

---

## 📦 Estructuras de Datos Aplicadas

| Requisito                         | Estructura               | Estado       | Detalles                                                      |
|----------------------------------|--------------------------|--------------|---------------------------------------------------------------|
| Gestión de entidad principal     | Arreglo unidimensional   | ✅ Completado | Registro de pestañas abiertas (nombres de archivos).         |
| Interacción cronológica          | Lista enlazada           | ✅ Completado | Historial de archivos abiertos recientemente (máximo 10).    |
| Acciones reversibles             | Pila dinámica            | ✅ Completado  | Para deshacer operaciones (ej: cerrar pestaña, ejecutar).    |
| Atención o cola de procesamiento| Cola con prioridad       | ✅ Completado  | Para tareas agendadas o procesos automáticos.                |
| Organización jerárquica          | Árbol binario de búsqueda| ⏳ Pendiente  | Indexación o búsqueda interna de funciones/snippets.         |
| Eficiencia en búsqueda           | Árbol AVL                | ⏳ Pendiente  | Búsqueda eficiente por nombre de archivo o estructura.       |
| TAD personalizado                | Árbol de carpetas        | ✅ Completado | NodoArchivo muestra nombre pero guarda ruta completa.        |

---

## 📌 Aplicación del Código

### 🧱 Gestión de Entidad Principal – Arreglo unidimensional

**Archivo:** `PanelEditor.java`  
```java
private static final int MAX_PESTAÑAS = 50;
private String[] pestañasAbiertas = new String[MAX_PESTAÑAS];
private int totalPestañas = 0;
````

```java
private void registrarPestaña(String nombreArchivo) {
    for (int i = 0; i < totalPestañas; i++) {
        if (pestañasAbiertas[i].equals(nombreArchivo)) {
            return; // Ya está registrada
        }
    }
    pestañasAbiertas[totalPestañas++] = nombreArchivo;
}
```

---

### 🌳 TAD Personalizado – Árbol de carpetas con `NodoArchivo`

**Archivo:** `NodoArchivo.java`

```java
public class NodoArchivo {
    private String nombre;
    private String rutaCompleta;

    public NodoArchivo(String nombre, String rutaCompleta) { ... }

    public String getRutaCompleta() { ... }

    @Override
    public String toString() {
        return nombre;
    }
}
```

**Usado en:** `PanelEditor.java`

```java
DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(
    new NodoArchivo(f.getName(), f.getAbsolutePath())
);

NodoArchivo datos = (NodoArchivo) nodo.getUserObject();
File archivo = new File(datos.getRutaCompleta());
```

---

### 🕘 Interacción Cronológica – Historial de Archivos Recientes

**Archivo:** `ListaHistorial.java`, `NodoHistorial.java`

```java
historial.agregar(nombre, rutaCompleta);
actualizarMenuAbrirReciente();
```

**En el menú:**

```java
for (NodoHistorial item : historial.obtenerTodos()) {
    JMenuItem opcion = new JMenuItem(item.nombreArchivo);
    opcion.addActionListener(e -> {
        File archivo = new File(item.rutaCompleta);
        if (archivo.exists()) {
            if (!estaPestañaAbierta(archivo.getName())) {
                abrirArchivoEnPestaña(archivo);
            }
        }
    });
    menuAbrirReciente.add(opcion);
}
```

### 🔁 Acciones Reversibles – Pila Dinámica

**Archivo:** `HistorialAcciones.java`  
**Estructura aplicada:** `Stack<NodoAccion>`  
**Descripción:**

Para registrar y revertir acciones del sistema, se implementó una pila dinámica de acciones (`pilaDeshacer`), complementada con otra pila (`pilaRehacer`) para soportar el rehacer de acciones. Esta estructura permite implementar el comportamiento típico de "Deshacer / Rehacer" de editores modernos.

- Cada acción realizada (como abrir o cerrar una pestaña) se encapsula en un objeto `NodoAccion` y se apila usando `pilaDeshacer.push(...)`.
- El método `deshacer()` realiza un `pop()` de la pila principal, ejecuta la acción inversa, y la guarda en la pila secundaria para rehacer.
- El método `rehacer()` extrae de `pilaRehacer` y vuelve a aplicarla.
- Todas las acciones se visualizan en una lista para navegación cronológica, usando el método `getHistorial()`.

**Fragmentos de código:**
```java
public void registrar(NodoAccion accion) {
    pilaDeshacer.push(accion); // push
    historial.add(accion);
    pilaRehacer.clear();
}

public NodoAccion deshacer() {
    NodoAccion acc = pilaDeshacer.pop();  // pop
    pilaRehacer.push(acc);                // push
    return acc;
}
````

**Ejecución:**

* Se integra con el menú `menuEditarAtras` y `menuEditarAdelante`.
* Las acciones también se listan en `listHistorial`, permitiendo retroceder hasta un punto específico del historial mediante `retrocederHasta(...)`.

✔️ Esta implementación cumple completamente el requisito de utilizar una **pila dinámica** para acciones reversibles en el sistema.

 
---

## 🧱 Tecnologías Usadas

* **Java 17+**
* **Swing**
* **RSyntaxTextArea**
* **JTree y JTabbedPane**
* **POO y estructuras de datos personalizadas**

---

## 🗂 Estructura Inicial del Proyecto

```
/Source Package
/ikode
  PanelEditor.java
  NodoArchivo.java
  NodoHistorial.java
  ListaHistorial.java
```

 
---

## 📌 Próximos Pasos

- Implementar historial con lista enlazada.
- Agregar soporte para pila de acciones reversibles.
- Usar cola con prioridad para tareas pendientes.
- Añadir buscador basado en árbol AVL o ABB.
- Documentar diagrama de clases y decisiones técnicas.

---

## 📷 Capturas (opcional)

*(Aquí se colocaran imágenes del GUI una vez terminado)*

---

## 📜 Licencia

Proyecto académico para fines educativos. No distribuible comercialmente.

 
