package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.StackLevel;
import org.example.models.tools.MilkPail;

public enum ToolType implements Item {
    BasicHoe("Basic Hoe" , StackLevel.Basic),
    BronzeHoe("Bronze Hoe" , StackLevel.Bronze),
    IronHoe("Iron Hoe" , StackLevel.Iron),
    GoldHoe("Gold Hoe" , StackLevel.Gold),
    IridiumHoe("Iridium Hoe" , StackLevel.Iridium),
    BasicPickaxe("Basic Pickaxe" , StackLevel.Basic),
    BronzePickaxe("Bronze Pickaxe" , StackLevel.Bronze),
    IronPickaxe("Iron Pickaxe" , StackLevel.Iron),
    GoldPickaxe("Gold Pickaxe" , StackLevel.Gold),
    IridiumPickaxe("Iridium Pickaxe" , StackLevel.Iridium),
    BasicAxe("Basic Axe" , StackLevel.Basic),
    BronzeAxe("Bronze Axe" , StackLevel.Bronze),
    IronAxe("Iron Axe" , StackLevel.Iron),
    GoldAxe("Gold Axe" , StackLevel.Gold),
    IridiumAxe("Iridium Axe" , StackLevel.Iridium),
    BasicWateringCan("Basic Watering Can" , StackLevel.Basic),
    BronzeWateringCan("Bronze Watering Can" , StackLevel.Bronze),
    IronWateringCan("Iron Watering Can" , StackLevel.Iron),
    GoldWateringCan("Gold Watering Can" , StackLevel.Gold),
    IridiumWateringCan("Iridium Watering Can" , StackLevel.Iridium),
    TrainingRod("Training Rod" , StackLevel.Training),
    BambooFishingPole("Bamboo Fishing Pole" , StackLevel.Bamboo),
    FiberglassRod("Fiberglass Rod" , StackLevel.Fiberglass),
    IridiumRod("Iridium Rod" , StackLevel.Iridium),
    Scythe("Scythe" , StackLevel.Basic),
    MilkPail("Milk pail" , StackLevel.Basic),
    Shear("Shear" , StackLevel.Basic),
    BasicBackpack("Basic Backpack" , StackLevel.Basic),
    LargeBackpack("Large Backpack" , StackLevel.Large),
    DeluxeBackpack("Deluxe Backpack" , StackLevel.Deluxe),
    BasicTrashCan("Basic Trash Can" , StackLevel.Basic),
    BronzeTrashCan("Bronze Trash Can" , StackLevel.Bronze),
    IronTrashCan("Iron Trash Can" , StackLevel.Iron),
    GoldTrashCan("Gold Trash Can" , StackLevel.Gold),
    IridiumTrashCan("Iridium Trash Can" , StackLevel.Iridium),;

    private final String name;
    private final StackLevel level;

    ToolType(String name, StackLevel level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    public StackLevel getLevel() {
        return level;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
