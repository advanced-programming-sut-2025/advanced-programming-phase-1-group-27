package org.example.models.enums.Plants;

public enum SaplingType implements PlantSourceType {
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

    public TreeType getTree() {
        return tree;
    }
}
