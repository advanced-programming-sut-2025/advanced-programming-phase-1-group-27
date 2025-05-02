package org.example.models.enums;

import org.example.models.Stacks;

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
    private ArrayList<Stacks> stacks;

    Shops(NPCType manager , int startTime, int endTime , ArrayList<Stacks> stacks) {
        this.manager = manager;
        this.startTime = startTime;
        this.endTime = endTime;
    }


}
