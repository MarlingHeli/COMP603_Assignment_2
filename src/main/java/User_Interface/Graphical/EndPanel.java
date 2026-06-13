package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;
import Model.QuizSession;

public class EndPanel extends JPanel {

    public EndPanel(
        QuizSession quiz,
        Runnable onMenu,
        Runnable onExit
    ) {

        setLayout(new BorderLayout());

        JLabel trophyLabel =
            new JLabel(quiz.getTrophy());

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

        JLabel scoreLabel =
            new JLabel(
                quiz.getScoreText()
            );

        scoreLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

        JLabel dialogueLabel =
            new JLabel(
                quiz.getEndingDialogue()
            );

        dialogueLabel.setHorizontalAlignment(
            SwingConstants.CENTER
        );

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

        JPanel centerPanel =
            new JPanel();

        centerPanel.setLayout(
            new BoxLayout(
                centerPanel,
                BoxLayout.Y_AXIS
            )
        );

        centerPanel.add(trophyLabel);
        centerPanel.add(scoreLabel);
        centerPanel.add(dialogueLabel);

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
            e -> onMenu.run()
        );

        exitButton.addActionListener(
            e -> onExit.run()
        );
    }
}