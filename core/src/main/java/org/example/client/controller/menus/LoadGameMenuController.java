package org.example.client.controller.menus;

import org.example.client.view.menu.LoadGameMenuView;
import org.example.server.models.Result;

public class LoadGameMenuController extends MenuController{
    private LoadGameMenuView view;

    public LoadGameMenuController(LoadGameMenuView view) {
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
