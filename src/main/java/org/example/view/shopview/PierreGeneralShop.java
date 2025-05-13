package org.example.view.shopview;

import org.example.controller.shopcontroller.PierreGeneralShopController;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.enums.commands.ShopCommands;
import org.example.view.AppMenu;
import org.example.view.GameMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PierreGeneralShop extends AppMenu {
    private final PierreGeneralShopController controller;

    public PierreGeneralShop() {
        controller = new PierreGeneralShopController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (ShopCommands.ShowAllProducts.getMatcher(input) != null) {
            System.out.println(controller.showAllProducts());
        }
        else if (ShopCommands.ShowAllAvailableProducts.getMatcher(input) != null) {
            System.out.println(controller.showAllAvailableProducts());
        }
        else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("productName").trim(),
                    matcher.group("count")
            ));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
