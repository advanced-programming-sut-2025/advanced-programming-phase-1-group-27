package org.example.view;

import org.example.controller.GameMenuController;
import org.example.controller.HomeController;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.HomeCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeView extends AppMenu {
    private final HomeController controller;

    public HomeView() {
        controller = new HomeController(this);
    }

    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine();
        Matcher matcher;
        if (HomeCommands.ShowCraftingRecipes.getMatcher(input) != null) {
            System.out.print(controller.showCraftingRecipes());
        }
        else if ((matcher = HomeCommands.CraftItem.getMatcher(input)) != null) {
            System.out.println(controller.craft(
                    matcher.group("itemName").trim()
            ));
        }
        else if ((matcher = HomeCommands.PutPickRefrigerator.getMatcher(input)) != null) {
            System.out.println(controller.putOrPickFromRefrigerator(
                    matcher.group("itemName").trim(),
                    matcher.group("function").trim()
            ));
        }
        else if (HomeCommands.ShowCookingRecipes.getMatcher(input) != null) {
            System.out.println(controller.showCookingRecipes());
        }
        else if ((matcher = HomeCommands.CookItem.getMatcher(input)) != null) {
            System.out.println(controller.cook(
                    matcher.group("itemName").trim()
            ));
        }
        else if (HomeCommands.Exit.getMatcher(input) != null) {
            System.out.println(controller.exit());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
