package org.example.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements MenuCommands {
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    Register,
    PickQuestion,
    Login,
    ForgetPassword,
    AnswerQuestion,
    ExitMenu,
    ShowCurrentMenu;

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
