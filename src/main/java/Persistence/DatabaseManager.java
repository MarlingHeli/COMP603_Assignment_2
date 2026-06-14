package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DatabaseManager {

    private static final String URL = "jdbc:derby:FeedMeJava;create=true";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Failed to connect to DataBase");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Failed to close database connection.");
        }
    }
    // FIXED: Un-commented and fully activated to allow MainController to fetch high scores

    public HashMap<String, Integer> getHighScores() {
        HashMap<String, Integer> scores = new HashMap<>();
        // Safety metadata query via a temporary check logic instance
        DatabaseUser tempDao = new DatabaseUser(connection);
        if (!tempDao.checkTableExists("PLAYERS")) {
            System.out.println("Table not found: PLAYERS");
            return scores;
        }
        String sql = "SELECT USERNAME, HIGHSCORE FROM PLAYERS";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                String username = resultSet.getString("USERNAME");
                int highscore = resultSet.getInt("HIGHSCORE");
                scores.put(username, highscore);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get HighScores");
        }
        System.out.println("Retrieved highscores");
        return scores;
    }
}
