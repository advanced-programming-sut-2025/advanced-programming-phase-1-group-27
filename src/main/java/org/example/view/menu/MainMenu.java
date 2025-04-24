package org.example.view.menu;

import org.example.controller.MainMenuMenuController;
import org.example.enums.commands.MainMenuCommands;
import org.example.models.Result;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends AppMenu {
    private final MainMenuMenuController controller = new MainMenuMenuController();

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
