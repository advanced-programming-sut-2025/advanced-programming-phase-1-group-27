package org.example.view;

import java.util.Scanner;

public abstract class AppMenu {
    public abstract void executeCommands(Scanner scanner);

    public void printString(String string) {
        System.out.println(string);
    }
}
