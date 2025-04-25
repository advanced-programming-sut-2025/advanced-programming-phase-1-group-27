package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements MenuCommands {
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    ExitMenu("menu\\s+exit"),
    ShowCurrentMenu("show\\s+current\\s+menu");

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
