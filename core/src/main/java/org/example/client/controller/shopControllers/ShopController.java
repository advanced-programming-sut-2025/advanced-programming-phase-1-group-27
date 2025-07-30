package org.example.client.controller.shopControllers;

import org.example.client.Main;
import org.example.client.controller.menus.MenuController;
import org.example.client.view.AppMenu;
import org.example.client.view.shopview.PurchaseMenuView;
import org.example.server.models.Result;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;

public class ShopController extends MenuController {
    private final AppMenu shopMenu;
    private final NPCType npc;

    public ShopController(AppMenu shopMenu, NPCType npc) {
        this.shopMenu = shopMenu;
        this.npc = npc;
    }

    public void purchase(Stock stock) {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PurchaseMenuView(stock , npc , shopMenu));
    }

    public void buyAnimal(Stock stock) {
        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new PurchaseMenuView(stock , npc , shopMenu));
    }

    public void build(Stock stock) {
        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new PurchaseMenuView(stock , npc , shopMenu));
    }

    public void upgrade() {
        Main.getMain().getScreen().dispose();

    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        // TODO : Chikar bayad konam?
        return null;
    }
}
