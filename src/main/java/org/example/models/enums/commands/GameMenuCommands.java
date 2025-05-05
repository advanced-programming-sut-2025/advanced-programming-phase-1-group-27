package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements MenuCommands {
    ExitGame("exit\\s+game"),
    NextTurn("next\\s+turn"),
    TerminateGame("terminate\\s+game"),
    EnterMenu("menu\\s+enter\\s+(?<menuName>.+)"),
    ExitMenu("menu\\s+exit"),
    ShowCurrentMenu("show\\s+current\\s+menu"),
    Home("go\\s+to\\s+home"),
    ShowWeather("weather"),
    ForecastWeather("weather\\s+forecast"),
    CheatSetWeather("cheat\\s+weather\\s+set\\s+(?<weatherName>\\S+)"),
    CheatThor("cheat\\s+Thor\\s+-l\\s+(?<i>\\d)\\s+(?<j>\\d)");

    private final String pattern;

    GameMenuCommands(String pattern) {
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
