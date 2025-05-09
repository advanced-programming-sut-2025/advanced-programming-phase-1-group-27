package org.example.view;

import org.example.models.App;
import org.example.models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().executeCommands(scanner);
        } while (App.getCurrentMenu() != Menu.ExitMenu);
    }
}
