package Model;

import java.util.ArrayList;
import java.util.List;

public class AppStateModel {

    private GameState currentState;
    private User currentUser;
    private QuizSession currentQuiz;
    private boolean skipIntro;

    private final List<StateListener> listeners = new ArrayList<>();

    public void addListener(StateListener listener) {
        listeners.add(listener);
    }

    public void setState(GameState newState) {
        currentState = newState;
        notifyListeners();
    }

    public GameState getState() {
        return currentState;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentQuiz(QuizSession quiz) {
        this.currentQuiz = quiz;
    }

    public QuizSession getCurrentQuiz() {
        return currentQuiz;
    }

    public void setSkipIntro(boolean skip) {
        this.skipIntro = skip;
    }

    public boolean isSkipIntro() {
        return skipIntro;
    }

    private void notifyListeners() {
        for (StateListener listener : listeners) {
            listener.onStateChanged(currentState);
        }
    }
}
