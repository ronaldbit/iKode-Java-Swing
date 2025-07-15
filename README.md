
# iKode - Editor de Código con Estructuras de Datos

**Curso:** Algoritmos y Estructuras de Datos  
**Proyecto Final**  
**Autor:** [Ramos Malca, Ronald Antonio]  
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

### 📌 Arreglo Unidimensional – Pestañas Abiertas
- Se usa un arreglo de tamaño fijo (`MAX_PESTAÑAS = 50`) para registrar los archivos abiertos.
- Evita duplicados y lleva control del número total de pestañas.

---

## 📦 Estructuras de Datos Aplicadas

| Requisito                         | Estructura               | Estado       | Detalles                                                      |
|----------------------------------|--------------------------|--------------|---------------------------------------------------------------|
| Gestión de entidad principal     | Arreglo unidimensional   | ✅ Completado | Registro de pestañas abiertas (nombres de archivos).         |
| Interacción cronológica          | Lista enlazada           | ⏳ Pendiente  | Se usará para historial de archivos abiertos.                |
| Acciones reversibles             | Pila dinámica            | ⏳ Pendiente  | Para deshacer operaciones (ej: cerrar pestaña, ejecutar).    |
| Atención o cola de procesamiento| Cola con prioridad       | ⏳ Pendiente  | Para tareas agendadas o procesos automáticos.                |
| Organización jerárquica          | Árbol binario de búsqueda| ⏳ Pendiente  | Indexación o búsqueda interna de funciones/snippets.         |
| Eficiencia en búsqueda           | Árbol AVL                | ⏳ Pendiente  | Búsqueda eficiente por nombre de archivo o estructura.       |
| TAD personalizado                | Árbol de carpetas        | ✅ Completado | NodoArchivo muestra nombre pero guarda ruta completa.        |

---

## 🧱 Tecnologías Usadas

- **Java 17+**
- **Swing**
- **RSyntaxTextArea**
- **JTree y JTabbedPane**
- **POO y estructuras de datos personalizadas**

---

## 🗂 Estructura Inicial del Proyecto

/src
/vistas
PanelEditor.java
NodoArchivo.java
/estructura
ArchivoManager.java
 
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

 
