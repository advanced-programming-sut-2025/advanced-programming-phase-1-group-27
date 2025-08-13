package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands implements MenuCommands {
    Register("registerViaGraphics\\s+-u\\s+(?<username>.+)\\s+" +
            "-p\\s+(?<password>\\S+)\\s+(?<reEnteredPassword>\\S+)\\s+" +
            "-n\\s+(?<nickname>.+)\\s+" +
            "-e\\s+(?<email>.+)\\s+" +
            "-g\\s+(?<gender>.+)"),
    PickQuestion("pick\\s+question\\s+-q\\s+(?<questionId>-?\\d+)\\s+-a\\s+(?<answer>.+)\\s+-c\\s+(?<reenteredAnswer>.+)");

    private final String pattern;

    RegisterMenuCommands(String pattern) {
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
