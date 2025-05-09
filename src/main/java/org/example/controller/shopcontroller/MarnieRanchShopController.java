package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.MarnieRanchShop;

public class MarnieRanchShopController {
    private final MarnieRanchShop view;

    public MarnieRanchShopController(MarnieRanchShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
