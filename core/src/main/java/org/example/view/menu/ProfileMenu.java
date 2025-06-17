package org.example.view.menu;

import org.example.controller.ProfileMenuController;
import org.example.models.enums.commands.MainMenuCommands;
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
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if ((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            System.out.println(controller.changeUsername(
                    matcher.group("username").trim()
            ));
        }
        else if ((matcher = ProfileMenuCommands.ChangeNickname.getMatcher(input)) != null) {
            System.out.println(controller.changeNickname(
                    matcher.group("nickname").trim()
            ));
        }
        else if ((matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null) {
            System.out.println(controller.changeEmail(
                    matcher.group("email").trim()
            ));
        }
        else if ((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            System.out.println(controller.changePassword(
                    matcher.group("newPassword").trim(),
                    matcher.group("oldPassword").trim()
            ));
        }
        else if (ProfileMenuCommands.UserInfo.getMatcher(input) != null) {
            System.out.println(controller.showInfo());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
