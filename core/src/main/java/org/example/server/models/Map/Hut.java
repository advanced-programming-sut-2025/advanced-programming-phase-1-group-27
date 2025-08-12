package org.example.server.models.Map;

import org.example.server.models.Building;
import org.example.server.models.Cell;
import org.example.server.models.Player;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

public class Hut extends Building {
    // items which are place in the fridge
    private Backpack refrigerator = new Backpack(ToolType.LargeBackpack);
    private Cell door;
    private Player owner;

    public Hut(Cell door, Cell topLeftCell) {
        super(topLeftCell, 4, 4);
        this.door = door;
    }

    public Backpack getRefrigerator() {
        return refrigerator;
    }

    public void setRefrigerator(Backpack refrigerator) {
        this.refrigerator = refrigerator;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) { // obsolete
        this.owner = owner;
    }

    public Cell getDoor() {
        return door;
    }
}
