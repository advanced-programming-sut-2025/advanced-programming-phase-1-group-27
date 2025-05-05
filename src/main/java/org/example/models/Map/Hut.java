package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Building;

public class Hut extends Building {
    private Cell door;

    public Hut(Cell door, Cell topLeftCell) {
        this.door = door;
        super(topLeftCell);
    }
}
