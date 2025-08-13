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
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.common.models.NPCs.Quest;
import org.example.common.models.items.ShopItems;

import java.util.ArrayList;
import java.util.Scanner;

public class QuestNPCMenuView extends AppMenu {

    private final InteractionsWithNPCController controller;

    private final String npcName;
    private final ArrayList<Quest> quests;

    private final GraphicalResult errorLabel;

    private Quest selectedQuest = null;

    private Table table;
    private ScrollPane scrollPane;

    private final TextButton addButton;
    private final TextButton finishButton;
    private final TextButton exitButton;

    private Stage stage;

    public QuestNPCMenuView(String npcName) {
        ClientApp.setNonMainMenu(this);
        controller = new InteractionsWithNPCController();
        this.npcName = npcName;
        quests = controller.getQuests(npcName);

        exitButton = new TextButton("Exit", skin);
        addButton = new TextButton("Add", skin);
        finishButton = new TextButton("Finish", skin);
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

        firstRow.add(requestLabel).width(300).left();
        firstRow.add(rewardLabel).width(300).left();
        firstRow.add(doneLabel).width(200).right();
        table.add(firstRow).width(1000).padBottom(5);
        table.row();

        for (Quest quest : quests) {
            Table row = new Table();
            Label requestLabel1 = new Label(quest.getRequest().getItem().getName() + "\n*" + quest.getRequest().getQuantity(), skin);
            Label rewardLabel1 = new Label("", skin);
            if (quest.getReward().getItem() == ShopItems.RelationLevel) {
                rewardLabel1.setText("+1 relation level");
            } else {
                rewardLabel1.setText(quest.getReward().getItem().getName() + "\n*" + quest.getReward().getQuantity());
            }
            Label isDoneLabel1 = new Label("", skin);

            if (quest.isDone()) {
                isDoneLabel1.setText(quest.getPlayerName());
            } else if (controller.doIHaveThisQuest(quest)) {
                isDoneLabel1.setText("In progress");
            } else {
                isDoneLabel1.setText("");
            }

            requestLabel1.setColor(Color.BLACK);
            rewardLabel1.setColor(Color.BLACK);
            isDoneLabel1.setColor(Color.BLACK);

            row.add(requestLabel1).width(300).left();
            row.add(rewardLabel1).width(300).left();
            row.add(isDoneLabel1).width(200).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            table.add(row).width(1000).padBottom(5);
            table.row();
            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    selectedQuest =  quest;
                }
            });
        }

        scrollPane.setSize(1000, Gdx.graphics.getHeight() - 400);
        scrollPane.setPosition(300, 200);
    }


    private void displayThings() {

        finishButton.setVisible(selectedQuest != null);
        addButton.setVisible(selectedQuest != null);

        finishButton.setPosition(3 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f + 100);
        addButton.setPosition(3 * Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 2f - 100);
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
        stage.addActor(addButton);
        stage.addActor(finishButton);
        stage.addActor(errorLabel.getMessage());
        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float delta) {
        showErrorMessage();
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

        addButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.addQuest(selectedQuest));
            }
        });

        finishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.finish(selectedQuest , npcName));
            }
        });
    }
}
