package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.CarpenterShop;

public class CarpenterShopController {
    private final CarpenterShop view;

    public CarpenterShopController(CarpenterShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
