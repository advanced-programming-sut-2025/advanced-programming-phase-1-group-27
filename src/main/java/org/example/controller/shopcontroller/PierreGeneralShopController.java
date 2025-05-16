package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.models.enums.items.Recipe;
import org.example.models.enums.items.ToolType;
import org.example.view.shopview.PierreGeneralShop;

public class PierreGeneralShopController extends MenuController {
    private final PierreGeneralShop view;

    public PierreGeneralShopController(PierreGeneralShop view) {
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

    public Result showAllProducts() {
        StringBuilder result = new StringBuilder();
        result.append("All Products : \n");
        int i = 1;
        for (Stock stock : App.getCurrentGame().getPierreGeneralStore().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getPierreGeneralStore().getStock()) {
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

//    public Result purchase(String productName, String quantityString) {
//        int quantity = quantityString == null? 1 : Integer.parseInt(quantityString);
//        Stock stock = App.getCurrentGame().getPierreGeneralStore().getStock(productName);
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
//        if (stock == null) {
//            return new Result(false, "Product not found!");
//        }
//        if (stock.getQuantity() == 0) {
//            return new Result(false, "Product is sold out!");
//        }
//        if (stock.getQuantity() != -1 && stock.getQuantity() < quantity) {
//            return new Result(false, "Not enough product in stock!");
//        }
//        if (App.getCurrentGame().getCurrentPlayer().getMoney() < stock.getSalePrice() * quantity) {
//            return new Result(false, "Not enough money!");
//        }
//        if (stock.getItem() instanceof Recipe) {
//             currentPlayer.getAvailableCraftingRecipes().add((Recipe) stock.getItem());
//        }
//        else if (stock.getItem() instanceof ToolType backpack) {
//            if (currentPlayer.getBackpackType() == ToolType.BasicBackpack)
//                return new Result(false, "You should first unlock large backpack!");
//            currentPlayer.setBackpack(backpack);
//        }
//        else {
//            if (!App.getCurrentGame().getCurrentPlayer().getBackpack().canAdd(
//                    stock.getItem(),
//                    stock.getStackLevel(),
//                    quantity)) {
//                return new Result(true, "Not enough space in backPack!");
//            }
//            App.getCurrentGame().getCurrentPlayer().getBackpack().addItems(
//                    stock.getItem(),
//                    stock.getStackLevel(),
//                    quantity);
//        }
//        App.getCurrentGame().getCurrentPlayer().spendMoney(stock.getSalePrice() * quantity);
//        App.getCurrentGame().getPierreGeneralStore().reduce(stock.getItem(), quantity);
//        return new Result(true, "You purchased successfully!");
//    }

    public Result purchase(String productName , String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Stock stock = App.getCurrentGame().getPierreGeneralStore().getStock(productName);
        if (stock == null) {
            return new Result(false, "Product not found!");
        }
        if (stock.getQuantity() == 0) {
            return new Result(false, "Product is sold out!");
        }
        if (stock.getQuantity() != -1
                && stock.getQuantity() < quantity) {
            return new Result(false, "Not enough product in stock!");
        }
        if (currentPlayer.getMoney() < stock.getSalePrice() * quantity) {
            return new Result(false, "Not enough money!");
        }
        if (!currentPlayer.getBackpack().canAdd(
                stock.getItem(),
                stock.getStackLevel(),
                quantity)) {
            return new Result(true, "Not enough space in backPack!");
        }
        currentPlayer.spendMoney(stock.getSalePrice() * quantity);
        App.getCurrentGame().getPierreGeneralStore().reduce(stock.getItem(), quantity);
        currentPlayer.getBackpack().addItems(
                stock.getItem(),
                stock.getStackLevel(),
                quantity);
        return new Result(true, "You purchased successfully!");
    }
}
