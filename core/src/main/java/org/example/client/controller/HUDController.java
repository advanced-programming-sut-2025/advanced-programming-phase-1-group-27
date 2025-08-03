package org.example.client.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.client.view.HUDView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.*;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.commands.CheatCommands;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.regex.Matcher;

public class HUDController extends MenuController {

    private final HUDView view;

    public HUDController(HUDView view) {
        this.view = view;
    }

    public void setMoneyInfo(Label label){

        String money = Integer.toString(ClientApp.getCurrentGame().getCurrentPlayer().getMoney());

        String output = String.join(" ", money.split(""));

        label.setText(output);

    }

    public void setTimeInfo(Label label){

        int time = ClientApp.getCurrentGame().getTime().getHour();

        String timeFormat = ( (time>12)? time-12:time ) + ((time>12)? "  pm":"  am");
        label.setText(timeFormat);

    }

    public void setDayInfo(Label label){

        String day = ClientApp.getCurrentGame().getTime().getDayOfWeek().substring(0,3) + ". " + ClientApp.getCurrentGame().getTime().getDate();
        label.setText(day);

    }

    public float getSlotPosition(){

        Integer slotIndex = ClientApp.getCurrentGame().getCurrentPlayer().getCurrentInventorySlotIndex();

        float imageSize = GameAssetManager.getGameAssetManager().getInventorySelectSlot().getWidth();

        if ( slotIndex == 0 || slotIndex == 1 || slotIndex == 2 ){

            return imageSize * slotIndex;

        }

        else {

            return ( imageSize * slotIndex + slotIndex);

        }


    }

    public float getItemPosition(Integer slotIndex){

        float imageSize = GameAssetManager.getGameAssetManager().getInventorySelectSlot().getWidth();

        if ( slotIndex == 0 || slotIndex == 1 || slotIndex == 2 ){

            return imageSize * slotIndex;

        }

        else {

            return ( imageSize * slotIndex + slotIndex);

        }


    }

    public void updateSlotIndex(Integer slotChange){

        Player player = ClientApp.getCurrentGame().getCurrentPlayer();

        Integer currentSlot = player.getCurrentInventorySlotIndex();

        if ( currentSlot+slotChange > 11 ){
            player.setCurrentInventorySlotIndex(0);
        }
        else if ( currentSlot+slotChange < 0 ){
            player.setCurrentInventorySlotIndex(11);
        }
        else{
            player.setCurrentInventorySlotIndex(currentSlot+slotChange);
        }

    }

    public void quickSetHotBarIndex(int index){
        ClientApp.getCurrentGame().getCurrentPlayer().setCurrentInventorySlotIndex(index);
    }

    private Image getClockByGameState(){

        ClientGame currentGame = ClientApp.getCurrentGame();

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
        else if ( ClientApp.getCurrentGame().getTime().getSeason().equals(Season.Summer) ){

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
        else if ( ClientApp.getCurrentGame().getTime().getSeason().equals(Season.Fall) ){

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

        float hour = (float) ClientApp.getCurrentGame().getTime().getHour();
        return - 180 * (hour - 9f) / (22f - 9f);

    }

    public void updateClockImage(){
        view.setClockImage(getClockByGameState());
    }

    public GraphicalResult handleTextInput(){

        String inputText = view.getTextInputField().getText();

        GraphicalResult graphicalResult = handleCheat(inputText);

        closeTextInputField();

        return graphicalResult;

    }

    public GraphicalResult craft(CraftingProduct craftingProduct) {
        Recipe recipe = craftingProduct.getRecipe();
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (!player.getBackpack().canAdd(craftingProduct, StackLevel.Basic, 1))
            return new GraphicalResult("Your backpack is full!");
        player.useRecipe(recipe);
        if (player.getEnergy() < 2) {
            player.consumeEnergy(player.getEnergy());
            player.getBackpack().reduceItems(recipe.getFinalProduct(), 1);
            return new GraphicalResult("Crafting failed! You don't have enough energy!");
        }
        player.consumeEnergy(2);
        return new GraphicalResult(craftingProduct.getName() + " crafted successfully", false);
    }

    public GraphicalResult removeFromInventory(Stacks stacks) {

        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        player.getBackpack().reduceItems(stacks.getItem(),stacks.getQuantity());
        return new GraphicalResult(stacks.getItem()+" ("+stacks.getQuantity()+")  removed successfully",false);


    }

    private GraphicalResult handleCheat(String input){

        Matcher matcher;
        CheatController controller = new CheatController();
        Result result = new Result(false,"Invalid Command");

        if ((matcher = CheatCommands.CheatSetWeather.getMatcher(input)) != null) {
            result = controller.cheatSetWeather(matcher.group("weatherName").trim());
        }
        else if ((matcher = CheatCommands.CheatThor.getMatcher(input)) != null) {
            result = controller.cheatThor(matcher.group("i"), matcher.group("j"));
        }
        else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            result = controller.cheatSetEnergy(matcher.group("value"));
        }
        else if (CheatCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
            result = controller.cheatEnergyUnlimited();
        }
        else if ((matcher = CheatCommands.CheatAdvanceTime.getMatcher(input)) != null) {
            result = controller.cheatAdvanceTime(matcher.group("timeString"));
        }
        else if ((matcher = CheatCommands.CheatAdvanceDate.getMatcher(input)) != null) {
            result = controller.cheatAdvanceDate(matcher.group("dayString"));
        }
        else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            result = controller.cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count")));
        }
        else if ((matcher = CheatCommands.CheatSetFriendShip.getMatcher(input)) != null) {
            result = controller.cheatSetFriendship(matcher.group("name"), matcher.group("amount"));
        }
        else if ((matcher = CheatCommands.CheatAddMoney.getMatcher(input)) != null) {
            result = controller.cheatAddMoney(matcher.group("amount"));
        }
        else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            result = controller.cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim()));
        }
        else if ((matcher = CheatCommands.CheatAddLevelNPC.getMatcher(input)) != null) {
            result = controller.cheatAddLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        }
        else if ((matcher = CheatCommands.CheatAddLevelPlayer.getMatcher(input)) != null) {
            result = controller.cheatAddPlayerLevel(
                    matcher.group("name").trim(),
                    matcher.group("level").trim());
        }

        return new GraphicalResult(result.message(),result.success()? GameAssetManager.getGameAssetManager().getAcceptColor() : GameAssetManager.getGameAssetManager().getErrorColor(), result.success() );


    }

    public void closeTextInputField(){

        view.setInputFieldVisible(false);
        view.getTextInputField().setText("");

    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }
}
