package enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCommands {
    CheatCode("\\s*cheat\\s+code\\s*"),
    CheatAdvanceTime("\\s*cheat\\s+advance\\s+time\\s+(?<X>\\d+)h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s+advance\\s+date\\s+(?<X>\\d+)d\\s*"),
    CheatThor("\\s*cheat\\s+Thor\\s+-l\\s+(?<x>\\d+)\\s+,\\s+(?<y>\\d+)\\s*"),
    CheatWeatherSet("\\s*cheat\\s+weather\\s+set\\s+(?<Type>.+)\\s*"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    CheatSetFriendShip("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>.+)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    CheatAdd("\\s*cheat\\s+add\\s+(?<count>\\d+)\\s+dollars\\s*");

    private final String pattern;

    CheatCommands(String pattern) {
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
