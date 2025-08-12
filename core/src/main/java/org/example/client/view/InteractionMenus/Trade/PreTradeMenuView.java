package org.example.client.view.InteractionMenus.Trade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.InteractionMenus.InteractionMenu;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.Scanner;

public class PreTradeMenuView extends AppMenu {

    private final TradeController controller;

    private final String targetUsername;

    private final TextButton tradeButton;
    private final TextButton historyButton;
    private final TextButton backButton;

    private final Label tradeMenuLabel;
    private final Label targetPlayerLabel;

    private final ArrayList<Stacks> targetInventory;

    private Stage stage;

    public PreTradeMenuView(String username) {

        controller = new TradeController();
        ClientApp.setNonMainMenu(this);
        targetUsername = username;

        tradeButton = new TextButton("Trade" , skin);
        historyButton = new TextButton("History Trade" , skin);
        backButton = new TextButton("Back" , skin);

        tradeMenuLabel = new Label("Trade Menu", skin);
        targetPlayerLabel = new Label("Target Player: " + username, skin);

        targetInventory = InteractionsWithUserController.getInventory(username);

        setListeners();
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        tradeMenuLabel.setFontScale(3f);
        tradeMenuLabel.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 6f);

        targetPlayerLabel.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 6f);

        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        tradeButton.setWidth(Gdx.graphics.getWidth() / 4f);
        historyButton.setWidth(Gdx.graphics.getWidth() / 4f);

        tradeButton.setPosition((Gdx.graphics.getWidth()-tradeButton.getWidth())/2f, 3 * Gdx.graphics.getHeight() / 6f);
        historyButton.setPosition((Gdx.graphics.getWidth()-historyButton.getWidth())/2f, 2 * Gdx.graphics.getHeight() / 6f);
        backButton.setPosition((Gdx.graphics.getWidth()-backButton.getWidth())/2f, Gdx.graphics.getHeight() / 6f);

        stage.addActor(menuBackground);
        stage.addActor(tradeButton);
        stage.addActor(historyButton);
        stage.addActor(backButton);
        stage.addActor(tradeMenuLabel);
        stage.addActor(targetPlayerLabel);

    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

        tradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.startTrade(targetUsername);
            }
        });

        historyButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.goToTradeHistory(targetUsername);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new InteractionMenu(targetUsername));
            }
        });

    }

    public TradeController getController() {
        return controller;
    }

    public ArrayList<Stacks> getTargetInventory() {
        return targetInventory;
    }
}
