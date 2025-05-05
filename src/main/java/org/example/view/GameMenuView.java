package org.example.view;

import org.example.controller.GameMenuController;
import org.example.models.enums.commands.GameMenuCommands;
import org.example.models.Result;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenuView extends AppMenu {
    private final GameMenuController controller;

    public GameMenuView() {
        controller = new GameMenuController(this);
    }

    public void executeCommands(Scanner scanner) {
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
        else if (GameMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (GameMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
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
