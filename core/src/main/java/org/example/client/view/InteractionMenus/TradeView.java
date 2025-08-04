package org.example.client.view.InteractionMenus;

import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.Scanner;

public class TradeView extends AppMenu {
    private final TradeController controller;

    private ArrayList<Stacks> selectedCurrent;
    private ArrayList<Stacks> selectedOther;
    private ArrayList<Stacks> othersInventory;

    public TradeView(String starter, String other) {
        controller = new TradeController();
        othersInventory = InteractionsWithUserController.getInventory(
                ClientApp.getLoggedInUser().getUsername().equals(starter)? other : starter
        );
        selectedCurrent = new ArrayList<>();
        selectedOther = new ArrayList<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public TradeController getController() {
        return controller;
    }

    @Override
    public void executeCommands(Scanner scanner) {
//        if (controller.playerPassedOut()) {
//            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
//            return;
//        }
//        String input = scanner.nextLine();
//        Matcher matcher;
//        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
//            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
//        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
//            System.out.println(controller.showCurrentMenu());
//        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
//            System.out.println(controller.exitMenu());
//        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
//        } else if ((matcher = InteractionsWithUserCommands.Trade.getMatcher(input)) != null) {
//            System.out.println(controller.trade(
//                    matcher.group("username").trim(),
//                    matcher.group("type").trim(),
//                    matcher.group("item").trim(),
//                    matcher.group("amount").trim(),
//                    matcher.group("price"),
//                    matcher.group("targetItem"),
//                    matcher.group("targetAmount")
//            ));
//        } else if (InteractionsWithUserCommands.TradeList.getMatcher(input) != null) {
//            System.out.println(controller.tradeList());
//        } else if ((matcher = InteractionsWithUserCommands.TradeResponse.getMatcher(input)) != null) {
//            System.out.println(controller.tradeResponse(
//                    matcher.group("response").trim(),
//                    matcher.group("id").trim()
//            ));
//        } else if ((matcher = InteractionsWithUserCommands.TradeHistory.getMatcher(input)) != null) {
//            System.out.println(controller.tradeHistory());
//        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryShow());
//        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
//                    matcher.group("itemName").trim(),
//                    Integer.parseInt(matcher.group("number").trim())
//            ));
//        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
//                    matcher.group("itemName").trim(),
//                    Integer.parseInt(matcher.group("count"))
//            ));
//        } else {
//            System.out.println(new Result(false, "invalid command!"));
//        }
    }
}
