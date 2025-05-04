package org.example.models.enums.commands;

public enum InteractionsWithNPCCommands {
    MeetNPC("\\s*meet\\s+NPC\\s+(?<npcName>.+)\\s*"),
    GiftNPC("\\s*gift\\s+NPC\\s+(?<npcName>.+)\\s+-i\\s+(?<item>.+)\\s*"),
    FriendshipNPCList("\\s*friendship\\s+NPC\\s+list\\s*"),
    QuestList("\\s*quests\\s+list\\s*"),
    QuestFinish("\\s*quests\\s+finish\\s+-i\\s+(?<index>\\d+)\\s*");

    private final String pattern;

    InteractionsWithNPCCommands(String pattern) {
        this.pattern = pattern;
    }

}
