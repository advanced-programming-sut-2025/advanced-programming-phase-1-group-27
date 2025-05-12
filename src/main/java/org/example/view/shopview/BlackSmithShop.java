package org.example.view.shopview;

import org.example.controller.shopcontroller.BlackSmithShopController;
import org.example.models.enums.Menu;
import org.example.view.AppMenu;
import org.example.view.GameMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BlackSmithShop extends AppMenu {
    private final BlackSmithShopController controller;

    public BlackSmithShop() {
        controller = new BlackSmithShopController(this);
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
