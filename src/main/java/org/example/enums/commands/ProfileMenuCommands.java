package org.example.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements MenuCommands {
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    ChangeUsername,
    ChangeNickname,
    ChangeEmail,
    ChangePassword,
    UserInfo,
    ExitMenu,
    ShowCurrentMenu;

    private final String pattern;

    ProfileMenuCommands(String pattern) {
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
