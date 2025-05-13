package org.example.view;

import org.example.controller.InteractionsWithOthers.TradeController;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.InteractionsWithNPCCommands;
import org.example.models.enums.commands.InteractionsWithUserCommands;
import org.example.models.enums.commands.ShopCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeView extends AppMenu {
    private final TradeController controller;

    public TradeView() {
        controller = new TradeController(this);
    }

    @Override
    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((matcher = InteractionsWithUserCommands.Trade.getMatcher(input)) != null) {
            //TODO : Rassa
        } else if ((matcher = InteractionsWithUserCommands.TradeList.getMatcher(input)) != null) {
            System.out.println(controller.tradeList());
        } else if ((matcher = InteractionsWithUserCommands.TradeResponse.getMatcher(input)) != null) {
            System.out.println(controller.tradeResponse(
                    matcher.group("response").trim() ,
                    matcher.group("id").trim()
            ));
        } else if ((matcher = InteractionsWithUserCommands.TradeHistory.getMatcher(input)) != null) {
            System.out.println(controller.tradeHistory());
        } else {
            System.out.println("Invalid command");
        }
    }
}
