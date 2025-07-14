package org.example.server.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.example.server.models.*;
import org.example.server.models.Map.Hut;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.commands.CheatCommands;
import org.example.server.models.enums.commands.InGameCommandLineCommands;
import org.example.server.models.enums.commands.InteractionsWithUserCommands;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.CraftingProduct;
import org.example.server.models.tools.Backpack;
import org.example.client.view.HomeView;

import java.util.regex.Matcher;

public class HomeController extends MenuController {
    private final HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
    }

    private Texture getClockByGameState(){

        Game currentGame = App.getCurrentGame();

        if ( currentGame.getTime().getSeason().equals(Season.Spring) ){

            if ( currentGame.getCurrentWeather().equals(Weather.Rainy) ){
                return GameAssetManager.getGameAssetManager().getRainySpring();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Sunny) ){
                return GameAssetManager.getGameAssetManager().getSunnySpring();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Stormy) ){
                return GameAssetManager.getGameAssetManager().getStormySpring();
            }
            else{
                return GameAssetManager.getGameAssetManager().getSnowySpring();
            }

        }
        else if ( App.getCurrentGame().getTime().getSeason().equals(Season.Summer) ){

            if ( currentGame.getCurrentWeather().equals(Weather.Rainy) ){
                return GameAssetManager.getGameAssetManager().getRainySummer();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Sunny) ){
                return GameAssetManager.getGameAssetManager().getSunnySummer();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Stormy) ){
                return GameAssetManager.getGameAssetManager().getStormySummer();
            }
            else{
                return GameAssetManager.getGameAssetManager().getSnowySummer();
            }

        }
        else if ( App.getCurrentGame().getTime().getSeason().equals(Season.Fall) ){

            if ( currentGame.getCurrentWeather().equals(Weather.Rainy) ){
                return GameAssetManager.getGameAssetManager().getRainyFall();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Sunny) ){
                return GameAssetManager.getGameAssetManager().getSunnyFall();
            }
            else if ( currentGame.getCurrentWeather().equals(Weather.Stormy) ){
                return GameAssetManager.getGameAssetManager().getStormyFall();
            }
            else{
                return GameAssetManager.getGameAssetManager().getSnowyFall();
            }

        }

        if ( currentGame.getCurrentWeather().equals(Weather.Rainy) ){
            return GameAssetManager.getGameAssetManager().getRainyWinter();
        }
        else if ( currentGame.getCurrentWeather().equals(Weather.Sunny) ){
            return GameAssetManager.getGameAssetManager().getSunnyWinter();
        }
        else if ( currentGame.getCurrentWeather().equals(Weather.Stormy) ){
            return GameAssetManager.getGameAssetManager().getStormyWinter();
        }
        else{
            return GameAssetManager.getGameAssetManager().getSnowyWinter();
        }

    }

    public float getClockArrowDegree() {

        float hour = (float) App.getCurrentGame().getTime().getHour();
        return - 180 * (hour - 9f) / (22f - 9f);

    }

    public void updateClockImage(){
        view.setClockImage(new Image(getClockByGameState()));
    }

    public void handleTextInput(){

        String inputText = view.getTextInputField().getText();
        Matcher matcher = InGameCommandLineCommands.Cheat.getMatcher(inputText);

        if ( matcher != null ){
            handleCheat(matcher.group("cheat"));
        }

        closeTextInputField();

    }

    private void handleCheat(String input){

        Matcher matcher;

        if ((matcher = CheatCommands.CheatSetWeather.getMatcher(input)) != null) {
            controller.cheatSetWeather(matcher.group("weatherName").trim());
        }
        else if ((matcher = CheatCommands.CheatThor.getMatcher(input)) != null) {
            controller.cheatThor(matcher.group("i"), matcher.group("j"));
        }
        else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            controller.cheatSetEnergy(matcher.group("value"));
        }
        else if (CheatCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
            controller.cheatEnergyUnlimited();
        }
        else if ((matcher = CheatCommands.CheatAdvanceTime.getMatcher(input)) != null) {
            controller.cheatAdvanceTime(matcher.group("timeString"));
        }
        else if ((matcher = CheatCommands.CheatAdvanceDate.getMatcher(input)) != null) {
            controller.cheatAdvanceDate(matcher.group("dayString"));
        }
        else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            controller.cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count")));
        }
        else if ((matcher = CheatCommands.CheatSetFriendShip.getMatcher(input)) != null) {
            controller.cheatSetFriendship(matcher.group("name"), matcher.group("amount"));
        }
        else if ((matcher = CheatCommands.CheatAddMoney.getMatcher(input)) != null) {
            controller.cheatAddMoney(matcher.group("amount"));
        }
        else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            controller.cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim()));
        }
        else if ((matcher = CheatCommands.CheatAddLevelNPC.getMatcher(input)) != null) {
            interactionsWithNPCController.cheatAddLevel(
                matcher.group("name").trim(),
                matcher.group("level").trim());
        }
        else if ((matcher = CheatCommands.CheatAddLevelPlayer.getMatcher(input)) != null) {
            interactionsWithUserController.cheatAddPlayerLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        }

    }

    public void closeTextInputField(){

        view.setInputFieldVisible(false);
        view.getTextInputField().setText("");

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
        Backpack refrigerator = hut.getOwner().getRefrigerator();
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
