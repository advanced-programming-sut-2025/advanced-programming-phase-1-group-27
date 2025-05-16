package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.AnimalProperty.Animal;
import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.App;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.Shops.Shop;
import org.example.models.Stock;
import org.example.models.enums.Menu;
import org.example.models.enums.items.AnimalType;
import org.example.models.enums.items.Recipe;
import org.example.models.enums.items.ShopItems;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Tool;
import org.example.view.shopview.MarnieRanch;

public class MarnieRanchShopController extends MenuController {
    private final MarnieRanch view;

    public MarnieRanchShopController(MarnieRanch view) {
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

    public Result buyAnimal(String animalString, String name) {
        AnimalType type = AnimalType.getItem(animalString);
        if (type == null) {
            return new Result(false, "Invalid animal type!");
        }
        Animal animal = new Animal(type, name);
        Player player = App.getCurrentGame().getCurrentPlayer();
        Shop shop = App.getCurrentGame().getMarnieRanch();


        if (shop.getStock(type.getName()).getQuantity() == 0) {
            return new Result(false, "Out of stock!");
        }
        else if (player.getMoney() >= type.getPrice()) {
            shop.getStock(animalString).reduceQuantity();
            for (Barn barn : player.getFarmMap().getBarns()) {
                if (type.getAppropriateFarmType().contains(barn.getType()) &&
                        barn.getType().getCapacity() > barn.getAnimals().size()) {
                    barn.addAnimal(animal);
                    player.getFarmMap().addAnimal(animal);
                    return new Result(true, "You have bought a " + animalString +
                            " and it is in the " + barn.getType().getName());
                }
            }
            for (Coop coop : player.getFarmMap().getCoops()) {
                if (type.getAppropriateFarmType().contains(coop.getType()) &&
                        coop.getType().getCapacity() > coop.getAnimals().size()) {
                    coop.addAnimal(animal);
                    player.getFarmMap().addAnimal(animal);
                    return new Result(true, "You have bought a " + animalString +
                            " and it is in the " + coop.getType().getName());
                }
            }
            return new Result(false, "There is No Space For This Animal in Your Farm!");
        } else {
            return new Result(false, "You dont Have Enough Money! You Have " + player.getMoney() +
                    " while you need " + type.getPrice());
        }
    }

    public Result showAllProducts() {
        StringBuilder result = new StringBuilder();
        result.append("All Products : \n");
        int i = 1;
        for (Stock stock : App.getCurrentGame().getMarnieRanch().getStock()) {
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
        for (Stock stock : App.getCurrentGame().getMarnieRanch().getStock()) {
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
        int quantity = quantityString == null? 1 : Integer.parseInt(quantityString);
        Stock stock = App.getCurrentGame().getMarnieRanch().getStock(productName);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (stock == null) {
            return new Result(false, "Product not found!");
        }
        if (stock.getItem() == ToolType.MilkPail) {
            if (stock.getQuantity() == 0) {
                return new Result(false, "Product is sold out!");
            }
            if (stock.getQuantity() != -1
                    && stock.getQuantity() < quantity) {
                return new Result(false, "Not enough product in stock!");
            }
        } else if (stock.getItem() == ToolType.Shear) {
            if (stock.getQuantity() == 0) {
                return new Result(false, "Product is sold out!");
            }
            if (stock.getQuantity() != -1
                    && stock.getQuantity() < quantity) {
                return new Result(false, "Not enough product in stock!");
            }
        } else if (stock.getItem() == ShopItems.Hay) { // unlimited!

        } else {
            return new Result(false, "You can't purchase animal!");
        }
        if (currentPlayer.getMoney() < stock.getPrice() * quantity) {
            return new Result(false, "You have not enough money!");
        }
        if (!currentPlayer.getBackpack().canAdd(
                stock.getItem(),
                stock.getStackLevel(),
                quantity)) {
            return new Result(true, "Not enough space in backPack!");
        }
        currentPlayer.spendMoney(stock.getSalePrice() * quantity);
        App.getCurrentGame().getMarnieRanch().reduce(stock.getItem(), quantity);
        currentPlayer.getBackpack().addItems(
                stock.getItem(),
                stock.getStackLevel(),
                quantity);
        return new Result(true, "You purchased successfully!");
    }
}
