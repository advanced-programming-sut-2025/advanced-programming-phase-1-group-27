package org.example.models;

import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Map.*;
import org.example.models.NPCs.NPC;
import org.example.models.enums.Plants.*;
import org.example.models.enums.CellType;
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
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getCurrentCell() == this && player != App.getCurrentGame().getCurrentPlayer())
                return false;
        }
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

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void plant(Plant plant) {
        object = plant;
        plant.setCell(this);
    }

    public void placeForagingCrop() {
        Season currentSeason = App.getCurrentGame().getTime().getSeason();
        int randomInt = new Random( ).nextInt(
                CropType.getForagingCropsBySeason().get(currentSeason).size());
        Crop crop = new Crop(CropType.getForagingCropsBySeason().get(currentSeason).get(randomInt));
        crop.setTillNextHarvest(0);
        crop.setForaging(true);
        plant(crop);
    }

    public void placeForagingTree() {
        Tree tree = new Tree(TreeType.getForagingTrees().get(new Random().nextInt(TreeType.getForagingTrees().size())));
        tree.setTillNextHarvest(0);
        tree.setForaging(true);
        plant(tree);
    }

    public void placeForagingSeed() {
        Season currentSeason = App.getCurrentGame().getTime().getSeason();
        SeedType seedType = SeedType.getForagingSeedsBySeason().get(currentSeason).get(
                new Random().nextInt(SeedType.getForagingSeedsBySeason().get(currentSeason).size()));
        if (seedType.getPlant() == null) {
             CropType cropType = CropType.getMixedSeedPossibilitiesBySeason().get(currentSeason).get(
                     new Random().nextInt(CropType.getMixedSeedPossibilitiesBySeason().get(currentSeason).size()));
             Crop crop = new Crop(cropType);
             crop.setForaging(true);
             plant(crop);
        } else {
            Crop crop = new Crop(seedType.getPlant());
            crop.setForaging(true);
            plant(crop);
        }
    }

    public void placeForagingMineral() {
        ArrayList<MineralType> foragingMinerals = MineralType.getForagingMinerals();
        int randomInt = new Random( ).nextInt(foragingMinerals.size());
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
        int k = 1;
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (this == player.getCurrentCell())
                return "\u001B[36m" + k + "\u001B[0m";
            k++;
        }
        if (cellType.equals(CellType.MapLink)) {
            return "\033[43m" + " " + "\u001B[0m";
        } else if (object != null) {
            switch (object) {
                case Tree tree -> {
                    return "\u001B[32m" + "T" + "\u001B[0m";
                }
                case Crop crop -> {
                    return "\u001B[32m" + "C" + "\u001B[0m";
                }
                case MineralType mineralType -> {
                    return "\u001B[47m" + " " + "\u001B[0m";
                }
                case NPC npc -> {
                    return "\u001B[34m" + Character.toUpperCase(npc.getName().charAt(0)) + "\u001B[0m";
                }
                default -> {
                }
            }
        } else if (cellType.equals(CellType.Quarry) && object == null) {
            return "\033[0;107m" + " " + "\u001B[0m";
        }
        else if (cellType.equals(CellType.Free)) {
            return "\033[0;33m" + " " + "\u001B[0m";
        } else if (cellType.equals(CellType.Water)) {
            return "\u001B[44m" + " " + "\u001B[0m";
        } else if (cellType.equals(CellType.Door)) {
            return "\u001B[47m" + " " + "\u001B[0m";
        } else if (cellType.equals(CellType.Building)) {
            if (building instanceof Hut) {
                return "\u001B[41m" + " " + "\u001B[0m";
            } else if (building instanceof GreenHouse){
                return "\u001B[42m" + " " + "\u001B[0m";
            } else if (building instanceof NPCHouse) {
                return "\u001B[41m" + "N" + "\u001B[0m";
            } else if (building instanceof StoreBuilding) {
                return "\u001B[44m" + "S" + "\u001B[0m";
            } else if (building instanceof Barn) {
                return "\u001B[43m" + "B" + "\u001B[0m";
            } else if (building instanceof Coop) {
                return "\u001B[43m" + "Q" + "\u001B[0m";
            }
        } else if (cellType.equals(CellType.View)) {
            return "\u001B[35m" + "~" + "\u001B[0m";
        }
        return "?";
    }

}
