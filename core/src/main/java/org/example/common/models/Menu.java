package org.example.common.models;

import org.example.client.view.*;

public enum Menu {
    WelcomeMenu,
    LoginMenu,
    RegisterMenu,
    ForgetPasswordMenu,
    MainMenu,
    PregameMenu,
    GameMenu,
    ProfileMenu,
    Home,
    Outside,
    BlackSmithShop,
    CarpenterShop,
    FishShop,
    JojaMartShop,
    MarnieRanch,
    PierreGeneralShop,
    StardropSaloonShop,
    Trade,
    ExitMenu;

    public static Menu getMenu(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.toString().equals(menuName))
                return menu;
        }
        return null;
    }

    public AppMenu getMenu() {
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
            case WelcomeMenu -> "welcome menu";
            case RegisterMenu -> "registerViaGraphics menu";
            case LoginMenu -> "login menu";
            case ForgetPasswordMenu -> "forget password menu";
            case MainMenu -> "main menu";
            case PregameMenu -> "pregame menu";
            case ProfileMenu -> "profile menu";
            case GameMenu -> "game menu";
            case Home -> "home";
            case Outside -> "outside";
            case BlackSmithShop -> "blacksmith";
            case CarpenterShop -> "carpenter shop";
            case FishShop -> "fish shop";
            case JojaMartShop -> "joja mart";
            case MarnieRanch -> "marnie ranch";
            case PierreGeneralShop -> "pierre general store";
            case StardropSaloonShop -> "stardrop shop";
            case Trade -> "trade menu";
            case ExitMenu -> "exit menu";
        };
    }
}
