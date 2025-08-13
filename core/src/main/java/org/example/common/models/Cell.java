package org.example.common.models;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.GameAssetManager;
import org.example.common.models.AnimalProperty.Animal;
import org.example.common.models.AnimalProperty.AnimalEnclosure;
import org.example.common.models.AnimalProperty.Barn;
import org.example.common.models.AnimalProperty.Coop;
import org.example.common.models.Map.*;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.Plants.*;
import org.example.common.models.Seasons.Season;
import org.example.common.models.items.MineralType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Cell {
    private final Position position;
    private final Map map;
    private Object object = null;
    private CellType cellType;
    private Building building = null;
    private String string = null;
    private boolean isProtected;

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

    public void handleInfo(LinkedTreeMap<String, Object> info) {
        this.cellType = CellType.getCellType((String) info.get("cellType"));
        this.isProtected = (boolean) info.get("isProtected");
        handleObjectInfo((LinkedTreeMap<String, Object>) info.get("object"));
        handleBuildingInfo((LinkedTreeMap<String, Object>) info.get("building"));
    }

    private void handleBuildingInfo(LinkedTreeMap<String, Object> info) {
        String type = (String) info.get("type");
        if (type.equals("shippingBin")) {
            ShippingBin shippingBin = new ShippingBin(this, (LinkedTreeMap<String, Object>) info.get("shippingBin"));
            building = shippingBin;
            setType(CellType.Building);
            if (map instanceof FarmMap farmMap)
                farmMap.addShippingBin(shippingBin);
        }
        else { // type == none
            if (building instanceof ShippingBin || building instanceof AnimalEnclosure)
                building = null;
        }
    }

    private void handleObjectInfo(LinkedTreeMap<String, Object> info) {
        String type = (String) info.get("type");
        if (type.equals("plant")) {
            Plant plant = Plant.handleInfo((LinkedTreeMap<String, Object>) info.get("plant"));
            object = plant;
            plant.setCell(this);
        }
        else if (type.equals("mineral")) {
            object = MineralType.getItem((String) info.get("mineral"));
        }
        else if (type.equals("artisan")) {
            object = new Artisan((LinkedTreeMap<String, Object>) info.get("artisan"));
        }
        else { // type == none
            if (object instanceof Plant || object instanceof MineralType || object instanceof Artisan) {
                object = null;
            }
        }
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("cellType", cellType.toString());
        info.put("isProtected", isProtected);
        info.put("object", getObjectInfo());
        info.put("building", getBuildingInfo());
        return info;
    }

    private HashMap<String, Object> getObjectInfo() {
        HashMap<String, Object> info = new HashMap<>();
        if (object instanceof Plant plant) {
            info.put("type", "plant");
            info.put("plant", plant.getInfo());
        }
        else if (object instanceof MineralType mineralType) {
            info.put("type", "mineral");
            info.put("mineral", mineralType.name());
        }
        else if (object instanceof Artisan artisan) {
            info.put("type", "artisan");
            info.put("artisan", artisan.getInfo());
        }
        else
            info.put("type", "none");
        return info;
    }

    private HashMap<String, Object> getBuildingInfo() {
        HashMap<String, Object> info = new HashMap<>();
        if (building instanceof ShippingBin shippingBin) {
            info.put("type", "shippingBin");
            info.put("shippingBin", shippingBin.getInfo());
        }
        else {
            info.put("type", "none");
        }
        return info;
    }

    public void addAdjacentCell(Cell cell) {
        adjacentCells.add(cell);
    }

    public boolean isPassable() {
//        for (Player player : App.getCurrentGame().getPlayers()) {
//            if (player.getCurrentCell() == this && player != App.getCurrentGame().getCurrentPlayer())
//                return false;
//        }

        if (object instanceof Plant || object instanceof Animal || object instanceof MineralType
        || object instanceof Artisan)
            return false;
        return cellType == CellType.Plowed || cellType == CellType.Free || cellType == CellType.View ||
                cellType == CellType.Door || cellType == CellType.MapLink || cellType == CellType.Quarry;
    }

    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public CellType getType() {
        return cellType;
    }

    public void setType(CellType cellType) {
        this.cellType = cellType;
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

    public void setString(String string) {
        this.string = string;
    }

    public void placeForagingCrop(Season currentSeason) {
        int randomInt = new Random().nextInt(
                CropType.getForagingCropsBySeason().get(currentSeason).size());
        CropType cropType = CropType.getForagingCropsBySeason().get(currentSeason).get(randomInt);
        placeCrop(cropType);
    }

    public void placeCrop(CropType cropType) {
        Crop crop = new Crop(cropType);
        crop.setTillNextHarvest(0);
        crop.setForaging(true);
        plant(crop);
    }

    public void placeForagingTree() {
        TreeType treeType = TreeType.getForagingTrees().get(new Random().nextInt(TreeType.getForagingTrees().size()));
        placeTree(treeType);
    }

    public void placeTree(TreeType treeType) {
        Tree tree = new Tree(treeType);
        tree.setTillNextHarvest(0);
        tree.maxCurrentStage();
        tree.setForaging(true);
        plant(tree);
    }

    public void placeForagingSeed(Season currentSeason) {
        SeedType seedType = SeedType.getForagingSeedsBySeason().get(currentSeason).get(
                new Random().nextInt(SeedType.getForagingSeedsBySeason().get(currentSeason).size()));
        if (seedType.getPlant() == null) {
            CropType cropType = CropType.getMixedSeedPossibilitiesBySeason().get(currentSeason).get(
                    new Random().nextInt(CropType.getMixedSeedPossibilitiesBySeason().get(currentSeason).size()));
            placeSeed(cropType);
        } else {
            placeSeed(seedType.getPlant());
        }
    }

    public void placeSeed(PlantType plantType) {
        Crop crop = new Crop(plantType);
        crop.setForaging(true);
        plant(crop);
    }

    public void placeForagingMineral() {
        ArrayList<MineralType> foragingMinerals = MineralType.getForagingMinerals();
        int randomInt = new Random().nextInt(foragingMinerals.size());
        placeMineral(foragingMinerals.get(randomInt));
    }

    public void placeMineral(MineralType mineral) {
        object = mineral;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
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
            object = MineralType.Coal;
            if (cellType == CellType.Plowed)
                cellType = CellType.Free;
        } else if (object instanceof Plant && building == null) {
            cellType = CellType.Free;
            object = null;
        }
    }

    public Map getMap() {
        return map;
    }

    public Texture getTexture() {
        if (cellType == CellType.Free) {
            return GameAssetManager.getGameAssetManager().getFreeCellTexture();
        } else if (cellType == CellType.Quarry) {
            return GameAssetManager.getGameAssetManager().getQuarryCellTexture();
        } else if (cellType == CellType.View)
            return GameAssetManager.getGameAssetManager().getNpcMapCellTexture();
        else if (cellType == CellType.Water)
            return GameAssetManager.getGameAssetManager().getWaterCellTexture();
        else if (cellType == CellType.Building && map instanceof NPCMap)
            return GameAssetManager.getGameAssetManager().getNpcMapCellTexture();
        else if (cellType == CellType.Building && map instanceof FarmMap)
            return GameAssetManager.getGameAssetManager().getFreeCellTexture();
        else if (cellType == CellType.Plowed)
            if (object != null && object instanceof Plant plant && plant.getWateredToday())
                return GameAssetManager.getGameAssetManager().getHoedAndWateredCellTexture();
            else
                return GameAssetManager.getGameAssetManager().getHoedCellTexture();
        else if (cellType == CellType.MapLink)
            return GameAssetManager.getGameAssetManager().getMapLinkCellTexture();
        return null;
    }

    public Object getForagingType() {
        if (object instanceof Crop crop)
            return crop.getType();
        if (object instanceof Tree tree)
            return tree.getType();
        if (object instanceof MineralType mineral)
            return mineral;
        return null;
    }

}
