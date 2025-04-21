package view.menu;

import controller.MainMenuMenuController;
import enums.commands.MainMenuCommands;
import models.Result;
import view.AppMenu;

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
