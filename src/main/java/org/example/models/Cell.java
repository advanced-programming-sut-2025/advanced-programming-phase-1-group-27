package org.example.models;

import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Map.GreenHouse;
import org.example.models.Map.Hut;
import org.example.models.Map.NPCHouse;
import org.example.models.Map.StoreBuilding;
import org.example.models.enums.Plants.Crop;
import org.example.models.enums.Plants.Plant;
import org.example.models.enums.Plants.Tree;
import org.example.models.enums.CellType;
import org.example.models.enums.Plants.CropType;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.items.MineralType;

import java.util.ArrayList;
import java.util.Random;

public class Cell {
    private final Position position;
    private Object object = null;
    private CellType cellType;
    private Building building = null;

    private ArrayList<Cell> adjacentCells = new ArrayList<>();

    public Cell(CellType cellType, Position position) {
        this.cellType = cellType;
        this.position = position;
    }

    public Cell(Position position) {
        cellType = CellType.Free;
        this.position = position;
    }

    public void addAdjacentCell(Cell cell) {
        adjacentCells.add(cell);
    }

    public boolean isPassable() {
        return cellType == CellType.Plowed || cellType == CellType.Free || cellType == CellType.View ||
                cellType == CellType.Door;
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
    public void setBuilding(Building building) {
        this.building = building;
    }

    public void plant(Plant plant) {
        cellType = CellType.Occupied;
        object = plant;
        plant.setCell(this);
    }

    public void placeForagingCrop() {
        Season currentSeason = App.getCurrentGame().getTime().getSeason();
        int randomInt = new Random(System.currentTimeMillis()).nextInt(
                CropType.getForagingCropsBySeason().get(currentSeason).size());
        plant(new Crop(CropType.getForagingCropsBySeason().get(currentSeason).get(randomInt)));
    }

    public void placeForagingMineral() {
        ArrayList<MineralType> foragingMinerals = MineralType.getForagingMinerals();
        int randomInt = new Random(System.currentTimeMillis()).nextInt(foragingMinerals.size());
        cellType = CellType.Occupied;
        object = foragingMinerals.get(randomInt);
    }

    public Position getPosition() {
        return position;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void thor() {
        if (cellType == CellType.Occupied && object instanceof Tree) {
            object = new Stacks(MineralType.Coal, 1);
        } else if (cellType == CellType.Occupied && object instanceof Plant) {
            cellType = CellType.Free;
            object = null;
        }
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
            if (building instanceof Hut) {
                return "H";
            } else if (building instanceof GreenHouse){
                return "G";
            } else if (building instanceof NPCHouse) {
                return "N";
            } else if (building instanceof StoreBuilding) {
                return "S";
            } else if (building instanceof Barn) {
                return "B";
            } else if (building instanceof Coop) {
                return "Q";
            }
        } else if (cellType.equals(CellType.Occupied)) {
            if (object instanceof Tree) {
                return "T";
            } else if (object instanceof Crop) {
                return "C";
            } else if(object instanceof MineralType) {
                return "R";
            }
        } else if (cellType.equals(CellType.View)) {
            return "~";
        }
        return "?";
    }

}
