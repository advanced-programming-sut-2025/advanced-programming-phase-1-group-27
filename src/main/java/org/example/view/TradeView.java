package org.example.view;

import org.example.controller.InteractionsWithOthers.TradeController;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.InteractionsWithNPCCommands;
import org.example.models.enums.commands.InteractionsWithUserCommands;
import org.example.models.enums.commands.MainMenuCommands;
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
        if (controller.playerPassedOut()) {
            ((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner);
            return;
        }
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        }
        else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        }
        else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        }
        else if((matcher = InteractionsWithUserCommands.Trade.getMatcher(input)) != null) {
            System.out.println(controller.trade(
                    matcher.group("username").trim(),
                    matcher.group("type").trim(),
                    matcher.group("item").trim(),
                    matcher.group("amount").trim(),
                    matcher.group("price"),
                    matcher.group("targetItem"),
                    matcher.group("targetAmount")
            ));
        }
        else if (InteractionsWithUserCommands.TradeList.getMatcher(input) != null) {
            System.out.println(controller.tradeList());
        }
        else if ((matcher = InteractionsWithUserCommands.TradeResponse.getMatcher(input)) != null) {
            System.out.println(controller.tradeResponse(
                    matcher.group("response").trim() ,
                    matcher.group("id").trim()
            ));
        }
        else if ((matcher = InteractionsWithUserCommands.TradeHistory.getMatcher(input)) != null) {
            System.out.println(controller.tradeHistory());
        }
        else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
