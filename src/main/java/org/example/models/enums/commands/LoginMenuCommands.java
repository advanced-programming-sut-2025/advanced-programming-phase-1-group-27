package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements MenuCommands {
    Register("register\\s+-u\\s+(?<username>.+)\\s+" +
            "-p\\s+(?<password>.+)\\s+(?<reEnteredPassword>.+)\\s+" +
            "-n\\s+(?<nickname>.+)\\s+" +
            "-e\\s+(?<email>.+)\\s+" +
            "-g\\s+(?<gender>.+)"),
    PickQuestion("pick\\s+question\\s+-q\\s+(?<questionId>\\d+)\\s+-a\\s+(?<answer>.+)\\s+-c\\s+(?<reenteredAnswer>.+)"),
    Login("login\\s+-u\\s+(?<username>.+)\\s+-p\\s+(?<password>.+)(?:\\s+(?<stay>--stay-logged-in))?"),
    ForgetPassword("forget\\s+password\\s+-u\\s+(?<username>.+)"),
    AnswerQuestion("answer\\s+-a\\s+(?<answer>.+)"),
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    ExitMenu("menu\\s+exit"),
    ShowCurrentMenu("show\\s+current\\s+menu");

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
