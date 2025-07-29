package org.example.client.controller.shopControllers;

import org.example.client.controller.MenuController;
import org.example.client.view.shopview.PurchaseMenuView;
import org.example.server.models.Result;

public class PurchaseMenuController extends MenuController {
    private final PurchaseMenuView passwordMenuView;

    public PurchaseMenuController(PurchaseMenuView passwordMenuView) {
        this.passwordMenuView = passwordMenuView;
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
