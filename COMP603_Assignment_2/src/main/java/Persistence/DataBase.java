/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;   

/**
 *
 * @author hmarl
 */
public class DataBase {
    String DBusername = "admin";
    String DBpassword = "password";
    //database name: FeedMeJava
    String url = "jdbc:derby:FeedMeJava;create=true";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    DatabaseMetaData dbmd;
    
    public DataBase()
    {
        try {
            //connect to database
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
        } catch (SQLException ex) {
            System.out.println("Failed to connect to DataBase");
        }
        
    }
    
    //REMEMBER TO CLOSE CONNECTION AFTER GAME
    public void closeConnection()
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Failed to close connection");
            }
        }
    }
    
    public void closeStatement()
    {
        if (statement != null)
        {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Failed to close statement");
            }
        }
    }
    
    public void closeResultSet()
    {
        if (resultSet != null)
        {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                System.out.println("Failed to close resultSet");
            }
        }
    }
        
    //used to add, remove, modify tables
    public void updateDB(String sql)
    {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            this.closeStatement();
            
        } catch (SQLException ex) {
            System.out.println("Failed to update DataBase");
        }
    }
    
    //used to retrieve records from tables
    //REMEMBER TO CLOSE RESULTSET AND STATEMENT LATER
    public ResultSet queryDB(String sql)
    {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //MUST CHECK IF RESULTSET IS NULL BEFORE OPERATING WITH IT
            return resultSet;
        } catch (SQLException ex) {
            System.out.println("Failed to query DataBase");
            return null;
        }
    }
       
    public void createPlayersTable()
    {
        //check if table exists
        if (this.checkTableExists("PLAYERS"))
        {
            //delete table to start fresh
            this.updateDB("DROP TABLE PLAYERS");
            System.out.println("Dropped table: PLAYERS");
        }
        //make players primary key
        String sql = "CREATE TABLE PLAYERS (USERNAME VARCHAR(30) PRIMARY KEY, "
                + "PETNAME VARCHAR(30), HIGHSCORE INT)";
        this.updateDB(sql);
        System.out.println("Created table: PLAYERS");
    }
    
    public void createQuizSessionTable()
    {
        //check if table exists
        if (this.checkTableExists("QUIZSESSION"))
        {
            //delete table to start fresh
            this.updateDB("DROP TABLE QUIZSESSION");
            System.out.println("Dropped table: QUIZSESSION");
        }
        //make players primary key
        String sql = "CREATE TABLE QUIZSESSION (USERNAME VARCHAR(30) PRIMARY KEY,"
                + "CURRENTQUESTIONINDEX INT, NUMCORRECTANSWERS INT, "
                + "QUESTIONS VARCHAR(100))";
        this.updateDB(sql);
        System.out.println("Created table: QUIZSESSION");
        
    }
    
    public boolean checkTableExists(String table)
    {
        try {
            dbmd = connection.getMetaData();
            //filter for table type only
            String[] types = {"TABLE"};
            resultSet = dbmd.getTables(null, null, null, types);
            
            //check if table names match
            while(resultSet.next())
            {
                String name = resultSet.getString("TABLE_NAME");
                if (table.equalsIgnoreCase(name))
                {
                    this.closeResultSet();
                    return true;
                }
                        
            }
            
        } catch (SQLException ex) {
            System.out.println("Failed to get Metadata");
        }
        this.closeResultSet();
        return false;
    }
    
    public void addRecord(String table, String username, String sql)
    {
        if (this.checkUsernameExists(table, username))
        {
            //remove record before adding duplicate
            this.removeRecord(table, username);
        }
        this.updateDB(sql);
        
        System.out.println("Added record");
    }
    
    public void removeRecord(String table, String username)
    {
        if (!this.checkTableExists(table))
        {
            System.out.println("Table not found: " + table);
            return;
        }
        String sql = "DELETE FROM " + table + " WHERE USERNAME = '" + username + "'";
        this.updateDB(sql);
        
        System.out.println("Removed record");
    }
    
    public boolean checkUsernameExists(String table, String username)
    {
        if (this.checkTableExists(table))
        {
            try {
                statement = connection.createStatement();
                String sql = "SELECT USERNAME FROM " + table;
                resultSet = this.queryDB(sql);
                
                while (resultSet.next())
                {
                    String user = resultSet.getString("USERNAME");
                    
                    if (user.equals(username))
                    {
                        this.closeResultSet();
                        this.closeStatement();
                        return true;
                    }
                }
                this.closeResultSet();
                this.closeStatement();
                
            } catch (SQLException ex) {
                System.out.println("Failed to check Username");
            }
        }
        System.out.println("Table not found");
        return false;
    }
    
    //TO DO
    //return usernames and highscores
    public HashMap getHighScores()
    {
        HashMap<String,Integer> scores = new HashMap();
        
        if (!this.checkTableExists("PLAYERS"))
        {
            System.out.println("Table not found: PLAYERS");
            return null;
        }
        
        try {
            statement = connection.createStatement();
            String sql = "SELECT USERNAME, HIGHSCORE FROM PLAYERS";
            resultSet = this.queryDB(sql);
            
            while (resultSet.next())
            {
                String username = resultSet.getString("USERNAME");
                int highscore = resultSet.getInt("HIGHSCORE");
                scores.put(username, highscore);
            }
            
            this.closeResultSet();
            this.closeStatement();
            
        } catch (SQLException ex) {
            System.out.println("Failed to get HighScores");
        }

        //sort by highscores in descending order



        System.out.println("Retrieved highscores");
        return scores;
    }
    
}
