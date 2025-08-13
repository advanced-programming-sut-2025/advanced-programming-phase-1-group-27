package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements MenuCommands {
    Login("login\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>\\S+)(?:\\s+(?<stay>--stay-logged-in))?"),
    ForgetPassword("forget\\s+password\\s+-u\\s+(?<username>.+)"),
    AnswerQuestion("answer\\s+-a\\s+(?<answer>.+)");

    private final String pattern;

    LoginMenuCommands(String pattern) {
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
