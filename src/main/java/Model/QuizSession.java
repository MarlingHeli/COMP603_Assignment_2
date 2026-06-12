/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author hmarl
 */

import Question.Question;
import java.util.List;

public class QuizSession {
    private int currentQuestionIndex;
    private List<Question> questions;
    private int numCorrectAnswers;
    private User user;

    public QuizSession(int currentQuestionIndex, List<Question> questions,
                       int numCorrectAnswers, User user) 
    {
        this.currentQuestionIndex = currentQuestionIndex;
        this.questions = questions;
        this.numCorrectAnswers = numCorrectAnswers;
        this.user = user;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public int getNumCorrectAnswers() {
        return numCorrectAnswers;
    }

    public User getUser() {
        return user;
    }

    public void answerCorrect() {
        numCorrectAnswers++;
        currentQuestionIndex++;
    }

    public void answerWrong() {
        currentQuestionIndex++;
    }
    
    public void incrementScore() {
        numCorrectAnswers++;
    }
    
    public String getScoreText() {
        return "You got " + numCorrectAnswers + "/" + questions.size() + " questions correct.";
    }
    
    public String getTrophy() {

        double percentage =
            (double) numCorrectAnswers /
            questions.size() * 100;

        if (percentage >= 80) {
            return "GOLD";
        }

        if (percentage >= 70) {
            return "SILVER";
        }

        if (percentage >= 60) {
            return "BRONZE";
        }

        return "No Trophy...";
    }
    
    public String getEndingDialogue() {

        double percentage =
            (double) numCorrectAnswers /
            questions.size() * 100;

        if (percentage >= 80) {
            return user.getPetName()
                + " says: Congratulations! "
                + "Your wallet has been saved!";
        }

        if (percentage >= 70) {
            return user.getPetName()
                + " says: Unfortunately silver "
                + "does not win pet food.";
        }

        if (percentage >= 60) {
            return user.getPetName()
                + " says: Bronze is good, "
                + "but no pet food.";
        }

        return user.getPetName()
            + " says: Nice try. "
            + "Can you do better?";
    }
    
    public Question getCurrentQuestion() {
    
        // Safety check so we don't go out of bounds
        if (currentQuestionIndex >= questions.size()) {
            return null;
        }

        // Return question at current index
        return questions.get(currentQuestionIndex);
    }

}
