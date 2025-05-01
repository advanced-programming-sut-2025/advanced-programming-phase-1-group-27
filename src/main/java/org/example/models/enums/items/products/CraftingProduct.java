package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.enums.items.Recipe;

public enum CraftingProduct implements Item {
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
    private final int price;
    // TODO: create constructor
}
