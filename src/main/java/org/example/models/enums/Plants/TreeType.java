package org.example.models.enums.Plants;

import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;

public enum TreeType implements PlantType {
    ApricotTree(SaplingType.AppleSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Apricot, new Season[]{Season.Spring}, 1),
    CherryTree(SaplingType.CherrySapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Cherry, new Season[]{Season.Spring}, 1),
    BananaTree(SaplingType.BananaSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Banana, new Season[]{Season.Summer}, 1),
    MangoTree(SaplingType.MangoSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Mango, new Season[]{Season.Summer}, 1),
    OrangeTree(SaplingType.OrangeSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Orange, new Season[]{Season.Summer}, 1),
    PeachTree(SaplingType.PeachSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Peach, new Season[]{Season.Summer}, 1),
    AppleTree(SaplingType.AppleSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Apple, new Season[]{Season.Fall}, 1),
    PomegranateTree(SaplingType.PomegranateSapling, new int[]{7, 7, 7, 7}, 28,
            FruitType.Pomegranate, new Season[]{Season.Fall}, 1),
    OakTree(SaplingType.Acorn, new int[]{7, 7, 7, 7}, 28,
            FruitType.OakResin, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7),
    MapleTree(SeedType.MapleSeed, new int[]{7, 7, 7, 7}, 28,
            FruitType.MapleSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 9),
    PineTree(SaplingType.PineCone, new int[]{7, 7, 7, 7}, 28,
            FruitType.PineTar, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 5),
    MahoganyTree(SeedType.MahoganySeed, new int[]{7, 7, 7, 7}, 28,
            FruitType.Sap, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1),
    MushroomTree(SeedType.MushroomTreeSeed, new int[]{7, 7, 7, 7}, 28,
            FruitType.CommonMushroom, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 1),
    MysticTree(SeedType.MysticTreeSeed, new int[]{7, 7, 7, 7}, 28,
            FruitType.MysticSyrup, new Season[]{Season.Spring, Season.Summer, Season.Fall, Season.Winter}, 7);

    private final PlantSourceType source;
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final Season[] season;
    private final int harvestCycle;
    private static ArrayList<TreeType> foragings = new ArrayList<>();

    TreeType(PlantSourceType source, int[] stages, int harvestTime, FruitType fruit, Season[] season, int harvestCycle) {
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.fruit = fruit;
        this.season = season;
        this.harvestCycle = harvestCycle;
    }

    public PlantSourceType getSource() {
        return source;
    }

    public int[] getStages() {
        return stages;
    }

    public int getHarvestTime() {
        return harvestTime;
    }

    public FruitType getFruit() {
        return fruit;
    }

    public Season[] getSeason() {
        return season;
    }

    public int getHarvestCycle() {
        return harvestCycle;
    }
}
