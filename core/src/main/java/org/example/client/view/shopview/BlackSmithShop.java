package org.example.client.view.shopview;

import org.example.models.enums.commands.*;
import org.example.server.controller.GameMenuController;
import org.example.server.controller.ToolController;
import org.example.server.controller.shopcontroller.BlackSmithShopController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.client.view.AppMenu;
import org.example.client.view.GameView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BlackSmithShop extends AppMenu {
    private final BlackSmithShopController controller;

    public BlackSmithShop() {
        controller = new BlackSmithShopController(this);
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

    @Override
    public void executeCommands(Scanner scanner) {
        if (controller.playerPassedOut()) {
            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if ((matcher = MainMenuCommands.ShowCurrentMenu.getMatcher(input)) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = MainMenuCommands.ExitMenu.getMatcher(input)) != null) {
            System.out.println(controller.exitMenu());
        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
        } else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
        } else if ((matcher = ToolCommands.ToolsUpgrade.getMatcher(input)) != null) {
            System.out.println(controller.upgradeTool(
                    matcher.group("toolName").trim()
            ));
        } else if ((matcher = ShopCommands.ShowAllProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllProducts());
        } else if ((matcher = ShopCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllAvailableProducts());
        } else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("productName").trim(),
                    matcher.group("count").trim()
            ));
        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        } else if ((matcher = GameMenuCommands.ShowMoney.getMatcher(input)) != null) {
            GameMenuController gameMenuController = ((GameView) Menu.GameMenu.getMenu()).getController();
            System.out.println(gameMenuController.showMoney());
        } else if ((matcher = ToolCommands.ToolsShowAvailable.getMatcher(input)) != null) {
            ToolController toolController = new ToolController();
            System.out.println(toolController.showAvailableTools());
        } else if ((matcher = ToolCommands.ToolsShowCurrent.getMatcher(input)) != null) {
            ToolController toolController = new ToolController();
            System.out.println(toolController.showCurrentTool());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
