package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Place;

public class GreenHouse extends Place {
    private Cell door;
    private Cell[][] cells = new Cell[5][6];
    private boolean isRepaired = false;

    public GreenHouse(Cell door) {
        this.door = door;
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void repair() {
        this.isRepaired = true;
    }
}
