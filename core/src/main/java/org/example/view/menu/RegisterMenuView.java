package org.example.view.menu;

import org.example.controller.RegisterMenuController;
import org.example.models.Result;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.enums.commands.RegisterMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenuView extends AppMenu {
    private final RegisterMenuController controller;

    public RegisterMenuView() {
        controller = new RegisterMenuController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = RegisterMenuCommands.Register.getMatcher(input)) != null) {
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
        else if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public String reTypePassword(Scanner scanner) {
        System.out.println("reentered password doesn't match password!");
        System.out.println("reenter your password: (type \"RANDOM\" to obtain a random password)");
        return scanner.nextLine().trim();
    }

    public Result suggestUsername(String suggestedUsername, Scanner scanner) {
        System.out.println("Username already taken!");
        System.out.println("How about " + suggestedUsername + "?");
        System.out.println("Press [Y/y] to approve.");
        String response = scanner.nextLine().trim();
        return controller.reRegister(response);
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
        return controller.checkAnswer(RegisterMenuCommands.PickQuestion.getMatcher(input));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
