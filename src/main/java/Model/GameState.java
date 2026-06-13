package Model;

public enum GameState {
    MENU,
    NAME_INPUT,
    INTRO,
    QUIZ,
    RESULT,
    LOAD_SCREEN, // Tracks intermediate username checking and save selection screens
    END
}
