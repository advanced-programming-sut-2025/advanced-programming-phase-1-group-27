package org.example.common.models;

public abstract class Building {
    protected final int height, width;
    protected Cell topLeftCell;

    public Building(Cell topLeftCell, int height, int width) {
        this.topLeftCell = topLeftCell;
        this.height = height;
        this.width = width;
    }

    public Cell getTopLeftCell() {
        return topLeftCell;
    }
}
