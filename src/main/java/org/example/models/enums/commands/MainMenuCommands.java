package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements MenuCommands {
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    NewGame("game\\s+new\\s+-u" +
            "(?:\\s+(?<username1>\\S+))?" +
            "(?:\\s+(?<username2>\\S+))?" +
            "(?:\\s+(?<username3>\\S+))?" +
            "(?:\\s+(?<overflow>\\S+))?"),
    ExitMenu("menu\\s+exit"),
    ShowCurrentMenu("show\\s+current\\s+menu"),
    Logout("user\\s+logout");

    private final String pattern;

    MainMenuCommands(String pattern) {
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
