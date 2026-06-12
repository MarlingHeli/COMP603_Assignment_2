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
    
//    //only save highest score in user's file
//    public void saveHighestScore()
//    {
//        if (numCorrectAnswers > user.getHighScore())
//        {
//            user.setHighScore(numCorrectAnswers);
//        }
//    }

    /**
     * Determines trophy result
     */
    public String calculateResult() {
        double percentage = (double) numCorrectAnswers / questions.size() * 100;

        if (percentage >= 80)
        {
            return "Gold trophy\n" + user.getPetName() + " says: \"Congratulations! "
                    + "Your wallet has been saved!\"";
        }
        if (percentage >= 70)
        {
            return "Silver trophy\n" + user.getPetName() + " says, \"Congratulations! "
                    + "Unfortunately, silver does not get a\nlifetime supply of petfood.\n"
                    + "Do you think you can do better?\"";
        }
        if (percentage >= 60)
        {
            return "Bronze trophy\n" + user.getPetName() + " says, \"Congratulations! "
                    + "Unfortunately, bronze does not get a\nlifetime supply of petfood.\n"
                    + "Do you think you can do better?\"";
        }
        return "No trophy (lose)\n" + user.getPetName() + " says, \"Nice try! "
                + "Do you think you can do better?\"";
    }
    
    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

}