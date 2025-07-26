package org.example.client.controller.shopControllers;

import org.example.client.controller.MenuController;
import org.example.client.view.shopview.JojaMartShop;
import org.example.client.view.shopview.PierreGeneralShop;
import org.example.server.models.Result;

public class JojaMartShopController extends MenuController {
    private final JojaMartShop view;

    public JojaMartShopController(JojaMartShop view) {
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
