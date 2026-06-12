/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:derby:FeedMeJava;create=true";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    private Connection connection;

    public DatabaseManager()
    {
        //connect to database
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Failed to connect to DataBase");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    //remember to close connection at end of game
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException ex) {
            System.out.println(
                "Failed to close database connection."
            );
        }
    }
}
    
//    //TO DO
//    //return usernames and highscores
//    public HashMap getHighScores()
//    {
//        HashMap<String,Integer> scores = new HashMap();
//        
//        if (!this.checkTableExists("PLAYERS"))
//        {
//            System.out.println("Table not found: PLAYERS");
//            return null;
//        }
//        
//        try {
//            statement = connection.createStatement();
//            String sql = "SELECT USERNAME, HIGHSCORE FROM PLAYERS";
//            resultSet = this.queryDB(sql);
//            
//            while (resultSet.next())
//            {
//                String username = resultSet.getString("USERNAME");
//                int highscore = resultSet.getInt("HIGHSCORE");
//                scores.put(username, highscore);
//            }
//            
//            this.closeResultSet();
//            this.closeStatement();
//            
//        } catch (SQLException ex) {
//            System.out.println("Failed to get HighScores");
//        }
//
//        //sort by highscores in descending order
//
//
//
//        System.out.println("Retrieved highscores");
//        return scores;
//    }
    

