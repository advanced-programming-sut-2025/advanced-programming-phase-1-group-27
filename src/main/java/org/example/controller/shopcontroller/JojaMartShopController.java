package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.Stock;
import org.example.models.enums.Menu;
import org.example.view.shopview.JojaMartShop;

public class JojaMartShopController extends MenuController {
    private final JojaMartShop view;

    public JojaMartShopController(JojaMartShop view) {
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
        for (Stock stock : App.getCurrentGame().getJojaMart().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getJojaMart().getStock()) {
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

    public Result Purchase(String productName , String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Stock stock = App.getCurrentGame().getJojaMart().getStock(productName);
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
