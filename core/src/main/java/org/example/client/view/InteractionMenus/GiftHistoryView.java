package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.GiftController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.server.models.Relations.Gift;
import org.example.server.models.Stacks;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.enums.Features;
import org.example.server.models.enums.Plants.FruitType;

import java.util.ArrayList;
import java.util.Scanner;

public class GiftHistoryView extends AppMenu {

    private final GiftController controller;
    Gift selectedGift = null;

    private final String username;
    private final ArrayList<Gift> gifts;

    private Table table;
    private ScrollPane scrollPane;

    private final TextButton increaseAmountButton;
    private final TextButton decreaseAmountButton;
    private final TextButton rateButton;
    private final TextButton exitButton;

    private final Label countLabel;

    private int rateNumber = 1;

    private Stage stage;

    public GiftHistoryView(String username) {
        ClientApp.setNonMainMenu(this);
        controller = new GiftController();
        this.username = username;
        gifts = controller.getGiftHistory(username);

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(0, 0);

        increaseAmountButton = new TextButton("+", skin);
        decreaseAmountButton = new TextButton("-", skin);
        rateButton = new TextButton("Rate", skin);
        countLabel = new Label("", skin);

        table = new Table();
        scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        stage = new Stage(new ScreenViewport());

        setListeners();
    }

    private void displayButtons() {
        exitButton.setPosition(0, 0);
        stage.addActor(exitButton);
    }

    private void displayItems() {
        table.clear();

        Table firstRow = new Table();
        Label fromLabel = new Label("From:", skin);
        Label toLabel = new Label("To:", skin);
        Label items1 = new Label("Item:", skin);
        Label rateLabel = new Label("Rate:", skin);

        fromLabel.setColor(Color.BLACK);
        toLabel.setColor(Color.BLACK);
        items1.setColor(Color.BLACK);
        rateLabel.setColor(Color.BLACK);

        firstRow.add(fromLabel).width(200).left();
        firstRow.add(toLabel).width(200).left();
        firstRow.add(items1).width(200).right();
        firstRow.add(rateLabel).width(200).right();
        table.add(firstRow).width(1000).padBottom(5);
        table.row();

        for (Gift gift : gifts) {
            Table row = new Table();
            Label fromLabel1 = new Label(gift.getFrom(), skin);
            Label toLabel1 = new Label(gift.getTo(), skin);
            Label items11 = new Label(gift.getStack().getItem().getName() + "*" + gift.getStack().getQuantity(), skin);
            Label rateLabel1 = new Label("", skin);
            if (gift.getRate() != -1) {
                rateLabel1.setText(gift.getRate());
            }

            fromLabel1.setColor(Color.BLACK);
            toLabel1.setColor(Color.BLACK);
            items11.setColor(Color.BLACK);
            rateLabel1.setColor(Color.BLACK);

            row.add(fromLabel1).width(200).left();
            row.add(toLabel1).width(200).left();
            row.add(items11).width(200).right();
            row.add(rateLabel1).width(200).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            table.add(row).width(800).padBottom(5);
            table.row();
            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    selectedGift = gift;
                }

            });
        }

        scrollPane.setSize(1000, Gdx.graphics.getHeight() - 400);
        scrollPane.setPosition(300, 200);
    }

    private void displayThings() {
        countLabel.setText(Integer.toString(rateNumber));

        increaseAmountButton.setVisible(selectedGift != null);
        decreaseAmountButton.setVisible(selectedGift != null);
        rateButton.setVisible(selectedGift != null);
        countLabel.setVisible(selectedGift != null);

        increaseAmountButton.setPosition(3 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f + 100);
        countLabel.setPosition(3 * Gdx.graphics.getWidth() / 4f + increaseAmountButton.getWidth()/2f, Gdx.graphics.getHeight() / 2f + 50);
        decreaseAmountButton.setPosition(3 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f - 100);

        rateButton.setPosition(3 * Gdx.graphics.getWidth() / 4f + 200, Gdx.graphics.getHeight() / 2f);
    }


    private void displayBackground() {
        stage.addActor(menuBackground);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();
        stage.addActor(exitButton);
        stage.addActor(increaseAmountButton);
        stage.addActor(decreaseAmountButton);
        stage.addActor(rateButton);
        stage.addActor(countLabel);
        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        displayThings();
        Main.getBatch().begin();
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

    private void setListeners() {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exit();
            }
        });

        increaseAmountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                rateNumber = Math.min(5, rateNumber + 1);
            }
        });

        decreaseAmountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                rateNumber = Math.max(1, rateNumber - 1);
            }
        });

        rateButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.rateGift(selectedGift, rateNumber);
            }
        });
    }
}
