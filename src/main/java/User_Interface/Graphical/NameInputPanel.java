package User_Interface.Graphical;

import Model.User;
import javax.swing.*;
import java.util.function.Consumer;
import java.awt.*;

public class NameInputPanel extends BackgroundPanel {

    private JTextField usernameField;
    private JTextField petField;
    private JButton submitButton;

    public NameInputPanel(Consumer<User> onSubmit) {

        super("Backgrounds/StartNewGameBg.png");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        usernameField = new JTextField(20);
        petField = new JTextField(20);
        submitButton = new JButton("Continue");

        JLabel userLabel = new JLabel("Enter username");
        JLabel petLabel = new JLabel("Enter pet name");

        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        petLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setMaximumSize(new Dimension(200, 30));
        petField.setMaximumSize(new Dimension(200, 30));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        add(userLabel);
        add(Box.createVerticalStrut(10));
        add(usernameField);

        add(Box.createVerticalStrut(20));

        add(petLabel);
        add(Box.createVerticalStrut(10));
        add(petField);

        add(Box.createVerticalStrut(20));
        add(submitButton);

        add(Box.createVerticalGlue());

        submitButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String petname = petField.getText().trim();

            if (username.isEmpty() || petname.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please fill both fields"
                );
                return;
            }

            User user = new User(username, petname, 0);
            onSubmit.accept(user);
        });
    }
}