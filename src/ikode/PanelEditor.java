package ikode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
 
public class PanelEditor extends javax.swing.JFrame {
    
    private DefaultTreeModel modeloArbol = new DefaultTreeModel(null);
    private ArchivoManager archivoManager = new ArchivoManager();
    
    //private File ultimaCarpetaSeleccionada; 
    //llamamos a historial de archivos recientes
    private HistorialRecientes historial = new HistorialRecientes();
    
    //llamamos a historial de acciones
    private HistorialAcciones historialAcciones = new HistorialAcciones(); 
    private Map<String, String> rutasAbiertas = new HashMap<>();
    
    //cola de priodidad para guardar
    private ColaConPrioridad colaTareas;


    //private Map<String, Component> pestañasAbiertas = new HashMap<>();
    private static final int MAX_PESTAÑAS = 50;
    private String[] pestañasAbiertas = new String[MAX_PESTAÑAS];
    private int totalPestañas = 0;
    
    //Arbol de busqueda 
  
    ABBIndice indiceActual = new ABBIndice(); 
    private Map<String, Integer> mapaFuncionLinea = new HashMap<>();
     // Modelo de lista para mostrar funciones en la interfaz
    private javax.swing.DefaultListModel<String> modeloListaFunciones = new DefaultListModel<>();
    
    
    
    
    public PanelEditor(String titulo, String carpeta) {
        setTitle(titulo != null ? titulo : "iKode :: Nuevo proyecto");
        
        initComponents();
        
        treeArchivos.setModel(modeloArbol);
        
        getContentPane().setLayout(new BorderLayout());
        
        //Se asigna el textarea de consola a class console.
        console.init(consola); // Inicializa el área y listener
        // Mensajes de bienvenida del sistema (sin prompt al inicio)
        console.log("iKode - Editor de código | vs3.5.4");
        console.log("[© Ronald Ramos - iKode.live] - {License for free use.} [awarded to 200.37.57.131]");
        // Muestra el primer prompt (porque init() ya no lo hace automáticamente)
        console.showPrompt();  // <<< necesitas añadir este método si aún no lo tienes
        // Ahora asignamos los comandos
        console.setCommandHandler(cmd -> {
            switch (cmd.toLowerCase()) {
                case "clear" -> { consola.setText("");  return ""; }
                case "install" -> {
                    console.setBusy(true);
                    new Thread(() -> {
                        for (int i = 0; i <= 10; i++) {
                            String bar = "[" + "*".repeat(i) + ".".repeat(10 - i) + "]";
                            SwingUtilities.invokeLater(() ->
                                console.replaceFromPrompt("Instalando " + bar)
                            );
                            try { Thread.sleep(300); } catch (Exception ignored) {}
                        }
                        console.log("Instalación completa");
                        console.setBusy(false);
                        console.showPrompt(); // <- mostrar prompt al terminar
                    }).start();
                    return "";
                }
                case "exit" -> { System.exit(0);  return ""; }
                default -> {  return console.PROMPT + " '" + cmd + "' no se reconoce como comando."; }
            }
        });
        
        
        // Agregar un listener de drop para detectar cuando se arrastra un archivo
        tabs.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {  return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor); }// Asegurarse de que se está arrastrando un archivo

            @Override
            public boolean importData(TransferSupport support) {
                // Obtener el archivo arrastrado
                try {
                    java.util.List<File> files = (java.util.List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : files) {
                        abrirArchivoEnPestaña(file);  // Llamar al método para abrir el archivo
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        
        actualizarListaHistorial();
        
        // Inicializar la cola con la lógica de guardado
        colaTareas = new ColaConPrioridad(this::guardarArchivo);
         
    }
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        panelHead = new javax.swing.JPanel();
        panelBody = new javax.swing.JPanel();
        splitPanel = new javax.swing.JSplitPane();
        panelIzquierdo = new javax.swing.JPanel();
        splitIzquierda = new javax.swing.JSplitPane();
        panelTreeArchivos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeArchivos = new javax.swing.JTree();
        panelEsqHist = new javax.swing.JPanel();
        splitIzquierdoEsqHist = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listEsquema = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listHistorial = new javax.swing.JList<>();
        panelDerecho = new javax.swing.JPanel();
        splitDerecho = new javax.swing.JSplitPane();
        panelTabs = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        panelTerminal = new javax.swing.JPanel();
        tabTerminal = new javax.swing.JTabbedPane();
        tabPanelTerminal = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        consola = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        panelFooter = new javax.swing.JPanel();
        lblErrores = new javax.swing.JLabel();
        lblMensajes = new javax.swing.JLabel();
        lblLineaColumna = new javax.swing.JLabel();
        lblLenguaje = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        menuNuevoArchivo = new javax.swing.JMenuItem();
        menuNuevoVentana = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuAbrirArchivo = new javax.swing.JMenuItem();
        menuAbrirCarpeta = new javax.swing.JMenuItem();
        menuAbrirReciente = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuGuardarArchivo = new javax.swing.JMenuItem();
        menuGuardarTodo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuCerrarArchivo = new javax.swing.JMenuItem();
        menuCerrarTodo = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuSalir = new javax.swing.JMenuItem();
        menu2 = new javax.swing.JMenu();
        menuEditarAtras = new javax.swing.JMenuItem();
        menuEditarAdelante = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(204, 204, 204));
        panel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panel.setRequestFocusEnabled(false);
        panel.setLayout(new java.awt.BorderLayout());

        panelHead.setBackground(new java.awt.Color(255, 255, 255));
        panelHead.setMinimumSize(new java.awt.Dimension(10, 100));
        panelHead.setPreferredSize(new java.awt.Dimension(843, 25));

        javax.swing.GroupLayout panelHeadLayout = new javax.swing.GroupLayout(panelHead);
        panelHead.setLayout(panelHeadLayout);
        panelHeadLayout.setHorizontalGroup(
            panelHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 790, Short.MAX_VALUE)
        );
        panelHeadLayout.setVerticalGroup(
            panelHeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        panel.add(panelHead, java.awt.BorderLayout.NORTH);

        panelBody.setBackground(new java.awt.Color(255, 255, 255));

        splitPanel.setDividerSize(4);

        panelIzquierdo.setMinimumSize(new java.awt.Dimension(200, 100));

        splitIzquierda.setDividerSize(3);
        splitIzquierda.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelTreeArchivos.setBackground(new java.awt.Color(255, 255, 255));
        panelTreeArchivos.setMinimumSize(new java.awt.Dimension(0, 300));

        treeArchivos.setBorder(javax.swing.BorderFactory.createTitledBorder("Archivos"));
        treeArchivos.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeArchivosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeArchivos);

        javax.swing.GroupLayout panelTreeArchivosLayout = new javax.swing.GroupLayout(panelTreeArchivos);
        panelTreeArchivos.setLayout(panelTreeArchivosLayout);
        panelTreeArchivosLayout.setHorizontalGroup(
            panelTreeArchivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        panelTreeArchivosLayout.setVerticalGroup(
            panelTreeArchivosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        splitIzquierda.setTopComponent(panelTreeArchivos);

        splitIzquierdoEsqHist.setDividerSize(4);
        splitIzquierdoEsqHist.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        listEsquema.setBorder(javax.swing.BorderFactory.createTitledBorder("Esquema"));
        listEsquema.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listEsquema.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listEsquemaValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(listEsquema);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        splitIzquierdoEsqHist.setTopComponent(jPanel1);

        listHistorial.setBorder(javax.swing.BorderFactory.createTitledBorder("Historial"));
        listHistorial.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listHistorial.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listHistorialValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listHistorial);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );

        splitIzquierdoEsqHist.setRightComponent(jPanel2);

        javax.swing.GroupLayout panelEsqHistLayout = new javax.swing.GroupLayout(panelEsqHist);
        panelEsqHist.setLayout(panelEsqHistLayout);
        panelEsqHistLayout.setHorizontalGroup(
            panelEsqHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitIzquierdoEsqHist)
        );
        panelEsqHistLayout.setVerticalGroup(
            panelEsqHistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitIzquierdoEsqHist)
        );

        splitIzquierda.setRightComponent(panelEsqHist);

        javax.swing.GroupLayout panelIzquierdoLayout = new javax.swing.GroupLayout(panelIzquierdo);
        panelIzquierdo.setLayout(panelIzquierdoLayout);
        panelIzquierdoLayout.setHorizontalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitIzquierda)
        );
        panelIzquierdoLayout.setVerticalGroup(
            panelIzquierdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitIzquierda)
        );

        splitPanel.setLeftComponent(panelIzquierdo);

        splitDerecho.setBackground(new java.awt.Color(255, 255, 255));
        splitDerecho.setDividerSize(2);
        splitDerecho.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelTabs.setBackground(new java.awt.Color(255, 255, 255));
        panelTabs.setMinimumSize(new java.awt.Dimension(100, 300));

        tabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelTabsLayout = new javax.swing.GroupLayout(panelTabs);
        panelTabs.setLayout(panelTabsLayout);
        panelTabsLayout.setHorizontalGroup(
            panelTabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
        );
        panelTabsLayout.setVerticalGroup(
            panelTabsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        splitDerecho.setTopComponent(panelTabs);

        panelTerminal.setBackground(new java.awt.Color(255, 255, 255));

        consola.setColumns(20);
        consola.setRows(5);
        jScrollPane4.setViewportView(consola);

        javax.swing.GroupLayout tabPanelTerminalLayout = new javax.swing.GroupLayout(tabPanelTerminal);
        tabPanelTerminal.setLayout(tabPanelTerminalLayout);
        tabPanelTerminalLayout.setHorizontalGroup(
            tabPanelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
        );
        tabPanelTerminalLayout.setVerticalGroup(
            tabPanelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
        );

        tabTerminal.addTab("Terminal", tabPanelTerminal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        tabTerminal.addTab("tab2", jPanel3);

        javax.swing.GroupLayout panelTerminalLayout = new javax.swing.GroupLayout(panelTerminal);
        panelTerminal.setLayout(panelTerminalLayout);
        panelTerminalLayout.setHorizontalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTerminal)
        );
        panelTerminalLayout.setVerticalGroup(
            panelTerminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabTerminal, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        splitDerecho.setRightComponent(panelTerminal);

        javax.swing.GroupLayout panelDerechoLayout = new javax.swing.GroupLayout(panelDerecho);
        panelDerecho.setLayout(panelDerechoLayout);
        panelDerechoLayout.setHorizontalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitDerecho)
        );
        panelDerechoLayout.setVerticalGroup(
            panelDerechoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitDerecho)
        );

        splitPanel.setRightComponent(panelDerecho);

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPanel)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPanel)
        );

        panel.add(panelBody, java.awt.BorderLayout.CENTER);

        panelFooter.setBackground(new java.awt.Color(255, 255, 255));

        lblErrores.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lblErrores.setText("x:  >:");

        lblMensajes.setText("...");

        lblLineaColumna.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lblLineaColumna.setText("ln: 0 col: 0");
        lblLineaColumna.setToolTipText("");

        lblLenguaje.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lblLenguaje.setText("Html");

        javax.swing.GroupLayout panelFooterLayout = new javax.swing.GroupLayout(panelFooter);
        panelFooter.setLayout(panelFooterLayout);
        panelFooterLayout.setHorizontalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFooterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblErrores, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addComponent(lblMensajes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(lblLineaColumna, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(lblLenguaje, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelFooterLayout.setVerticalGroup(
            panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFooterLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addGroup(panelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblErrores)
                    .addComponent(lblMensajes)
                    .addComponent(lblLineaColumna)
                    .addComponent(lblLenguaje)))
        );

        panel.add(panelFooter, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        menu1.setText("Archivo");

        menuNuevoArchivo.setText("Nuevo archivo");
        menu1.add(menuNuevoArchivo);

        menuNuevoVentana.setText("Nueva ventana");
        menuNuevoVentana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoVentanaActionPerformed(evt);
            }
        });
        menu1.add(menuNuevoVentana);
        menu1.add(jSeparator1);

        menuAbrirArchivo.setText("Abrir archivo");
        menuAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirArchivoActionPerformed(evt);
            }
        });
        menu1.add(menuAbrirArchivo);

        menuAbrirCarpeta.setText("Abrir carpeta");
        menuAbrirCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirCarpetaActionPerformed(evt);
            }
        });
        menu1.add(menuAbrirCarpeta);

        menuAbrirReciente.setText("Abrir reciente");
        menu1.add(menuAbrirReciente);
        menu1.add(jSeparator2);

        menuGuardarArchivo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuGuardarArchivo.setText("Guardar archivo");
        menuGuardarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarArchivoActionPerformed(evt);
            }
        });
        menu1.add(menuGuardarArchivo);

        menuGuardarTodo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuGuardarTodo.setText("Guardar todo");
        menuGuardarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarTodoActionPerformed(evt);
            }
        });
        menu1.add(menuGuardarTodo);
        menu1.add(jSeparator3);

        menuCerrarArchivo.setText("Cerrar archivo");
        menu1.add(menuCerrarArchivo);

        menuCerrarTodo.setText("Cerrar todo");
        menu1.add(menuCerrarTodo);
        menu1.add(jSeparator5);

        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        menu1.add(menuSalir);

        menu.add(menu1);

        menu2.setText("Editar");

        menuEditarAtras.setText("Atras");
        menuEditarAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarAtrasActionPerformed(evt);
            }
        });
        menu2.add(menuEditarAtras);

        menuEditarAdelante.setText("Adelante");
        menuEditarAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarAdelanteActionPerformed(evt);
            }
        });
        menu2.add(menuEditarAdelante);

        menu.add(menu2);

        setJMenuBar(menu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuAbrirCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirCarpetaActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int opcion = chooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            File carpeta = chooser.getSelectedFile();
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode(carpeta.getName());
            cargarArchivosEnArbol(carpeta, raiz);

            modeloArbol.setRoot(raiz);
            modeloArbol.reload();
        }
    }//GEN-LAST:event_menuAbrirCarpetaActionPerformed

    private void treeArchivosValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeArchivosValueChanged
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) treeArchivos.getLastSelectedPathComponent();
        if (nodo == null || !nodo.isLeaf()) return;
        NodoArchivo datos = (NodoArchivo) nodo.getUserObject();
        File archivo = new File(datos.getRutaCompleta());
        if (!archivo.exists() || archivo.isDirectory()) return;
        registrarPestaña(archivo.getName());
        abrirArchivoEnPestaña(archivo);
        
        historial.agregar(archivo.getName(), archivo.getAbsolutePath());
        actualizarMenuAbrirReciente();
        
        historialAcciones.registrar(new NodoAccion(NodoAccion.Tipo.ABRIR,archivo.getName(), archivo.getAbsolutePath()) );
        actualizarListaHistorial();
         
        console.log( "open: " + archivo.getAbsolutePath() + " ::: " + archivo.getName() );
    }//GEN-LAST:event_treeArchivosValueChanged

    private void menuNuevoVentanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoVentanaActionPerformed
        PanelEditor nueva = new PanelEditor("iKode :: Nuevo proyecto",null); // o puedes pasar parámetros si quieres 
        nueva.setVisible(true);
    }//GEN-LAST:event_menuNuevoVentanaActionPerformed

    private void menuAbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirArchivoActionPerformed
        
        JFileChooser chooser = new JFileChooser(); // Crea un selector de archivos
        int opcion = chooser.showOpenDialog(this); // Muestra el diálogo de apertura
        if (opcion == JFileChooser.APPROVE_OPTION) { // Si el usuario selecciona un archivo
            File archivo = chooser.getSelectedFile(); // Obtiene el archivo seleccionado
            abrirArchivoEnPestaña(archivo); // Abre el archivo en una nueva pestaña
        }
     
    }//GEN-LAST:event_menuAbrirArchivoActionPerformed

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuEditarAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarAtrasActionPerformed
            NodoAccion acc = historialAcciones.deshacer();
            if (acc != null) {
                if (acc.getTipo() == NodoAccion.Tipo.ABRIR) {
                    // Deshacer abrir → cerrar pestaña
                    cerrarPestañaPorNombre(acc.getArchivo());
                } else if (acc.getTipo() == NodoAccion.Tipo.CERRAR) {
                    // Deshacer cerrar → volver a abrir
                    abrirArchivoDesdeRuta(acc.getRuta());
                }
            }
            actualizarListaHistorial();
    }//GEN-LAST:event_menuEditarAtrasActionPerformed

    private void menuEditarAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarAdelanteActionPerformed
        NodoAccion acc = historialAcciones.rehacer();
        if (acc != null) {
            if (acc.getTipo() == NodoAccion.Tipo.ABRIR) {
                // Rehacer abrir → volver a abrir
                abrirArchivoDesdeRuta(acc.getRuta());
            } else if (acc.getTipo() == NodoAccion.Tipo.CERRAR) {
                // Rehacer cerrar → volver a cerrar
                cerrarPestañaPorNombre(acc.getArchivo());
            }
        }
        actualizarListaHistorial();
    }//GEN-LAST:event_menuEditarAdelanteActionPerformed

    private void listHistorialValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listHistorialValueChanged
        if (!evt.getValueIsAdjusting()) {
            int index = listHistorial.getSelectedIndex();
            if (index >= 0) {
                // Revertir hasta esa posición exacta
                List<NodoAccion> accionesRevertidas = historialAcciones.retrocederHasta(index + 1); // +1 para dejar incluida esa acción

                for (NodoAccion acc : accionesRevertidas) {
                    console.log("Revirtiendo acción: " + acc.getTipo() + " → " + acc.getArchivo());
                    if (acc.getTipo() == NodoAccion.Tipo.ABRIR) {
                        cerrarPestañaPorNombre(acc.getArchivo());
                    } else if (acc.getTipo() == NodoAccion.Tipo.CERRAR) {
                        abrirArchivoDesdeRuta(acc.getRuta());
                    }
                }

                actualizarListaHistorial();
            }
        }
    }//GEN-LAST:event_listHistorialValueChanged

    private void menuGuardarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarArchivoActionPerformed
        int index = tabs.getSelectedIndex();
        if (index >= 0) {
            String nombre = tabs.getTitleAt(index);
            colaTareas.agregarTarea("Guardar " + nombre, 1); // prioridad alta
            colaTareas.procesarTodo(); // puedes reemplazar esto luego por procesado asíncrono
        }
    }//GEN-LAST:event_menuGuardarArchivoActionPerformed

    private void menuGuardarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarTodoActionPerformed
        for (int i = 0; i < tabs.getTabCount(); i++) {
            String nombre = tabs.getTitleAt(i);
            colaTareas.agregarTarea("Guardar " + nombre, 2); // prioridad media
        }
        colaTareas.procesarTodo();
    }//GEN-LAST:event_menuGuardarTodoActionPerformed

    private void listEsquemaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listEsquemaValueChanged
        if (!evt.getValueIsAdjusting()) {
            String seleccionado = listEsquema.getSelectedValue();
            if (seleccionado != null && mapaFuncionLinea.containsKey(seleccionado)) {
                int linea = mapaFuncionLinea.get(seleccionado);
                RSyntaxTextArea textArea = (RSyntaxTextArea) ((RTextScrollPane) tabs.getSelectedComponent()).getTextArea();
                try {
                    int offset = textArea.getLineStartOffset(linea);
                    textArea.setCaretPosition(offset);
                    textArea.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_listEsquemaValueChanged

    private void tabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsStateChanged
        Component comp = tabs.getSelectedComponent();
        if (comp instanceof RTextScrollPane scroll) {
            RSyntaxTextArea currentTextArea = (RSyntaxTextArea) scroll.getTextArea();
            escanearFunciones(currentTextArea);
        }
    }//GEN-LAST:event_tabsStateChanged
     
    private void actualizarListaHistorial() {
        DefaultListModel<String> modelo = new DefaultListModel<>();

        for (NodoAccion acc : historialAcciones.getHistorial()) {
            String texto = acc.toString();
            if (!acc.estaActiva()) {
                texto = "(deshecha) " + texto;
            }
            modelo.addElement(texto);
        }

        listHistorial.setModel(modelo);
    }
    
    private void cerrarPestañaPorNombre(String nombre) {
    for (int i = 0; i < tabs.getTabCount(); i++) {
        String titulo = tabs.getTitleAt(i);
        if (titulo.equals(nombre)) {
            tabs.remove(i);
            return;
        }
    }
}

    private void abrirArchivoDesdeRuta(String ruta) { console.log("ruta: " + ruta);
    File archivo = new File(ruta);
    if (archivo.exists()) {
        if (!estaPestañaAbierta(archivo.getName())) { console.log("archivo cerrado esta abriendose");
            abrirArchivoEnPestaña(archivo);
        } else { console.log("archivo cerrado existe, solo enfocando");
            // Si ya está abierta, solo enfócala
            for (int i = 0; i < tabs.getTabCount(); i++) {
                if (tabs.getTitleAt(i).equals(archivo.getName())) {
                    tabs.setSelectedIndex(i);
                    break;
                }
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "El archivo ya no existe: " + ruta);
    }
}

    
    private void actualizarMenuAbrirReciente() {
        menuAbrirReciente.removeAll();

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
    }

    
    private void cargarArchivosEnArbol(File carpeta, DefaultMutableTreeNode nodoPadre) {
        File[] elementos = carpeta.listFiles();
        if (elementos != null) {
            for (File f : elementos) {
                DefaultMutableTreeNode nodo = new DefaultMutableTreeNode( new NodoArchivo(f.getName(), f.getAbsolutePath()));
                nodoPadre.add(nodo);
                if (f.isDirectory()) {
                    cargarArchivosEnArbol(f, nodo);
                } else {
                    archivoManager.agregar(f.getAbsolutePath());
                }
            }
        }
    }


    private void registrarPestaña(String nombreArchivo) {
            // Evitar duplicados
            for (int i = 0; i < totalPestañas; i++) {
                if (pestañasAbiertas[i] != null && pestañasAbiertas[i].equals(nombreArchivo)) {
                    return; // Ya está registrada
                }
            }
            // Registrar si hay espacio
            if (totalPestañas < MAX_PESTAÑAS) {
                pestañasAbiertas[totalPestañas] = nombreArchivo;
                totalPestañas++;
            } else {
                console.log("Límite máximo de pestañas alcanzado.");
            }
        }


        private boolean estaPestañaAbierta(String titulo) {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals(titulo)) {
                tabs.setSelectedIndex(i); // enfocarla si ya está
                return true;
            }
        }
        return false;
    }
      
        
// === Utilidad para extensión ======================
private static String getExtension(String filename) {
    int dot = filename.lastIndexOf('.');
    return (dot >= 0) ? filename.substring(dot + 1).toLowerCase() : "";
}

// === Mapas de extensiones a Syntax =================
private static final Map<String, String> CODE_SYNTAX = Map.ofEntries(
    Map.entry("java",  SyntaxConstants.SYNTAX_STYLE_JAVA),
    Map.entry("js",    SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT),
    Map.entry("html",  SyntaxConstants.SYNTAX_STYLE_HTML),
    Map.entry("css",   SyntaxConstants.SYNTAX_STYLE_CSS),
    Map.entry("php",   SyntaxConstants.SYNTAX_STYLE_PHP),
    Map.entry("py",    SyntaxConstants.SYNTAX_STYLE_PYTHON),
    Map.entry("sql",   SyntaxConstants.SYNTAX_STYLE_SQL),
    Map.entry("xml",   SyntaxConstants.SYNTAX_STYLE_XML),
    Map.entry("json",  SyntaxConstants.SYNTAX_STYLE_JSON),
    Map.entry("c",     SyntaxConstants.SYNTAX_STYLE_C),
    Map.entry("cpp",   SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS),
    Map.entry("sh",    SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL)
);

private static final Set<String> IMAGE_EXT = Set.of( "png", "jpg", "jpeg", "gif", "bmp", "svg");
private static final Set<String> TEXT_LIKE = Set.of( "txt", "md", "csv", "log" );  // texto plano sin resaltado
// === Método principal ==============================
private void abrirArchivoEnPestaña(File archivo) {
    String nombreArchivo = archivo.getName();

    if (estaPestañaAbierta(nombreArchivo)) {
        console.log("Archivo ya abierto");
        return;
    }
     

    String ext = getExtension(nombreArchivo);

    try {
        JComponent contenidoTab;   // será el scroll o el panel según el caso
        

        /*--------- 4. Crear pestaña con botón de cierre --*/
        JPanel tabPanel = new JPanel(new BorderLayout());
        tabPanel.setOpaque(false);

        JLabel lbl = new JLabel(nombreArchivo + " ");
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // espacio entre texto y botón
        
        /*--------- 1. Código fuente reconocido ----------*/
        if (CODE_SYNTAX.containsKey(ext) || TEXT_LIKE.contains(ext)) {
            String contenido = Files.readString(archivo.toPath());

            RSyntaxTextArea area = new RSyntaxTextArea();
            area.setSyntaxEditingStyle(CODE_SYNTAX.getOrDefault(ext, SyntaxConstants.SYNTAX_STYLE_NONE) );
            area.setText(contenido);
            area.setEditable(true);
            area.setCodeFoldingEnabled(true);
 
            // Agrega el listener de cambios
            area.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                private boolean modificado = false;

                private void actualizarTitulo() {
                    boolean textoCambiado = !area.getText().equals(contenido);
                    if (textoCambiado && !modificado) {
                        lbl.setText("*" + nombreArchivo); // cambia el título
                        modificado = true;
                    } else if (!textoCambiado && modificado) {
                        lbl.setText(nombreArchivo); // lo vuelve al original
                        modificado = false;
                    }
                }

                public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarTitulo(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarTitulo(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarTitulo(); }
            });
            
            area.addCaretListener(e -> actualizarBarraEstado());
            
            contenidoTab = new RTextScrollPane(area);
            
            escanearFunciones(area);
             
        /*--------- 2. Imagen ----------------------------*/
        } else if (IMAGE_EXT.contains(ext)) {

            ImageIcon icon = new ImageIcon(archivo.getAbsolutePath());
            JLabel label  = new JLabel(icon);
            label.setHorizontalAlignment(JLabel.CENTER);
            contenidoTab = new JScrollPane(label);

        /*--------- 3. No soportado ----------------------*/
        } else {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel("<html><center>Vista previa no soportada<br>(" + ext + ")</center></html>",JLabel.CENTER), BorderLayout.CENTER);
            contenidoTab = panel;
        }
        
        /*--------- 4. Crear pestaña con botón de cierre --*/
         
        JButton btnCerrar = new JButton("x");
        btnCerrar.setMargin(new Insets(0, 0, 0, 0));
        btnCerrar.setPreferredSize(new Dimension(16, 16));
        btnCerrar.setBorder(BorderFactory.createEmptyBorder());
        btnCerrar.setFocusPainted(false);
        btnCerrar.setContentAreaFilled(false);
        // Hover
        btnCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {   btnCerrar.setContentAreaFilled(true);  }
            public void mouseExited(java.awt.event.MouseEvent evt) { btnCerrar.setContentAreaFilled(false);}
        });
        btnCerrar.addActionListener(e -> {
            int i = tabs.indexOfTabComponent(tabPanel);
            if (i != -1) {
                String nombre = tabs.getTitleAt(i);
                if (confirmarGuardadoAntesDeCerrar(nombre)) {
                    tabs.remove(i);
                    String ruta = rutasAbiertas.get(nombre.replace("*", ""));
                    historialAcciones.registrar(new NodoAccion(NodoAccion.Tipo.CERRAR, nombre, ruta));
                    rutasAbiertas.remove(nombre.replace("*", ""));
                    actualizarListaHistorial(); 
                }
            }
        });
 
        // Alinear el texto a la izquierda y el botón a la derecha
        tabPanel.add(lbl, BorderLayout.CENTER);
        tabPanel.add(btnCerrar, BorderLayout.EAST);
        
        rutasAbiertas.put(nombreArchivo, archivo.getAbsolutePath());
        
        tabs.addChangeListener(e -> actualizarBarraEstado());
        
        tabs.addTab(nombreArchivo, contenidoTab);
        tabs.setTabComponentAt(tabs.getTabCount() - 1, tabPanel);
        tabs.setSelectedComponent(contenidoTab);
        
        
                
    } catch (IOException ex) {
        console.error("Error al abrir el archivo: " + ex.getMessage());
    }
}


private boolean confirmarGuardadoAntesDeCerrar(String nombreArchivo) {
    if (nombreArchivo.startsWith("*")) {
        int r = JOptionPane.showConfirmDialog(
            this,
            "¿Deseas guardar los cambios en " + nombreArchivo.substring(1) + "?",
            "Cambios sin guardar",
            JOptionPane.YES_NO_CANCEL_OPTION
        );

        if (r == JOptionPane.YES_OPTION) {
            colaTareas.agregarTarea("Guardar " + nombreArchivo.substring(1), 1);
            colaTareas.procesarTodo();
            return true;
        } else if (r == JOptionPane.NO_OPTION) {
            return true; // Cierra sin guardar
        } else {
            return false; // Cancelar
        }
    }
    return true;
}

private void guardarArchivo(String nombreArchivo) {
    try {
        String ruta = rutasAbiertas.get(nombreArchivo.replace("*", ""));
        if (ruta == null) {
            console.error("Ruta no encontrada para " + nombreArchivo);
            return;
        }

        File archivo = new File(ruta);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            Component componente = tabs.getComponentAt(i);
            String titulo = tabs.getTitleAt(i).replace("*", "");

            if (titulo.equals(nombreArchivo.replace("*", ""))) {
                if (componente instanceof RTextScrollPane scrollPane) {
                    Component view = scrollPane.getViewport().getView();
                    if (view instanceof RSyntaxTextArea area) {
                        String contenido = area.getText();
                        Files.writeString(archivo.toPath(), contenido);

                        // Quitar asterisco del título visual
                        Component tabHeader = tabs.getTabComponentAt(i);
                        if (tabHeader instanceof JPanel panel) {
                            for (Component c : panel.getComponents()) {
                                if (c instanceof JLabel lbl) {
                                    lbl.setText(titulo); // Quita el asterisco
                                }
                            }
                        } 
                        console.log("💾 Archivo guardado: " + titulo);
                        return;
                    }
                }
            }
        }

        console.error("No se encontró el editor de texto para: " + nombreArchivo);
    } catch (IOException ex) {
        console.error("Error al guardar el archivo: " + ex.getMessage());
    }
}


private void analizarEstructura(String texto, ABBIndice indice) {
    String[] lineas = texto.split("\n");
    for (int i = 0; i < lineas.length; i++) {
        String linea = lineas[i].trim();

        // Detectar clases
        if (linea.matches("class\\s+\\w+.*")) {
            String nombre = linea.split("\\s+")[1].split("\\{")[0];
            indice.insertar("[Clase] " + nombre, i + 1);
        }

        // Detectar funciones
        else if (linea.matches(".*\\s+\\w+\\s*\\(.*\\)\\s*\\{?")) {
            String[] partes = linea.split("\\(")[0].split("\\s+");
            String nombre = partes[partes.length - 1];
            if (!nombre.equals("if") && !nombre.equals("for") && !nombre.equals("while") && !nombre.equals("switch"))
                indice.insertar("  ↳ " + nombre + "()", i + 1);
        }
    }
}

 
private void escanearFunciones(RSyntaxTextArea textArea) {
        modeloListaFunciones.clear();  // Limpia la lista actual
        mapaFuncionLinea.clear();      // Limpia el mapa de nombres → línea

        String[] lineas = textArea.getText().split("\n"); // Separa el texto en líneas
        String claseActual = null; // Guarda el nombre de la clase actual (si se detecta)

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim(); // Elimina espacios a los lados

            // Detecta declaración de clase de forma simple (public/private/protected class Nombre)
            if (linea.matches("(public |private |protected )?class \\w+.*")) {
                claseActual = linea.split(" ")[linea.split(" ").length - 1].replace("{", ""); // Extrae el nombre de la clase
                modeloListaFunciones.addElement("🧱 Clase: " + claseActual); // Agrega a la lista con ícono
                mapaFuncionLinea.put("🧱 Clase: " + claseActual, i); // Guarda la línea
            }

            // Detecta métodos o funciones de forma general (líneas con paréntesis y llave)
            else if (linea.matches(".*\\s+\\w+\\(.*\\).*\\{?")) {
                String nombreMetodo = linea.replaceAll("\\{", "").trim(); // Limpia la llave si hay
                String etiqueta = (claseActual != null ? "  ↳ " : "") + "🔧 " + nombreMetodo; // Añade ícono y flecha si está dentro de clase
                modeloListaFunciones.addElement(etiqueta); // Agrega a la lista
                mapaFuncionLinea.put(etiqueta, i); // Guarda la línea donde aparece
            }
        }

        listEsquema.setModel(modeloListaFunciones); // Asocia el modelo a la lista visual
    }


private void actualizarBarraEstado() {
    try {
        RTextScrollPane sp = (RTextScrollPane) tabs.getSelectedComponent();
        RSyntaxTextArea textArea = (RSyntaxTextArea) sp.getViewport().getView();

        int pos = textArea.getCaretPosition();
        int linea = textArea.getLineOfOffset(pos);
        int columna = pos - textArea.getLineStartOffset(linea);

        lblLineaColumna.setText("ln: " + (linea + 1) + ", col: " + (columna + 1));

        // Obtener el título de la pestaña actual
        int index = tabs.getSelectedIndex();
        String nombre = (index >= 0) ? tabs.getTitleAt(index).replace("*", "") : "?";

        // Obtener la extensión
        int punto = nombre.lastIndexOf('.');
        String extension = (punto != -1) ? nombre.substring(punto + 1) : "?";
        lblLenguaje.setText(extension);

        // Buscar errores y advertencias
        Pattern errorPattern = Pattern.compile("\\b(error|exception|undefined|syntax error)\\b", Pattern.CASE_INSENSITIVE);
        Pattern warningPattern = Pattern.compile("\\b(warning|deprecated|caution)\\b", Pattern.CASE_INSENSITIVE);

        long errores = textArea.getText().lines().filter(line -> errorPattern.matcher(line).find()).count();
        long advertencias = textArea.getText().lines().filter(line -> warningPattern.matcher(line).find()).count();

        lblErrores.setText("x: " + errores + " >: " + advertencias);

    } catch (BadLocationException ex) {
        lblLineaColumna.setText("ln: ?, col: ?");
    } catch (Exception ex) {
        lblLineaColumna.setText("ln: ?, col: ?");
        lblLenguaje.setText("?");
        lblErrores.setText("x: 0 >: 0");
    }
}



    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelEditor(null,null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consola;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel lblErrores;
    private javax.swing.JLabel lblLenguaje;
    private javax.swing.JLabel lblLineaColumna;
    private javax.swing.JLabel lblMensajes;
    private javax.swing.JList<String> listEsquema;
    private javax.swing.JList<String> listHistorial;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenu menu1;
    private javax.swing.JMenu menu2;
    private javax.swing.JMenuItem menuAbrirArchivo;
    private javax.swing.JMenuItem menuAbrirCarpeta;
    private javax.swing.JMenu menuAbrirReciente;
    private javax.swing.JMenuItem menuCerrarArchivo;
    private javax.swing.JMenuItem menuCerrarTodo;
    private javax.swing.JMenuItem menuEditarAdelante;
    private javax.swing.JMenuItem menuEditarAtras;
    private javax.swing.JMenuItem menuGuardarArchivo;
    private javax.swing.JMenuItem menuGuardarTodo;
    private javax.swing.JMenuItem menuNuevoArchivo;
    private javax.swing.JMenuItem menuNuevoVentana;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelDerecho;
    private javax.swing.JPanel panelEsqHist;
    private javax.swing.JPanel panelFooter;
    private javax.swing.JPanel panelHead;
    private javax.swing.JPanel panelIzquierdo;
    private javax.swing.JPanel panelTabs;
    private javax.swing.JPanel panelTerminal;
    private javax.swing.JPanel panelTreeArchivos;
    private javax.swing.JSplitPane splitDerecho;
    private javax.swing.JSplitPane splitIzquierda;
    private javax.swing.JSplitPane splitIzquierdoEsqHist;
    private javax.swing.JSplitPane splitPanel;
    private javax.swing.JPanel tabPanelTerminal;
    private javax.swing.JTabbedPane tabTerminal;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTree treeArchivos;
    // End of variables declaration//GEN-END:variables
}
