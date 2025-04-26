package org.example.controller;

import org.example.models.enums.Menu;
import org.example.models.App;
import org.example.models.Result;
import org.example.view.menu.MainMenu;

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
        return new Result(true, "Redirecting to " + newMenu.toString() + " ...");
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
}
