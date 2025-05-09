package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.PierreGeneralShop;

public class PierreGeneralShopController {
    private final PierreGeneralShop view;

    public PierreGeneralShopController(PierreGeneralShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
