package Question;

public class MultiChoiceQuestion implements Question {

    private String questionText;
    private String explanation;
    private String[] options;
    private int answer;
    private int id;

    public MultiChoiceQuestion(
        String questionText,
        String[] options,
        String explanation,
        int answer,
        int id
    ) {
        this.questionText = questionText;
        this.options = options;
        this.explanation = explanation;
        this.answer = answer;
        this.id = id;
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
            return choice == answer;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public int getQuestionID() {
        return id;
    }

    @Override
    public String[] getOptions() {
        return options;
    }
}