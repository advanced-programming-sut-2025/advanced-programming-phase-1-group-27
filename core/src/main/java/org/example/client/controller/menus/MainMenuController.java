package org.example.client.controller.menus;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.server.models.Result;

public class MainMenuController extends MenuController {
    private MainMenuView view;
    public MainMenuController(MainMenuView view) {
        this.view = view;
    }

    public void logout(){
        ClientApp.setLoggedInUser(null);
        ClientApp.deleteLoginUserFile();
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new WelcomeMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public void goToLobbyMenu(){
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new LobbyMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public void goToLoadGameMenu() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new PreLoadGameMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public void goToProfileMenu(){
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new ProfileMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    public void goToUserInfoMenu() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new UserInfoView(ClientApp.getCurrentMenu()));
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
    }
}
