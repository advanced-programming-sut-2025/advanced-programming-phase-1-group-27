package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.server.models.*;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.ProcessedProductType;
import org.example.server.models.tools.Backpack;

public class ToolController{
    public Result upgradeTool(String toolName) {
        ToolType toolType = getTool(toolName);
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
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
        ClientApp.getCurrentGame().getCurrentPlayer().setCurrentTool(null);
        return new Result(true, "You upgraded item!");
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
