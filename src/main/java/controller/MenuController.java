package controller;

import enums.Menu;
import models.App;
import models.Result;
import view.AppMenu;

public abstract class MenuController {
    public abstract Result enterMenu(String menuName) {}

    public Result exitMenu() {}

    public Result ShowCurrentMenu() {}
}
