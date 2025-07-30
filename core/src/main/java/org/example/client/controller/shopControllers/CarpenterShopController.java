package org.example.client.controller.shopControllers;

import org.example.client.controller.menus.MenuController;
import org.example.client.view.shopview.CarpenterShop;
import org.example.server.models.Result;

public class CarpenterShopController extends MenuController {
    private final CarpenterShop view;

    public CarpenterShopController(CarpenterShop view) {
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
