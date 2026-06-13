package User_Interface.Graphical;

import Model.User;
import javax.swing.*;
import java.util.function.BiConsumer;
import java.awt.*;

public class NewGamePanel extends BackgroundPanel {

    private JTextField usernameField;
    private JTextField petField;
    private JCheckBox skipIntroBox;
    private JButton submitButton;
    private JLabel username;
    private JLabel petname;

    public NewGamePanel(
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
        
        username = new JLabel("Username");
        petname = new JLabel("Pet name");
        
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        petField.setAlignmentX(Component.CENTER_ALIGNMENT);
        skipIntroBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        petname.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        add(username);
        add(usernameField);

        add(Box.createVerticalStrut(20));

        add(petname);
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