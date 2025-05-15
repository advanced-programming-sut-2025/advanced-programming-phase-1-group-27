package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements MenuCommands {
    NextTurn("next\\s+turn"),
    TerminateGame("exit\\s+game"),
    ExitMenu("menu\\s+exit"),
    PlaceItem("place\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-d\\s+(?<direction>\\d+)"),
    ShowWeather("weather"),
    ForecastWeather("weather\\s+forecast"),
    Walk("walk\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)"),
    PrintMap("print\\s+map\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)\\s+-s\\s+(?<size>\\d+)"),
    HelpReadingMap("help\\s+reading\\s+map"),
    ShowEnergy("energy\\s+show"),
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
    InventoryShow("inventory\\s+show"),
    InventoryTrash("inventory\\s+trash\\s+-i\\s+(?<itemName>.+)\\s+-n\\s+(?<number>\\d+)"),
    SellItem("sell\\s+(?<name>\\S.*\\S)(\\s+-n\\s+(?<amount>\\d+))?"),
    Time("time"),
    Date("date"),
    DateTime("datetime"),
    DayOfWeek("day\\s+of\\s+the\\s+week"),
    Season("season");

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
