package org.example.view;

import org.example.controller.GameMenuController;
import org.example.controller.HomeController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class HomeView extends AppMenu {
    private final HomeController controller;

    public HomeView() {
        controller = new HomeController(this);
    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
    }
}
