package org.example.models.Stores;

import org.example.enums.NPCType;
import org.example.models.Map;
import org.example.models.Position;

public abstract class Store {
    private NPCType manager;
    private int start;
    private int end;
    private Position door;
    private Map map;

    public Store(NPCType manager , int start , int end) {
        this.manager = manager;
        this.start = start;
        this.end = end;
    }

}


