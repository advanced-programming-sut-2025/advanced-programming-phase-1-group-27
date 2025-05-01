package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Place;

public class Hut extends Place {
    private Cell door;

    public Hut(Cell door) {
        this.door = door;
    }
}
