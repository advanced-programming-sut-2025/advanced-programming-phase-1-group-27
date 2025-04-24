package org.example.enums;

import org.example.models.Stack;

import java.util.ArrayList;

public enum Shops {
    Blacksmith(NPCType.Clint , 9 , 16),
    CarpenterShop(NPCType.Morris , 9 , 23),
    FishShop(NPCType.Pierre , 9 , 17),
    JojaMart(NPCType.Robin , 9 , 20),
    MarnieShop(NPCType.Willy , 9 , 17),
    PierreGeneralStore(NPCType.Marnie , 9 , 16),
    TheStardropSaloon(NPCType.Gus , 12 , 24);

    private final NPCType manager;
    private final int startTime;
    private final int endTime;
    private final ArrayList<Stack> stacks;



    Shops(NPCType manager , int startTime, int endTime, ArrayList<Stack> stacks) {
        this.manager = manager;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stacks = stacks;
    }
}
