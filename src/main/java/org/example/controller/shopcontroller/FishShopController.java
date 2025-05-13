package org.example.controller.shopcontroller;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.view.shopview.FishShop;

public class FishShopController extends MenuController {
    private final FishShop view;

    public FishShopController(FishShop view) {
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
        App.setCurrentMenu(Menu.GameMenu);
        App.getCurrentGame().getCurrentPlayer().setCurrentMenu(Menu.GameMenu);
        return new Result(true, "Redirecting to game menu ...");
    }
}
