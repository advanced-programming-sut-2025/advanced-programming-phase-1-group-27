package org.example.view;

import org.example.controller.AnimalEnclosureController;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.AnimalEnclosureCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class AnimalEnclosureView extends AppMenu{
    private final AnimalEnclosureController controller;

    public AnimalEnclosureView() {
        controller = new AnimalEnclosureController(this);
    }

    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = AnimalEnclosureCommands.PetAnimal.getMatcher(input)) != null) {

        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
