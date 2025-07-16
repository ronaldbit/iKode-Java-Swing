package ikode;

public class live {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new PanelEditor(null,null).setVisible(true);
        });
    }
    
}
