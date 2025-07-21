package org.example.client.view;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class AppView {

    public void runViaTerminal() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().executeCommands(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }

    public void runViaGraphics() {

        if (ClientApp.loadSavedUser()) {
            Main.getMain().setScreen(new MainMenuView());
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new HomeView());
            return;
        }
        Main.getMain().setScreen(new WelcomeMenuView());
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new HomeView());

    }



}
