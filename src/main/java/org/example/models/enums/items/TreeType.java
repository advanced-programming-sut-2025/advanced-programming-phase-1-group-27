package org.example.models.enums.items;

import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;

public enum TreeType {
    ApricotTree(SaplingType.AppleSapling),
    CherryTree(SaplingType.CherrySapling),
    BananaTree(SaplingType.BananaSapling),
    MangoTree(SaplingType.MangoSapling),
    OrangeTree(SaplingType.OrangeSapling),
    PeachTree(SaplingType.PeachSapling),
    AppleTree(SaplingType.AppleSapling),
    PomegranateTree(SaplingType.PomegranateSapling),
    OakTree(SaplingType.Acorn,),
    MapleTree(),
    PineTree(),
    MahoganyTree(),
    MushroomTree(),
    MysticTree(),
    AcornTree();

    private final SaplingType source;
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final Season season;
    private final int harvestCycle;
    public static ArrayList<TreeType> foragings = new ArrayList<>();

    TreeType(SeedType source, int[] stages, int harvestTime, FruitType fruit, Season season, int harvestCycle) {
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.fruit = fruit;
        this.season = season;
        this.harvestCycle = harvestCycle;
    }
}
