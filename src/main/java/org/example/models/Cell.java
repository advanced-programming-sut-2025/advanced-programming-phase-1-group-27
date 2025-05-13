package org.example.models;

import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Map.*;
import org.example.models.NPCs.NPC;
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
    private final Map map;
    private Object object = null;
    private CellType cellType;
    private Building building = null;
    boolean isQuarry = false;

    private ArrayList<Cell> adjacentCells = new ArrayList<>();

    public Cell(CellType cellType, Position position, Map map) {
        this.cellType = cellType;
        this.position = position;
        this.map = map;
    }

    public Cell(Position position, Map map) {
        cellType = CellType.Free;
        this.position = position;
        this.map = map;
    }

    public void addAdjacentCell(Cell cell) {
        adjacentCells.add(cell);
    }

    public boolean isPassable() {
        return cellType == CellType.Plowed || cellType == CellType.Free || cellType == CellType.View ||
                cellType == CellType.Door || cellType == CellType.MapLink;
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

    public Building getBuilding() {
        return building;
    }

    public boolean isQuarry() {
        return isQuarry;
    }

    public void setQuarry(boolean quarry) {
        isQuarry = quarry;
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
        int randomInt = new Random( ).nextInt(
                CropType.getForagingCropsBySeason().get(currentSeason).size());
        Crop crop = new Crop(CropType.getForagingCropsBySeason().get(currentSeason).get(randomInt));
        crop.setTillNextHarvest(0);
        plant(crop);
    }

    public void placeForagingMineral() {
        ArrayList<MineralType> foragingMinerals = MineralType.getForagingMinerals();
        int randomInt = new Random( ).nextInt(foragingMinerals.size());
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
        if (object instanceof Tree) {
            object = new Stacks(MineralType.Coal, 1);
        } else if (object instanceof Plant && building == null) {
            cellType = CellType.Free;
            object = null;
        }
    }

    public Map getMap() {
        return map;
    }

    @Override
    public String toString() {
        if (this == App.getCurrentGame().getCurrentPlayer().getCurrentCell()) {
            return "Y";
        }
        else if (isQuarry && object == null) {
            return "\u001B[30m" + "#" + "\u001B[0m";
        }
        else if (cellType.equals(CellType.Free)) {
            return "\s";
        } else if (object instanceof NPC) {
            return "\u001B[36m" + "N" + "\u001B[0m";
        } else if (cellType.equals(CellType.Water)) {
            return "\u001B[34m" + "W" + "\u001B[0m";
        } else if (cellType.equals(CellType.Door)) {
            return "D";
        } else if (cellType.equals(CellType.Building)) {
            if (building instanceof Hut) {
                return "\u001B[41m" + "H" + "\u001B[0m";
            } else if (building instanceof GreenHouse){
                return "\u001B[42m" + "G" + "\u001B[0m";
            } else if (building instanceof NPCHouse) {
                return "\u001B[41m" + "N" + "\u001B[0m";
            } else if (building instanceof StoreBuilding) {
                return "\u001B[44m" + "S" + "\u001B[0m";
            } else if (building instanceof Barn) {
                return "\u001B[43m" + "B" + "\u001B[0m";
            } else if (building instanceof Coop) {
                return "\u001B[43m" + "Q" + "\u001B[0m";
            }
        } else if (cellType.equals(CellType.Occupied)) {
            if (object instanceof Tree) {
                return "\u001B[32m" + "T" + "\u001B[0m";
            } else if (object instanceof Crop) {
                return "\u001B[32m" + "C" + "\u001B[0m";
            } else if(object instanceof MineralType) {
                return "\u001B[32m" + "R" + "\u001B[0m";
            }
        } else if (cellType.equals(CellType.View)) {
            return "\u001B[35m" + "~" + "\u001B[0m";
        } else if (cellType.equals(CellType.MapLink)) {
            return "\u001B[33m" + "L" + "\u001B[0m";
        }
        return "?";
    }

}
