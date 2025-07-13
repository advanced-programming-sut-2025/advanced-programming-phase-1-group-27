package org.example.server.controller;

import org.example.models.*;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;
import org.example.models.enums.items.products.ProcessedProductType;
import org.example.models.tools.*;

public class ToolController {

    public Result upgradeTool(String toolName) {
        ToolType toolType = getTool(toolName);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Backpack backpack = currentPlayer.getBackpack();
        if (toolType == null) {
            return new Result(false, "Tool is invalid!");
        }
        Stacks stack = backpack.getStackToolWithName(toolName);
        if (stack == null) {
            return new Result(false, "Tool is unavailable!");
        }
        if (toolType == ToolType.TrainingRod
                || toolType == ToolType.BambooPole
                || toolType == ToolType.FiberglassRod
                || toolType == ToolType.IridiumRod) {
            return new Result(false, "You can not upgrade your rod!");
        }
        if (toolType == ToolType.BasicBackpack
                || toolType == ToolType.LargeBackpack
                || toolType == ToolType.DeluxeBackpack) {
            return new Result(false, "You can not upgrade your backpack!");
        }
        if (toolType == ToolType.MilkPail
                || toolType == ToolType.Shear
                || toolType == ToolType.Scythe) {
            return new Result(false, "You can not upgrade your milk pail or shear or scythe!");
        }
        int price;
        Item item;
        int ratio;
        if (toolType.getLevel() == StackLevel.Iridium) {
            return new Result(false, "You can not upgrade your tool!");
        } else if (toolType.getLevel() == StackLevel.Gold) {
            price = 12500;
            item = ProcessedProductType.IridiumMetalBar;
        } else if (toolType.getLevel() == StackLevel.Iron) {
            price = 5000;
            item = ProcessedProductType.GoldMetalBar;
        } else if (toolType.getLevel() == StackLevel.Copper) {
            price = 2500;
            item = ProcessedProductType.IronMetalBar;
        } else { // toolType.getLevel() == StackLevel.Basic
            price = 1000;
            item = ProcessedProductType.CopperMetalBar;
        }
        if (toolType == ToolType.BasicTrashCan
                || toolType == ToolType.CopperTrashCan
                || toolType == ToolType.IronTrashCan
                || toolType == ToolType.GoldTrashCan) {
            ratio = 1;
        } else { // Other items
            ratio = 2;
        }
        if (!backpack.hasEnoughItem(item, 5)) {
            return new Result(false, "You don't have enough item!");
        }
        if (currentPlayer.getMoney() < price * ratio) {
            return new Result(false, "You don't have enough money!");
        }
        currentPlayer.spendMoney(price * ratio);
        backpack.reduceItems(item, 5);
        backpack.upgradeLevel(stack);
        App.getCurrentGame().getCurrentPlayer().setCurrentTool(null);
        return new Result(true, "You upgraded item!");
    }

    public Result ToolsUse(String directionString) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        int direction = Integer.parseInt(directionString);
        if (direction < 0 || direction > 7 || player.getCurrentCell().getAdjacentCells().get(direction) == null) {
            return new Result(false, "Invalid direction!");
        }
        Tool tool = player.getCurrentTool();
        if (tool == null) {
            return new Result(false, "You have no tools in hand!!");
        } else {
            return tool.use(player.getCurrentCell().getAdjacentCells().get(direction));
        }


    }

    public Result showAvailableTools() {
        StringBuilder result = new StringBuilder();
        result.append("Available Tools : \n");
        Player player = App.getCurrentGame().getCurrentPlayer();
        int i = 0;
        for (Stacks stacks : player.getBackpack().getItems()) {
            Item item = stacks.getItem();
            if (item instanceof ToolType) {
                if (i != 0) {
                    result.append("\n");
                }
                result.append(item.getName());
                i++;
            }
        }
        return new Result(true, result.toString());
    }

    public Result showCurrentTool() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (player.getCurrentTool() == null) {
            return new Result(false, "No tool equipped");
        }
        return new Result(true, player.getCurrentTool().getName());
    }

    public Result equipTool(String toolName) {
        ToolType toolType = getTool(toolName);
        Backpack backpack = App.getCurrentGame().getCurrentPlayer().getBackpack();
        if (toolType == null) {
            return new Result(false, "Tool is invalid!");
        }
        Stacks stack = backpack.getStackToolWithName(toolName);
        if (stack == null) {
            return new Result(false, "Tool not found");
        }
        Item item = stack.getItem();
        Tool tool = null;
        if (item == ToolType.BasicHoe
                || item == ToolType.CopperHoe
                || item == ToolType.IronHoe
                || item == ToolType.GoldHoe
                || item == ToolType.IridiumHoe) {
            tool = new Hoe(toolType);
        } else if (item == ToolType.BasicAxe
                || item == ToolType.CopperAxe
                || item == ToolType.IronAxe
                || item == ToolType.GoldAxe
                || item == ToolType.IridiumAxe) {
            tool = new Axe(toolType);
        } else if (item == ToolType.BasicWateringCan
                || item == ToolType.CopperWateringCan
                || item == ToolType.IronWateringCan
                || item == ToolType.GoldWateringCan
                || item == ToolType.IridiumWateringCan) {
            tool = new WateringCan(toolType);
        } else if (item == ToolType.BasicTrashCan
                || item == ToolType.CopperTrashCan
                || item == ToolType.IronTrashCan
                || item == ToolType.GoldTrashCan
                || item == ToolType.IridiumTrashCan) {
            tool = new TrashCan(toolType);
        } else if (item == ToolType.TrainingRod
                || item == ToolType.BambooPole
                || item == ToolType.FiberglassRod
                || item == ToolType.IridiumRod) {
            return new Result(false, "You can not equip your rod!");
        } else if (item == ToolType.BasicBackpack
                || item == ToolType.LargeBackpack
                || item == ToolType.DeluxeBackpack) {
            return new Result(false, "You can not equip your backpack!");
        } else if (item == ToolType.MilkPail) {
            tool = new MilkPail();
        } else if (item == ToolType.Shear) {
            tool = new Shear();
        } else if (item == ToolType.Scythe) {
            tool = new Scythe();
        } else if (item == ToolType.BasicPickaxe
                || item == ToolType.CopperPickaxe
                || item == ToolType.IronPickaxe
                || item == ToolType.GoldPickaxe
                || item == ToolType.IridiumPickaxe) {
            tool = new Pickaxe(toolType);
        }
        App.getCurrentGame().getCurrentPlayer().setCurrentTool(tool);
        return new Result(true, "Tool equipped!");
    }

    private ToolType getTool(String toolName) {
        for (ToolType toolType : ToolType.values()) {
            if (toolType.getName().equalsIgnoreCase(toolName)) {
                return toolType;
            }
        }
        return null;
    }
}
