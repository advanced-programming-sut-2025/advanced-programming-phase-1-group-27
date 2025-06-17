package org.example.models.Map;

import org.example.models.App;
import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.NPCs.NPC;
import org.example.models.enums.NPCType;

public class NPCHouse extends Building {
    private final NPC npc;
    private Cell door;

    public NPCHouse(NPC npc, Cell topLeftCell) {
        this.npc = npc;
        super(topLeftCell, 4, 4);
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
