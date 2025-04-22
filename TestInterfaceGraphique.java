import javax.swing.SwingUtilities;

public class TestInterfaceGraphique {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceConfiguration().show();
            }
        });
    }
}
