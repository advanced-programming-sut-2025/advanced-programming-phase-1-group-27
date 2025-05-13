package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.Stacks;
import org.example.models.Stock;
import org.example.models.enums.Menu;
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
        StringBuilder result = new StringBuilder("All Products : \n");
        for (int i = 0; i < App.getCurrentGame().getPierreGeneralStore().getStock().size(); i++) {
            Stock stock = App.getCurrentGame().getBlacksmith().getStock().get(i);
            result.append(i).append(". ").append(stock.getItem().getName()).append(" - ");
            if (stock.getQuantity() == -1)
                result.append("Unlimited");
            else if (stock.getQuantity() == 0)
                result.append("Sold out");
            else
                result.append(stock.getQuantity());
            result.append(" - ").append(stock.getSalePrice()).append("$\n");
        }
        return new Result(true, result.toString());
    }

    public Result showAllAvailableProducts() {
        StringBuilder result = new StringBuilder("All Available Products: \n");
        for (int i = 0; i < App.getCurrentGame().getPierreGeneralStore().getStock().size(); i++) {
            Stock stock = App.getCurrentGame().getBlacksmith().getStock().get(i);
            if (stock.getQuantity() == -1) {
                result.append(i).append(". ").append(stock.getItem().getName()).append(" - ");
                result.append("Unlimited").append(" - ").append(stock.getSalePrice()).append("$\n");
            }
            else if (stock.getQuantity() > 0) {
                result.append(i).append(". ").append(stock.getItem().getName()).append(" - ");
                result.append(stock.getQuantity()).append(" - ");
                result.append(stock.getSalePrice()).append("$\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result purchase(String productName, String quantityString) {
        int quantity = quantityString == null? 1 : Integer.parseInt(quantityString);
        Stock stock = App.getCurrentGame().getPierreGeneralStore().getStock(productName);
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
        App.getCurrentGame().getPierreGeneralStore().reduce(stock.getItem(), quantity);
        App.getCurrentGame().getCurrentPlayer().getBackpack().addItems(
                stock.getItem(),
                stock.getStackLevel(),
                quantity);
        return new Result(true, "You purchased successfully!");
    }
}
