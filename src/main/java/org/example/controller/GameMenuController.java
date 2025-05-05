package org.example.controller;

import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.models.enums.Weathers.Weather;
import org.example.view.GameMenuView;

import java.util.ArrayList;
import java.util.Scanner;

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
        Game game = App.getCurrentGame();
        if (!App.getLoggedInUser().getUsername().equals(game.getAdmin().getUsername()))
            return new Result(false, "You cannot end this game!");
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to main menu ...");
    }

    public void nextTurn(Scanner scanner) {
        App.getCurrentGame().getCurrentPlayer().setNextTurnEnergy();
        boolean fullTurn =  App.getCurrentGame().nextPlayer();
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Poll poll = currentPlayer.getPoll();
        if (poll != null) {
            if (poll.isPollComplete()) {
                if (poll.requestIsAccepted())
                    eraseGame();
                currentPlayer.setPoll(null);
            }
            else {
                int result = getPlayerVote(scanner);
                if (result == 1)
                    poll.vote();
                else
                    poll.oppose();
                currentPlayer.setPoll(null);
            }
        }
        if (fullTurn)
            App.getCurrentGame().passAnHour();
        // TODO: parsa pasokh be soalat mokhtalef
    }

    public Result terminateGame() {
        ArrayList<Player> players = App.getCurrentGame().getPlayers();
        Poll poll = new Poll(players.size());
        poll.vote(); // the first player should always vote
        for (Player player : players) {
            player.setPoll(poll);
        }
        return new Result(true, "Request sent to other players.");
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

    private void eraseGame() {
        Game game = App.getCurrentGame();
        for (Player player : game.getPlayers()) {
            player.setCurrentGame(null);
        }
        App.setCurrentGame(null);
        App.setCurrentMenu(Menu.MainMenu);
    }

    private int getPlayerVote(Scanner scanner) {
        view.printString("Would you like to terminate the game? (accept|reject)");
        Result result;
        do {
            result = view.askToVote(scanner);
        } while (!result.success());
        return result.message().equals("accept") ? 1 : 0;
    }

    // Cheats :
    
    public void cheatSetWeather() {
        Game game = App.getCurrentGame();
        game.setTomorrowWeather(game.getTime().getSeason().pickARandomWeather());
    }
}
