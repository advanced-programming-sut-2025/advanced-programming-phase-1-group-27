package org.example.models.Map;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.enums.NPCType;

public class NPCHouse extends Building {
    private final NPCType npc;
    private Cell door;

    public NPCHouse(NPCType npc, Cell topLeftCell) {
        this.npc = npc;
        super(topLeftCell);
        npc.setHome(this);
    }

    public NPCType getNpc() {
        return npc;
    }


    public Cell getDoor() {
        return door;
    }

    public void setDoor(Cell door) {
        this.door = door;
    }
}
