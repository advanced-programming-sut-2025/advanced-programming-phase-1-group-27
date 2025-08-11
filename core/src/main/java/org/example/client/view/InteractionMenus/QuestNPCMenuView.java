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
import org.example.common.models.GraphicalResult;
import org.example.server.models.NPCs.Quest;
import org.example.server.models.Relations.Gift;

import java.util.ArrayList;
import java.util.Scanner;

public class QuestNPCMenuView extends AppMenu {

    private final GiftController controller;
    Gift selectedGift = null;

    private final String npcName;
    private final ArrayList<Quest> quests;

    private final GraphicalResult errorLabel;

    private Table table;
    private ScrollPane scrollPane;

    private final TextButton exitButton;

    private Stage stage;

    public QuestNPCMenuView(String npcName) {
        ClientApp.setNonMainMenu(this);
        controller = new GiftController();
        this.npcName = npcName;
//        gifts = controller.getGiftHistory(username);
        quests = new ArrayList<>();

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(0, 0);

        table = new Table();
        scrollPane = new ScrollPane(table, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        errorLabel = new GraphicalResult("");

        stage = new Stage(new ScreenViewport());

        setListeners();
    }

    private void displayButtons() {
        exitButton.setPosition(0, 0);
        stage.addActor(exitButton);
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - 175, Gdx.graphics.getHeight() - 40);
    }


    private void displayItems() {
        table.clear();

        Table firstRow = new Table();
        Label requestLabel = new Label("Request:", skin);
        Label rewardLabel = new Label("Reward:", skin);
        Label doneLabel = new Label("Done?", skin);

        requestLabel.setColor(Color.BLACK);
        rewardLabel.setColor(Color.BLACK);
        doneLabel.setColor(Color.BLACK);

        firstRow.add(requestLabel).width(200).left();
        firstRow.add(rewardLabel).width(200).left();
        firstRow.add(doneLabel).width(200).right();
        table.add(firstRow).width(800).padBottom(5);
        table.row();

        for (Quest quest : quests) {
//            Table row = new Table();
//            Label requestLabel1 = new Label(gift.getFrom(), skin);
//            Label rewardLabel1 = new Label(gift.getTo(), skin);
//            Label isDoneLabel1 = new Label(gift.getStack().getItem().getName() + "*" + gift.getStack().getQuantity(), skin);
//            Label rateLabel1 = new Label("", skin);
//            if (gift.getRate() != -1) {
//                rateLabel1.setText(gift.getRate());
//            }
//
//            requestLabel1.setColor(Color.BLACK);
//            rewardLabel1.setColor(Color.BLACK);
//            isDoneLabel1.setColor(Color.BLACK);
//
//            row.add(requestLabel1).width(200).left();
//            row.add(rewardLabel1).width(200).left();
//            row.add(isDoneLabel1).width(200).right();
//
//            float itemHeight = 50f;
//            row.setHeight(itemHeight);
//            table.add(row).width(800).padBottom(5);
//            table.row();
//            row.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    playClickSound();
//                    selectedGift = gift;
//                }
//
//            });
        }

        scrollPane.setSize(1000, Gdx.graphics.getHeight() - 400);
        scrollPane.setPosition(300, 200);
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
        stage.addActor(errorLabel.getMessage());
        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float delta) {
        showErrorMessage();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
    }
}
