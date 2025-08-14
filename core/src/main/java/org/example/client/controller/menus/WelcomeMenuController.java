package org.example.client.controller.menus;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LoginMenuView;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.WelcomeMenuView;
import org.example.common.models.Menu;
import org.example.common.models.Result;

public class WelcomeMenuController extends MenuController {

    private WelcomeMenuView view;

    public WelcomeMenuController(WelcomeMenuView view) {
        this.view = view;
    }

    public void handleWelcomeMenuButtons() {
    }

    @Override
    public Result enterMenu(String menuName) {
        Menu newMenu = Menu.getMenu(menuName);
        if (newMenu == null)
            return new Result(false, "menu doesn't exist!");
        if (newMenu == Menu.RegisterMenu)
            return goToRegisterMenu();
        if (newMenu == Menu.LoginMenu)
            return goToLoginMenu();
        return new Result(false, "Can't enter this menu!");
    }

    @Override
    public Result exitMenu() {
        playClickSound();
        return null;
    }

    public Result goToRegisterMenu() {
        playClickSound();
        ClientApp.setCurrentMenu(new RegisterMenuView());
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return new Result(true, "Redirecting to registerViaGraphics menu ...");
    }

    public Result goToLoginMenu() {
        playClickSound();
        ClientApp.setCurrentMenu(new LoginMenuView());
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return new Result(true, "Redirecting to login menu ...");
    }
}
