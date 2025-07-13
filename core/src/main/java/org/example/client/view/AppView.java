package org.example.client.view;

import org.example.client.Main;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.WelcomeMenuView;

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

        if (App.getSavedUser() != null) {
            Main.getMain().setScreen(new MainMenuView());
            return;
        }
        Main.getMain().setScreen(new WelcomeMenuView());
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HomeView());
    }



}
