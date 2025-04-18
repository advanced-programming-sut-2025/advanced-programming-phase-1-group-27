package enums.Commands;

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
