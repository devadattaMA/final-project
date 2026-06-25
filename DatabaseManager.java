import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // TODO: Change USER and PASSWORD to match your SQL Server login
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=game_project;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";       // your SQL Server username
    private static final String PASSWORD = "";     // your SQL Server password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Database connection successful!");
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
