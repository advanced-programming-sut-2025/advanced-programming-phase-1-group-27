package org.example.view;

import org.example.controller.AnimalEnclosureController;
import org.example.models.enums.Menu;

import java.util.Scanner;

public class AnimalEnclosureView extends AppMenu{
    private final AnimalEnclosureController controller;

    public AnimalEnclosureView(AnimalEnclosureView view) {
        controller = new AnimalEnclosureController(this);
    }

    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
    }
}
