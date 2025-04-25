package org.example.models.enums.items;

import org.example.models.enums.Seasons.Season;

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
    MysticTree;

    private final SeedType source;
    private final int[] stages;
    private final int harvestTime;
    private final FruitType fruit;
    private final Season season;
}
