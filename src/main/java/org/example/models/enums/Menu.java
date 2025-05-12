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
    MarnieRanch(new MarnieRanch()),
    PierreGeneralShop(new PierreGeneralShop()),
    StardropSaloonShop(new StardropSaloonShop()),
    AnimalEnclosure(new AnimalEnclosureView()),
    Trade(new TradeView()),
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
            case "login menu" -> LoginMenu;
            case "main menu" -> MainMenu;
            case "game menu" -> GameMenu;
            case "profile menu" -> ProfileMenu;
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
            case Home -> "home";
            case BlackSmithShop -> "black smith shop";
            case CarpenterShop -> "carpenter shop";
            case FishShop -> "fish shop";
            case JojaMartShop -> "joja shop";
            case MarnieRanch -> "marnie shop";
            case PierreGeneralShop -> "pierre shop";
            case StardropSaloonShop -> "stardrop shop";
            case AnimalEnclosure -> "animal enclosure";
            case Trade -> "trade menu";
            case ExitMenu -> "exit menu";
        };
    }
}
