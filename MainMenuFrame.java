import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private Player currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        buildUI();
    }

    private void buildUI() {
        setTitle("Tic-Tac-Toe - Main Menu");
        setSize(300, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        // Welcome label
        JLabel lblWelcome = new JLabel("Hello, " + currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridy = 0;
        panel.add(lblWelcome, gbc);

        // Buttons
        btnStartGame   = createMenuButton("▶  Start Game",   new Color(46, 139, 87));
        btnStatistics  = createMenuButton("📊  My Statistics", new Color(70, 130, 180));
        btnTopScorers  = createMenuButton("🏆  Top 5 Scorers", new Color(184, 134, 11));
        btnExit        = createMenuButton("✖  Exit",          new Color(178, 34, 34));

        gbc.gridy = 1; panel.add(btnStartGame, gbc);
        gbc.gridy = 2; panel.add(btnStatistics, gbc);
        gbc.gridy = 3; panel.add(btnTopScorers, gbc);
        gbc.gridy = 4; panel.add(btnExit, gbc);

        add(panel);

        // Button actions
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton createMenuButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
