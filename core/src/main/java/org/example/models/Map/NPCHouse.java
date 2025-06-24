package org.example.models.Map;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.NPCs.NPC;

public class NPCHouse extends Building {
    private final NPC npc;
    private Cell door;

    public NPCHouse(NPC npc, Cell topLeftCell) {
        super(topLeftCell, 4, 4);
        this.npc = npc;
    }

    public NPC getNpc() {
        return npc;
    }

    public Cell getDoor() {
        return door;
    }

    public void setDoor(Cell door) {
        this.door = door;
    }
}
