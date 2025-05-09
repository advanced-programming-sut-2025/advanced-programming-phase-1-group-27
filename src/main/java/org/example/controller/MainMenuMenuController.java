package org.example.controller;

import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.view.menu.MainMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuMenuController extends MenuController {
    private MainMenu view;

    public MainMenuMenuController(MainMenu view) {
        this.view = view;
    }

    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.LoginMenu)
            return new Result(false, "you should logout to enter this menu!");
        App.setCurrentMenu(newMenu);
        return new Result(true, "Redirecting to " + newMenu + " ...");
    }

    public Result createNewGame(String username1, String username2, String username3, String overflow, Scanner scanner) {
        if (username1 == null)
            return new Result(false, "No username provided!");
        if (overflow != null)
            return new Result(false, "You can't play with more than 3 players!");
        ArrayList<User> users = new ArrayList<>();
        users.add(App.getLoggedInUser());
        if (username1 != null)
            users.add(App.getUserByUsername(username1));
        if (username2 != null)
            users.add(App.getUserByUsername(username2));
        if (username3 != null)
            users.add(App.getUserByUsername(username3));
        for (User user : users) {
            if (user == null)
                return new Result(false, "Username not found!");
        }
        if (users.getFirst().getCurrentGame() != null)
            return new Result(false, "You are already in a game!");
        for (int i = 1; i < users.size(); i++) {
            if (users.get(i).getCurrentGame() != null)
                return new Result(false, "User is already in a game!");
        }
        ArrayList<Player> players = new ArrayList<>();
        for (User user : users) {
            players.add(new Player(user));
        }
        Game game = new Game(players);
        for (User user : users) {
            user.setCurrentGame(game);
        }
        for (Player player : players) {
            getPlayerMap(player, game, scanner);
        }
        App.setCurrentGame(game);
        App.setCurrentMenu(Menu.GameMenu);
        return new Result(true, "Game created!");
    }

    public Result loadGame() {
        User user = App.getLoggedInUser();
        Game game = user.getCurrentGame();
        if (game == null)
            return new Result(false, "There are no game to load!");
        game.setAdmin(user);
        App.setCurrentGame(game);
        return new Result(true, "Game loaded!");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "Redirecting to login menu ...");
    }

    public Result logout() {
        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "User logged out successfully!");
    }

    private void getPlayerMap(Player player, Game game, Scanner scanner) {
        view.printString("Choosing " + player.getUsername() + "'s map:");
        Result result;
        int mapId;
        do {
            result = view.inputMap(scanner);
            mapId = result.success()? Integer.parseInt(result.message()): -1;
            for (Player thisPlayer : game.getPlayers()) {
                if (thisPlayer.getCurrentMap() != null && thisPlayer.getCurrentMap() == game.getFarmMap(mapId))
                    result = new Result(false, "");
            }
        } while (!result.success());
        player.setFarmMap(game.getFarmMap(mapId));
        game.getFarmMap(mapId).getHut().setOwner(player);
    }
}
