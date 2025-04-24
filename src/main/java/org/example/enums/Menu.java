package org.example.enums;

import org.example.view.AppMenu;
import org.example.view.menu.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    GameMenu(new GameMenu()),
    ProfileMenu(new ProfileMenu()),
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void executeCommands(Scanner scanner) {}

    public static Menu getMenu(String menuName) {
        return switch (menuName) {
            case "login" -> LoginMenu;
            case "main" -> MainMenu;
            case "game" -> GameMenu;
            case "profile" -> ProfileMenu;
            default -> null;
        };
    }
}
