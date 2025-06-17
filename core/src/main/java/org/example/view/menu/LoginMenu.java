package org.example.view.menu;

import org.example.controller.LoginMenuMenuController;
import org.example.models.enums.Gender;
import org.example.models.enums.commands.LoginMenuCommands;
import org.example.models.Result;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends AppMenu {
    private final LoginMenuMenuController controller;

    public LoginMenu() {
        controller = new LoginMenuMenuController(this);
    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.Register.getMatcher(input)) != null) {
            System.out.println(controller.register(
                    matcher.group("username").trim(),
                    matcher.group("password").trim(),
                    matcher.group("reEnteredPassword").trim(),
                    matcher.group("nickname").trim(),
                    matcher.group("email").trim(),
                    matcher.group("gender").trim(),
                    scanner
            ));
        }
        else if ((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            System.out.println(controller.login(
                    matcher.group("username").trim(),
                    matcher.group("password").trim(),
                    matcher.group("stay") != null
            ));
        }
        else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
            System.out.println(controller.forgetPassword(
                    matcher.group("username").trim(),
                    scanner
            ));
        }
        else if ((matcher = LoginMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (LoginMenuCommands.ExitMenu.getMatcher(input) != null) {
            controller.exitMenu();
        }
        else if (LoginMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Result suggestUsername(String suggestedUsername, Scanner scanner) {
        System.out.println("Username already taken!");
        System.out.println("How about " + suggestedUsername + "?");
        System.out.println("Press [Y/y] to approve.");
        String response = scanner.nextLine().trim();
        return controller.reRegister(response);
    }

    public String reTypePassword(Scanner scanner) {
        System.out.println("reentered password doesn't match password!");
        System.out.println("reenter your password: (type \"RANDOM\" to obtain a random password)");
        return scanner.nextLine().trim();
    }

    public String isPasswordAccepted(String password, Scanner scanner) {
        System.out.println("Password: " + password);
        System.out.println("Type Y/y if the password is appropriate, " +
                "E if you want to go back to login menu and " +
                "type anything else to regenerate.");
        return scanner.nextLine().trim();
    }

    public Result pickQuestion(Scanner scanner) {
        System.out.println("Pick one question: ");
        String input = scanner.nextLine().trim();
        return controller.checkAnswer(LoginMenuCommands.PickQuestion.getMatcher(input));
    }

    public Matcher getAnswer(Scanner scanner) {
        String input = scanner.nextLine().trim();
        return LoginMenuCommands.AnswerQuestion.getMatcher(input);
    }
}
