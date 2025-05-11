package org.example.view.shopview;

import org.example.controller.shopcontroller.MarnieRanchShopController;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.ShopCommands;
import org.example.view.AppMenu;
import org.example.view.GameMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MarnieRanchShop extends AppMenu {
    private final MarnieRanchShopController controller;

    public MarnieRanchShop() {
        controller = new MarnieRanchShopController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;

        if ((matcher = ShopCommands.BuyAnimal.getMatcher(input)) != null) {
            System.out.println(controller.buyAnimal(matcher.group("animal"), matcher.group("name")));
        } else {
            System.out.println("Invalid Command.");
        }
    }
}
