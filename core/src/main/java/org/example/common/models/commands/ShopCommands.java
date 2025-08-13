package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopCommands {
    BuildBuilding("\\s*build\\s+-a\\s+(?<buildingName>.+)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*"),
    ShowAllProducts("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    Purchase("\\s*purchase\\s+(?<productName>\\S+)(?:\\s+-n\\s+(?<count>\\d+))?\\s*"),
    Sell("\\s*sell\\s+(?<productName).+)\\s+(?-n.+)\\s+count\\s*"),
    BuyAnimal("buy\\s+animal\\s+-a\\s+(?<animal>\\S.*\\S)\\s+-n\\s+(?<name>\\S.*\\S)");

    private final String pattern;

    ShopCommands(String pattern) {
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
