package org.example.controller;

import org.example.models.App;
import org.example.models.Item;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.items.Recipe;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.view.GameMenuView;
import org.example.view.HomeView;

import java.util.Scanner;

public class HomeController {
    private final HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
    }

    public Result showCraftingRecipes() {
        StringBuilder result = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        for (Recipe craftingRecipe : player.getAvailableCraftingRecipes()) {
            result.append(craftingRecipe.description());
            result.append("--------\n");
        }
        return new Result(true, result.toString());
    }

    public Result craft(String itemName, Scanner scanner ) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        CraftingProduct item = CraftingProduct.getCraftingProduct(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        if (player.getEnergy() < 2)
            return new Result(false, "You don't have enough energy to craft " + itemName);
        if (player.getBackpack().isFull())
            return new Result(false, "Your backpack is full!");
        Recipe recipe = Recipe.getRecipe(item);
        if (!player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        player.useRecipe(recipe);
        player.reduceEnergy(2);
        return new Result(true, itemName + " crafted successfully!");
    }

    public Result placeItem(String itemName, int direction) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = player.getItemFromBackpack(itemName);
        if (item == null)
            return new Result(false, "Item not found in backpack!");
        if (direction < 1 || direction > 8)
            return new Result(false, "Invalid direction!");
        // TODO: ba sobhan check kon chejoori place konim
        return null;
    }

    public Result cheatAddItem(String itemName, int count) {
        // TODO: function incomplete
        return null;
    }

    public Result putOrPickFromRefrigerator(String itemName) {
        // TODO: function incomplete
        return null;
    }

    public Result showCookingRecipes() {
        // TODO: function incomplete
        return null;
    }

    public Result cook(String itemName) {
        // TODO: function incomplete
        return null;
    }

    public Result eatFood(String foodName) {
        // TODO: function incomplete
        return null;
    }

    public Result exit() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        App.setCurrentMenu(Menu.GameMenu);
        currentPlayer.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "Exiting home ...");
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
