package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.view.shopview.BlackSmithShop;

public class BlackSmithShopController extends MenuController {
    private final BlackSmithShop view;

    public BlackSmithShopController(BlackSmithShop view) {
        this.view = view;
    }


    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }

    @Override
    public Result enterMenu(String menuName) {
        return new Result(false, "You can't enter any menu from here!");
    }

    @Override
    public Result exitMenu() {
        // TODO: rassa bodo;
        return null;
    }
}
