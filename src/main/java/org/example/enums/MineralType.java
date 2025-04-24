package org.example.enums;

public enum MineralType implements Item{
    Quartz,
    CopperOre,
    IronOre,
    GoldOre;
    //...

    private String name, decs;
    private int sellPrice;

    @Override
    public void use() {

    }
}
