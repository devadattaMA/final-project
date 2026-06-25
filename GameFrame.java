import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private Player currentPlayer;
    private PlayerService playerService;
    private GameLogic gameLogic;

    private JButton[] buttons;
    private JLabel lblStatus;
    private JButton btnRestart;
    private JButton btnMenu;

    private boolean gameOver = false;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic = new GameLogic();
        buildUI();
    }

    private void buildUI() {
        setTitle("Tic-Tac-Toe - Playing as " + currentPlayer.getUsername());
        setSize(380, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Status label at top
        lblStatus = new JLabel("Your turn! (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(lblStatus, BorderLayout.NORTH);

        // 3x3 board in center
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(new Color(230, 230, 230));
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnRestart = new JButton("Restart");
        btnRestart.setBackground(new Color(70, 130, 180));
        btnRestart.setForeground(Color.WHITE);
        btnRestart.setFont(new Font("Arial", Font.BOLD, 13));

        btnMenu = new JButton("Main Menu");
        btnMenu.setBackground(new Color(105, 105, 105));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.setFont(new Font("Arial", Font.BOLD, 13));

        bottomPanel.add(btnRestart);
        bottomPanel.add(btnMenu);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Restart resets board without updating stats
        btnRestart.addActionListener(e -> resetBoard());

        // Go back to main menu
        btnMenu.addActionListener(e -> {
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        });
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Player move
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            lblStatus.setText("Cell already taken! Pick another.");
            return;
        }
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(30, 100, 200));
        buttons[index].setEnabled(false);

        // Check if player wins
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Check draw
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Computer move
        lblStatus.setText("Computer is thinking...");
        int compIndex = gameLogic.computerMove();
        gameLogic.makeMove(compIndex, 'O');
        buttons[compIndex].setText("O");
        buttons[compIndex].setForeground(new Color(200, 50, 50));
        buttons[compIndex].setEnabled(false);

        // Check if computer wins
        if (gameLogic.checkWinner('O')) {
            finishGame("LOSE");
            return;
        }

        // Check draw again after computer move
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        lblStatus.setText("Your turn! (X)");
    }

    private void finishGame(String result) {
        gameOver = true;

        // Update stats in database
        playerService.updateStatistics(currentPlayer, result);

        String message;
        if (result.equals("WIN")) {
            message = "🎉 You Win! +10 points";
            lblStatus.setText("You won!");
        } else if (result.equals("LOSE")) {
            message = "😞 You Lose! Better luck next time.";
            lblStatus.setText("Computer won!");
        } else {
            message = "🤝 It's a Draw! +3 points";
            lblStatus.setText("It's a draw!");
        }

        // Disable all buttons
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }

        int choice = JOptionPane.showOptionDialog(this,
            message + "\n\nWhat would you like to do?",
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Play Again", "Main Menu"},
            "Play Again");

        if (choice == 0) {
            resetBoard();
        } else {
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            this.dispose();
        }
    }

    private void resetBoard() {
        gameLogic.resetBoard();
        gameOver = false;
        lblStatus.setText("Your turn! (X)");
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
            btn.setBackground(new Color(230, 230, 230));
        }
    }
}
