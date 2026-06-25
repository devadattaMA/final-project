import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {

    private PlayerService playerService;

    public TopScorersFrame() {
        this.playerService = new PlayerService();
        buildUI();
    }

    private void buildUI() {
        setTitle("🏆 Top 5 Scorers");
        setSize(450, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("🏆 Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Rank", "Username", "Wins", "Losses", "Draws", "Score"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        // Fetch top 5 from database
        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        int rank = 1;
        for (Player p : topPlayers) {
            model.addRow(new Object[]{
                rank++,
                p.getUsername(),
                p.getWins(),
                p.getLosses(),
                p.getDraws(),
                p.getScore()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(40); // Rank column narrow

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnClose = new JButton("Close");
        btnClose.setBackground(new Color(105, 105, 105));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(new Font("Arial", Font.BOLD, 12));
        btnClose.addActionListener(e -> this.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnClose);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }
}
