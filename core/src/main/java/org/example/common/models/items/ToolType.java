package org.example.common.models.items;

import org.example.common.models.Item;
import org.example.common.models.StackLevel;
import org.example.common.models.tools.*;

public enum ToolType implements Item {
    BasicHoe("Basic Hoe", StackLevel.Basic, "Items/Tools/Hoe/Hoe.png"),
    CopperHoe("Copper Hoe", StackLevel.Copper, "Items/Tools/Hoe/Copper_Hoe.png"),
    IronHoe("Iron Hoe", StackLevel.Iron, "Items/Tools/Hoe/Steel_Hoe.png"),
    GoldHoe("Gold Hoe", StackLevel.Gold, "Items/Tools/Hoe/Gold_Hoe.png"),
    IridiumHoe("Iridium Hoe", StackLevel.Iridium, "Items/Tools/Hoe/Iridium_Hoe.png"),
    BasicPickaxe("Basic Pickaxe", StackLevel.Basic, "Items/Tools/Pickaxe/Pickaxe.png"),
    CopperPickaxe("Copper Pickaxe", StackLevel.Copper, "Items/Tools/Pickaxe/Copper_Pickaxe.png"),
    IronPickaxe("Iron Pickaxe", StackLevel.Iron, "Items/Tools/Pickaxe/Steel_Pickaxe.png"),
    GoldPickaxe("Gold Pickaxe", StackLevel.Gold, "Items/Tools/Pickaxe/Gold_Pickaxe.png"),
    IridiumPickaxe("Iridium Pickaxe", StackLevel.Iridium, "Items/Tools/Pickaxe/Iridium_Pickaxe.png"),
    BasicAxe("Basic Axe", StackLevel.Basic, "Items/Tools/Axe/Axe.png"),
    CopperAxe("Copper Axe", StackLevel.Copper, "Items/Tools/Axe/Copper_Axe.png"),
    IronAxe("Iron Axe", StackLevel.Iron, "Items/Tools/Axe/Steel_Axe.png"),
    GoldAxe("Gold Axe", StackLevel.Gold, "Items/Tools/Axe/Gold_Axe.png"),
    IridiumAxe("Iridium Axe", StackLevel.Iridium, "Items/Tools/Axe/Iridium_Axe.png"),
    BasicWateringCan("Basic Watering Can", StackLevel.Basic,
            "Items/Tools/Watering_Can/Watering_Can.png"),
    CopperWateringCan("Copper Watering Can", StackLevel.Copper,
            "Items/Tools/Watering_Can/Copper_Watering_Can.png"),
    IronWateringCan("Iron Watering Can", StackLevel.Iron,
            "Items/Tools/Watering_Can/Steel_Watering_Can.png"),
    GoldWateringCan("Gold Watering Can", StackLevel.Gold,
            "Items/Tools/Watering_Can/Gold_Watering_Can.png"),
    IridiumWateringCan("Iridium Watering Can", StackLevel.Iridium,
            "Items/Tools/Watering_Can/Iridium_Watering_Can.png"),
    TrainingRod("Training Rod", StackLevel.Training, "Items/Tools/Fishing_Rod/Training_Rod.png"),
    BambooPole("Bamboo Pole", StackLevel.Bamboo, "Items/Tools/Fishing_Rod/Bamboo_Pole.png"),
    FiberglassRod("Fiberglass Rod", StackLevel.Fiberglass, "Items/Tools/Fishing_Rod/Fiberglass_Rod.png"),
    IridiumRod("Iridium Rod", StackLevel.Iridium, "Items/Tools/Fishing_Rod/Iridium_Rod.png"),
    Scythe("Scythe", StackLevel.Basic, "Items/Tools/Scythe/Scythe.png"),
    MilkPail("Milk pail", StackLevel.Basic, "Items/Tools/Milk_Pail.png"),
    Shear("Shear", StackLevel.Basic, "Items/Tools/Shear.png"),
    BasicBackpack("Basic Backpack", StackLevel.Basic, "Items/Tools/Backpack/Backpack.png"),
    LargeBackpack("Large Backpack", StackLevel.Large, "Items/Tools/Backpack/Backpack.png"),
    DeluxeBackpack("Deluxe Backpack", StackLevel.Deluxe, "Items/Tools/Backpack/Deluxe_Backpack.png"),
    BasicTrashCan("Basic Trash Can", StackLevel.Basic, "Items/Tools/Trash_Can/Trash_Can_Copper.png"),
    CopperTrashCan("Copper Trash Can", StackLevel.Copper, "Items/Tools/Trash_Can/Trash_Can_Copper.png"),
    IronTrashCan("Iron Trash Can", StackLevel.Iron, "Items/Tools/Trash_Can/Trash_Can_Steel.png"),
    GoldTrashCan("Gold Trash Can", StackLevel.Gold, "Items/Tools/Trash_Can/Trash_Can_Gold.png"),
    IridiumTrashCan("Iridium Trash Can", StackLevel.Iridium, "Items/Tools/Trash_Can/Trash_Can_Iridium.png"),
    ;

    private final String name;
    private final StackLevel level;
    private final String address;

    ToolType(String name, StackLevel level, String address) {
        this.name = name;
        this.level = level;
        this.address = address;
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
        return switch (fishPoleName) {
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
        return switch (type) {
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

    public String getAddress() {
        return address;
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

    public Tool getTheFuckingTool() {
        if (this.getName().matches(".*Hoe"))
            return new Hoe(this);
        else if (this.getName().matches(".*Pickaxe"))
            return new Pickaxe(this);
        else if (this.getName().matches(".*Axe"))
            return new Axe(this);
        else if (this.getName().matches(".*Watering Can"))
            return new WateringCan(this);
        else if (this.getName().matches(".*Trash Can"))
            return new TrashCan(this);
        else if (this.getName().matches(".*Shear"))
            return new Shear();
        else if (this.getName().equals("Scythe"))
            return new Scythe();
        else if (this.getName().equals("Milk pail"))
            return new MilkPail();
        else if (this.getName().matches(".*[Rod|Pole]"))
            return new FishingPole(this);
        else
            return null;
    }
}
