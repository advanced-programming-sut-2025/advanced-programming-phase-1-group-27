package view.menu;

import controller.GameMenuController;
import enums.commands.GameMenuCommands;
import models.Result;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends AppMenu {
    private GameMenuController controller = new GameMenuController();

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = GameMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
