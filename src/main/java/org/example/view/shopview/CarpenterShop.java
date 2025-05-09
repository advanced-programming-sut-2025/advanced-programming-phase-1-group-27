package org.example.view.shopview;

import org.example.controller.shopcontroller.CarpenterShopController;
import org.example.models.enums.Menu;
import org.example.view.AppMenu;
import org.example.view.GameMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CarpenterShop extends AppMenu {
    private final CarpenterShopController controller;

    public CarpenterShop() {
        controller = new CarpenterShopController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
    }
}
