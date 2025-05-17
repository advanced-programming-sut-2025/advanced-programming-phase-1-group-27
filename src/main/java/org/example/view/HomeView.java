package org.example.view;

import org.example.controller.GameMenuController;
import org.example.controller.HomeController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.CheatCommands;
import org.example.models.enums.commands.GameMenuCommands;
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
            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
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
        else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
        }
        else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
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
        else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().eatFood(
                    matcher.group("itemName").trim()
            ));
        }
        else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().inventoryShow());
        }
        else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
        }
        else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        }
        else if (GameMenuCommands.ShowEnergy.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().showEnergy());
        }
        else if ((matcher = GameMenuCommands.EatFood.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().eatFood(
                    matcher.group("itemName").trim()
            ));
        }
        else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().cheatSetEnergy(
                    matcher.group("value").trim()
            ));
        }
        else if ((matcher = CheatCommands.CheatSetAbility.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().cheatSetAbility(
                    matcher.group("abilityName").trim(),
                    Integer.parseInt(matcher.group("level").trim())
            ));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
