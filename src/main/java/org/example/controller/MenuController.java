package org.example.controller;

import org.example.models.Result;

public abstract class MenuController {
    public abstract Result enterMenu(String menuName) {}

    public Result exitMenu() {}

    public Result ShowCurrentMenu() {}
}
