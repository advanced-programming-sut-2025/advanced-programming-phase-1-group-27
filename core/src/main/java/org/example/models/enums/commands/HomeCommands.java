package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HomeCommands {
    ShowCraftingRecipes("crafting\\s+show\\s+recipes"),
    CraftItem("crafting\\s+craft\\s+(?<itemName>.+)"),
    PutPickRefrigerator("cooking\\s+refrigerator\\s+(?<function>put|pick)\\s+(?<itemName>.+)"),
    ShowCookingRecipes("cooking\\s+show\\s+recipes"),
    CookItem("cooking\\s+prepare\\s+(?<itemName>.+)");

    private final String pattern;

    HomeCommands(String pattern) {
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
