package org.example.controller;

import org.example.models.Result;
import org.example.view.menu.PreGameMenuView;

public class PreGameMenuController extends MenuController{

    private final PreGameMenuView view;


    public PreGameMenuController(PreGameMenuView view) {
        this.view = view;
    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }
}
