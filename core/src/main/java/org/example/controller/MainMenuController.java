package org.example.controller;

import org.example.Main;
import org.example.models.*;
import org.example.models.enums.Menu;
import org.example.view.menu.MainMenuView;
import org.example.view.menu.PreGameMenuView;
import org.example.view.menu.ProfileMenuView;
import org.example.view.menu.WelcomeMenuView;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenuController extends MenuController {
    private MainMenuView view;

    public MainMenuController(MainMenuView view) {
        this.view = view;
    }

    public void goToPregameMenu() {

        App.setCurrentMenu(Menu.PregameMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PreGameMenuView());

    }

    public void goToProfileMenu() {

        App.setCurrentMenu(Menu.ProfileMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView());

    }

    public Result logout() {

        App.setLoggedInUser(null);
        App.setCurrentMenu(Menu.WelcomeMenu);
        App.deleteLoginUserFile();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new WelcomeMenuView());
        return new Result(true, "User logged out successfully!");

    }



    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.LoginMenu)
            return new Result(false, "you should logout to enter this menu!");
        if (newMenu != Menu.ProfileMenu)
            return new Result(false, "Can't enter this menu!");
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
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                if (users.get(i).equals(users.get(j))) {
                    return new Result(false, "Duplicate username found!");
                }
            }
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
        Game game;
        App.setCurrentGame(game = new Game(players));
        game.init();
        for (User user : users) {
            user.setCurrentGame(game);
        }
        for (Player player : players) {
            getPlayerMap(player, game, scanner);
        }
        App.setCurrentMenu(Menu.Home);
        return new Result(true, "Game created! You are now in your House!");
    }

    public Result loadGame() {
        User user = App.getLoggedInUser();
        Game game = user.getCurrentGame();
        if (game == null)
            return new Result(false, "There are no game to load!");
        game.setAdmin(user);
        App.setCurrentGame(game);
        App.setCurrentMenu(game.getCurrentPlayer().getCurrentMenu());
        return new Result(true, "Game loaded!");
    }

    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "Redirecting to login menu ...");
    }


    private void getPlayerMap(Player player, Game game, Scanner scanner) {
        Result result;
        int mapId = 1;
        do {
            view.printString("Choosing " + player.getUsername() + "'s map:");
            result = view.inputMap(scanner);
            if (!result.success())
                continue;
            mapId = Integer.parseInt(result.message());
            for (Player thisPlayer : game.getPlayers()) {
                if (thisPlayer.getFarmMap() != null && thisPlayer.getFarmMap() == game.getFarmMap(mapId))
                    result = new Result(false, "");
            }
        } while (!result.success());
        player.setFarmMap(game.getFarmMap(mapId));
        player.setCurrentCell(game.getFarmMap(mapId).getCell(8, 70));
        game.getFarmMap(mapId).getHut().setOwner(player);
    }
}
