package org.example.client.controller;

import org.example.client.view.menu.ForgetPasswordMenuView;
import org.example.client.view.menu.LobbyMenuView;
import org.example.server.models.Result;

public class LobbyMenuController extends MenuController {
    LobbyMenuView view;
    public LobbyMenuController(LobbyMenuView view) {
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
