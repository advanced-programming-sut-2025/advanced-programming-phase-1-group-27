package org.example.models;

import org.example.models.enums.CellType;

import java.util.ArrayList;

public class Cell {
    private Object object = null;
    private CellType cellType;
    private Place place = null;
    private Position position;
    private Character view = null;

    private ArrayList<Cell> adjacentCells = new ArrayList<>();

    public Cell() {
        cellType = CellType.Free;
    }
    public void addAdjacentCell(Cell cell) {
        adjacentCells.add(cell);
    }

    public boolean isPassable() {
        return cellType == CellType.Plowed || cellType == CellType.Free;
    }

    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

}
