package org.example.models;

import org.example.models.Map.Map;

public abstract class Building {
    protected Cell topLeftCell;
    protected final int height, width;

    public Building(Cell topLeftCell, int height, int width) {
        this.topLeftCell = topLeftCell;
        this.height = height;
        this.width = width;
    }

    public Cell getTopLeftCell() {
        return topLeftCell;
    }
}
