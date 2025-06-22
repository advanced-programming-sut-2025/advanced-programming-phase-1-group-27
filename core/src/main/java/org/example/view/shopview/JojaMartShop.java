package org.example.view.shopview;

import org.example.controller.GameMenuController;
import org.example.controller.shopcontroller.JojaMartShopController;
import org.example.models.App;
import org.example.models.Result;
import org.example.models.enums.Menu;
import org.example.models.enums.commands.CheatCommands;
import org.example.models.enums.commands.GameMenuCommands;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.models.enums.commands.ShopCommands;
import org.example.view.AppMenu;
import org.example.view.GameMenuView;

import java.util.Scanner;
import java.util.regex.Matcher;

public class JojaMartShop extends AppMenu {
    private final JojaMartShopController controller;

    public JojaMartShop() {
        controller = new JojaMartShopController(this);
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
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
            return;
        }
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
        } else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
        } else if (ShopCommands.ShowAllProducts.getMatcher(input) != null) {
            System.out.println(controller.showAllProducts());
        } else if ((matcher = ShopCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllAvailableProducts());
        } else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchase(
                    matcher.group("productName").trim(),
                    matcher.group("count").trim()
            ));
        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().inventoryShow());
        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("number").trim())
            ));
        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
            System.out.println(((GameMenuView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
                    matcher.group("itemName").trim(),
                    Integer.parseInt(matcher.group("count"))
            ));
        } else if ((matcher = GameMenuCommands.ShowMoney.getMatcher(input)) != null) {
            GameMenuController gameMenuController = ((GameMenuView) Menu.GameMenu.getMenu()).getController();
            System.out.println(gameMenuController.showMoney());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
