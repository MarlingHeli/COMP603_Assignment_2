package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;
import Model.*;

public class GUI extends JFrame implements StateListener {

    private final AppStateModel appState;

    public GUI(AppStateModel appState) {

        this.appState = appState;

        appState.addListener(this);

        setTitle("Feed Me Java");
        setSize(900,700);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        setVisible(true);
    }

    @Override
    public void onStateChanged(
            GameState state
    ) {

        getContentPane().removeAll();

        switch(state) {

            case MENU -> {

                add(
                        new MenuPanel(
                                () -> appState.setState(
                                        GameState.NAME_INPUT
                                ),
                                () -> appState.setState(
                                        GameState.LOAD_GAME
                                ),
                                () -> System.exit(0)
                        ),
                        BorderLayout.CENTER
                );
            }

            case NAME_INPUT -> {

                add(
                        new NameInputPanel(
                                (user, skip) -> {

                                    appState.setCurrentUser(
                                            user
                                    );

                                    appState.setSkipIntro(
                                            skip
                                    );

                                    if(skip) {

                                        appState.setState(
                                                GameState.QUIZ
                                        );

                                    }
                                    else {

                                        appState.setState(
                                                GameState.INTRO
                                        );
                                    }
                                }
                        ),
                        BorderLayout.CENTER
                );
            }

            case INTRO -> {

                User user =
                        appState.getCurrentUser();

                add(
                        new IntroPanel(
                                user.getUsername(),
                                user.getPetName(),

                                () -> appState.setState(
                                        GameState.QUIZ
                                )
                        ),
                        BorderLayout.CENTER
                );
            }

            case QUIZ -> {

                JPanel panel =
                        new JPanel();

                panel.add(
                        new JLabel(
                                "QUIZ GOES HERE"
                        )
                );

                add(
                        panel,
                        BorderLayout.CENTER
                );
            }

            case RESULTS -> {

                JPanel panel =
                        new JPanel();

                panel.add(
                        new JLabel(
                                "RESULTS"
                        )
                );

                add(
                        panel,
                        BorderLayout.CENTER
                );
            }
        }

        revalidate();
        repaint();
    }
}