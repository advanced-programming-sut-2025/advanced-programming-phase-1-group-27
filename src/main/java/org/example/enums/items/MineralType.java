package org.example.enums.items;

public enum MineralType implements Item {
    Quartz,
    CopperOre,
    IronOre,
    Stone,
    GoldOre;
    //...

    private String name, decs;
    private int sellPrice;

    @Override
    public void use() {

    }

    @Override
    public int getPrice() {
        return 0;
    }
}
