package enums.commands;

import java.util.regex.Matcher;

public enum ProfileMenuCommands implements MenuCommands {
    ChangeUsername,
    ChangeNickname,
    ChangeEmail,
    ChangePassword,
    UserInfo,
    EnterMenu,
    ExitMenu,
    ShowCurrentMenu;

    public Matcher getMatcher(String input) {}
}
