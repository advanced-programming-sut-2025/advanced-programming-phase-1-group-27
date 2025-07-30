package org.example.client.controller.shopControllers;

import org.example.client.controller.menus.MenuController;
import org.example.client.view.shopview.MarnieRanch;
import org.example.server.models.Result;

public class MarnieRanchController extends MenuController {
    private final MarnieRanch view;

    public MarnieRanchController(MarnieRanch view) {
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
