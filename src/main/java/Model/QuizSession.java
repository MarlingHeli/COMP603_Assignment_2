package Model;

import Question.Question;
import java.util.List;

public class QuizSession {

    private int currentQuestionIndex;
    private List<Question> questions;
    private int numCorrectAnswers;
    private User user;

    public QuizSession(int currentQuestionIndex, List<Question> questions, int numCorrectAnswers, User user) {
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

    public Question getCurrentQuestion() {
        if (isFinished()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    public boolean isFinished() {
        return currentQuestionIndex >= questions.size();
    }

    public void answerCorrect() {
        numCorrectAnswers++;
        currentQuestionIndex++;
    }

    public void answerWrong() {
        currentQuestionIndex++;
    }

    public void advanceQuestion() {
        currentQuestionIndex++;
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public double getPercentage() {
        if (questions.isEmpty()) {
            return 0;
        }
        return ((double) numCorrectAnswers / questions.size()) * 100.0;
    }

    public void updateHighScore() {
        user.saveHighestScore(numCorrectAnswers);
    }

    public String getScoreText() {
        return "You got " + numCorrectAnswers + "/" + questions.size() + " questions correct.";
    }

    public String getTrophy() {
        double percentage = getPercentage();
        if (percentage >= 80) {
            return "GOLD";
        }
        if (percentage >= 70) {
            return "SILVER";
        }
        if (percentage >= 60) {
            return "BRONZE";
        }
        return "NO TROPHY";
    }

    public String getEndingDialogue() {
        double percentage = getPercentage();
        String pet = user.getPetName();
        if (percentage >= 80) {
            return pet + " says: Congratulations! Your wallet has been saved!";
        }
        if (percentage >= 70) {
            return pet + " says: Unfortunately silver does not win pet food.";
        }
        if (percentage >= 60) {
            return pet + " says: Bronze is good, but no pet food.";
        }
        return pet + " says: Nice try. Can you do better?";
    }

    public String getQuestionIDString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            builder.append(questions.get(i).getQuestionID());
            if (i < questions.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
