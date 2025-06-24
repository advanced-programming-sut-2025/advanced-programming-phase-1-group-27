package org.example.controller;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.view.menu.WelcomeMenuView;

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
        App.setCurrentMenu(Menu.ExitMenu);
        return null;
    }

    private Result goToRegisterMenu() {
        App.setCurrentMenu(Menu.RegisterMenu);
        return new Result(true, "Redirecting to register menu ...");
    }

    private Result goToLoginMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        return new Result(true, "Redirecting to login menu ...");
    }
}
