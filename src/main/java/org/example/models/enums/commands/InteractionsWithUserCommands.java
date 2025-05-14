package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InteractionsWithUserCommands {
    Friendships("\\s*friendships\\s*"),
    TalkToUser("\\s*talk\\s+-u\\s+(?<username>.+)\\s+-m\\s+(?<message>.+)\\s*"),
    TalkHistory("\\s*talk\\s+history\\s+-u\\s+(?<username>.+)\\s*"),
    Gift("\\s*gift\\s+-u\\s+(?<username>.+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>.+)\\s*"),
    GiftList("\\s*gift\\s+list\\s*"),
    GiftRate("\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>.+)\\s+-r\\s+(?<rate>\\d+)\\s*"),
    GiftHistory("\\s*gift\\s+history\\s+-u\\s+(?<username>.+)\\s*"),
    Hug("\\s*hug\\s+-u\\s+(?<username>.+)\\s*"),
    Flower("\\s*flower\\s+-u\\s+(?<username>.+)\\s*"),
    AskMarriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>.+)\\s+-r\\s+(?<ring>.+)\\s*"),
    Respond("\\s*respond\\s+-(?<respond>accept|reject)\\s+-u\\s+(?<username>.+)\\s*"),
    StartTrade("\\s*start\\s+trade\\s*"),
    Trade("trade\\s+-u\\s+(?<username>.+)\\s+-t\\s+(?<type>.+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>\\d+)" +
            "(?:\\s+-p\\s+(?<price>\\d+))?(?:\\s+-ti\\s+(?<targetItem>\\S.*\\S)\\s+\\|\\s+-ta\\s+(?<targetAmount>\\d+))?"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse("trade\\s+response\\s+–(?<response>accept|reject)\\s+-i\\s+(?<id>\\d+)\\s*"),
    TradeHistory("trade\\s+history\\s*");

    private final String pattern;

    InteractionsWithUserCommands(String pattern) {
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
