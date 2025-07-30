package org.example.server.controller.shopcontroller;

import org.example.client.controller.menus.MenuController;
import org.example.server.controller.ToolController;
import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.Stock;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;
import org.example.client.view.shopview.BlackSmithShop;

import java.util.Map;

public class BlackSmithShopController extends MenuController {
    private final BlackSmithShop view;
    private final ToolController toolController = new ToolController();


    public BlackSmithShopController(BlackSmithShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from here!");
    }

    @Override
    public Result exitMenu() {
        App.setCurrentMenu(Menu.GameMenu);
        App.getCurrentGame().getCurrentPlayer().setCurrentMenu(Menu.GameMenu);
        return new Result(true, "Redirecting to game menu ...");
    }

    public Result upgradeTool(String toolName) {
        ToolType toolType = ToolType.getItem(toolName);
        if (toolType == null) {
            return new Result(false, "Invalid tool name!");
        }
        StackLevel stackLevel = toolType.getLevel();
        Map<String, Integer> upgradeLimit = App.getCurrentGame().getBlacksmith().getUpgradeLimit();
        Integer limit = 0;
        String mode = null;
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
        if (limit < 1) {
            return new Result(false, "Limit reached!");
        }
        Result result = toolController.upgradeTool(toolName);
        if (!result.success()) {
            return new Result(false, result.toString());
        }
        App.getCurrentGame().getBlacksmith().getUpgradeLimit().put(mode, 0);
        return new Result(true, "You upgraded you tool successfully!( " + mode + " )");
    }

    public Result showAllProducts() {
        StringBuilder result = new StringBuilder();
        result.append("All Products : \n");
        int i = 1;
        for (Stock stock : App.getCurrentGame().getBlacksmith().getStock()) {
            result.append(i).append(" . ").append(stock.getItem().getName()).append(" - ");
            if (stock.getQuantity() == -1) {
                result.append("Unlimited");
            } else if (stock.getQuantity() == 0) {
                result.append("Sold Out");
            } else {
                result.append(stock.getQuantity());
            }
            result.append(" - ");
            result.append(stock.getPrice()).append(" $ \n");
            i++;
        }

        return new Result(true, result.toString());
    }

    public Result showAllAvailableProducts() {
        StringBuilder result = new StringBuilder();
        result.append("All Available Products : \n");
        int i = 1;
        for (Stock stock : App.getCurrentGame().getBlacksmith().getStock()) {
            if (stock.getQuantity() == -1) {
                result.append(i).append(" . ").append(stock.getItem().getName()).append(" - ");
                result.append("Unlimited").append(" - ");
                result.append(stock.getPrice()).append(" $ \n");
                i++;
            } else if (stock.getQuantity() == 0) {
                continue;
            } else {
                result.append(i).append(" . ").append(stock.getItem().getName()).append(" - ");
                result.append(stock.getQuantity()).append(" - ");
                result.append(stock.getPrice()).append(" $ \n");
                i++;
            }
        }
        return new Result(true, result.toString());
    }

    public Result purchase(String productName, String quantityString) {
        int quantity = quantityString == null ? 1 : Integer.parseInt(quantityString);
        Stock stock = App.getCurrentGame().getBlacksmith().getStock(productName);
        if (stock == null) {
            return new Result(false, "Product not found!");
        }
        if (stock.getQuantity() == 0) {
            return new Result(false, "Product is sold out!");
        }
        if (stock.getQuantity() != -1 && stock.getQuantity() < quantity) {
            return new Result(false, "Not enough product in stock!");
        }
        if (App.getCurrentGame().getCurrentPlayer().getMoney() < stock.getSalePrice() * quantity) {
            return new Result(false, "Not enough money!");
        }
        if (!App.getCurrentGame().getCurrentPlayer().getBackpack().canAdd(
                stock.getItem(),
                stock.getStackLevel(),
                quantity)) {
            return new Result(true, "Not enough space in backPack!");
        }
        App.getCurrentGame().getCurrentPlayer().spendMoney(stock.getSalePrice() * quantity);
        App.getCurrentGame().getBlacksmith().reduce(stock.getItem(), quantity);
        App.getCurrentGame().getCurrentPlayer().getBackpack().addItems(
                stock.getItem(),
                stock.getStackLevel(),
                quantity);
        return new Result(true, "You purchased successfully!");
    }

//    public Result purchase(String productName, String quantityString) {
//        int quantity = Integer.parseInt(quantityString);
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
//        Stock stock = App.getCurrentGame().getBlacksmith().getStock(productName);
//        if (stock == null) {
//            return new Result(false, "Product not found!");
//        }
//        if (stock.getQuantity() == 0) {
//            return new Result(false, "Product is sold out!");
//        }
//        if (stock.getQuantity() != -1
//                && stock.getQuantity() < quantity) {
//            return new Result(false, "Not enough product in stock!");
//        }
//        if (currentPlayer.getMoney() < stock.getSalePrice() * quantity) {
//            return new Result(false, "Not enough money!");
//        }
//        if (!currentPlayer.getBackpack().canAdd(
//                stock.getItem(),
//                stock.getStackLevel(),
//                quantity)) {
//            return new Result(true, "Not enough space in backPack!");
//        }
//        currentPlayer.spendMoney(stock.getSalePrice() * quantity);
//        App.getCurrentGame().getBlacksmith().reduce(stock.getItem(), quantity);
//        currentPlayer.getBackpack().addItems(
//                stock.getItem(),
//                stock.getStackLevel(),
//                quantity);
//        return new Result(true, "You purchased successfully!");
//    }
}
