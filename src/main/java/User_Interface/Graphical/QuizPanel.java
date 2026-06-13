package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;

import Model.QuizSession;
import Question.Question;

public class QuizPanel extends BackgroundPanel {

    private JButton submitButton;
    private JRadioButton option1;
    private JRadioButton option2;
    private JRadioButton option3;

    public QuizPanel(
        Question question,
        QuizSession quiz,
        Runnable onAnswerSubmitted
    ) {

        super("/Backgrounds/QuizBg.png");

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        JLabel questionLabel =
            new JLabel(
                "<html>"
                + question.getQuestionText()
                + "</html>"
            );

        questionLabel.setAlignmentX(
            Component.CENTER_ALIGNMENT
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
        add(questionLabel);
        add(Box.createVerticalStrut(30));
        add(option1);
        add(option2);
        add(option3);
        add(Box.createVerticalStrut(20));
        add(submitButton);
        add(Box.createVerticalGlue());

        submitButton.addActionListener(
            e -> {

                int answer = -1;

                if (option1.isSelected())
                    answer = 1;

                else if (option2.isSelected())
                    answer = 2;

                else if (option3.isSelected())
                    answer = 3;

                if (answer == -1) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Choose an answer."
                    );

                    return;
                }

                boolean correct =
                    question.checkAnswer(
                        String.valueOf(answer)
                    );

                if (correct) {
                    quiz.answerCorrect();
                }
                else {
                    quiz.answerWrong();
                }

                onAnswerSubmitted.run();
            }
        );
    }
}