package enums.commands;

import java.util.regex.Matcher;

public enum GameMenuCommands implements MenuCommands {
    CreateGame,
    SetMap,
    LoadGame,
    ExitGame,
    NextTurn,
    EnterMenu,
    ExitMenu,
    ShowCurrentMenu;

    public Matcher getMatcher(String input) {}
}
