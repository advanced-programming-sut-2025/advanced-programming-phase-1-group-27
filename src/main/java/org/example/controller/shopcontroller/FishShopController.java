package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.FishShop;

public class FishShopController {
    private final FishShop view;

    public FishShopController(FishShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
