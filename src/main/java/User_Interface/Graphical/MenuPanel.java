package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;

public class MenuPanel
        extends BackgroundPanel {
    
    Image image;

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

        //add logo
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Components/FeedMeJavaLogo.png"));
        JLabel logoLabel = new JLabel(logoIcon);
        
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
        logoLabel.setAlignmentX( Component.CENTER_ALIGNMENT);

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
        
        add(logoLabel);
                
        add(start);
        add(Box.createVerticalStrut(20));

        add(load);
        add(Box.createVerticalStrut(20));

        add(exit);

        add(Box.createVerticalGlue());
    }
}