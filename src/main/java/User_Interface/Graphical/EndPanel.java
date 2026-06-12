/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Interface.Graphical;

/**
 *
 * @author 2
 */

import javax.swing.*;
import java.awt.*;
import Model.QuizSession;

public class EndPanel extends JPanel {

    public EndPanel(
        QuizSession quiz,
        GUI gui,
        Runnable onFinish
    ) {

        setLayout(
            new BorderLayout()
        );

        /*
        Trophy title
        */

        JLabel trophyLabel =
            new JLabel(
                quiz.getTrophy()
            );

        trophyLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                42
            )
        );

        trophyLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        /*
        Score
        */

        JLabel scoreLabel =
            new JLabel(
                quiz.getScoreText()
            );

        scoreLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        /*
        Dialogue
        */

        JLabel dialogueLabel =
            new JLabel(
                quiz.getEndingDialogue()
            );

        dialogueLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        /*
        Buttons
        */

        JButton menuButton =
            new JButton(
                "Return to Menu"
            );

        JButton exitButton =
            new JButton(
                "Exit"
            );

        JPanel buttonPanel =
            new JPanel();

        buttonPanel.add(menuButton);
        buttonPanel.add(exitButton);

        /*
        Layout
        */

        JPanel centerPanel =
            new JPanel();

        centerPanel.setLayout(
            new BoxLayout(
                centerPanel,
                BoxLayout.Y_AXIS
            )
        );

        centerPanel.add(
            trophyLabel
        );

        centerPanel.add(
            scoreLabel
        );

        centerPanel.add(
            dialogueLabel
        );

        add(
            centerPanel,
            BorderLayout.CENTER
        );

        add(
            buttonPanel,
            BorderLayout.SOUTH
        );

        /*
        Actions
        */

        menuButton.addActionListener(
            e -> {
                gui.showMenu();
            }
        );

        exitButton.addActionListener(
            e -> {
                System.exit(0);
            }
        );
    }
}