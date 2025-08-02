package org.example.client.controller.shopControllers;

import org.example.client.Main;
import org.example.client.controller.ToolController;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.shopview.UpgradeMenuView;
import org.example.common.models.GraphicalResult;
import org.example.server.models.Result;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;

import java.util.Map;

public class UpgradeMenuController extends MenuController {
    private final ToolController toolController;
    private final AppMenu shopMenuView;
    private final UpgradeMenuView view;

    public UpgradeMenuController(UpgradeMenuView view , AppMenu shopMenuView) {
        this.toolController = new ToolController();
        this.shopMenuView = shopMenuView;
        this.view = view;
    }

    public GraphicalResult upgradeTool(String toolName) {
        ToolType toolType = ToolType.getItem(toolName);
        StackLevel stackLevel = toolType.getLevel();
        Map<String, Integer> upgradeLimit = ClientApp.getCurrentGame().getBlacksmith().getUpgradeLimit();
        Integer limit = 0;
        String mode = null;
        if (toolType == ToolType.TrainingRod
                || toolType == ToolType.BambooPole
                || toolType == ToolType.FiberglassRod
                || toolType == ToolType.IridiumRod) {
            return new GraphicalResult("You can not upgrade your rod!");
        }
        if (toolType == ToolType.BasicBackpack
                || toolType == ToolType.LargeBackpack
                || toolType == ToolType.DeluxeBackpack) {
            return new GraphicalResult("You can not upgrade your backpack!");
        }
        if (toolType == ToolType.MilkPail
                || toolType == ToolType.Shear
                || toolType == ToolType.Scythe) {
            return new GraphicalResult("You can not upgrade your milk pail or shear or scythe!");
        }
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
            return new GraphicalResult("You can not upgrade iridium item!");
        }
        if (limit < 1) {
            return new GraphicalResult("Limit reached!");
        }
        GraphicalResult result = toolController.upgradeTool(toolName);
        if (result.hasError()) {
            return result;
        }
        ClientApp.getCurrentGame().getBlacksmith().getUpgradeLimit().put(mode, 0);
        return result;
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(shopMenuView);
        return null;
    }
}
