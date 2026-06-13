package User_Interface.Graphical;

import javax.swing.*;
import java.awt.*;
import Model.*;
//import Question.Question;
//import Question.QuestionPool;
//import Model.QuizSession;

public class GUI extends JFrame implements StateListener {

    private final AppStateModel appState;

    public GUI(AppStateModel appState) {

        this.appState = appState;

        appState.addListener(this);

        this.setTitle("Feed Me Java");
        setSize(900,700);

        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        //prevent window resizing
        this.setResizable(false);

        this.setLayout(
                new BorderLayout()
        );

        this.setVisible(true);
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
                        new NewGamePanel(
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

                QuizSession quiz =
                        appState.getCurrentQuiz();

                if (quiz == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Quiz session missing."
                    );

                    appState.setState(
                            GameState.MENU
                    );

                    return;
                }

                add(
                        new QuizPanel(
                                quiz,
                                () -> {
                                    if (quiz.isFinished()) {
                                        appState.setState(
                                                GameState.END
                                        );
                                    } else {
                                        appState.setState(
                                                GameState.QUIZ
                                        );
                                    }
                                }
                        ),
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