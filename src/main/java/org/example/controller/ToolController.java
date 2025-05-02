package org.example.controller;

import org.example.models.*;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Tool;

public class ToolController {
    public Result upgradeTool(){
        // TODO: function incomplete
        return null;
    }

    public Result userTool(){
        // TODO: function incomplete
        return null;
    }

    public Result showAvailableTools(){
        StringBuilder result = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        int i = 0;
        for(Item item : player.getBackpack().getItems()){
            if(item instanceof Tool){
                if(i != 0){
                    result.append("\n");
                }
                result.append(item.toString());
                i++;
            }
        }
        return new Result(true , result.toString());
    }

    public Result showCurrentTool(){
        Player player = App.getCurrentGame().getCurrentPlayer();
        return new Result(true , player.getCurrentTool().toString());
    }

    public Result equipTool(String toolName){
        ToolType toolType = getTool(toolName);
        if(toolType == null){
            return new Result(false, "Tool not found");
        }
        // TODO: function incomplete
        return null;
    }

    private ToolType getTool(String toolName){
        if(toolName.equals("Hoe")){
            return ToolType.Hoe;
        }else if(toolName.equals("Pickaxe")){
            return ToolType.Pickaxe;
        }else if(toolName.equals("Axe")){
            return ToolType.Axe;
        }else if(toolName.equals("Watering can")){
            return ToolType.WateringCan;
        }else if(toolName.equals("Fishing pole")){
            return ToolType.FishingPole;
        }else if(toolName.equals("Scythe")){
            return ToolType.Scythe;
        }else if(toolName.equals("Milk pain")){
            return ToolType.MilkPail;
        }else if(toolName.equals("Shear")){
            return ToolType.Shear;
        }else if(toolName.equals("Backpack")){
            return ToolType.BackPack;
        }else if(toolName.equals("Trash can")){
            return ToolType.TrashCan;
        }else {
            return null;
        }
    }

    private boolean isToolInBackPack(String toolName){
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.getBackpack().getTool(toolName) == null){
            return false;
        }
        return true;
    }
}
