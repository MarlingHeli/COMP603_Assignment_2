/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistence;

/**
 *
 * @author hmarl
 */

import Model.QuizSession;
import Model.User;
import Question.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * DAO for QUIZSESSION table.
 */
public class DatabaseQuizSession
    extends DatabaseDAO
    implements QuizRecord {

    DatabaseQuestions databaseQuestion;
    
    public DatabaseQuizSession(Connection connection) {
        super(connection);
        databaseQuestion = new DatabaseQuestions(connection);
        this.createTable();
    }

    @Override
    public void createTable() {

        String sql =
            """
            CREATE TABLE QUIZSESSION
            (
                USERNAME VARCHAR(30) PRIMARY KEY,
                CURRENTQUESTIONINDEX INT,
                NUMCORRECTANSWERS INT,
                QUESTIONS VARCHAR(500)
            )
            """;

        try (
            Statement statement =
                connection.createStatement()
        ) {

            if (!checkTableExists("QUIZSESSION")) {

                statement.executeUpdate(sql);

                System.out.println(
                    "QUIZSESSION table created"
                );
            }
            else {

                System.out.println(
                    "QUIZSESSION table already exists"
                );
            }
        }
        catch (SQLException ex) {

            System.out.println(
                "Failed to create QUIZSESSION table"
            );
        }
    }


    @Override
    public void saveGame(QuizSession session) {

        String username = session.getUser().getUsername();

        if (usernameExists(username)) {
            updateGame(session);
        }
        else {
            insertGame(session);
        }
    }

    private void insertGame(QuizSession session) {

        String sql =
            """
            INSERT INTO QUIZSESSION
            (
                USERNAME,
                CURRENTQUESTIONINDEX,
                NUMCORRECTANSWERS,
                QUESTIONS
            )
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement stmt =
                connection.prepareStatement(sql)) {

            stmt.setString(
                1,
                session.getUser().getUsername()
            );

            stmt.setInt(
                2,
                session.getCurrentQuestionIndex()
            );

            stmt.setInt(
                3,
                session.getNumCorrectAnswers()
            );

            stmt.setString(
                4,
                serialiseQuestionIDs(session.getQuestions())
            );

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(
                "Failed to insert quiz session"
            );
        }
    }

    private void updateGame(QuizSession session) {

        String sql =
            """
            UPDATE QUIZSESSION
            SET CURRENTQUESTIONINDEX = ?,
                NUMCORRECTANSWERS = ?,
                QUESTIONS = ?
            WHERE USERNAME = ?
            """;

        //try catch with resources to automatically close statement
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1,session.getCurrentQuestionIndex());
            stmt.setInt(2,session.getNumCorrectAnswers());
            stmt.setString(3,serialiseQuestionIDs(session.getQuestions()));
            stmt.setString(4,session.getUser().getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(
                "Failed to update quiz session"
            );
        }
    }

    //format question list for storing in table
    private String serialiseQuestionIDs(List<Question> questions) {

        StringBuilder builder = new StringBuilder();

        for (Question question : questions) {

            builder.append(
                question.getQuestionID()
            );

            builder.append(",");
        }

        //remove end comma
        if (builder.length() > 0) {

            builder.deleteCharAt(
                builder.length() - 1
            );
        }

        return builder.toString();
    }

    @Override
    public QuizSession loadGame(User user) {

        String sql =
            """
            SELECT *
            FROM QUIZSESSION
            WHERE USERNAME = ?
            """;

        try (PreparedStatement stmt =
                connection.prepareStatement(sql)) {

            stmt.setString(
                1,
                user.getUsername()
            );

            ResultSet resultSet =
                stmt.executeQuery();

            if (resultSet.next()) {

                int currentQuestionIndex =
                    resultSet.getInt(
                        "CURRENTQUESTIONINDEX"
                    );

                int numCorrectAnswers =
                    resultSet.getInt(
                        "NUMCORRECTANSWERS"
                    );

                String questionIDs =
                    resultSet.getString(
                        "QUESTIONS"
                    );

                List<Question> questions = databaseQuestion.getQuestionsFromIDs(questionIDs);

                return new QuizSession(
                    currentQuestionIndex,
                    questions,
                    numCorrectAnswers,
                    user
                );
            }
        }
        catch (SQLException ex) {

            System.out.println(
                "Failed to load quiz session"
            );
        }

        return null;
    }

    public void deleteSession(
        String username
    ) {

        String sql =
            """
            DELETE FROM QUIZSESSION
            WHERE USERNAME = ?
            """;

        try (
            PreparedStatement stmt =
                connection.prepareStatement(sql)
        ) {

            stmt.setString(
                1,
                username
            );

            stmt.executeUpdate();
        }
        catch (SQLException ex) {

            System.out.println(
                "Failed to delete session"
            );
        }
    }

    public boolean usernameExists(String username) {
        
        String sql = """
            SELECT USERNAME
            FROM QUIZSESSION
            WHERE USERNAME = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            //get first and only result 
            return rs.next();

        } catch (SQLException ex) {
            System.out.println("Failed to check username");
        }

        return false;
    }

}