package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.Stock;
import org.example.models.enums.AbilityType;
import org.example.models.enums.Menu;
import org.example.models.enums.items.Recipe;
import org.example.models.enums.items.ToolType;
import org.example.view.shopview.FishShop;

public class FishShopController extends MenuController {
    private final FishShop view;

    public FishShopController(FishShop view) {
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
        for (Stock stock : App.getCurrentGame().getFishShop().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getFishShop().getStock()) {
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
        int quantity = Integer.parseInt(quantityString);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Stock stock = App.getCurrentGame().getFishShop().getStock(productName);
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
        if (stock.getItem() == Recipe.FishSmokerRecipe) {
            currentPlayer.getAvailableCraftingRecipes().add(Recipe.FishSmokerRecipe);
        } else {
            if (!currentPlayer.getBackpack().canAdd(
                    stock.getItem(),
                    stock.getStackLevel(),
                    quantity)) {
                return new Result(true, "Not enough space in backPack!");
            }
            if (stock.getItem() == ToolType.FiberglassRod) {
                if (currentPlayer.getAbility(AbilityType.Fishing).getLevel() < 2) {
                    return new Result(true, "Not enough fishing skill level!");
                }
            } else if (stock.getItem() == ToolType.IridiumRod) {
                if (currentPlayer.getAbility(AbilityType.Fishing).getLevel() < 4) {
                    return new Result(true, "Not enough fishing skill level!");
                }
            }
            currentPlayer.getBackpack().addItems(stock.getItem(), stock.getStackLevel(), quantity);
        }
        App.getCurrentGame().getFishShop().reduce(stock.getItem(), quantity);
        currentPlayer.spendMoney(stock.getPrice() * quantity);
        return new Result(true, "You have successfully purchased " + stock.getItem().getName() + "!");
    }
}
