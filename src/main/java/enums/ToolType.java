package enums;

public enum ToolType implements Item{
    Axe("Axe"),
    BackPack("Backpack"),
    FishingPole("Fishing pole"),
    Hoe("Hoe"),
    MilkPail("Milk pail"),
    Pickaxe("Pickaxe"),
    Scythe("Scythe"),
    Shear("Shear"),
    TrashCan("Trash can"),
    WateringCan("Watering can");

    private final String name;

    ToolType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
