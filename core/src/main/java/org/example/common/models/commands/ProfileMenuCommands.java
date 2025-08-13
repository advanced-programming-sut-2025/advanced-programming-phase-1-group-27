package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements MenuCommands {
    ChangeUsername("change\\s+username\\s+-u\\s+(?<username>.+)"),
    ChangeNickname("change\\s+nickname\\s+-u\\s+(?<nickname>.+)"),
    ChangeEmail("change\\s+email\\s+-e\\s+(?<email>.+)"),
    ChangePassword("change\\s+password\\s+-p\\s+(?<newPassword>.+)\\s+-o\\s+(?<oldPassword>.+)"),
    UserInfo("user\\s+info");

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
