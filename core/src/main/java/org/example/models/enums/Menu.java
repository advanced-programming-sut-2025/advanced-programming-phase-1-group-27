package org.example.models.enums;

import org.example.client.view.AppMenu;
import org.example.client.view.GameView;
import org.example.client.view.HomeView;
import org.example.client.view.TradeView;
import org.example.client.view.menu.*;
import org.example.client.view.shopview.*;

import java.util.Scanner;

public enum Menu {
    WelcomeMenu(new WelcomeMenuView()),
    LoginMenu(new LoginMenuView()),
    RegisterMenu(new RegisterMenuView()),
    ForgetPasswordMenu(new SecurityQuestionMenuView(null)),
    MainMenu(new MainMenuView()),
    //    PregameMenu(new PreGameMenuView()),
    GameMenu(new GameView()),
    ProfileMenu(new ProfileMenuView()),
    Home(new HomeView()),
    BlackSmithShop(new BlackSmithShop()),
    CarpenterShop(new CarpenterShop()),
    FishShop(new FishShop()),
    JojaMartShop(new JojaMartShop()),
    MarnieRanch(new MarnieRanch()),
    PierreGeneralShop(new PierreGeneralShop()),
    StardropSaloonShop(new StardropSaloonShop()),
    Trade(new TradeView()),
    ExitMenu(new ExitMenuView());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public static Menu getMenu(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.toString().equals(menuName))
                return menu;
        }
        return null;
    }

    public void executeCommands(Scanner scanner) {
        menu.executeCommands(scanner);
    }

    public AppMenu getMenu() {
        return menu;
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
            case RegisterMenu -> "register menu";
            case LoginMenu -> "login menu";
            case ForgetPasswordMenu -> "forget password menu";
            case MainMenu -> "main menu";
//            case PregameMenu -> "pregame menu";
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
            case Trade -> "trade menu";
            case ExitMenu -> "exit menu";
        };
    }
}
