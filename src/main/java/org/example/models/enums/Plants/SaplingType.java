package org.example.models.enums.Plants;

import org.example.models.Item;

public enum SaplingType implements Item, PlantSourceType {
    ApricotSapling(TreeType.ApricotTree),
    CherrySapling(TreeType.CherryTree),
    BananaSapling(TreeType.BananaTree),
    MangoSapling(TreeType.MangoTree),
    OrangeSapling(TreeType.OrangeTree),
    PeachSapling(TreeType.PeachTree),
    AppleSapling(TreeType.AppleTree),
    PomegranateSapling(TreeType.PomegranateTree),
    Acorn(TreeType.OakTree),
    PineCone(TreeType.PineTree);

    private final TreeType tree;

    SaplingType(TreeType plantType) {
        this.tree = plantType;
    }

    public TreeType getPlant() {
        return tree;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    public static SaplingType getItem(String itemName) {
        for (SaplingType saplingtype : values()) {
            if (saplingtype.getName().equalsIgnoreCase(itemName.replace(" ", ""))) {
                return saplingtype;
            }
        }
        return null;
    }
}
