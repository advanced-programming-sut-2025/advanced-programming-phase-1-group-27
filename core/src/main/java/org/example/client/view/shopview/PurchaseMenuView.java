package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.shopControllers.PurchaseMenuController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;

import java.util.Scanner;

public class PurchaseMenuView extends AppMenu {
    private final Stock stock;
    private final PurchaseMenuController controller;

    private final NPCType npc;

    private final TextButton increaseItemButton;
    private final TextButton decreaseItemButton;
    private final TextButton exitButton;
    private final TextButton purchaseButton;

    private final com.badlogic.gdx.scenes.scene2d.ui.Label moneyLabel;
    private final com.badlogic.gdx.scenes.scene2d.ui.Label priceLabel;
    private final com.badlogic.gdx.scenes.scene2d.ui.Label quantityLabel;
    private final com.badlogic.gdx.scenes.scene2d.ui.Label nameLabel;
    private final com.badlogic.gdx.scenes.scene2d.ui.Label levelLabel;
    private final Label price;
    private final Label quantity;
    private final Label name;
    private final Label level;
    private final Label image;
    private Label numberLabel;
    private Label sumLabel;
    private int number;

    private final com.badlogic.gdx.scenes.scene2d.ui.Image npcImage;
    private final com.badlogic.gdx.scenes.scene2d.ui.Image itemImage;
    private final Image creamImage;
    private final Image brownImage;
    private final Image coinImage;


    private Stage stage;

    public PurchaseMenuView(Stock stock , NPCType npc , AppMenu shopMenu) {
        this.stock = stock;
        this.npc = npc;

        controller = new PurchaseMenuController(this , shopMenu);

        npcImage = new com.badlogic.gdx.scenes.scene2d.ui.Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);

        itemImage = stock.getItem().getItemImage();
        itemImage.setSize(itemImage.getWidth() * 2.5f, itemImage.getHeight() * 2.5f);


        coinImage = new Image(GameAssetManager.getGameAssetManager().getCoinTexture());
        coinImage.setSize(coinImage.getWidth() * 3f, coinImage.getHeight() * 3f);

        priceLabel = new Label(stock.getPrice() + "" , skin);
        quantityLabel = new Label(stock.getQuantity() + "" , skin);
        nameLabel = new Label(stock.getItem().getName() , skin);
        if(stock.getStackLevel() == null){
            levelLabel = new Label("Basic" , skin);
        }else {
            levelLabel = new Label(stock.getStackLevel().toString() , skin);
        }


        image = new Label("Image" , skin);
        name = new Label("Name" , skin);
        level = new Label("Level" , skin);
        price = new Label("Price" , skin);
        quantity = new Label("Quantity" , skin);

        priceLabel.setFontScale(1.5f);
        quantityLabel.setFontScale(1.5f);
        nameLabel.setFontScale(1.5f);
        levelLabel.setFontScale(1.5f);
        image.setFontScale(1.5f);
        name.setFontScale(1.5f);
        level.setFontScale(1.5f);
        price.setFontScale(1.5f);
        quantity.setFontScale(1.5f);

        nameLabel.setColor(Color.BLACK);
        levelLabel.setColor(Color.BLACK);
        priceLabel.setColor(Color.BLACK);
        quantityLabel.setColor(Color.BLACK);
        image.setColor(Color.BLACK);
        name.setColor(Color.BLACK);
        level.setColor(Color.BLACK);
        price.setColor(Color.BLACK);
        quantity.setColor(Color.BLACK);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() - (int) npcImage.getHeight() - 20,
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                (int) npcImage.getHeight(),
                30));

        moneyLabel = new Label(ClientApp.getCurrentGame().getCurrentPlayer().getMoney() + "" , skin);

        increaseItemButton = new TextButton("+", skin);
        decreaseItemButton = new TextButton("-", skin);
        exitButton = new TextButton("Exit", skin);
        purchaseButton = new TextButton("Purchase", skin);

        number = 0;
        numberLabel = new Label(number + "", skin);
        sumLabel = new Label("Sum : " + number * stock.getSalePrice(), skin);
        numberLabel.setFontScale(2f);
        sumLabel.setFontScale(2f);
        sumLabel.setColor(Color.BLACK);
        numberLabel.setColor(Color.BLACK);

        setListeners();
    }

    private void displayNPC(){
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);
        stage.addActor(npcImage);
    }

    private void displayBackground(){
        brownImage.setPosition(0 , 5);
        creamImage.setPosition( 0, npcImage.getHeight() + 20);

        creamImage.setColor(1f, 1f, 1f, 0.5f);
        brownImage.setColor(1f, 1f, 1f, 1f);

        stage.addActor(brownImage);
        stage.addActor(creamImage);
    }

    private void setListeners(){
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
//                controller.exitMenu();
            }
        });

        increaseItemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                number++;
                if(stock.getQuantity() <= number && stock.getQuantity() != -1){
                    number = stock.getQuantity();
                }
            }
        });

        decreaseItemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                if(number > 0){
                    number--;
                }
            }
        });

        purchaseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
//              controller.purchase();
            }
        });
    }

    private void displayMoney(){
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
        moneyLabel.setFontScale(1.5f);
        moneyLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
        coinImage.setPosition(Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50);
        stage.addActor(moneyLabel);
        stage.addActor(coinImage);
    }

    private void displayItem(){
        quantityLabel.setText(stock.getQuantity() + "");
        priceLabel.setText(stock.getSalePrice() + "");

        float width1 = nameLabel.getWidth();
        float width2 = width1 + levelLabel.getWidth();
        float width3 = width2 + quantityLabel.getWidth();

        image.setPosition(Gdx.graphics.getWidth() / 4f - 300 , Gdx.graphics.getHeight() - 100);
        name.setPosition(Gdx.graphics.getWidth() / 4f - 100, Gdx.graphics.getHeight() - 100);
        level.setPosition(Gdx.graphics.getWidth() / 4f + width1, Gdx.graphics.getHeight() - 100);
        quantity.setPosition(Gdx.graphics.getWidth() / 4f + width2 + 200, Gdx.graphics.getHeight() - 100);
        price.setPosition(Gdx.graphics.getWidth() / 4f + width3 + 400, Gdx.graphics.getHeight() - 100);

        itemImage.setPosition(Gdx.graphics.getWidth() / 4f - 300 , Gdx.graphics.getHeight() - 250);
        nameLabel.setPosition(Gdx.graphics.getWidth() / 4f - 100 , Gdx.graphics.getHeight() - 250);
        levelLabel.setPosition(Gdx.graphics.getWidth()/4f + width1 , Gdx.graphics.getHeight() - 250);
        quantityLabel.setPosition(Gdx.graphics.getWidth()/4f + width2 + 200 , Gdx.graphics.getHeight() - 250);
        priceLabel.setPosition(Gdx.graphics.getWidth()/4f + width3 + 400, Gdx.graphics.getHeight() - 250);

        stage.addActor(itemImage);
        stage.addActor(priceLabel);
        stage.addActor(quantityLabel);
        stage.addActor(nameLabel);
        stage.addActor(levelLabel);
        stage.addActor(image);
        stage.addActor(name);
        stage.addActor(level);
        stage.addActor(quantity);
        stage.addActor(price);
    }

    private void displayButtons(){
        numberLabel.setText(number);
        sumLabel.setText("Sum : " + number * stock.getSalePrice());

        sumLabel.setPosition(Gdx.graphics.getWidth() / 4f + increaseItemButton.getWidth() + decreaseItemButton.getWidth() + 300, Gdx.graphics.getHeight()/2f + 150);
        numberLabel.setPosition(Gdx.graphics.getWidth()/4f + increaseItemButton.getWidth() + 20 , Gdx.graphics.getHeight()/2f + 150);
        increaseItemButton.setPosition(Gdx.graphics.getWidth()/4f , Gdx.graphics.getHeight()/2f + 100);
        decreaseItemButton.setPosition(Gdx.graphics.getWidth()/4f + increaseItemButton.getWidth() + 100, Gdx.graphics.getHeight()/2f + 100);
        exitButton.setPosition(Gdx.graphics.getWidth()/4f ,  Gdx.graphics.getHeight()/2f - 150);
        purchaseButton.setPosition(Gdx.graphics.getWidth()/4f + exitButton.getWidth() + 200 , Gdx.graphics.getHeight()/2f - 150);

        stage.addActor(exitButton);
        stage.addActor(purchaseButton);
        stage.addActor(numberLabel);
        stage.addActor(increaseItemButton);
        stage.addActor(decreaseItemButton);
        stage.addActor(sumLabel);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        displayBackground();
        displayNPC();
        displayMoney();
        displayItem();
        displayButtons();
    }

    @Override
    public void resize(int width, int height) {

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
