package org.example.common.models.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCommands {

    CheatCode("\\s*cheat\\s+code\\s*"),
    CheatAdvanceTime("\\s*cheat\\s+advance\\s+time\\s+(?<timeString>\\d+)h\\s*"),
    CheatAdvanceDate("\\s*cheat\\s+advance\\s+date\\s+(?<dayString>\\d+)d\\s*"),
    CheatThor("cheat\\s+Thor\\s+-l\\s+(?<i>\\d+)\\s+(?<j>\\d+)"),
    CheatSetWeather("cheat\\s+weather\\s+set\\s+(?<weatherName>\\S+)"),
    CheatSetEnergy("energy\\s+set\\s+-v\\s+(?<value>\\d+)"),
    CheatEnergyUnlimited("energy\\s+unlimited"),
    CheatAddItem("\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-c\\s+(?<count>\\d+)\\s*"),
    CheatSetFriendShip("\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    CheatAddLevelNPC("\\s*cheat\\s+add\\s+level\\s+NPC\\s+-n\\s+(?<name>.+)\\s+-l\\s+(?<level>\\d+)\\s*"),
    CheatAddLevelPlayer("\\s*cheat\\s+add\\s+level\\s+player\\s+-n\\s+(?<name>.+)\\s+-l\\s+(?<level>\\d+)\\s*"),
    CheatAddXPPlayer("\\s*cheat\\s+add\\s+level\\s+player\\s+-n\\s+(?<name>.+)\\s+-x\\s+(?<level>\\d+)\\s*"),
    CheatAddMoney("\\s*cheat\\s+add\\s+(?<amount>\\d+)\\s+dollars\\s*"),
    CheatSetAbility("cheat\\s+set\\s+ability\\s+-n\\s+(?<abilityName>.+)\\s+-l\\s+(?<level>\\d+)"),
    ToAllChat("\\s*!(?<chatText>.+)\\s*"),
    PrivateChat("\\s*@(?<targetPlayer>\\S+)\\s(?<chatText>.+)\\s*");


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
