package org.example.client.controller.shopControllers;

import org.example.client.controller.menus.MenuController;
import org.example.client.view.shopview.StardropSaloonShop;
import org.example.server.models.Result;

public class StardropSaloonShopController extends MenuController {
    private StardropSaloonShop view;

    public StardropSaloonShopController(StardropSaloonShop view) {
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
