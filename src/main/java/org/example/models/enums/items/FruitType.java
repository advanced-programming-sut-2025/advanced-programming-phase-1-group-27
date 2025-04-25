package org.example.models.enums.items;

public enum FruitType implements Item {
    Apricot,
    Cherry,
    Banana,
    Mango,
    Orange,
    Peach,
    Apple,
    Pomegranate,
    OakResin,
    MapleSyrup,
    PineTar,
    Sap,
    CommonMushroom,
    MysticSyrup;

    private final int price;
    private final boolean isFruitEdible;
    private final int energy;
    private final int harvestCycle; // ?????
}
