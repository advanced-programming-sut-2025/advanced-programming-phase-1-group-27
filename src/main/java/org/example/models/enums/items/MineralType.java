package org.example.models.enums.items;

import org.example.models.Item;

import java.util.ArrayList;
import java.util.List;

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
    Stone(20),
    Wood(10),
    Coal(15);

    private int price;
    private static ArrayList<MineralType> foragingMinerals = new ArrayList<>(List.of(MineralType.values()));

    MineralType(int price) {
        this.price = price;
    }

    public static ArrayList<MineralType> getForagingMinerals() {
        return foragingMinerals;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
