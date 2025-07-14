package org.example.server.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InGameCommandLineCommands {

    Cheat("\\s*terminal\\s+(?<cheat>.+)\\s*");

    private final String pattern;

    InGameCommandLineCommands(String pattern) {
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
