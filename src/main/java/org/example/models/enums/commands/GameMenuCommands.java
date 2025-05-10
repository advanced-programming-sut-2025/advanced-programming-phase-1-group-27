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
    PlaceItem("place\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-d\\s+(?<direction>\\d+)"),
    ShowWeather("weather"),
    ForecastWeather("weather\\s+forecast"),
    Walk("walk\\s+-l\\s+(?<i>\\d)\\s+(?<j>\\d)"),
    PrintMap("print\\s+map\\s+-l\\s+(?<i>\\d)\\s+(?<j>\\d)\\s+-s\\s+(?<size>\\d+)"),
    HelpReadingMap("help\\s+reading\\s+map"),
    ShowEnergy("energy\\s+show"),
    ToolsUse("tools\\s+use\\s+-d\\s+(?<direction>\\d)"),
    CropInfo("crop\\s+info\\s+-n\\s+(?<cropName>\\S(.*\\S)?)\\s*"),
    Fishing("fishing\\s+-p\\s+(?<fishPole>.+)");

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
