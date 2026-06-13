package User_Interface.Graphical;

import Model.QuizSession;
import Question.Question;

import javax.swing.*;
import java.awt.*;

public class QuizPanel extends BackgroundPanel {

    private QuizSession quiz;

    private JLabel questionNumberLabel;
    private JTextArea questionTextArea;

    private JRadioButton option1;
    private JRadioButton option2;
    private JRadioButton option3;

    private JButton submitButton;

    public QuizPanel(
            QuizSession quiz,
            Runnable onQuestionFinished
    ) {

        super("/Backgrounds/QuizBg.png");

        this.quiz = quiz;

        Question question =
                quiz.getCurrentQuestion();

        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        questionNumberLabel =
                new JLabel(
                        "Question "
                        + (quiz.getCurrentQuestionIndex() + 1)
                        + " / "
                        + quiz.getTotalQuestions()
                );

        questionNumberLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        questionTextArea =
                new JTextArea(
                        question.getQuestionText()
                );

        questionTextArea.setEditable(false);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setOpaque(false);

        questionTextArea.setMaximumSize(
                new Dimension(
                        700,
                        100
                )
        );

        String[] options =
                question.getOptions();

        option1 =
                new JRadioButton(
                        options[0]
                );

        option2 =
                new JRadioButton(
                        options[1]
                );

        option3 =
                new JRadioButton(
                        options[2]
                );

        option1.setOpaque(false);
        option2.setOpaque(false);
        option3.setOpaque(false);

        option1.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        option2.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        option3.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        ButtonGroup group =
                new ButtonGroup();

        group.add(option1);
        group.add(option2);
        group.add(option3);

        submitButton =
                new JButton(
                        "Submit"
                );

        submitButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        add(Box.createVerticalGlue());

        add(questionNumberLabel);

        add(Box.createVerticalStrut(20));

        add(questionTextArea);

        add(Box.createVerticalStrut(30));

        add(option1);

        add(Box.createVerticalStrut(10));

        add(option2);

        add(Box.createVerticalStrut(10));

        add(option3);

        add(Box.createVerticalStrut(30));

        add(submitButton);

        add(Box.createVerticalGlue());

        submitButton.addActionListener(e -> {

            int selectedAnswer = -1;

            if (option1.isSelected()) {
                selectedAnswer = 1;
            }
            else if (option2.isSelected()) {
                selectedAnswer = 2;
            }
            else if (option3.isSelected()) {
                selectedAnswer = 3;
            }

            if (selectedAnswer == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select an answer."
                );

                return;
            }

            boolean correct =
                    question.checkAnswer(
                            String.valueOf(
                                    selectedAnswer
                            )
                    );

            if (correct) {

                quiz.answerCorrect();

            } else {

                quiz.answerWrong();
            }

            JOptionPane.showMessageDialog(
                    this,
                    (correct
                            ? "Correct!\n\n"
                            : "Incorrect.\n\n")
                    + question.getExplanation()
            );

            onQuestionFinished.run();
        });
    }
}