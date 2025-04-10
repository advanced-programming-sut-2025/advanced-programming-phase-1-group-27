package enums.commands;

import java.util.regex.Matcher;

public enum MainMenuCommands implements MenuCommands {
    Logout,
    EnterMenu,
    ExitMenu,
    ShowCurrentMenu;

    public Matcher getMatcher(String input) {}

}
