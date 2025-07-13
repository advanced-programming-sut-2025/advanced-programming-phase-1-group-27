package org.example.server.controller.shopcontroller;

import org.example.server.controller.MenuController;
import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.*;
import org.example.models.Map.FarmMap;
import org.example.models.Shops.Shop;
import org.example.models.enums.Menu;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.BuildingType;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.Recipe;
import org.example.client.view.shopview.CarpenterShop;

public class CarpenterShopController extends MenuController {
    private final CarpenterShop view;

    public CarpenterShopController(CarpenterShop view) {
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

    public Result buildBuilding(String buildingName, int x, int y) {
        BuildingType type = BuildingType.getEnclosureType(buildingName);
        if (type == null) {
            return new Result(false, "Enclosure type is invalid!");
        }
        Shop carpenterShop = App.getCurrentGame().getCarpenterShop();
        Stock stock = carpenterShop.getStock(type.getName());
        if (stock.getQuantity() == 0) {
            return new Result(false, "You can not build anymore!");
        }
        Player player = App.getCurrentGame().getCurrentPlayer();
        FarmMap farmMap = player.getFarmMap();
        Recipe recipe = Recipe.getRecipe(type);
        if (App.getCurrentGame().getCurrentPlayer().getMoney() < stock.getPrice()) {
            return new Result(false, "You don't have enough money!");
        }
        if (recipe != null && !player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        if (!farmMap.freeRectangle(x, y, type.getHeight(), type.getWidth()))
            return new Result(false, "There is no free space to place this animal enclosure!");
        if (recipe != null)
            player.useRecipe(recipe);
        if (type.isBarn())
            farmMap.placeBarn(x, y, new Barn(type, new Cell(new Position(x, y), farmMap)));
        else
            farmMap.placeCoop(x, y, new Coop(type, new Cell(new Position(x, y), farmMap)));
        carpenterShop.reduce(stock.getItem(), 1);
        player.getBackpack().reduceItems(type, 1);
        App.getCurrentGame().getCurrentPlayer().spendMoney(stock.getPrice());
        return new Result(true, "Animal enclosure successfully placed!");
    }

    public Result showAllProducts() {
        StringBuilder result = new StringBuilder();
        result.append("All Products : \n");
        int i = 1;
        for (Stock stock : App.getCurrentGame().getCarpenterShop().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getCarpenterShop().getStock()) {
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

    public Result purchase(String itemName, String quantityString) {
        int amount = quantityString == null ? 1 : Integer.parseInt(quantityString);
        int price = 0;
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (itemName.equalsIgnoreCase(MineralType.Wood.getName())) {
            price = amount * 10;
            if (!player.getBackpack().canAdd(MineralType.Wood, StackLevel.Basic, amount)) {
                return new Result(false, "You don't have enough wood!");
            }
            if (player.getMoney() < price) {
                return new Result(false, "You don't have enough money!");
            }
            player.getBackpack().addItems(MineralType.Wood, StackLevel.Basic, amount);
        } else if (itemName.equalsIgnoreCase(MineralType.Stone.getName())) {
            price = amount * 20;
            if (!player.getBackpack().canAdd(MineralType.Stone, StackLevel.Basic, amount)) {
                return new Result(false, "You don't have enough stone!");
            }
            if (player.getMoney() < price) {
                return new Result(false, "You don't have enough money!");
            }
            player.getBackpack().addItems(MineralType.Stone, StackLevel.Basic, amount);
        } else if (itemName.equalsIgnoreCase(BuildingType.ShippingBin.getName())) {
            price = amount * 250;
            int amountWood = amount * 150;
            if (!player.getBackpack().hasEnoughItem(MineralType.Wood, amountWood)) {
                return new Result(false, "You don't have enough wood!");
            }
            if (player.getMoney() < price) {
                return new Result(false, "You don't have enough money!");
            }
            if (!player.getBackpack().canAdd(BuildingType.ShippingBin, StackLevel.Basic, amount)) {
                return new Result(false, "You don't have enough space in backpack!");
            }
            player.getBackpack().reduceItems(MineralType.Wood, StackLevel.Basic, amountWood);
            player.spendMoney(price);
            player.getBackpack().addItems(BuildingType.ShippingBin, StackLevel.Basic, amount);
            return new Result(true, "You bought shipping bin * " + amount + " !");
        } else {
            return new Result(false, "Invalid item name!");
        }
        player.spendMoney(price);
        return new Result(true, "You bought " + amount + " of " + itemName + " !");
    }
}
