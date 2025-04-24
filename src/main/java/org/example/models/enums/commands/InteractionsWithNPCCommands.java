package org.example.models.enums.commands;

public enum InteractionsWithNPCCommands {
    MeetNPC(),
    GiftNPC(),
    FriendshipNPCList(),
    QuestList(),
    QuestFinish();

    private final String pattern;

    InteractionsWithNPCCommands(String pattern) {
        this.pattern = pattern;
    }

}
