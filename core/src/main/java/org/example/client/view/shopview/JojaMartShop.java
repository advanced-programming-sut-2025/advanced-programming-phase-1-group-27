package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.shopControllers.JojaMartShopController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;
import org.example.client.view.AppMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class JojaMartShop extends AppMenu {
    private final JojaMartShopController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image coinImage;
    private final Image creamImage;
    private final Image brownImage;
    private final Image backgroundImage;

    private Label moneyLabel;

    private ArrayList<Stock> stockItems;
    private Table stockTable;
    private ScrollPane scrollPane;
    private CheckBox showOnlyAvailableCheckBox;

    private Stage stage;

    public JojaMartShop() {
        controller = new JojaMartShopController(this);
        npc = NPCType.Morris;
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);
        coinImage = new Image(GameAssetManager.getGameAssetManager().getCoinTexture());
        coinImage.setSize(coinImage.getWidth() * 3f, coinImage.getHeight() * 3f);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() - (int) npcImage.getHeight() - 20,
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                (int) npcImage.getHeight(),
                30));

        backgroundImage = new Image(GameAssetManager.getGameAssetManager().getPierresGeneralTexture());

        stockItems = ClientApp.getCurrentGame().getShop("JojaMart").getStock();

        stockTable = new Table();
        scrollPane = new ScrollPane(stockTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        showOnlyAvailableCheckBox = new CheckBox("Filter", skin);
        showOnlyAvailableCheckBox.setChecked(true);
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
    }

    private void displayItems() {
        stockItems = ClientApp.getCurrentGame().getShop("JojaMart").getStock();
        stockTable.clear();

        float redAreaX = 100f;
        float redAreaWidth = Gdx.graphics.getWidth() - (2 * redAreaX);

        Table firstRow = new Table();
        Label nameLabel = new Label("Name", skin);
        Label priceLabel = new Label("Price", skin);
        Label quantityLabel = new Label("Quantity", skin);

        nameLabel.setColor(Color.BLACK);
        priceLabel.setColor(Color.BLACK);
        quantityLabel.setColor(Color.BLACK);

        nameLabel.setFontScale(1.5f);
        priceLabel.setFontScale(1.5f);
        quantityLabel.setFontScale(1.5f);

        firstRow.add(nameLabel).width(700).left();
        firstRow.add(priceLabel).width(300).right();
        firstRow.add(quantityLabel).width(100).right();
        stockTable.add(firstRow).width(redAreaWidth).padBottom(5);
        stockTable.row();
        stockTable.add().colspan(3).height(2).fillX().padBottom(10);
        stockTable.row();

        for (Stock stock : stockItems) {
            if (showOnlyAvailableCheckBox.isChecked() && stock.getQuantity() == 0) continue;

            Table row = new Table();
            Label nameLabel1 = new Label(stock.getItem().getName(), skin);
            Label priceLabel1 = new Label(stock.getPrice() + " G", skin);
            Label countLabel1 = new Label("", skin);

            if(stock.getQuantity() == -1){
                countLabel1.setText("unlimited");
            }else if(stock.getQuantity() == 0){
                countLabel1.setText("not available");
            }else {
                countLabel1.setText(String.valueOf(stock.getQuantity()));
            }

            Color color = stock.getQuantity() > 0 || stock.getQuantity() == -1 ? Color.BLACK : Color.GRAY;
            nameLabel1.setColor(color);
            priceLabel1.setColor(color);
            countLabel1.setColor(color);

            row.add(nameLabel1).pad(10).width(700).left();
            row.add(priceLabel1).pad(10).width(300).right();
            row.add(countLabel1).pad(10).width(100).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            stockTable.add(row).width(redAreaWidth).padBottom(5);
            stockTable.row();

            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    // controller.handlePurchase(stock);
                }
            });
        }

        scrollPane.setSize(redAreaWidth, 600);
        scrollPane.setPosition(-120, Gdx.graphics.getHeight()/2f - 150);

        stage.addActor(scrollPane);
    }

    private void displayMoney(){
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
        moneyLabel.setFontScale(1.5f);
        moneyLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
        coinImage.setPosition(Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50);
        stage.addActor(moneyLabel);
        stage.addActor(coinImage);
    }

    private void displayCheckBox(){
        showOnlyAvailableCheckBox.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight()/2f);
        stage.addActor(showOnlyAvailableCheckBox);
    }

    private void displayNPC(){
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);
        stage.addActor(npcImage);
    }

    private void displayBackground(){
        brownImage.setPosition(0 , 5);
        creamImage.setPosition( 0, npcImage.getHeight() + 20);
        backgroundImage.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() - brownImage.getHeight());
        backgroundImage.setPosition(0, brownImage.getHeight() + 20);

        creamImage.setColor(1f, 1f, 1f, 0.5f);
        brownImage.setColor(1f, 1f, 1f, 1f);

//        stage.addActor(backgroundImage);
        stage.addActor(brownImage);
        stage.addActor(creamImage);
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

        displayBackground();
        displayNPC();
        displayMoney();
        displayItems();
        displayCheckBox();
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
//        } else if ((matcher = ShopCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
//            System.out.println(controller.showAllAvailableProducts());
//        } else if ((matcher = ShopCommands.Purchase.getMatcher(input)) != null) {
//            System.out.println(controller.purchase(
//                    matcher.group("productName").trim(),
//                    matcher.group("count").trim()
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
