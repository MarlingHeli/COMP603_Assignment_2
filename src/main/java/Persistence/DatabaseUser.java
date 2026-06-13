/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

/**
 *
 * @author hmarl
 */
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO for PLAYERS table.
 */
public class DatabaseUser extends DatabaseDAO implements UserRecord {

    //use same connection
    public DatabaseUser(Connection connection) {
        super(connection);
    }

    @Override
    public void createTable() {

        String sql = """
            CREATE TABLE PLAYERS
            (
                USERNAME VARCHAR(30) PRIMARY KEY,
                PETNAME VARCHAR(30)
            )
            """;

        try (Statement statement = connection.createStatement()) {

            if (!checkTableExists("PLAYERS")) {
                statement.executeUpdate(sql);
                System.out.println("PLAYERS table created");
            } else {
                System.out.println("PLAYERS table already exists");
            }
        } catch (SQLException ex) {
            System.out.println("Failed to create PLAYERS table");
        }
    }

    @Override
    public void saveRecord(User user) {
        //check if user record already exists
        if (usernameExists(user.getUsername())) {
            updateUser(user);
        } else {
            insertUser(user);
        }
    }

    private void insertUser(User user) {

        String sql = """
            INSERT INTO PLAYERS
            (
                USERNAME,
                PETNAME,
                HIGHSCORE
            )
            VALUES
            (
                ?, ?, ?
            )
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPetName());
            statement.setInt(3, user.getHighScore());
            statement.executeUpdate();

        } catch (SQLException ex) {
//            ex.printStackTrace();
            System.out.println("Failed to insert user record");
        }
    }

    private void updateUser(User user) {

        String sql = """
            UPDATE PLAYERS
            SET
                PETNAME = ?,
                HIGHSCORE = ?
            WHERE
                USERNAME = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getPetName());
            statement.setInt(2, user.getHighScore());
            statement.setString(3, user.getUsername());
            statement.executeUpdate();
        } catch (SQLException ex) {
//            ex.printStackTrace();
            System.out.println("Failed to update user record");
        }
    }

    @Override
    public User loadRecord(String username) {

        String sql = """
            SELECT *
            FROM PLAYERS
            WHERE USERNAME = ?
            """;

        //using try with resources to automatically close statement
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,username);

            try (
                ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet.getString("USERNAME"), 
                            resultSet.getString("PETNAME"), 
                            resultSet.getInt("HIGHSCORE"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to load user");
        }

        return null;
    }

//    @Override
    public boolean usernameExists(String username ) {

        String sql = """
            SELECT USERNAME
            FROM PLAYERS
            WHERE USERNAME = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException ex) {
//                    ex.printStackTrace();
            System.out.println("Failed to check username");
        }

        return false;
    }

}
