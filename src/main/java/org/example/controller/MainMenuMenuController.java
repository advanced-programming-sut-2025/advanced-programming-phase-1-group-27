package org.example.controller;

import org.example.enums.Menu;
import org.example.models.App;
import org.example.models.Result;

public class MainMenuMenuController extends MenuController {
    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        App.setCurrentMenu(newMenu);
        return new Result(true, "");
    }

    public Result logout() {}
}
