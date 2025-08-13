package org.example.common.models.items;

import org.example.common.models.Item;
import org.example.common.models.StackLevel;

import java.util.ArrayList;
import java.util.List;

public enum MineralType implements Item {
    //GEMS
    Quartz(25 , "Items/Minerals/Quartz.png"),
    EarthCrystal(50 , "Items/Minerals/Earth_Crystal.png"),
    FrozenTear(75 , "Items/Minerals/Frozen_Tear.png"),
    FireQuartz(100 , "Items/Minerals/Fire_Quartz.png"),
    Emerald(250 ,  "Items/Minerals/Emerald.png"),
    Aquamarine(180 , "Items/Minerals/Aquamarine.png"),
    Ruby(250 , "Items/Minerals/Ruby.png"),
    Amethyst(100 , "Items/Minerals/Amethyst.png"),
    Topaz(80 , "Items/Minerals/Topaz.png"),
    Jade(200 , "Items/Minerals/Jade.png"),
    Diamond(750 , "Items/Minerals/Diamond.png"),
    PrismaticShard(2000 , "Items/Minerals/Prismatic_Shard.png"),
    //MINERALS
    Stone(20, StackLevel.Basic , "Items/Minerals/Stone.png"),
    CopperOre(5, StackLevel.Copper , "Items/Minerals/Copper_Ore.png"),
    IronOre(10, StackLevel.Iron , "Items/Minerals/Iron_Ore.png"),
    GoldOre(25, StackLevel.Gold , "Items/Minerals/Gold_Ore.png"),
    IridiumOre(100, StackLevel.Iridium , "Items/Minerals/Iridium_Ore.png"),
    //NONEMINERALS
    Wood(10 , "Items/Minerals/Wood.png"),
    Fiber(2 , "Items/Minerals/Fiber.png"),
    Coal(15 , "Items/Minerals/Coal.png"),;

    private static ArrayList<MineralType> foragingMinerals = new ArrayList<MineralType>(List.of(MineralType.values())) {{
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
    private int price;
    private StackLevel level = null;
    private String address;

    MineralType(int price , String address) {
        this.price = price;
        this.address = address;
    }

    MineralType(int price, StackLevel level , String address) {
        this.price = price;
        this.level = level;
        this.address = address;
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

    public String getAddress() {
        return this.address;
    }

    public static MineralType getItem(String itemName) {
        for (MineralType mineralType : MineralType.values()) {
            if (mineralType.getName().equalsIgnoreCase(itemName)) {
                return mineralType;
            }
        }
        return null;
    }

    public StackLevel getLevel() {
        return level;
    }

    @Override
    public Integer getPrice() {
        return price;
    }
}
