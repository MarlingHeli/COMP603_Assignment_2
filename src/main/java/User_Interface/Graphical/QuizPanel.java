/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;
import User_Interface.Graphical.GUI;
import Question.Question;
import Model.QuizSession;

public class QuizPanel extends JPanel {

    private Question question;
    private QuizSession quiz;
    private GUI gui;
    private Runnable onFinish;

    public QuizPanel(
        Question question,
        QuizSession quiz,
        GUI gui,
        Runnable onFinish
    ) {
        this.question = question;
        this.quiz = quiz;
        this.gui = gui;

        setLayout(new BorderLayout());

        JLabel questionLabel =
            new JLabel(question.getQuestionText());

        String[] options =
            question.getOptions();

        JRadioButton a =
            new JRadioButton(options[0]);

        JRadioButton b =
            new JRadioButton(options[1]);

        JRadioButton c =
            new JRadioButton(options[2]);

        ButtonGroup group =
            new ButtonGroup();

        group.add(a);
        group.add(b);
        group.add(c);

        JPanel answerPanel =
            new JPanel();

        answerPanel.setLayout(
            new BoxLayout(
                answerPanel,
                BoxLayout.Y_AXIS
            )
        );

        answerPanel.add(a);
        answerPanel.add(b);
        answerPanel.add(c);

        JButton submitButton =
            new JButton("Submit");

        add(questionLabel,
            BorderLayout.NORTH);

        add(answerPanel,
            BorderLayout.CENTER);

        add(submitButton,
            BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {

            String selectedAnswer = null;

            if(a.isSelected())
                selectedAnswer = a.getText();

            else if(b.isSelected())
                selectedAnswer = b.getText();

            else if(c.isSelected())
                selectedAnswer = c.getText();

            if(selectedAnswer == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select an answer."
                );
                return;
            }

            boolean correct =
                question.checkAnswer(
                    selectedAnswer
                );

            if(correct) {
                quiz.answerCorrect();
            }
            else {
                quiz.answerWrong();
            }

            gui.showResult(
                correct,
                quiz,
                question.getExplanation(),
                () -> {
                    if (quiz.getCurrentQuestionIndex()
                        < quiz.getQuestions().size()) {

                        gui.showQuiz(quiz, onFinish);

                    }
                    else {
                        onFinish.run();
                    }
                }
            );

        });
    }
}
