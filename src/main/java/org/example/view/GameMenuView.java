package org.example.view;

import org.example.controller.GameMenuController;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.CheatCommands;
import org.example.models.enums.commands.GameMenuCommands;
import org.example.models.Result;
import org.example.models.enums.commands.HomeCommands;

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
        if ((matcher = GameMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (GameMenuCommands.ExitGame.getMatcher(input) != null) {
            System.out.println(controller.exitGame());
        }
        else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            controller.nextTurn(scanner);
        }
        else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(controller.terminateGame());
        }
        else if (GameMenuCommands.Home.getMatcher(input) != null) {
            System.out.println(controller.goToHome());
        }
        else if (GameMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (GameMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if (GameMenuCommands.ShowWeather.getMatcher(input) != null) {
            System.out.println(controller.showWeather());
        }
        else if (GameMenuCommands.ForecastWeather.getMatcher(input) != null) {
            System.out.println(controller.forecastWeather());
        }
        else if ((matcher = GameMenuCommands.CheatSetWeather.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetWeather(matcher.group("weatherName").trim()));
        }
        else if ((matcher = GameMenuCommands.CheatThor.getMatcher(input)) != null) {
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
        else if ((matcher = GameMenuCommands.CheatSetEnergy.getMatcher(input)) != null) {
            System.out.println(controller.cheatSetEnergy(matcher.group("value")));
        }
        else if (GameMenuCommands.CheatEnergyUnlimited.getMatcher(input) != null) {
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

    public Result askToPassTurn(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (GameMenuCommands.NextTurn.getMatcher(input) == null)
            return new Result(false, "invalid command!");
        return new Result(true, "");
    }
}
