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
import java.util.function.Consumer;

public class LoadPanel extends BackgroundPanel {

    private JTextField usernameField;
    private JButton submitButton;

    public LoadPanel(Consumer<String> onSubmit) {

        super("/Backgrounds/StartNewGameBg.png");

        setLayout(new GridBagLayout());

        usernameField = new JTextField(20);
        submitButton = new JButton("Load Game");

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);

        inputPanel.add(new JLabel("Enter username"));
        inputPanel.add(usernameField);
        inputPanel.add(submitButton);

        add(inputPanel);

        submitButton.addActionListener(e -> {

            String username = usernameField.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter username"
                );
                return;
            }

            onSubmit.accept(username);
        });
    }
}
