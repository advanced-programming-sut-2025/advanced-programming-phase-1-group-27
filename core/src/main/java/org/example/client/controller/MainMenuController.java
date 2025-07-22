package org.example.client.controller;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.ProfileMenuView;
import org.example.client.view.menu.WelcomeMenuView;
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
        Main.getMain().setScreen(new WelcomeMenuView());
    }

    public void goToLobbyMenu(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LobbyMenuView());
    }

    public void goToProfileMenu(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenuView());
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
