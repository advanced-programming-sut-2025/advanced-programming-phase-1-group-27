package org.example.models.enums.commands;

public enum InteractionsWithUserCommands {
    Friendships("\\s*friendships\\s*"),
    TalkToUser("\\s*talk\\s+-u\\s+(?<username>.+)\\s+-m\\s+(?<message>.+)\\s*"),
    TalkHistory("\\s*talk\\s+history\\s+-u\\s+(?<username>\\s*"),
    Gift("\\s*gift\\s+-u\\s+(?<username>.+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>.+)\\s*"),
    GiftList("\\s*gift\\s+list\\s*"),
    GiftRate("\\s*gift\\s+rate\\s+-i\\s+(?<gift-number>.+)\\s+-r\\s+(?<rate>.+)\\s*"),
    GiftHistory("\\s*gift\\s+history\\s+-u\\s+(?<username>.+)\\s*"),
    Hug("\\s*hug\\s+-u\\s+(?<username>.+)\\s*"),
    Flower("\\s*flower\\s+-u\\s+(?<username>.+)\\s*"),
    AskMarriage("\\s*ask\\s+marriage\\s+-u\\s+(?<username>.+)\\s+-r\\s+(?<ring>.+)\\s*"),
    Respond("\\s*respond\\s+(<respond>-accept | –reject)\\s+-u\\s+(?<username>.+)\\s*"),
    StartTrade("\\s*start\\s+trade\\s*"),
    Trade("trade -u <username> -t <type> -i <item> -a <amount> [-p <price>] [-ti <targetItem> | -ta <targetAmount>]"),
    TradeList("\\s*trade\\s+list\\s*"),
    TradeResponse(),
    TradeHistory();

    private final String pattern;

    InteractionsWithUserCommands(String pattern) {
        this.pattern = pattern;
    }
}
