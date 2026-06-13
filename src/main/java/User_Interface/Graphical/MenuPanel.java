package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;

public class MenuPanel
        extends BackgroundPanel {

    public MenuPanel(
        Runnable onStart,
        Runnable onLoad,
        Runnable onExit
    ) {

        super(
            "/Backgrounds/MenuBg.png"
        );

        setLayout(
            new BoxLayout(
                this,
                BoxLayout.Y_AXIS
            )
        );

        JButton start =
            new JButton(
                "Start New Game"
            );

        JButton load =
            new JButton(
                "Load Game"
            );

        JButton exit =
            new JButton(
                "Exit"
            );

        start.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        load.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        exit.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );

        start.addActionListener(
            e -> onStart.run()
        );

        load.addActionListener(
            e -> onLoad.run()
        );

        exit.addActionListener(
            e -> onExit.run()
        );

        add(Box.createVerticalGlue());

        add(start);
        add(Box.createVerticalStrut(20));

        add(load);
        add(Box.createVerticalStrut(20));

        add(exit);

        add(Box.createVerticalGlue());
    }
}