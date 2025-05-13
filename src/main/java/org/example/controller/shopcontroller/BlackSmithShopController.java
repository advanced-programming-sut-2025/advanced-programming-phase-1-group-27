package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.controller.ToolController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;
import org.example.view.shopview.BlackSmithShop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlackSmithShopController extends MenuController {
    private final BlackSmithShop view;

    public BlackSmithShopController(BlackSmithShop view) {
        this.view = view;
    }


    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    private final ToolController toolController = new ToolController();

    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter " +
                "any menu from here!");
    }

    @Override
    public Result exitMenu() {
        // TODO: rassa bodo;
        return null;
    }

    public Result upgradeTool(String toolName) {
        ToolType toolType = getTool(toolName);
        if (toolType == null) {
            return new Result(false, "Invalid tool name!");
        }
        StackLevel stackLevel = toolType.getLevel();
        Map<String, Integer> upgradeLimit = App.getCurrentGame().getBlacksmith().getUpgradeLimit();
        int limit = 0;
        String mode = null;
        if (stackLevel == StackLevel.Basic) {
            if (toolType.getName().equals("Basic Trash Can")) {
                limit = upgradeLimit.get("Copper Trash Can");
                mode = "Copper Trash Can";
            } else {
                limit = upgradeLimit.get("Copper Tool");
                mode = "Copper Tool";
            }
        } else if (stackLevel == StackLevel.Copper) {
            if (toolType.getName().equals("Copper Trash Can")) {
                limit = upgradeLimit.get("Iron Trash Can");
                mode = "Iron Trash Can";
            } else {
                limit = upgradeLimit.get("Iron Tool");
                mode = "Iron Tool";
            }
        } else if (stackLevel == StackLevel.Iron) {
            if (toolType.getName().equals("Iron Trash Can")) {
                limit = upgradeLimit.get("Gold Trash Can");
                mode = "Gold Trash Can";
            } else {
                limit = upgradeLimit.get("Gold Tool");
                mode = "Gold Tool";
            }
        } else if (stackLevel == StackLevel.Gold) {
            if (toolType.getName().equals("Gold Trash Can")) {
                limit = upgradeLimit.get("Iridium Trash Can");
                mode = "Iridium Trash Can";
            } else {
                limit = upgradeLimit.get("Iridium Tool");
                mode = "Iridium Tool";
            }
        } else if (stackLevel == StackLevel.Iridium) {
            return new Result(false, "You can not upgrade iridium item!");
        }
        if(limit < 1){
            return new Result(false, "Limit reached!");
        }
        App.getCurrentGame().getBlacksmith().getUpgradeLimit().put(mode , 0);
        toolController.upgradeTool(toolName);
        return new Result(true, "You upgraded you tool successfully!( " + mode + " )");
    }

    public Result showAllProducts(){

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
