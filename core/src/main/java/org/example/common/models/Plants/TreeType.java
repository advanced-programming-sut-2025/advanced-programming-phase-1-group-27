package org.example.common.models.Plants;

import org.example.common.models.Seasons.Season;

import java.util.ArrayList;
import java.util.List;

public enum TreeType implements PlantType {
    ApricotTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Apricot, new Season[]{Season.Spring}, 1,
            "assets/Images/Trees/Apricot"),
    CherryTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Cherry, new Season[]{Season.Spring}, 1,
            "assets/Images/Trees/Cherry"),
    BananaTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Banana, new Season[]{Season.Summer}, 1,
             "assets/Images/Trees/Banana"),
    MangoTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Mango, new Season[]{Season.Summer}, 1,
             "assets/Images/Trees/Mango"),
    OrangeTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Orange, new Season[]{Season.Summer}, 1,
             "assets/Images/Trees/Orange"),
    PeachTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Peach, new Season[]{Season.Summer}, 1,
             "assets/Images/Trees/Peach"),
    AppleTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Apple, new Season[]{Season.Fall}, 1,
             "assets/Images/Trees/Apple"),
    PomegranateTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Pomegranate, new Season[]{Season.Fall}, 1,
             "assets/Images/Trees/Pomegranate"),
    OakTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.OakResin, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7,
             "assets/Images/Trees/Oak"),
    MapleTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.MapleSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 9,
             "assets/Images/Trees/Maple"),
    PineTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.PineTar, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 5,
             "assets/Images/Trees/Pine"),
    MahoganyTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.Sap, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1,
             "assets/Images/Trees/Mahogany"),
    MushroomTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.CommonMushroom, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1,
             "assets/Images/Trees/Mushroom"),
    MysticTree(new int[]{7, 7, 7, 7}, 28,
            FruitType.MysticSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7,
             "assets/Images/Trees/Mystic");

    private static final ArrayList<TreeType> foragings = new ArrayList<>(List.of(OakTree, MapleTree, PineTree, MahoganyTree,
            MushroomTree));
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final ArrayList<Season> seasons;
    private final int harvestCycle;
    private PlantSourceType source;
    private final String address;

    TreeType(int[] stages, int harvestTime, FruitType fruit, Season[] seasons, int harvestCycle
            , String address) {
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.fruit = fruit;
        this.seasons = new ArrayList<>(List.of(seasons));
        this.harvestCycle = harvestCycle;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public String toString() {
        return this.name();
    }

    public boolean canBecomeGiant() {
        return false;
    }
}
