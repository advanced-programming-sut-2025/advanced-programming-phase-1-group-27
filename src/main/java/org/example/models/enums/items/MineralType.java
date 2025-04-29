package org.example.models.enums.items;

public enum MineralType implements Item {
    Quartz(25),
    EarthCrystal(50),
    FrozenTear(75),
    FireQuartz(100),
    Emerald(250),
    Aquamarine(180),
    Ruby(250),
    Amethyst(100),
    Topaz(80),
    Jade(200),
    Diamond(750),
    PrismaticShard(2000),
    CopperOre(5),
    IronOre(10),
    GoldOre(25),
    IridiumOre(100),
    Stone(0),
    Wood(0); // jash shayad avaz she

    private int price;

    MineralType(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
