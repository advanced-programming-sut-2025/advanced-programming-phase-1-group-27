package models.Stores;

import enums.NPCType;

public abstract class Store {
    private NPCType manager;
    private int start;
    private int end;
    //private Position door;
    //private Map map;

    public Store(NPCType manager , int start , int end) {
        this.manager = manager;
        this.start = start;
        this.end = end;
    }

}


