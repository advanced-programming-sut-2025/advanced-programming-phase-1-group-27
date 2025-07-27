package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.shopControllers.PierreGeneralShopController;
import org.example.common.models.GameAssetManager;
import org.example.server.models.enums.NPCType;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class PierreGeneralShop extends AppMenu {
    private final PierreGeneralShopController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image coinImage;

    private Label moneyLabel = null;

    private Stage stage;

    public PierreGeneralShop() {
        controller = new PierreGeneralShopController(this);
        npc = NPCType.Pierre;
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);
        coinImage = new Image(GameAssetManager.getGameAssetManager().getCoinTexture());

//        moneyLabel = new Label(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()), skin);
        moneyLabel = new Label("1000" , skin);
    }

    private void displayMoney(){
//        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
        moneyLabel = new Label("1000" , skin);
        stage.addActor(moneyLabel);
    }

    private void displayNPC(){
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);

        stage.addActor(npcImage);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        displayNPC();
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
//        } else if (ShopCommands.ShowAllProducts.getMatcher(input) != null) {
//            System.out.println(controller.showAllProducts());
//        } else if (ShopCommands.ShowAllAvailableProducts.getMatcher(input) != null) {
//            System.out.println(controller.showAllAvailableProducts());
//        } else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
//            System.out.println(controller.purchase(
//                    matcher.group("productName").trim(),
//                    matcher.group("count")
//            ));
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
//        } else if ((matcher = GameMenuCommands.ShowMoney.getMatcher(input)) != null) {
//            GameMenuController gameMenuController = ((GameView) Menu.GameMenu.getMenu()).getController();
//            System.out.println(gameMenuController.showMoney());
//        } else {
//            System.out.println(new Result(false, "invalid command!"));
//        }
    }
}
