package org.example.client.controller.shopControllers;

import org.example.client.controller.menus.MenuController;
import org.example.client.view.shopview.FishShop;
import org.example.server.models.Result;

public class FishShopController extends MenuController {
    private final FishShop view;

    public FishShopController(FishShop view) {
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
