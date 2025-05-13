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
        for (Menu menu : Menu.values()) {
            if (menu.toString().equals(menuName))
                return menu;
        }
        return null;
    }

    public boolean isShop() {
        return this == BlackSmithShop ||
                this == CarpenterShop ||
                this == FishShop ||
                this == JojaMartShop ||
                this == MarnieRanch ||
                this == PierreGeneralShop ||
                this == StardropSaloonShop;
    }

    @Override
    public String toString() {
        return switch (this) {
            case LoginMenu -> "login menu";
            case MainMenu -> "main menu";
            case ProfileMenu -> "profile menu";
            case GameMenu -> "game menu";
            case Home -> "home";
            case BlackSmithShop -> "blacksmith";
            case CarpenterShop -> "carpenter shop";
            case FishShop -> "fish shop";
            case JojaMartShop -> "joja mart";
            case MarnieRanch -> "marnie ranch";
            case PierreGeneralShop -> "pierre general store";
            case StardropSaloonShop -> "stardrop shop";
            case AnimalEnclosure -> "animal enclosure";
            case Trade -> "trade menu";
            case ExitMenu -> "exit menu";
        };
    }
}
