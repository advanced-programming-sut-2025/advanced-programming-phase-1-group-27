package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.BlackSmithShop;

public class BlackSmithShopController {
    private final BlackSmithShop view;

    public BlackSmithShopController(BlackSmithShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
