package Persistence;

import Model.User;
import Model.QuizSession;
import Question.Question;
import Question.QuestionPool;

import java.io.*;
import java.util.*;

public class UserRecordFileIO implements UserRecord {

    private final String FOLDER = "saves/";

    public UserRecordFileIO() {
        new File(FOLDER).mkdirs(); // create saves folder
    }

    @Override
    public void saveGame(QuizSession session) {

        String username = session.getUser().getUsername().trim();
        
        //access specific user file based on username
        File file = new File(FOLDER + username + "_game.txt");
        
        try (FileWriter fw = new FileWriter(file, false)) {
            // false = overwrite existing file

            fw.write("username=" + session.getUser().getUsername() + "\n");
//            fw.write("petName=" + session.getUser().getPetName() + "\n");
//            fw.write("highScore="+session.getUser().getHighScore() + "\n");
            fw.write("currentQuestionIndex=" + session.getCurrentQuestionIndex() + "\n");
            fw.write("numCorrectAnswers=" + session.getNumCorrectAnswers() + "\n");
            fw.write("questions=");
            
            //save list of questions through their IDs
            for (Question q : session.getQuestions()) {
                fw.write(q.getQuestionID() + ",");
            }

//            System.out.println("Game saved for user: " + username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public QuizSession loadGame(String username) {
        try {
            File file = new File(FOLDER + username.trim() + "_game.txt");

            if (!file.exists()) {
                return null;
            }

            Scanner sc = new Scanner(file);

            // Read and split each line
            String nameLine = sc.nextLine();      // "username Bob"
//            String petLine = sc.nextLine();       // "petName Cat"
//            String highScoreLine = sc.nextLine(); 
            String indexLine = sc.nextLine();     // "currentQuestionIndex 3"
            String scoreLine = sc.nextLine();     // "numCorrectAnswers 2"
            String idsLine = sc.nextLine();       // "questions 1,2,3,"

            sc.close();

            // Extract values using split, get right hand side value
            // trim to remove whitespaces
            String name = nameLine.split("=")[1].trim();
//            String petName = petLine.split("=")[1];
//            int highScore = Integer.parseInt(highScoreLine.split("=")[1]);
            int index = Integer.parseInt(indexLine.split("=")[1]);
            int score = Integer.parseInt(scoreLine.split("=")[1]);

            // Extract question IDs (everything after "questions ")
            String idsPart = idsLine.split("=", 2)[1];

            // Rebuild objects
//            User user = new User(name, petName, highScore);
            //start using loadRecord method to retrieve user data
            //help separate user and game data
            User user = loadRecord(username);
            QuestionPool pool = new QuestionPool();
            List<Question> questions = new ArrayList<>();

            //split question ids at commas
            String[] ids = idsPart.split(",");
            for (String id : ids) {
                if (!id.isBlank()) {
                    questions.add(
                        pool.getQuestionByID(Integer.parseInt(id))
                    );
                }
            }

            return new QuizSession(index, questions, score, user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    @Override
    public void saveRecord(User user) {

        String username = user.getUsername().trim();
        //create file for user
        File file = new File(FOLDER + username + "_user.txt");

        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write("username="+ user.getUsername() + "\n");
            fw.write("petName="+ user.getPetName() + "\n");
            fw.write("highScore="+ user.getHighScore() + "\n");

//            System.out.println("User record saved for: " + username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User loadRecord(String username) {
        try {
            File file = new File(FOLDER + username + "_user.txt");
            //check if file does not exist
            if (!file.exists()) return null;

            Scanner sc = new Scanner(file);
            
            String nameLine = sc.nextLine();
            String petLine = sc.nextLine();
            String highScoreLine = sc.nextLine(); 
            //close scanner
            sc.close();
            
            String name = nameLine.split("=")[1].trim();
            String petName = petLine.split("=")[1].trim();
            int highScore = Integer.parseInt(highScoreLine.split("=")[1]);

            return new User(name, petName, highScore);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}