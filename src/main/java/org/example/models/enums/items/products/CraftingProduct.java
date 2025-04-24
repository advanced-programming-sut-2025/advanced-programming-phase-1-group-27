package org.example.models.enums.items.products;

import org.example.models.enums.items.Item;
import org.example.models.Recipe;

public enum CraftingProduct implements Product, Item {
    CherryBomb,
    Bomb,
    MegaBomb,
    Sprinkler,
    QualitySprinkler,
    IridiumSprinkler,
    CharcoalKlin,
    Furnace,
    Scarecrow,
    DeluxeScarecrow,
    BeeHouse,
    CheesePress,
    Keg,
    Loom,
    MayonnaiseMachine,
    OilMaker,
    PreservesJar,
    Dehydrator,
    FishSmoker,
    MysticTreeSeed;
    // TODO: different crafting products
    private final Recipe recipe;
    // TODO: create constructor
}
