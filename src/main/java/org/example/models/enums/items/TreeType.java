package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;

public enum TreeType implements Item {
    ApricotTree,
    CherryTree,
    BananaTree,
    MangoTree,
    OrangeTree,
    PeachTree,
    AppleTree,
    PomegranateTree,
    OakTree,
    MapleTree,
    PineTree,
    MahoganyTree,
    MushroomTree,
    MysticTree,
    AcornTree;

    public static ArrayList<TreeType> foragings = new ArrayList<>();
    private final SeedType source;
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final Season season;
    private final int harvestCycle;

    TreeType(SeedType source, int[] stages, int harvestTime, FruitType fruit, Season season, int harvestCycle) {
        this.source = source;
        this.stages = stages;
        this.harvestTime = harvestTime;
        this.fruit = fruit;
        this.season = season;
        this.harvestCycle = harvestCycle;
    }
}
