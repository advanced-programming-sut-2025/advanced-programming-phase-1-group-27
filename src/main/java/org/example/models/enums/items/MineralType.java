package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.StackLevel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum MineralType implements Item {
    //GEMS
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
    //MINERALS
    Stone(20, StackLevel.Basic),
    CopperOre(5, StackLevel.Bronze),
    IronOre(10, StackLevel.Iron),
    GoldOre(25, StackLevel.Gold),
    IridiumOre(100, StackLevel.Iridium),
    //NONEMINERALS
    Wood(10),
    Fiber(2),
    Coal(15);

    private int price;
    private StackLevel level = null;
    private static ArrayList<MineralType> foragingMinerals = new ArrayList<MineralType>(List.of(MineralType.values())){{
        remove(Wood);
        remove(Fiber);
    }};
    private static ArrayList<MineralType> jewels = new ArrayList<MineralType>(List.of(
            Quartz, EarthCrystal, FrozenTear, FireQuartz, Emerald, Aquamarine,
            Ruby, Amethyst, Topaz, Jade, Diamond, PrismaticShard));
    private static ArrayList<MineralType> minerals = new ArrayList<MineralType>(List.of(
            CopperOre, GoldOre, IronOre, IridiumOre
    ));
    private static ArrayList<MineralType> noneMinerals = new ArrayList<MineralType>(List.of(
            Wood, Fiber, Coal
    ));

    MineralType(int price) {
        this.price = price;
    }

    MineralType(int price, StackLevel level) {
        this.price = price;
        this.level = level;
    }

    public StackLevel getLevel() {
        return level;
    }

    public static ArrayList<MineralType> getForagingMinerals() {
        return foragingMinerals;
    }

    public static ArrayList<MineralType> getJewels() {
        return jewels;
    }

    public static ArrayList<MineralType> getMinerals() {
        return minerals;
    }

    public static ArrayList<MineralType> getNoneMinerals() {
        return noneMinerals;
    }

    @Override
    public Integer getPrice() {
        return price;
    }
}
