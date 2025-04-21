package controller;

import enums.Menu;
import models.App;
import models.Result;

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
