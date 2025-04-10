package enums.commands;

import java.util.regex.Matcher;

public enum LoginMenuCommands implements MenuCommands {
    Register,
    PickQuestion,
    Login,
    ForgetPassword,
    AnswerQuestion,
    EnterMenu,
    ExitMenu,
    ShowCurrentMenu;

    public Matcher getMatcher(String input) {}

}
