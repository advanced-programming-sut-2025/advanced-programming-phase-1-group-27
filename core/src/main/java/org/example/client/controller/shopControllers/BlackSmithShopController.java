package org.example.client.controller.shopControllers;

import org.example.client.controller.menus.MenuController;
import org.example.client.view.shopview.BlackSmithShop;
import org.example.server.controller.ToolController;
import org.example.server.models.Result;

public class BlackSmithShopController extends MenuController {
    private BlackSmithShop view;
    private final ToolController toolController = new ToolController();

    public BlackSmithShopController(BlackSmithShop view) {
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
