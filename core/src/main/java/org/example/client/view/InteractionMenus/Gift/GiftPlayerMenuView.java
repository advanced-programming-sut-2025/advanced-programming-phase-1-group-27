package org.example.client.view.InteractionMenus.Gift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.GiftController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Math.max;

public class GiftPlayerMenuView extends AppMenu {

    private final GiftController controller;

    private final String starter;
    private final String other;

    private final HashMap<Stacks, Label> onScreenItemsQuantity;
    private final Label itemCountLabel;

    private final Image inventoryBackground;
    private final Image selectBox;
    private final TextButton increaseAmountButton;
    private final TextButton decreaseAmountButton;

    private final TextButton backButton;
    private final TextButton sendGiftButton;

    private final Stage stage;


    private final ArrayList<Stacks> onScreenItems = new ArrayList<>();

    private int itemCount;
    private int rowNum;

    private Integer selectItemIndex;

    public GiftPlayerMenuView(String starter, String other) {

        controller = new GiftController();
        ClientApp.setNonMainMenu(this);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        itemCount = 1;
        rowNum = 0;
        selectItemIndex = null;


        this.starter = starter;
        this.other = other;

        increaseAmountButton = new TextButton("+", skin);
        decreaseAmountButton = new TextButton("-", skin);


        backButton = new TextButton("Back", skin);
        sendGiftButton = new TextButton("Send Gift", skin);

        inventoryBackground = GameAssetManager.getGameAssetManager().getGiftPlayerMenuBackground();
        selectBox = GameAssetManager.getGameAssetManager().getSelectSlot();
        selectBox.setVisible(false);


        onScreenItemsQuantity = new HashMap<>();

        for (Stacks slot : ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems()) {
            addToScreen(slot);
        }

        itemCountLabel = new Label(Integer.toString(itemCount), skin);
        setListeners();
    }

    private void displayInventory() {
        synchronized (onScreenItems) {
            for (Stacks image : onScreenItems) {
                image.getItem().getItemImage().toFront();
                image.getItem().getItemImage().setVisible(false);
            }

            for (int i = 0; i < Math.min(onScreenItems.size() - 12 * rowNum, 48); i++) {
                onScreenItems.get(i + 12 * rowNum).getItem().getItemImage().setPosition(520 + 64 * (i % 12), 742 - 67 * (i / 12));
                onScreenItems.get(i + 12 * rowNum).getItem().getItemImage().setVisible(true);
            }

            if (selectItemIndex != null && 12 * rowNum <= selectItemIndex && selectItemIndex < 12 * (rowNum + 4)) {
                selectBox.setVisible(true);
                selectBox.setPosition(onScreenItems.get(selectItemIndex).getItem().getItemImage().getX() - 5, onScreenItems.get(selectItemIndex).getItem().getItemImage().getY() - 10);
            } else {
                selectBox.setVisible(false);
            }
        }
    }

    private void displayItemQuantity() {
        synchronized (onScreenItems) {
            for (Stacks stacks : onScreenItems) {
                Label label = onScreenItemsQuantity.get(stacks);
                if (stacks.getItem().getItemImage().isVisible()) {
                    label.setVisible(true);
                    label.setPosition(stacks.getItem().getItemImage().getX() + stacks.getItem().getItemImage().getWidth() - 15, stacks.getItem().getItemImage().getY() + stacks.getItem().getItemImage().getHeight() - 25);
                    label.toFront();
                } else {
                    label.setVisible(false);
                }

            }
        }
    }

    private void displayButtons() {

        backButton.setVisible(true);
        sendGiftButton.setVisible(selectItemIndex != null);
        increaseAmountButton.setVisible(selectItemIndex != null);
        decreaseAmountButton.setVisible(selectItemIndex != null);


        backButton.setWidth(sendGiftButton.getWidth());

        sendGiftButton.setPosition(inventoryBackground.getX() + 100, inventoryBackground.getY() - sendGiftButton.getHeight() - 50);
        backButton.setPosition(sendGiftButton.getX() + sendGiftButton.getWidth() + 100, inventoryBackground.getY() - backButton.getHeight() - 50);


        increaseAmountButton.setPosition(inventoryBackground.getX() + inventoryBackground.getWidth() + 40, itemCountLabel.getY() + itemCountLabel.getHeight() + 20);
        decreaseAmountButton.setPosition(inventoryBackground.getX() + inventoryBackground.getWidth() + 40, itemCountLabel.getY() - decreaseAmountButton.getHeight() - 20);

    }

    private void displayLabels() {

        itemCountLabel.setText(Integer.toString(itemCount));


        itemCountLabel.setVisible(selectItemIndex != null);

        itemCountLabel.setPosition(inventoryBackground.getX() + inventoryBackground.getWidth() + 80, inventoryBackground.getY() + inventoryBackground.getHeight() / 2f - itemCountLabel.getHeight() / 2f);

    }

    @Override
    public void show() {

        inventoryBackground.setPosition((Gdx.graphics.getWidth() - inventoryBackground.getWidth()) / 2f, 2 * (Gdx.graphics.getHeight() - inventoryBackground.getHeight()) / 3f);


        stage.addActor(menuBackground);
        stage.addActor(increaseAmountButton);
        stage.addActor(decreaseAmountButton);
        stage.addActor(backButton);
        stage.addActor(sendGiftButton);
        stage.addActor(itemCountLabel);
        stage.addActor(inventoryBackground);
        stage.addActor(selectBox);

    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        displayInventory();
        displayItemQuantity();
        displayLabels();
        displayButtons();
        stage.draw();

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

    private void setListeners() {


        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (keycode == Input.Keys.DOWN) {
                    if (onScreenItems.size() - (12 * rowNum) > 48) {
                        rowNum++;
                    }
                } else if (keycode == Input.Keys.UP) {
                    rowNum = Math.max(0, rowNum - 1);
                }
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                for (int i = 0; i < 48; i++) {
                    if (520 + (i % 12) * 64 < x && x < 520 + (i % 12) * 64 + 64) {
                        if (743 - (i / 12) * 66 < y && y < 743 - (i / 12) * 66 + 66) {

                            if (selectItemIndex != null && selectItemIndex == (rowNum * 12 + i)) {
                                selectItemIndex = null;
                            } else {
                                selectItemIndex = rowNum * 12 + i;
                                itemCount = 1;
                            }

                            return true;
                        }

                    }

                }


                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {


                if (amountY > 0) {
                    if (onScreenItems.size() - (12 * rowNum) > 48) {
                        rowNum++;
                        return true;
                    }
                } else {
                    rowNum = Math.max(0, rowNum - 1);
                    return true;
                }

                return false;

            }

        });


        increaseAmountButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                itemCount = Math.min(onScreenItems.get(selectItemIndex).getQuantity(), itemCount + 1);

            }

        });

        decreaseAmountButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                itemCount = Math.max(1, itemCount - 1);

            }

        });

        sendGiftButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO PARSA: starter - other - onScreenItems.get(selectItemIndex) - itemCount
                controller.gift(other , onScreenItems.get(selectItemIndex), itemCount);
            }
        });

        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGiftMenuView(other));

            }

        });

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void addToScreen(Stacks item) {
        Image itemImage = item.getItem().getItemImage();
        itemImage.setSize(48, 48);
        stage.addActor(itemImage);
        onScreenItems.add(item);

        Label quantityLabel = new Label(Integer.toString(item.getQuantity()), skin);
        quantityLabel.setVisible(false);
        quantityLabel.setColor(Color.RED);
        quantityLabel.setFontScale(0.7f);
        stage.addActor(quantityLabel);
        onScreenItemsQuantity.put(item, quantityLabel);
    }


}
