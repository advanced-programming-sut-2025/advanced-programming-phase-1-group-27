package org.example.client.view.shopview;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import org.example.client.controller.shopControllers.MarnieRanchController;
import org.example.common.models.GameAssetManager;
import org.example.server.models.enums.NPCType;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class MarnieRanch extends AppMenu {
    private final MarnieRanchController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image coinImage;

    private final Label moneyLabel = null;

    private Stage stage;

    public MarnieRanch() {
        controller = new MarnieRanchController(this);
        npc = NPCType.Marnie;
        npcImage = new Image(new Texture(npc.getAddress()));
        coinImage = new Image(GameAssetManager.getGameAssetManager().getCoinTexture());

//        moneyLabel = new Label(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()), skin);

    }

    private void displayMoney(){
//        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));

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
//        if (controller.playerPassedOut()) {
//            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
//            return;
//        }
//        String input = scanner.nextLine().trim();
//        Matcher matcher;
//        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
//            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
//        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
//            System.out.println(controller.showCurrentMenu());
//        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
//            System.out.println(controller.exitMenu());
//        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
//        } else if (GameMenuCommands.NextTurn.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
//        } else if ((matcher = ShopCommands.BuyAnimal.getMatcher(input)) != null) {
//            System.out.println(controller.buyAnimal(matcher.group("animal"), matcher.group("name")));
//        } else if ((matcher = ShopCommands.ShowAllProducts.getMatcher(input)) != null) {
//            System.out.println(controller.showAllProducts());
//        } else if ((matcher = ShopCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
//            System.out.println(controller.showAllAvailableProducts());
//        } else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
//            System.out.println(controller.purchase(
//                    matcher.group("productName").trim(),
//                    matcher.group("count").trim()
//            ));
//        } else if ((matcher = GameMenuCommands.ShowMoney.getMatcher(input)) != null) {
//            GameMenuController gameMenuController = ((GameView) Menu.GameMenu.getMenu()).getController();
//            System.out.println(gameMenuController.showMoney());
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
//            System.out.println("Invalid Command.");
//        }
    }
}
