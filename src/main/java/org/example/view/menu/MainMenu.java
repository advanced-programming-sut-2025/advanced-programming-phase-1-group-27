package org.example.view.menu;

import org.example.controller.MainMenuMenuController;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.Result;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends AppMenu {
    private final MainMenuMenuController controller;

    public MainMenu() {
        controller = new MainMenuMenuController(this);
    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if ((matcher = MainMenuCommands.NewGame.getMatcher(input)) != null) {
            System.out.println(controller.createNewGame(
                    matcher.group("username1"),
                    matcher.group("username2"),
                    matcher.group("username3"),
                    matcher.group("overflow"),
                    scanner
            ));
        }
        else if (MainMenuCommands.LoadGame.getMatcher(input) != null) {
            System.out.println(controller.loadGame());
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if (MainMenuCommands.Logout.getMatcher(input) != null) {
            System.out.println(controller.logout());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Result inputMap(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.SetMap.getMatcher(input)) != null) {
            return new Result(true, matcher.group("mapNumber"));
        }
        return new Result(false, "invalid command!");
    }

    public void printString(String string) {
        System.out.println(string);
    }
}
