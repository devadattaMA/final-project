import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // Test database connection first - check terminal for result
        DatabaseManager.testConnection();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
