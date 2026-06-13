package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;

import Model.AppStateModel;
import Model.StateListener;
import Model.GameState;

public class GUI extends JFrame implements StateListener {

    private final AppStateModel appState;

    /*
        Constructor
    */
    public GUI(AppStateModel appState) {

        this.appState = appState;

        // register observer
        appState.addListener(this);

        setTitle("Feed Me Java!");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // use one central layout
        setLayout(new BorderLayout());

        // show frame
        setVisible(true);
    }

    /*
        Called automatically when AppState changes
    */
    @Override
    public void onStateChanged(GameState state) {

        // remove current screen
        getContentPane().removeAll();

        switch (state) {

            case MENU -> {
                add(
                    new MenuPanel(
                        () -> appState.setState(GameState.NAME_INPUT),

                        () -> appState.setState(GameState.LOAD_GAME),

                        () -> System.exit(0)
                    ),
                    BorderLayout.CENTER
                );
            }

            case NAME_INPUT -> {

                add(
                    new NameInputPanel(
                        user -> {
                            System.out.println(
                                "User entered: "
                                + user.getUsername()
                            );

                            appState.setState(
                                GameState.QUIZ
                            );
                        }
                    ),
                    BorderLayout.CENTER
                );
            }

            case LOAD_GAME -> {

                add(
                    new LoadPanel(
                        username -> {
                            System.out.println(
                                "Loading: "
                                + username
                            );

                            appState.setState(
                                GameState.QUIZ
                            );
                        }
                    ),
                    BorderLayout.CENTER
                );
            }

            case QUIZ -> {

                JPanel placeholder =
                    new JPanel();

                placeholder.add(
                    new JLabel(
                        "Quiz screen here"
                    )
                );

                add(
                    placeholder,
                    BorderLayout.CENTER
                );
            }

            case RESULTS -> {

                JPanel placeholder =
                    new JPanel();

                placeholder.add(
                    new JLabel(
                        "Results screen"
                    )
                );

                add(
                    placeholder,
                    BorderLayout.CENTER
                );
            }
        }

        revalidate();
        repaint();
    }
}