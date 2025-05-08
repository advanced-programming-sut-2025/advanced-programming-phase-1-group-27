package org.example.controller;

import org.example.models.*;
import org.example.models.Map.FarmMap;
import org.example.models.Map.Map;
import org.example.models.enums.CellType;
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

        handlePoll(currentPlayer, scanner);

        if (fullTurn)
            App.getCurrentGame().passAnHour();
        if (currentPlayer.getDayEnergy() <= 0) {
            nextTurn(scanner);
            return;
        }
        view.printString(currentPlayer.getUsername() + "'s turn!");
        App.setCurrentMenu(currentPlayer.getCurrentMenu());
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

    public Result goToHome() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        App.setCurrentMenu(Menu.Home);
        currentPlayer.setCurrentMenu(Menu.Home);
        return new Result(true, "Redirecting to Home ...");
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

    public Result walk(String s, String t, Scanner scanner) {
        if (!s.matches("\\d") || !t.matches("\\d")) {
            return new Result(false, "GO KILL YOURSELF");
        }
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Map currentMap = currentPlayer.getCurrentMap();
        Cell destination = currentMap.getCell(i, j);
        if (!currentMap.areConnected(currentPlayer.getCurrentCell(), destination)) {
            return new Result(false, "There Is No Path Between These Cells");
        } else {
            int energy = currentMap.getDistance(currentPlayer.getCurrentCell(), destination) / 20;
            System.out.println("The Energy Needed for This Walk is " +
                     energy + " And You Have " + currentPlayer.getEnergy() +
                    ", Would You Like To Walk? (Y/N)");
            String answer = scanner.nextLine();
            while (true) {
                if (answer.trim().equals("Y")) {
                    if (currentPlayer.getEnergy() > energy) {
                        currentPlayer.consumeEnergy(energy);
                        currentPlayer.setCurrentCell(destination);
                        return new Result(true, "You Walked And Now Are On Cell(" +
                                i + "," + j);
                    } else {
                        energy = 0;
                        currentPlayer.passOut();
                        return new Result(false, "You Passed Out!!");
                    }
                } else if (answer.trim().equals("N")) {
                    return new Result(false, "Alright.");
                }
                System.out.println("Invalid Response, Please Answer By (Y/N)");
                answer = scanner.nextLine();
            }
        }
    }

    public Result printMap(String s, String t, String sizeString) {
        if (!s.matches("\\d") || !t.matches("\\d") || !sizeString.matches("\\d+")) {
            return new Result(false, "GO KILL YOURSELF");
        }
        int x = Integer.parseInt(s), y = Integer.parseInt(t), size = Integer.parseInt(sizeString);
        String view = "";
        Map map = App.getCurrentGame().getCurrentPlayer().getCurrentMap();
        if (x + size > map.getHeight() || y + size > map.getWidth()) {
            return new Result(false, "Size is Too Big");
        }
        for (int i = x; i < x + size; i++) {
            if (i > 0) view += "\n";
            for (int j = y; j < y + size; j++) {
                view += map.getCell(i, j).toString();
            }
        }
        return new Result(true, view);
    }

    public Result helpReadingMap() {
        return new Result(true, App.getCurrentGame().getCurrentPlayer().getCurrentMap().getMapReadingManual());
    }

    private void handlePoll(Player currentPlayer, Scanner scanner) {
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

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    // Cheats :
    
    public Result cheatSetWeather(String weatherString) {
        Weather weather = null;
        for (Weather w : Weather.values()) {
            if (w.toString().equals(weatherString)) {
                weather = w;
            }
        }
        if (weather == null) {
            return new Result(false, "Please a valid weather type from " + Weather.values());
        }
        Game game = App.getCurrentGame();
        game.setTomorrowWeather(game.getTime().getSeason().pickARandomWeather());
        return new Result(true, "Weather set to " + weather.toString() + " Weather!");
    }

    public Result cheatThor(String s, String t) {
        if (!s.matches("\\d") || !t.matches("\\d")) {
            return new Result(false, "GO KILL YOURSELF");
        }
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        FarmMap map = App.getCurrentGame().getCurrentPlayer().getFarmMap();
        Cell cell = map.getCell(i, j);
        if (cell.getType() == CellType.Building) {
            return new Result(false, "There is A Building!!");
        } else {
            cell.thor();
            return new Result(true, "The Cell [" + i + ", " + j + "] was hit by Thor!");
        }
    }
}
