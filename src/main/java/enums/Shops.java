package enums;

import models.Stack;
import models.Stores.Store;
import models.Time;

import java.util.ArrayList;

public enum Shops {
    Blacksmith(NPCType.Clint , 9 , 16),
    CarpenterShop(NPCType.Morris , 9 , 23),
    FishShop(NPCType.Pierre , 9 , 17),
    JojaMart(NPCType.Robin , 9 , 20),
    MarnieShop(NPCType.Willy , 9 , 17),
    PierreGeneralStore(NPCType.Marnie , 9 , 16),
    TheStardropSaloon(NPCType.Gus , 12 , 24);

    private final NPCType owner;
    private final int startTime;
    private final int endTime;


    Shops(NPCType npcType , int startTime, int endTime) {
        this.owner = npcType;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
