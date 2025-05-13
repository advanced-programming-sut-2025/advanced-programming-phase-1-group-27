package org.example.view;

import org.example.controller.GameMenuController;
import org.example.controller.InteractionsWithOthers.TradeController;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.CheatCommands;
import org.example.models.enums.commands.GameMenuCommands;
import org.example.models.Result;
import org.example.models.enums.commands.LoginMenuCommands;
import org.example.models.enums.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuView extends AppMenu {
    private final GameMenuController controller;

    public GameMenuView() {
        controller = new GameMenuController(this);
    }

    public GameMenuController getController() {
        return controller;
    }

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
        else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            controller.nextTurn(scanner);
        }
        else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(controller.terminateGame(scanner));
        }
        else if (GameMenuCommands.ShowWeather.getMatcher(input) != null) {
            System.out.println(controller.showWeather());
        }
        else if (GameMenuCommands.ForecastWeather.getMatcher(input) != null) {
            System.out.println(controller.forecastWeather());
        }
        else if ((matcher = CheatCommands.CheatSetWeather.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetWeather(matcher.group("weatherName").trim()));
        }
        else if ((matcher = CheatCommands.CheatThor.getMatcher(input)) != null) {
            System.out.println(controller.cheatThor(matcher.group("i"), matcher.group("j")));
        }
        else if ((matcher = GameMenuCommands.Walk.getMatcher(input)) != null) {
            System.out.println(controller.walk(matcher.group("i"), matcher.group("j"), scanner));
        }
        else if ((matcher = GameMenuCommands.PrintMap.getMatcher(input)) != null) {
            System.out.println(controller.printMap(matcher.group("i"), matcher.group("j"),
                    matcher.group("size")));
        }
        else if (GameMenuCommands.HelpReadingMap.getMatcher(input) != null) {
            System.out.println(controller.helpReadingMap());
        }
        else if (GameMenuCommands.ShowEnergy.getMatcher(input) != null) {
            System.out.println(controller.showEnergy());
        }
        else if ((matcher = CheatCommands.CheatSetEnergy.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetEnergy(matcher.group("value")));
        }
        else if (CheatCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
            System.out.println(controller.cheatEnergyUnlimited());
        }
        else if ((matcher = GameMenuCommands.ToolsUse.getMatcher(input)) != null) {
            System.out.println(controller.useTool(matcher.group("direction")));
        }
        else if ((matcher = GameMenuCommands.PlaceItem.getMatcher(input)) != null) {
            System.out.println(controller.placeItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("direction").trim())
            ));
        }
        else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        }
        else if ((matcher = CheatCommands.CheatSetFriendShip.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetFriendship(matcher.group("name"), matcher.group("amount")));
        }
        else if ((matcher = CheatCommands.CheatAddMoney.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddMoney(matcher.group("amount")));
        }
        else if ((matcher = GameMenuCommands.CropInfo.getMatcher(input)) != null) {
            System.out.println(controller.cropInfo(matcher.group("cropName")));
        }
        else if ((matcher = GameMenuCommands.Fishing.getMatcher(input)) != null) {
            System.out.println(controller.fishing(
                    matcher.group("fishPole").trim()
            ));
        }
        else if (GameMenuCommands.BuildGreenHouse.getMatcher(input) != null) {
            System.out.println(controller.buildGreenHouse());
        }
        else if ((matcher = GameMenuCommands.Plant.getMatcher(input)) != null) {
            System.out.println(controller.plant(matcher.group("seedName"), matcher.group("direction")));
        }
        else if ((matcher = GameMenuCommands.ShowPlant.getMatcher(input)) != null) {
            System.out.println(controller.showPlant(matcher.group("i"), matcher.group("j")));
        }
        else if ((matcher = GameMenuCommands.Pet.getMatcher(input)) != null) {
            System.out.println(controller.pet(matcher.group("name")));
        }
        else if (GameMenuCommands.ShowAnimals.getMatcher(input) != null) {
            System.out.println(controller.showAnimals());
        }
        else if ((matcher = GameMenuCommands.Shepherd.getMatcher(input)) != null) {
            System.out.println(controller.shepherd(matcher.group("name"),
                    matcher.group("i"), matcher.group("j")));
        }
        else if (GameMenuCommands.Products.getMatcher(input) != null) {
            System.out.println(controller.products());
        }
        else if ((matcher = GameMenuCommands.SellAnimal.getMatcher(input)) != null) {
            System.out.println(controller.sellAnimal(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommands.CollectProducts.getMatcher(input)) != null) {
            System.out.println(controller.collectProduct(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommands.FeedHay.getMatcher(input)) != null) {
            System.out.println(controller.feedHay(matcher.group("name")));
        }
        else if ((matcher = GameMenuCommands.UseArtisan.getMatcher(input)) != null) {
            System.out.println(controller.useArtisan(
                    matcher.group("artisanName").trim(),
                    matcher.group("itemList").trim()
            ));
        }
        else if ((matcher = GameMenuCommands.SellItem.getMatcher(input)) != null) {
            System.out.println(controller.sellItem(matcher.group("name"), matcher.group("amount")));
        }
        else if ((matcher = GameMenuCommands.GetArtisan.getMatcher(input)) != null) {
            System.out.println(controller.getArtisanProduct(
                    matcher.group("artisanName")
            ));
        }
        else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(controller.inventoryShow());
        }
        else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(controller.inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Result askToVote(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (!input.equals("accept") && !input.equals("reject")) {
            System.out.println("Invalid response!");
            return new Result(false, "invalid command!");
        }
        System.out.println("You have voted successfully!");
        return new Result(true, input);
    }
}
