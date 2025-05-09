package org.example.models.enums;

import org.example.view.*;
import org.example.view.menu.*;
import org.example.view.shopview.*;

import java.util.Scanner;

public enum Menu {
    LoginMenu(new LoginMenu()),
    MainMenu(new MainMenu()),
    GameMenu(new GameMenuView()),
    ProfileMenu(new ProfileMenu()),
    Home(new HomeView()),
    BlackSmithShop(new BlackSmithShop()),
    CarpenterShop(new CarpenterShop()),
    FishShop(new FishShop()),
    JojaMartShop(new JojaMartShop()),
    MarnieRanchShop(new MarnieRanchShop()),
    PierreGeneralShop(new PierreGeneralShop()),
    StardropSaloonShop(new StardropSaloonShop()),
    AnimalEnclosure(new AnimalEnclosureView()),
    ExitMenu(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void executeCommands(Scanner scanner) {
        menu.executeCommands(scanner);
    }

    public AppMenu getMenu() {
        return menu;
    }

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
            case Home -> "Home";
            case Shop -> "Shop";
            case AnimalEnclosure -> "Animal enclosure";
            case ExitMenu -> "exit menu";
        };
    }
}
