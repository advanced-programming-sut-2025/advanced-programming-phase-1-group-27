package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.StackLevel;
import org.example.models.tools.MilkPail;

public enum ToolType implements Item {
    BasicHoe("Basic Hoe" , StackLevel.Basic),
    CopperHoe("Copper Hoe" , StackLevel.Copper),
    IronHoe("Iron Hoe" , StackLevel.Iron),
    GoldHoe("Gold Hoe" , StackLevel.Gold),
    IridiumHoe("Iridium Hoe" , StackLevel.Iridium),
    BasicPickaxe("Basic Pickaxe" , StackLevel.Basic),
    CopperPickaxe("Copper Pickaxe" , StackLevel.Copper),
    IronPickaxe("Iron Pickaxe" , StackLevel.Iron),
    GoldPickaxe("Gold Pickaxe" , StackLevel.Gold),
    IridiumPickaxe("Iridium Pickaxe" , StackLevel.Iridium),
    BasicAxe("Basic Axe" , StackLevel.Basic),
    CopperAxe("Copper Axe" , StackLevel.Copper),
    IronAxe("Iron Axe" , StackLevel.Iron),
    GoldAxe("Gold Axe" , StackLevel.Gold),
    IridiumAxe("Iridium Axe" , StackLevel.Iridium),
    BasicWateringCan("Basic Watering Can" , StackLevel.Basic),
    CopperWateringCan("Copper Watering Can" , StackLevel.Copper),
    IronWateringCan("Iron Watering Can" , StackLevel.Iron),
    GoldWateringCan("Gold Watering Can" , StackLevel.Gold),
    IridiumWateringCan("Iridium Watering Can" , StackLevel.Iridium),
    TrainingRod("Training Rod" , StackLevel.Training),
    BambooPole("Bamboo Pole" , StackLevel.Bamboo),
    FiberglassRod("Fiberglass Rod" , StackLevel.Fiberglass),
    IridiumRod("Iridium Rod" , StackLevel.Iridium),
    Scythe("Scythe" , StackLevel.Basic),
    MilkPail("Milk pail" , StackLevel.Basic),
    Shear("Shear" , StackLevel.Basic),
    BasicBackpack("Basic Backpack" , StackLevel.Basic),
    LargeBackpack("Large Backpack" , StackLevel.Large),
    DeluxeBackpack("Deluxe Backpack" , StackLevel.Deluxe),
    BasicTrashCan("Basic Trash Can" , StackLevel.Basic),
    CopperTrashCan("Copper Trash Can" , StackLevel.Copper),
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

    public double getTrashCanModifier() {
        if (!ToolType.isTrashCan(this))
            return 0;
        if (this == ToolType.BasicTrashCan)
            return 0;
        if (this == ToolType.CopperTrashCan)
            return 0.15;
        if (this == ToolType.IronTrashCan)
            return 0.3;
        if (this == ToolType.GoldTrashCan)
            return 0.45;
        return 0.6;
    }

    public static ToolType getItem(String itemName) {
        for (ToolType toolType : ToolType.values()) {
            if (toolType.getName().equalsIgnoreCase(itemName)) {
                return toolType;
            }
        }
        return null;
    }

    public static ToolType getFishPole(String fishPoleName) {
        return switch(fishPoleName) {
            case "Training Rod" -> TrainingRod;
            case "Bamboo Pole" -> BambooPole;
            case "Fiberglass Rod" -> FiberglassRod;
            case "Iridium Rod" -> IridiumRod;
            default -> null;
        };
    }

    public static int getFishPoleEnergy(ToolType type) {
        return switch (type) {
            case TrainingRod -> 8;
            case BambooPole -> 8;
            case FiberglassRod -> 6;
            case IridiumRod -> 4;
            default -> -1;
        };
    }

    public static double getFishPoleModifier(ToolType type) {
        return switch(type) {
            case TrainingRod -> 0.1;
            case BambooPole -> 0.5;
            case FiberglassRod -> 0.9;
            case IridiumRod -> 1.2;
            default -> 0;
        };
    }

    public static boolean isTrashCan(ToolType type) {
        return type == BasicTrashCan ||
                type == CopperTrashCan ||
                type == IronTrashCan ||
                type == GoldTrashCan ||
                type == IridiumTrashCan;
    }

    public static int getBackpackCapacity(ToolType type) {
        return switch (type) {
            case BasicBackpack -> 12;
            case LargeBackpack -> 24;
            case DeluxeBackpack -> -1;
            default -> 0;
        };
    }
}
