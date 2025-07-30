package org.example.server.controller.shopcontroller;

import org.example.client.controller.menus.MenuController;
import org.example.server.models.App;
import org.example.server.models.Player;
import org.example.server.models.Result;
import org.example.server.models.Stock;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.items.Recipe;
import org.example.client.view.shopview.StardropSaloonShop;

public class StardropSaloonShopController extends MenuController {
    private final StardropSaloonShop view;

    public StardropSaloonShopController(StardropSaloonShop view) {
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
        for (Stock stock : App.getCurrentGame().getStardropSaloon().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getStardropSaloon().getStock()) {
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
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Stock stock = App.getCurrentGame().getStardropSaloon().getStock(productName);
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
        if (stock.getItem() instanceof Recipe) {
            currentPlayer.getAvailableCookingRecipes().add((Recipe) stock.getItem());
        } else {
            if (!currentPlayer.getBackpack().canAdd(
                    stock.getItem(),
                    stock.getStackLevel(),
                    quantity)) {
                return new Result(true, "Not enough space in backPack!");
            }
            currentPlayer.spendMoney(stock.getSalePrice() * quantity);
            App.getCurrentGame().getStardropSaloon().reduce(stock.getItem(), quantity);
            currentPlayer.getBackpack().addItems(
                    stock.getItem(),
                    stock.getStackLevel(),
                    quantity);
        }
        return new Result(true, "You purchased successfully!");
    }
}
