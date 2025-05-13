package org.example.view;

import org.example.controller.GameMenuController;
import org.example.controller.HomeController;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.HomeCommands;
import org.example.models.enums.commands.MainMenuCommands;

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
        if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(
                    matcher.group("menuName").trim()
            ));
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (HomeCommands.ShowCraftingRecipes.getMatcher(input) != null) {
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
        else if ((matcher = HomeCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(controller.eatFood(
                    matcher.group("itemName").trim()
            ));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
