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
    Walk("walk\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)"),
    PrintMap("print\\s+map\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)\\s+-s\\s+(?<size>\\d+)"),
    HelpReadingMap("help\\s+reading\\s+map"),
    ShowEnergy("energy\\s+show"),
    ToolsUse("tools\\s+use\\s+-d\\s+(?<direction>\\d+)"),
    CropInfo("crop\\s+info\\s+-n\\s+(?<cropName>\\S(.*\\S)?)\\s*"),
    Fishing("fishing\\s+-p\\s+(?<fishPole>.+)"),
    BuildGreenHouse("greenhouse\\s+build"),
    Plant("plant\\s+-s\\s+(?<seedName>\\S.*\\S)\\s+-d\\s+(?<direction>\\d+)"),
    ShowPlant("show\\s+plant\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)"),
    UseArtisan("artisan\\s+use\\s+(?<artisanName>\\S+)\\s+(?<itemList>.+)"),
    GetArtisan("artisan\\s+get\\s+(?<artisanName>\\S+)"),
    Pet("pet\\s+-n\\s+(?<name>\\S.*\\S)"),
    ShowAnimals("animals"),
    Shepherd("shepherd\\s+animals\\s+-n\\s+(?<name>\\S.*\\S)\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)"),
    FeedHay("feed\\s+hay\\s+-n\\s+(?<name>\\S.*\\S)"),
    Products("produces"),
    CollectProducts("collect\\s+produces\\s+-n\\s+(?<name>\\S.*\\S)"),
    SellAnimal("sell\\s+animal\\s+-n\\s+(?<name>\\S.*\\S)"),
    GoToShop("go\\s+to\\s+shop\\+(?<shopName>.+)");

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
