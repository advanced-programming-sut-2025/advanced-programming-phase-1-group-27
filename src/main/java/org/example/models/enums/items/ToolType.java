package org.example.models.enums.items;

public enum ToolType implements Item {
    Axe("Axe"),
    BackPack("Backpack"),
    LargeBackPack("Large Backpack"),
    DeluxeBackPack("Deluxe Backpack"),
    FishingPole("Fishing pole"),
    Hoe("Hoe"),
    MilkPail("Milk pail"),
    Pickaxe("Pickaxe"),
    Scythe("Scythe"),
    Shear("Shear"),
    TrashCan("Trash can"),
    TrainingRod("Training rod"),
    FiberglassRod("Fiberglass rod"),
    IridiumRod("Iridium rod"),
    BambooPole("Bamboo pole"),
    WateringCan("Watering can");

    private final String name;

    ToolType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
