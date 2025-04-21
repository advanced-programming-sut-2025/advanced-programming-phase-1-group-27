package view.menu;

import controller.ProfileMenuController;
import enums.commands.ProfileMenuCommands;
import models.Result;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    private final ProfileMenuController controller = new ProfileMenuController();

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = ProfileMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
