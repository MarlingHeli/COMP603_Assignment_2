package User_Interface.Graphical;

import Model.User;
import javax.swing.*;
import java.util.function.BiConsumer;
import java.awt.*;

public class NameInputPanel extends BackgroundPanel {

    private JTextField usernameField;
    private JTextField petField;
    private JCheckBox skipIntroBox;
    private JButton submitButton;

    public NameInputPanel(
            BiConsumer<User, Boolean> onSubmit
    ) {
        super("/Backgrounds/StartNewGameBg.png");

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        usernameField =
                new JTextField(20);

        petField =
                new JTextField(20);

        skipIntroBox =
                new JCheckBox(
                        "Skip Introduction"
                );

        submitButton =
                new JButton("Continue");

        skipIntroBox.setOpaque(false);

        usernameField.setMaximumSize(
                new Dimension(200,30)
        );

        petField.setMaximumSize(
                new Dimension(200,30)
        );

        add(Box.createVerticalGlue());

        add(new JLabel("Username"));
        add(usernameField);

        add(Box.createVerticalStrut(20));

        add(new JLabel("Pet Name"));
        add(petField);

        add(Box.createVerticalStrut(20));

        add(skipIntroBox);

        add(Box.createVerticalStrut(20));

        add(submitButton);

        add(Box.createVerticalGlue());

        submitButton.addActionListener(e -> {

            String username =
                    usernameField
                            .getText()
                            .trim();

            String pet =
                    petField
                            .getText()
                            .trim();

            if(username.isEmpty()
                    || pet.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Fill all fields"
                );
                return;
            }

            User user =
                    new User(
                            username,
                            pet,
                            0
                    );

            onSubmit.accept(
                    user,
                    skipIntroBox.isSelected()
            );
        });
    }
}