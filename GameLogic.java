import java.util.Random;

public class GameLogic {

    private char[] board;
    private Random random;

    public GameLogic() {
        board = new char[9];
        random = new Random();
        resetBoard();
    }

    // Clear the board for a new game
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    // Place symbol ('X' or 'O') at index. Returns false if move is invalid.
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) {
            return false;
        }
        if (board[index] != ' ') {
            return false; // Cell already taken
        }
        board[index] = symbol;
        return true;
    }

    // Check if the given symbol has won
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}              // diagonals
        };

        for (int[] pattern : patterns) {
            int a = pattern[0];
            int b = pattern[1];
            int c = pattern[2];
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false;
    }

    // Check if board is full (draw condition)
    public boolean isDraw() {
        for (char cell : board) {
            if (cell == ' ') {
                return false;
            }
        }
        return true;
    }

    // Computer picks a random empty cell and returns the index
    public int computerMove() {
        // First, try to win
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                if (checkWinner('O')) {
                    board[i] = ' '; // undo, GameFrame will call makeMove
                    return i;
                }
                board[i] = ' ';
            }
        }

        // Then, block the player from winning
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'X';
                if (checkWinner('X')) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }

        // Otherwise, pick a random empty cell
        int index;
        do {
            index = random.nextInt(9);
        } while (board[index] != ' ');

        return index;
    }

    public char[] getBoard() {
        return board;
    }
}
