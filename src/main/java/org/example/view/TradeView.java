package org.example.view;

import org.example.controller.InteractionsWithOthers.TradeController;

import java.util.Scanner;

public class TradeView extends AppMenu {
    private final TradeController controller;

    public TradeView() {
        controller = new TradeController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
