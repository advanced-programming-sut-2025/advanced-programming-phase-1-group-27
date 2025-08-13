package org.example.common.models.Map;

import org.example.common.models.Building;
import org.example.common.models.Cell;
import org.example.common.models.Player;
import org.example.common.models.items.ToolType;
import org.example.common.models.tools.Backpack;

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
