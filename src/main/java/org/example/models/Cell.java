package org.example.models;

import org.example.models.Map.Hut;
import org.example.models.enums.CellType;
import org.example.models.enums.items.CropType;
import org.example.models.enums.items.MineralType;

import java.util.ArrayList;
import java.util.Random;

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

    public void setType(CellType cellType) {
        this.cellType = cellType;
    }
    public CellType getType() {
        return cellType;
    }
    public void setPlace(Place place) {
        this.place = place;
    }
    public void placeForagingCrop() {
        int randomInt = new Random().nextInt(
                CropType.getForagingCropsBySeason().get(App.getCurrentGame().getTime().getSeason()).size());
        cellType = CellType.Occupied;
        object = new Crop(CropType.getForagingCropsBySeason().get(randomInt)); // TODO
    }
    public void placeForagingMineral() {
        int randomInt = new Random().nextInt(MineralType.values().length);
        cellType = CellType.Occupied;
        object = MineralType.values()[randomInt];
    }

    @Override
    public String toString() {
        if (cellType.equals(CellType.Free)) {
            return "\\s";
        } else if (cellType.equals(CellType.Water)) {
            return "W";
        } else if (cellType.equals(CellType.Door)) {
            return "D";
        } else if (cellType.equals(CellType.Building)) {
            if (place instanceof Hut) {
                return "H";
            } else {
                return "G";
            }
        } else if (cellType.equals(CellType.Occupied)) {
            if (object instanceof Crop) {
                return "C";
            } else if(object instanceof MineralType) {
                return "R";
            }
        }
        return "?";
    }
}
