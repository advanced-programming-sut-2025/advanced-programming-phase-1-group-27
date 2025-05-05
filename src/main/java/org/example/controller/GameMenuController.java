package org.example.controller;

import org.example.models.App;
import org.example.models.Game;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.Weathers.Weather;
import org.example.view.GameMenuView;

public class GameMenuController extends MenuController {
    private GameMenuView view;

    public GameMenuController(GameMenuView view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu != Menu.MainMenu)
            return new Result(false, "can't enter this menu!");
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public Result exitGame() {
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public void nextTurn() {
        App.getCurrentGame().getCurrentPlayer().setTomorrowEnergy();
        App.getCurrentGame().nextPlayer();
        // TODO: parsa pasokh be soalat mokhtalef
    }

    public Result showWeather() {
        return new Result(true, "The Current State Of Weather is " +
                App.getCurrentGame().getCurrentWeather() + " Weather!");
    }
    
    public Result forecastWeather() {
        Game game = App.getCurrentGame();
        Weather weather;
        if (game.getTomorrowWeather() != null) weather = game.getTomorrowWeather();
        else game.setTomorrowWeather(weather = game.getTime().getSeason().pickARandomWeather());
        return new Result(true, "The Weather Forecasted For Tomorrow is " +
                weather.toString() + " Weather!");
    }

    // Cheats :
    
    public void cheatSetWeather() {
        Game game = App.getCurrentGame();
        game.setTomorrowWeather(game.getTime().getSeason().pickARandomWeather());
    }
}
