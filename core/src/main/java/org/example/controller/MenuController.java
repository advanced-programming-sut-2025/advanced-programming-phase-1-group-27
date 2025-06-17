package org.example.controller;

import org.example.models.App;
import org.example.models.Result;

public abstract class MenuController {
    public abstract Result enterMenu(String menuName);

    public abstract Result exitMenu();

    public Result showCurrentMenu() {
        return new Result(true, App.getCurrentMenu().toString());
    }
}
