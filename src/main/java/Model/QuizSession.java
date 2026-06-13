package Model;

import Question.Question;
import java.util.List;

/**
 * Stores the state of a single quiz session.
 *
 * Responsible for:
 * - Tracking progress
 * - Tracking score
 * - Providing current question
 * - Calculating final results
 */
public class QuizSession {

    private int currentQuestionIndex;
    private List<Question> questions;
    private int numCorrectAnswers;
    private User user;

    public QuizSession(
            int currentQuestionIndex,
            List<Question> questions,
            int numCorrectAnswers,
            User user
    ) {
        this.currentQuestionIndex = currentQuestionIndex;
        this.questions = questions;
        this.numCorrectAnswers = numCorrectAnswers;
        this.user = user;
    }

    /**
     * Returns current question index.
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Returns all quiz questions.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Returns total number of correct answers.
     */
    public int getNumCorrectAnswers() {
        return numCorrectAnswers;
    }

    /**
     * Returns associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns current question.
     *
     * Returns null when quiz is complete.
     */
    public Question getCurrentQuestion() {

        if (isFinished()) {
            return null;
        }

        return questions.get(currentQuestionIndex);
    }

    /**
     * Returns true when all questions have been answered.
     */
    public boolean isFinished() {
        return currentQuestionIndex >= questions.size();
    }

    /**
     * Call when player answers correctly.
     */
    public void answerCorrect() {
        numCorrectAnswers++;
        currentQuestionIndex++;
    }

    /**
     * Call when player answers incorrectly.
     */
    public void answerWrong() {
        currentQuestionIndex++;
    }

    /**
     * Advances without changing score.
     *
     * Useful if you later add skipped questions.
     */
    public void advanceQuestion() {
        currentQuestionIndex++;
    }

    /**
     * Returns total question count.
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Returns score percentage.
     */
    public double getPercentage() {

        if (questions.isEmpty()) {
            return 0;
        }

        return ((double) numCorrectAnswers
                / questions.size())
                * 100.0;
    }

    /**
     * Updates user's highest score if needed.
     */
    public void updateHighScore() {
        user.saveHighestScore(numCorrectAnswers);
    }

    /**
     * Result text shown on ending screen.
     */
    public String getScoreText() {

        return "You got "
                + numCorrectAnswers
                + "/"
                + questions.size()
                + " questions correct.";
    }

    /**
     * Trophy earned.
     */
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

    /**
     * Ending dialogue.
     */
    public String getEndingDialogue() {

        double percentage = getPercentage();

        String pet = user.getPetName();

        if (percentage >= 80) {

            return pet
                    + " says: Congratulations! "
                    + "Your wallet has been saved!";
        }

        if (percentage >= 70) {

            return pet
                    + " says: Unfortunately silver "
                    + "does not win pet food.";
        }

        if (percentage >= 60) {

            return pet
                    + " says: Bronze is good, "
                    + "but no pet food.";
        }

        return pet
                + " says: Nice try. "
                + "Can you do better?";
    }

    /**
     * Converts question list into a comma-separated
     * string for storage in QUIZSESSION table.
     *
     * Example:
     * 34,40,21,7,8,19,36,11,2,10
     */
    public String getQuestionIDString() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < questions.size(); i++) {

            builder.append(
                    questions.get(i).getQuestionID()
            );

            if (i < questions.size() - 1) {
                builder.append(",");
            }
        }

        return builder.toString();
    }
}