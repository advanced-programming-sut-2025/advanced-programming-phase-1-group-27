package org.example.view.menu;

import org.example.controller.ProfileMenuController;
import org.example.models.enums.commands.ProfileMenuCommands;
import org.example.models.Result;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends AppMenu {
    private final ProfileMenuController controller;

    public ProfileMenu() {
        controller = new ProfileMenuController(this);
    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = ProfileMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (ProfileMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (ProfileMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
