package org.example.models.enums;

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

    @Override
    public String toString() {
        return switch (this) {
            case LoginMenu -> "login menu";
            case MainMenu -> "main menu";
            case ProfileMenu -> "profile menu";
            case GameMenu -> "game menu";
            case ExitMenu -> "exit menu";
        };
    }
}
