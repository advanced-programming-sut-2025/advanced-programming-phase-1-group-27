package Controller;

import enums.ToolType;
import models.App;
import models.Result;
import models.User;
import models.tools.Tool;

public class ToolController {
    public Result upgradeTool(){

    }

    public Result userTool(){

    }

    public Result showAvailableTools(){

    }

    public Result showCurrentTool(){

    }

    public Result equipTool(String toolName){
        ToolType toolType = getTool(toolName);
        if(toolType == null){
            return new Result(false, "Tool not found");
        }

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

    private boolean isToolInBackPack(ToolType toolType){
        if(App.getLoggedInUser().getCurrentBackpack())
    }
}
