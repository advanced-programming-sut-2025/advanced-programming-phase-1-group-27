package org.example.view;

import org.example.Main;
import org.example.models.App;
import org.example.models.enums.Menu;
import org.example.view.menu.MainMenuView;
import org.example.view.menu.WelcomeMenuView;

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
    }


}
