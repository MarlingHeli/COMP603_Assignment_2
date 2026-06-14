/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

/**
 *
 * @author hmarl
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Base DAO class. Provides common database functionality.
 */
public abstract class DatabaseDAO {

    protected Connection connection;

    public DatabaseDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Each DAO creates its own table.
     */
    public abstract void createTable();

//    public abstract boolean usernameExists(String username);
    /**
     * Check whether a table already exists.
     *
     * @param table Table name
     * @return true if table exists
     */
    public boolean checkTableExists(String table) {

        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            //get table type only
            String[] types = {"TABLE"};

            try (
                    ResultSet resultSet = dbmd.getTables(null, null, null, types)) {

                while (resultSet.next()) {

                    String tableName = resultSet.getString("TABLE_NAME");

                    //check if names match
                    if (table.equalsIgnoreCase(tableName)) {
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to read database metadata");
        }

        return false;
    }
}
