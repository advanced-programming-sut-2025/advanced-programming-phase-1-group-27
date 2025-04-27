package org.example.models.enums.items;

public enum MineralType implements Item {
    Quartz,
    EarthCrystal,
    FrozenTear,
    FireQuartz,
    Emerald,
    Aquamarine,
    Ruby,
    Amethyst,
    Topaz,
    Jade,
    Diamond,
    PrismaticShard,
    CopperOre,
    IronOre,
    Stone,
    GoldOre,
    Wood; // jash shayad avaj she

    private int price;

    @Override
    public int getPrice() {
        return price;
    }
}
