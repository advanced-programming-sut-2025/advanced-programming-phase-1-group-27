package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Building;

public class GreenHouse extends Building {
    private Cell door;
    private Cell[][] cells = new Cell[5][6];
    private boolean isRepaired = false;

    public GreenHouse(Cell door, Cell topLeftCell) {
        this.door = door;
        super(topLeftCell);
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void repair() {
        this.isRepaired = true;
    }
}
