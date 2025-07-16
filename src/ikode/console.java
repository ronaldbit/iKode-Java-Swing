package ikode;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;

/**
 * console  ·  Terminal + Logger en un solo lugar.
 */
public class console {

    /* ---------  configuración ---------- */
    static final String PROMPT = "PS C:\\>";   // Cambia tu prompt aquí
    private static JTextArea area;                     // El JTextArea real
    private static int promptPos = 0;                  // Índice de inicio de comando
    private static boolean busy = false;
    private static final List<String> history = new ArrayList<>();
    private static int histIndex = -1;

    /* ---------  Interfaz para comandos ---------- */
    @FunctionalInterface
    public interface CommandHandler {
        /** Devuelve la respuesta que se imprimirá (o "" si ya imprimiste tú).  */
        String onCommand(String cmd);
    }
    private static CommandHandler handler;   // asignado por el usuario

    /* ---------  Inicializar ---------- */
    public static void init(JTextArea txtArea) {
        area = txtArea;
        configurarArea();
        //mostrarPrompt();
    }
    public static void setCommandHandler(CommandHandler h) { handler = h; }

    /* ---------  Logging ---------- */
    public static void log  (String msg){ append(msg); }
    public static void info (String msg){ append(msg); }
    public static void warn (String msg){ append("[WARN] "  + msg); }
    public static void error(String msg){ append("[ERROR] " + msg); }

    /* ========  IMPLEMENTACIÓN PRIVADA  ======== */

    private static void append(String txt) {
        if (area != null)
            area.append(txt + "\n");
        else
            System.out.println(txt);
    }

    private static void configurarArea() {
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new java.awt.Font("Consolas", 0, 13));

        /* Evitar edición antes del prompt */
        ((AbstractDocument) area.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb,int off,String str,AttributeSet a) throws BadLocationException {
                if (off >= promptPos) super.insertString(fb, off, str, a);
            }
            @Override public void remove(FilterBypass fb,int off,int len) throws BadLocationException {
                if (off >= promptPos) super.remove(fb, off, len);
            }
            @Override public void replace(FilterBypass fb,int off,int len,String str,AttributeSet a) throws BadLocationException {
                if (off >= promptPos) super.replace(fb, off, len, str, a);
            }
        });

        /* Key listener */
        area.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (busy) { e.consume(); return; }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> { e.consume(); procesarCmd(); }
                    case KeyEvent.VK_UP    -> { e.consume(); navegarHist(-1); }
                    case KeyEvent.VK_DOWN  -> { e.consume(); navegarHist( 1); }
                    case KeyEvent.VK_HOME  -> { area.setCaretPosition(area.getText().length()); e.consume(); }
                }
            }
        });
    }

    private static void mostrarPrompt() {
        area.append(PROMPT + " ");
        promptPos = area.getText().length();
        area.setCaretPosition(promptPos);
    }

    private static void procesarCmd() {
        String cmd = area.getText().substring(promptPos).trim();
        if (cmd.isEmpty()) { area.append("\n"); mostrarPrompt(); return; }

        history.add(cmd);
        histIndex = history.size();

        String respuesta = (handler != null) ? handler.onCommand(cmd) : "";

        if (!respuesta.isEmpty()) area.append("\n" + respuesta + "\n");
        else if (respuesta.isEmpty() && !busy) area.append("\n");

        if (!busy) mostrarPrompt();
    }

    private static void navegarHist(int delta) {
        if (history.isEmpty()) return;
        histIndex = Math.max(0, Math.min(history.size()-1, histIndex + delta));
        area.replaceRange(history.get(histIndex), promptPos, area.getText().length());
        area.setCaretPosition(area.getText().length());
    }

    /* ---------- utilidades para procesos largos ------------ */
    public static void setBusy(boolean b) { busy = b; }
    public static void replaceFromPrompt(String txt) {
        area.replaceRange(txt, promptPos, area.getText().length());
    }
    
    public static void showPrompt() {
        mostrarPrompt();
    }

}
