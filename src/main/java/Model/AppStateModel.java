package Model;

import java.util.ArrayList;
import java.util.List;

public class AppStateModel {

    private GameState currentState;
    private final List<StateListener> listeners =
        new ArrayList<>();

    public void addListener(
        StateListener listener
    ) {
        listeners.add(listener);
    }

    public void setState(
        GameState newState
    ) {
        currentState = newState;
        notifyListeners();
    }

    public GameState getState() {
        return currentState;
    }

    private void notifyListeners() {
        for (
            StateListener listener
            : listeners
        ) {
            listener.onStateChanged(
                currentState
            );
        }
    }
}