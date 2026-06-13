/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Question;
/**
 *
 * @author hmarl
 */
import Model.User;
import Persistence.DatabaseQuestions;
import Persistence.DatabaseQuizSession;
import java.util.*;

public class QuestionPool {

    private DatabaseQuestions databaseQuestions;

    public QuestionPool(DatabaseQuestions databaseQuestions) {
        this.databaseQuestions = databaseQuestions;
    }

    public List<Question> getRandomQuestions(int numQuestions) {

        List<Question> questions =
            databaseQuestions.getQuestions();

        Collections.shuffle(questions);

        return new ArrayList<>(
            questions.subList(0, numQuestions)
        );
    }

//    public List<Question> getQuestionsFromIDs(String questionIDs) {
//
//        return databaseQuestions.getQuestionsFromIDs(questionIDs);
//    }
}
