package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.shopControllers.ShopController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.HUDView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;
import org.example.client.view.AppMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class StardropSaloonShop extends AppMenu {
    private final ShopController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image creamImage;
    private final Image brownImage;
    private final Image backgroundImage;

    private final TextButton exitButton;

    private final HUDView hudView;

    private float timer = 0f;


    private ArrayList<Stock> stockItems;
    private Table stockTable;
    private ScrollPane scrollPane;
    private CheckBox showOnlyAvailableCheckBox;

    private Stage stage;

    public StardropSaloonShop() {
        npc = NPCType.Gus;
        controller = new ShopController(this , npc);
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() - (int) npcImage.getHeight() - 20,
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                (int) npcImage.getHeight(),
                30));

        backgroundImage = new Image(GameAssetManager.getGameAssetManager().getStarDropTexture());

        stockItems = ClientApp.getCurrentGame().getShop("stardropSaloon").getStock();

        stockTable = new Table();
        scrollPane = new ScrollPane(stockTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        showOnlyAvailableCheckBox = new CheckBox("Filter", skin);
        showOnlyAvailableCheckBox.setChecked(true);

        exitButton = new TextButton("Exit", skin);
        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

        setListeners();
    }

    private void displayItems() {
        stockItems = ClientApp.getCurrentGame().getShop("stardropSaloon").getStock();
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
            Label priceLabel1 = new Label(stock.getSalePrice() + " G", skin);
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
                     controller.purchase(stock);
                }
            });
        }

        scrollPane.setSize(redAreaWidth, 600);
        scrollPane.setPosition(-120, Gdx.graphics.getHeight()/2f - 150);
    }

    private void displayCheckBox(){
        showOnlyAvailableCheckBox.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight()/2f);
        stage.addActor(showOnlyAvailableCheckBox);
    }

    private void displayButtons(){
        exitButton.setPosition(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight()/2f - 160);
        stage.addActor(exitButton);
    }

    private void setListeners(){
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });

        showOnlyAvailableCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                displayItems();
            }
        });
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
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();
        displayNPC();
        displayCheckBox();
        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float v) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (timer > 2f) {
            timer = 0f;
            displayItems();
        } else {
            timer += v;
        }
        Main.getBatch().begin();
        hudView.displayHUD(v);
        Main.getBatch().end();
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
    }
}
