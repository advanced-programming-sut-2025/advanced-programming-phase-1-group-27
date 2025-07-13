package org.example.server.models.Map;

import org.example.server.models.Building;
import org.example.server.models.Cell;
import org.example.server.models.Player;

public class Hut extends Building {
    private Cell door;
    private Player owner;

    public Hut(Cell door, Cell topLeftCell) {
        super(topLeftCell, 4, 4);
        this.door = door;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Cell getDoor() {
        return door;
    }
}
