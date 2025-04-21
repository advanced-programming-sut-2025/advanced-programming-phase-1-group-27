package enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements MenuCommands {
    CreateGame,
    SetMap,
    LoadGame,
    ExitGame,
    NextTurn,
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    ExitMenu,
    ShowCurrentMenu;

    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(this.pattern);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
