package org.example.server.models.enums.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.common.models.GameAssetManager;
import org.example.server.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.List;

public enum TreeType implements PlantType {
    ApricotTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Apricot, new Season[]{Season.Spring}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    CherryTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Cherry, new Season[]{Season.Spring}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    BananaTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Banana, new Season[]{Season.Summer}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    MangoTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Mango, new Season[]{Season.Summer}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    OrangeTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Orange, new Season[]{Season.Summer}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    PeachTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Peach, new Season[]{Season.Summer}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    AppleTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Apple, new Season[]{Season.Fall}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    PomegranateTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Pomegranate, new Season[]{Season.Fall}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    OakTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.OakResin, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    MapleTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.MapleSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 9,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    PineTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.PineTar, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 5,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    MahoganyTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Sap, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    MushroomTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.CommonMushroom, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"}),
    MysticTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.MysticSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7,
            new String[]{"7_1", "7_2", "7_3", "7_4", "7_5", "7_6", "7_7"});

    private static final ArrayList<TreeType> foragings = new ArrayList<>(List.of(OakTree, MapleTree, PineTree, MahoganyTree,
            MushroomTree));
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final ArrayList<Season> seasons;
    private final int harvestCycle;
    private PlantSourceType source;
    private final String[] addresses;

    TreeType(int[] stages, int harvestTime, FruitType fruit, Season[] seasons, int harvestCycle
            , String[] addresses) {
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.fruit = fruit;
        this.seasons = new ArrayList<>(List.of(seasons));
        this.harvestCycle = harvestCycle;
        this.addresses = addresses;
    }

    TreeType(int[] stages, int harvestTime, FruitType fruit, Season[] seasons, int harvestCycle) {
        this.source = source;
        this.stages = stages;
        this.fruit = fruit;
        this.seasons = new ArrayList<>(List.of(seasons));
        this.harvestCycle = harvestCycle;
        this.addresses = null;
        this.harvestTime = harvestTime;
    }

    public static TreeType getItem(String itemName) {
        for (TreeType item : values()) {
            if (item.toString().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return item;
            }
        }
        return null;
    }

    public static ArrayList<TreeType> getForagingTrees() {
        return foragings;
    }

    public PlantSourceType getSource() {
        if (source == null) {
            for (SaplingType saplingType : SaplingType.values()) {
                if (saplingType.getPlant() == this)
                    return source = saplingType;
            }
            for (SeedType seedType : SeedType.values()) {
                if (seedType.getPlant() == this)
                    return source = seedType;
            }
        }
        return source;
    }

    public int[] getStages() {
        return stages;
    }

    public int getTotalHarvestTime() {
        return harvestTime;
    }

    public FruitType getFruit() {
        return fruit;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getHarvestCycle() {
        return harvestCycle;
    }

    public boolean getOneTime() {
        return false;
    }

    public String[] getAddresses() {
        return addresses;
    }

    public ArrayList<TextureRegion> getTextures() {
        return GameAssetManager.getGameAssetManager().getTreeTextureMap().get(this);
    }

    public String toString() {
        return this.name();
    }
}
