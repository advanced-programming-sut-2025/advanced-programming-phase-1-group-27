package org.example.server.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.example.client.controller.MenuController;
import org.example.client.view.HUDView;
import org.example.common.models.GraphicalResult;
import org.example.server.models.App;
import org.example.server.models.Game;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.commands.CheatCommands;

import java.util.regex.Matcher;

public class HUDController extends MenuController {

    private final HUDView view;

    public HUDController(HUDView view) {
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

    public GraphicalResult handleTextInput(){

        String inputText = view.getTextInputField().getText();

        GraphicalResult graphicalResult = handleCheat(inputText);

        closeTextInputField();

        return graphicalResult;

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
