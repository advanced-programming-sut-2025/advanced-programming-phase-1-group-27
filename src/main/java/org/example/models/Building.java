package org.example.models;

public abstract class Building {
    protected Cell topLeftCell;

    public Building(Cell topLeftCell) {
        this.topLeftCell = topLeftCell;
    }

    public Cell getTopLeftCell() {
        return topLeftCell;
    }
}
