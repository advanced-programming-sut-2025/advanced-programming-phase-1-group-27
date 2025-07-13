package org.example.server.controller;

import org.example.client.Main;
import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.LoginMenuView;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.WelcomeMenuView;

public class WelcomeMenuController extends MenuController {

    private WelcomeMenuView view;

    public WelcomeMenuController(WelcomeMenuView view) {
        this.view = view;
    }

    public void handleWelcomeMenuButtons() {
    }

    @Override
    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.RegisterMenu)
            return goToRegisterMenu();
        if (newMenu == Menu.LoginMenu)
            return goToLoginMenu();
        return new Result(false, "Can't enter this menu!");
    }

    @Override
    public Result exitMenu() {
        playClickSound();
        App.setCurrentMenu(Menu.ExitMenu);
        return null;
    }

    public Result goToRegisterMenu() {
        playClickSound();
        App.setCurrentMenu(Menu.RegisterMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new RegisterMenuView());
        return new Result(true, "Redirecting to register menu ...");
    }

    public Result goToLoginMenu() {
        playClickSound();
        App.setCurrentMenu(Menu.LoginMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView());
        return new Result(true, "Redirecting to login menu ...");
    }
}
