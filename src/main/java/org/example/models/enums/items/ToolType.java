package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.tools.MilkPail;

public enum ToolType {
    BasicHoe("Basic Hoe"),
    BronzeHoe("Bronze Hoe"),
    IronHoe("Iron Hoe"),
    GoldHoe("Gold Hoe"),
    IridiumHoe("Iridium Hoe"),
    BasicPickaxe("Basic Pickaxe"),
    BronzePickaxe("Bronze Pickaxe"),
    IronPickaxe("Iron Pickaxe"),
    GoldPickaxe("Gold Pickaxe"),
    IridiumPickaxe("Iridium Pickaxe"),
    BasicAxe("Basic Axe"),
    BronzeAxe("Bronze Axe"),
    IronAxe("Iron Axe"),
    GoldAxe("Gold Axe"),
    IridiumAxe("Iridium Axe"),
    BasicWateringCan("Basic Watering Can"),
    BronzeWateringCan("Bronze Watering Can"),
    IronWateringCan("Iron Watering Can"),
    GoldWateringCan("Gold Watering Can"),
    IridiumWateringCan("Iridium Watering Can"),
    TrainingRod("Training Rod"),
    BambooFishingPole("Bamboo Fishing Pole"),
    FiberglassRod("Fiberglass Rod"),
    IridiumRod("Iridium Rod"),
    Scythe("Scythe"),
    MilkPail("Milk pail"),
    Shear("Shear"),
    BasicBackpack("Basic Backpack"),
    BigBackpack("Big Backpack"),
    DeluxeBackpack("Deluxe Backpack"),
    BasicTrashCan("Basic Trash Can"),
    BronzeTrashCan("Bronze Trash Can"),
    IronTrashCan("Iron Trash Can"),
    GoldTrashCan("Gold Trash Can"),
    IridiumTrashCan("Iridium Trash Can");

    private final String name;

    ToolType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
