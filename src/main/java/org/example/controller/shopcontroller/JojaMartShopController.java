package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.JojaMartShop;

public class JojaMartShopController {
    private final JojaMartShop view;

    public JojaMartShopController(JojaMartShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
