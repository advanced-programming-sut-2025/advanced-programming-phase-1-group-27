package org.example.server.controller;

import org.example.client.controller.menus.MenuController;
import org.example.common.models.*;
import org.example.server.models.*;
import org.example.common.models.Map.Hut;
import org.example.common.models.Plants.FruitType;
import org.example.common.models.items.Recipe;
import org.example.common.models.items.products.AnimalProduct;
import org.example.common.models.items.products.CookingProduct;
import org.example.common.models.items.products.CraftingProduct;
import org.example.common.models.tools.Backpack;
import org.example.client.view.HomeView;

public class HomeController extends MenuController {
    private final HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from home!");
    }

    public Result exitMenu() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        App.setCurrentMenu(Menu.GameMenu);
        currentPlayer.setCurrentMenu(Menu.GameMenu);
        currentPlayer.setCurrentMap(currentPlayer.getFarmMap());
        return new Result(true, "Exiting home ...");
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
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

    public Result craft(String itemName) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        CraftingProduct item = CraftingProduct.getItem(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        Recipe recipe = Recipe.getRecipe(item);
        if (!player.getAvailableCraftingRecipes().contains(recipe))
            return new Result(false, "You don't have this recipe!");
        if (!player.getBackpack().canAdd(recipe.getFinalProduct(), StackLevel.Basic, 1))
            return new Result(false, "Your backpack is full!");
        if (!player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        player.useRecipe(recipe);
        if (player.getEnergy() < 2) {
            player.consumeEnergy(player.getEnergy());
            player.getBackpack().reduceItems(recipe.getFinalProduct(), 1);
            return new Result(false, "Crafting failed! You don't have enough energy!");
        }
        player.consumeEnergy(2);
        return new Result(true, itemName + " crafted successfully!");
    }

    public Result putOrPickFromRefrigerator(String itemName, String func) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Hut hut = player.getFarmMap().getHut();
        Backpack refrigerator = hut.getRefrigerator();
        if (func.equals("put")) {
            Stacks slot = player.getBackpack().getSlotByItemName(itemName);
            if (slot == null)
                return new Result(false, "Item not found in inventory!");
            if (!canBePlacedInRefrigerator(slot.getItem()))
                return new Result(false, "Cannot place this item in refrigerator!");
            if (refrigerator.canAdd(slot.getItem(), slot.getStackLevel(), 1)) {
                refrigerator.addItems(slot.getItem(), slot.getStackLevel(), 1);
                player.getBackpack().reduceItems(slot.getItem(), slot.getStackLevel(), 1);
                return new Result(true, itemName + " added to refrigerator successfully!");
            }
            return new Result(false, "Refrigerator is full!");
        } else {
            Stacks slot = refrigerator.getSlotByItemName(itemName);
            if (slot == null)
                return new Result(false, "Item not found in refrigerator!");
            if (player.getBackpack().canAdd(slot.getItem(), slot.getStackLevel(), 1)) {
                player.getBackpack().addItems(slot.getItem(), slot.getStackLevel(), 1);
                refrigerator.reduceItems(slot.getItem(), slot.getStackLevel(), 1);
                return new Result(true, itemName + " picked successfully!");
            }
            return new Result(false, "Inventory is full!");
        }
    }

    public Result showCookingRecipes() {
        StringBuilder result = new StringBuilder();
        Player player = App.getCurrentGame().getCurrentPlayer();
        for (Recipe cookingRecipe : player.getAvailableCookingRecipes()) {
            result.append(cookingRecipe.description());
            result.append("--------\n");
        }
        return new Result(true, result.toString());
    }

    public Result cook(String itemName) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        CookingProduct item = CookingProduct.getItem(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        Recipe recipe = Recipe.getRecipe(item);
        if (!player.getAvailableCookingRecipes().contains(recipe))
            return new Result(false, "You don't have this recipe!");
        if (!player.getBackpack().canAdd(recipe.getFinalProduct(), StackLevel.Basic, 1))
            return new Result(false, "Your backpack is full!");
        if (!player.hasEnoughIngredients(recipe))
            return new Result(false, "You don't have enough ingredients!");
        player.useRecipe(recipe);
        if (player.getEnergy() < 3) {
            player.consumeEnergy(player.getEnergy());
            player.getBackpack().reduceItems(recipe.getFinalProduct(), StackLevel.Basic, 1);
            return new Result(false, "Cooking failed! You don't have enough energy!");
        }
        player.consumeEnergy(3);
        return new Result(true, itemName + " cooked successfully!");
    }

    private boolean canBePlacedInRefrigerator(Item item) {
        if (!(item instanceof CookingProduct) &&
                !(item instanceof ProcessedProduct) &&
                !(item instanceof FruitType) &&
                !(item instanceof AnimalProduct)) {
            return false;
        }
        if (item instanceof FruitType fruit && !fruit.isFruitEdible())
            return false;
        if (item instanceof ProcessedProduct processedProduct && !processedProduct.isEdible())
            return false;
        if (item instanceof AnimalProduct animalProduct && !animalProduct.isFood())
            return false;
        return true;
    }
}
