package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class PreTradeMenuView extends AppMenu {
    private final TradeController controller;
    private final String username;

    private final  AppMenu lastView;

    private final TextButton tradeButton;
    private final TextButton historyButton;

    private final Image creamImage;
    private final Image brownImage;

    private final Label label;

    private Stage stage;

    public PreTradeMenuView(String username , AppMenu lastView) {
        controller = new TradeController();
        this.lastView = lastView;
        this.username = username;
        tradeButton = new TextButton("Trade" , skin);
        historyButton = new TextButton("History Trade" , skin);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        label = new Label(username , skin);
        label.setColor(Color.BLACK);
        label.setFontScale(1.5f);
        setListeners();
    }

    private void displayBackground() {
        creamImage.setPosition(0, 0);
        brownImage.setPosition(0, 0);

        stage.addActor(brownImage);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        tradeButton.setWidth(300);
        historyButton.setWidth(300);
        label.setPosition(Gdx.graphics.getWidth() / 2 - label.getWidth() + 50,  Gdx.graphics.getHeight() / 2 + label.getHeight() + 50);
        tradeButton.setPosition(Gdx.graphics.getWidth() / 2 + tradeButton.getWidth() - 150, Gdx.graphics.getHeight() / 2 - tradeButton.getHeight());
        historyButton.setPosition(Gdx.graphics.getWidth() / 2 - historyButton.getWidth() - 150 , Gdx.graphics.getHeight() / 2 - historyButton.getHeight());
        stage.addActor(label);
        stage.addActor(historyButton);
        stage.addActor(tradeButton);
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
                controller.respondToStartTrade(username , true);
                stage.clear();
            }
        });

        historyButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.respondToStartTrade(username , false);
                stage.clear();
            }
        });
    }

    public TradeController getController() {
        return controller;
    }
}
