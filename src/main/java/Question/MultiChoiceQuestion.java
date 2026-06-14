package Question;

public class MultiChoiceQuestion implements Question {

    private int id;
    private String questionText;
    private String answer1;
    private String answer2;
    private String answer3;
    private int correctAnswer;
    private String explanation;

    public MultiChoiceQuestion(int id, String questionText, String answer1,
            String answer2, String answer3, int correctAnswer, String explanation) {
        this.id = id;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        try {
            int choice = Integer.parseInt(userAnswer.trim());
            return choice == correctAnswer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int getQuestionID() {
        return id;
    }

    public String[] getOptions() {
        return new String[]{answer1, answer2, answer3};
    }
}
