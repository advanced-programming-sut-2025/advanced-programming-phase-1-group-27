package org.example.controller;

import org.example.models.*;
import org.example.models.enums.items.ToolType;

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
        for(Stacks stacks: player.getBackpack().getItems()){
            Item item = stacks.getItem();
            if(item instanceof ToolType){
                if(i != 0){
                    result.append("\n");
                }
                result.append(item.getName());
                i++;
            }
        }
        return new Result(true , result.toString());
    }

    public Result showCurrentTool(){
        Player player = App.getCurrentGame().getCurrentPlayer();
        if(player.getCurrentTool() == null){
            return new Result(false , "No tool equipped");
        }
        return new Result(true , player.getCurrentTool().getName());
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
        for(ToolType toolType : ToolType.values()){
            if(toolType.getName().equalsIgnoreCase(toolName)){
                return toolType;
            }
        }
        return null;
    }

    private boolean isToolInBackPack(String toolName){
        Player player = App.getCurrentGame().getCurrentPlayer();
        return player.getBackpack().isToolAvailable(toolName);
    }
}
