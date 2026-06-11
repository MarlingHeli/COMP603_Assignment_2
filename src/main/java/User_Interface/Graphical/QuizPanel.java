/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;
import User_Interface.Graphical.GUI;

public class QuizPanel extends JPanel {

    public QuizPanel() {

        setLayout(new BorderLayout());

        JLabel questionLabel =
            new JLabel("Question 1: What is Java?");

        questionLabel.setFont(
            new Font("Arial", Font.BOLD, 24)
        );

        add(questionLabel, BorderLayout.NORTH);

        // Answers
        JRadioButton a =
            new JRadioButton("A. A programming language");

        JRadioButton b =
            new JRadioButton("B. A type of animal");

        JRadioButton c =
            new JRadioButton("C. A country");

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

        add(answerPanel,
            BorderLayout.CENTER);

        JButton submitButton =
            new JButton("Submit");

        add(submitButton,
            BorderLayout.SOUTH);
        
        submitButton.addActionListener(e -> {

            boolean correct = true; // temporary test value

            GUI gui = (GUI) SwingUtilities.getWindowAncestor(this);

            gui.showResult(
                correct,
                1,
                "This is the explanation."
        );
});

//        ----------------------------------------
        
        JButton hintButton = new JButton("?");
        
        JPanel topPanel =
            new JPanel(
                new BorderLayout()
            );

        topPanel.add(
            questionLabel,
            BorderLayout.CENTER
        );

        topPanel.add(
            hintButton,
            BorderLayout.EAST
        );

        add(
            topPanel,
            BorderLayout.NORTH
        );
        
        hintButton.addActionListener(e -> {

        int choice =
            JOptionPane.showConfirmDialog(
                this,
                "Ask Fluffy for a hint?",
                "Hint",
                JOptionPane.YES_NO_OPTION
            );

        if(choice ==
            JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(
                this,
                "Remember inheritance!"
            );
        }
    });
        
        
    }
}
