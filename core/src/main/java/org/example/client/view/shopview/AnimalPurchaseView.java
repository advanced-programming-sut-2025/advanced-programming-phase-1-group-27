package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.ResultController;
import org.example.client.controller.shopControllers.AnimalPurchaseController;
import org.example.client.controller.shopControllers.PurchaseMenuController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.client.view.HUDView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;

import java.util.Scanner;

public class AnimalPurchaseView extends AppMenu {
    private final Stock stock;
    private final AnimalPurchaseController controller;

    private final NPCType npc;

    private final TextButton increaseItemButton;
    private final TextButton decreaseItemButton;
    private final TextButton exitButton;
    private final TextButton purchaseButton;

    private final TextField nameField = new TextField("", skin);
    private final Label priceLabel;
    private final Label quantityLabel;
    private final Label nameLabel;
    private final Label levelLabel;
    private final Label price;
    private final Label quantity;
    private final Label name;
    private final Label level;
    private final Label image;
    private Label numberLabel;
    private Label sumLabel;
    private int number;

    private GraphicalResult error;

    private final HUDView hudView;

    private final Image npcImage;
    private final Image itemImage;
    private final Image creamImage;
    private final Image brownImage;


    private Stage stage;

    public AnimalPurchaseView(Stock stock , NPCType npc , AppMenu shopMenu) {
        this.stock = stock;
        this.npc = npc;

        controller = new AnimalPurchaseController(this , shopMenu);

        nameField.setMessageText("Enter animals name");
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);

        itemImage = new Image(stock.getItem().getTexture());
        itemImage.setScaling(Scaling.fit);
        itemImage.setSize(150, 150);

        priceLabel = new Label(stock.getPrice() + "" , skin);
        quantityLabel = new Label("" , skin);
        if(stock.getQuantity() == -1){
            quantityLabel.setText("unlimited");
        }else if(stock.getQuantity() == 0){
            quantityLabel.setText("not available");
        }else {
            quantityLabel.setText(String.valueOf(stock.getQuantity()));
        }
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

        error = new GraphicalResult();

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

        increaseItemButton = new TextButton("+", skin);
        decreaseItemButton = new TextButton("-", skin);
        exitButton = new TextButton("Exit", skin);
        purchaseButton = new TextButton("Purchase", skin);

        number = 0;
        numberLabel = new Label(number + "", skin);
        sumLabel = new Label("Sum : " + number * stock.getSalePrice(ClientApp.getCurrentGame().getTime().getSeason()), skin);
        numberLabel.setFontScale(2f);
        sumLabel.setFontScale(2f);
        sumLabel.setColor(Color.BLACK);
        numberLabel.setColor(Color.BLACK);

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

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
                controller.exitMenu();
            }
        });

        increaseItemButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                if (number == 1)return;
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
                error.set(controller.purchaseAnimal(stock , number , npc, nameField.getText()));
            }
        });
    }

    private void displayItem(){
        quantityLabel.setText(stock.getQuantity() == -1? "unlimited" : String.valueOf(stock.getQuantity()));
        priceLabel.setText(stock.getSalePrice(ClientApp.getCurrentGame().getTime().getSeason()) + "");

        float width1 = nameLabel.getWidth();
        float width2 = width1 + levelLabel.getWidth();
        float width3 = width2 + quantityLabel.getWidth();

        image.setPosition(Gdx.graphics.getWidth() / 4f - 300 , Gdx.graphics.getHeight() - 100);
        name.setPosition(Gdx.graphics.getWidth() / 4f - 100, Gdx.graphics.getHeight() - 100);
        level.setPosition(Gdx.graphics.getWidth() / 4f + width1 + 100, Gdx.graphics.getHeight() - 100);
        quantity.setPosition(Gdx.graphics.getWidth() / 4f + width2 + 200, Gdx.graphics.getHeight() - 100);
        price.setPosition(Gdx.graphics.getWidth() / 4f + width3 + 450, Gdx.graphics.getHeight() - 100);

        itemImage.setPosition(Gdx.graphics.getWidth() / 4f - 300 , Gdx.graphics.getHeight() - 250);
        nameLabel.setPosition(Gdx.graphics.getWidth() / 4f - 100 , Gdx.graphics.getHeight() - 250);
        levelLabel.setPosition(Gdx.graphics.getWidth()/4f + width1 + 100 , Gdx.graphics.getHeight() - 250);
        quantityLabel.setPosition(Gdx.graphics.getWidth()/4f + width2 + 200 , Gdx.graphics.getHeight() - 230);
        priceLabel.setPosition(Gdx.graphics.getWidth()/4f + width3 + 450, Gdx.graphics.getHeight() - 250);

        error.setPosition(20, 200);

        nameField.setPosition(Gdx.graphics.getWidth() / 4f + width1 + 100 , Gdx.graphics.getHeight() / 2f);
        nameField.setWidth(700);

        stage.addActor(itemImage);
        stage.addActor(priceLabel);
        stage.addActor(quantityLabel);
        stage.addActor(error.getMessage());
        stage.addActor(nameLabel);
        stage.addActor(levelLabel);
        stage.addActor(image);
        stage.addActor(name);
        stage.addActor(level);
        stage.addActor(quantity);
        stage.addActor(price);
        stage.addActor(nameField);
    }

    private void displayButtons(){
        numberLabel.setText(number);
        sumLabel.setText("Sum : " + number * stock.getSalePrice(ClientApp.getCurrentGame().getTime().getSeason()));

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

    public void show() {
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();
        displayNPC();
        displayItem();
    }

    @Override
    public void render(float v) {
        error.update(v);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        displayButtons();
        Main.getBatch().begin();
        hudView.displayOnlyClock();
        Main.getBatch().end();
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
