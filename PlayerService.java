import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerService {

    public Player login(String username, String password) {
        System.out.println("Trying login: [" + username + "] [" + password + "]"); // DEBUG
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            System.out.println("Connected to DB successfully"); // DEBUG
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id       = rs.getInt("id");
                String uname = rs.getString("username");
                int wins     = rs.getInt("wins");
                int losses   = rs.getInt("losses");
                int draws    = rs.getInt("draws");
                int score    = rs.getInt("score");
                conn.close();
                return new Player(id, uname, wins, losses, draws, score);
            } else {
                System.out.println("No matching user found in database"); // DEBUG
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage()); // DEBUG
        }
        return null;
    }

    public void updateStatistics(Player player, String result) {
        int additionalScore = 0;
        String sql = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        } else {
            return;
        }

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }

    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Player p = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
                conn.close();
                return p;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Get player error: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> topPlayers = new ArrayList<>();
        String sql = "SELECT TOP 5 * FROM players ORDER BY score DESC, wins DESC";

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player p = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
                topPlayers.add(p);
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Get top scorers error: " + e.getMessage());
        }

        return topPlayers;
    }
}
