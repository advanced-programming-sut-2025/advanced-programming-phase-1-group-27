package org.example.models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InteractionsWithNPCCommands {
    MeetNPC("\\s*meet\\s+NPC\\s+(?<npcName>.+)\\s*"),
    GiftNPC("\\s*gift\\s+NPC\\s+(?<npcName>.+)\\s+-i\\s+(?<item>.+)\\s*"),
    FriendshipNPCList("\\s*friendship\\s+NPC\\s+list\\s*"),
    QuestList("\\s*quests\\s+list\\s*"),
    QuestFinish("\\s*quests\\s+finish\\s+-n\\s+(?<npcName>.+)\\s+-i\\s+(?<index>\\d+)\\s*");

    private final String pattern;

    InteractionsWithNPCCommands(String pattern) {
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
