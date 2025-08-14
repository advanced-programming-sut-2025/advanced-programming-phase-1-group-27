package org.example.common.models.Plants;

import org.example.common.models.Item;

public enum SaplingType implements Item, PlantSourceType {
    ApricotSapling(TreeType.ApricotTree, "Items/SaplingProduct/Apricot_Sapling.png"),
    CherrySapling(TreeType.CherryTree, "Items/SaplingProduct/Cherry_Sapling.png"),
    BananaSapling(TreeType.BananaTree, "Items/SaplingProduct/Banana_Sapling.png"),
    MangoSapling(TreeType.MangoTree, "Items/SaplingProduct/Mango_Sapling.png"),
    OrangeSapling(TreeType.OrangeTree, "Items/SaplingProduct/Orange_Sapling.png"),
    PeachSapling(TreeType.PeachTree, "Items/SaplingProduct/Peach_Sapling.png"),
    AppleSapling(TreeType.AppleTree, "Items/SaplingProduct/Apple_Sapling.png"),
    PomegranateSapling(TreeType.PomegranateTree, "Items/SaplingProduct/Pomegranate_Sapling.png"),
    Acorn(TreeType.OakTree, "Items/SaplingProduct/Acorn.png"),
    PineCone(TreeType.PineTree, "Items/SaplingProduct/Pine_Cone.png"),
    ;

    private final TreeType tree;
    private final String address;

    SaplingType(TreeType plantType, String address) {
        this.tree = plantType;
        this.address = address;
    }


    public static SaplingType getItem(String itemName) {
        for (SaplingType saplingtype : values()) {
            if (saplingtype.getName().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return saplingtype;
            }
        }
        return null;
    }

    public TreeType getPlant() {
        return tree;
    }


    public String getAddress() {
        return address;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    public String toString() {
        return this.name();
    }
}
