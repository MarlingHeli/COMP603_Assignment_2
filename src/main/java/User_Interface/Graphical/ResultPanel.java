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

public class ResultPanel extends JPanel {

    public ResultPanel(
        boolean correct,
        int score,
        String explanation
    ) {

        setLayout(
            new BorderLayout()
        );

        JLabel resultLabel =
            new JLabel(
                correct
                ? "CORRECT!"
                : "INCORRECT"
            );

        resultLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                42
            )
        );

        resultLabel.setForeground(
            correct
            ? Color.GREEN
            : Color.RED
        );

        add(
            resultLabel,
            BorderLayout.NORTH
        );

        JTextArea explanationArea =
            new JTextArea(
                explanation
            );

        explanationArea.setEditable(
            false
        );

        add(
            explanationArea,
            BorderLayout.CENTER
        );

        JButton nextButton =
            new JButton("Next");

        add(
            nextButton,
            BorderLayout.SOUTH
        );
    }
}
