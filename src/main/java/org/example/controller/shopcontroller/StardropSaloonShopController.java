package org.example.controller.shopcontroller;

import org.example.models.App;
import org.example.view.shopview.PierreGeneralShop;
import org.example.view.shopview.StardropSaloonShop;

public class StardropSaloonShopController {
    private final StardropSaloonShop view;

    public StardropSaloonShopController(StardropSaloonShop view) {
        this.view = view;
    }

    public boolean playerPassedOut() {
        return App.getCurrentGame().getCurrentPlayer().hasPassedOut();
    }
}
