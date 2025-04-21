package view.menu;

import controller.LoginMenuMenuController;
import enums.commands.LoginMenuCommands;
import models.Result;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    private final LoginMenuMenuController controller = new LoginMenuMenuController();

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

}
