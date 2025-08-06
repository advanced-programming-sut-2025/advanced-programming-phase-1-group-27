package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class StartTradeView extends AppMenu {
    private final TradeController controller;
    private final String username;

    private final AppMenu lastView;

    private final TextButton yesButton;
    private final TextButton noButton;

    private final Image creamImage;
    private final Image brownImage;

    private final Label label;

    private Stage stage;

    public StartTradeView(String username , AppMenu lastView) {
        controller = new TradeController();
        this.username = username;
        this.lastView = lastView;
        yesButton = new TextButton("Yes" , skin);
        noButton = new TextButton("No" , skin);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        label = new Label(username + " wants to trade with you", skin);
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
        yesButton.setWidth(300);
        noButton.setWidth(300);
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() + 50,  Gdx.graphics.getHeight() / 2f + label.getHeight() + 50);
        yesButton.setPosition(Gdx.graphics.getWidth() / 2f + yesButton.getWidth() - 150, Gdx.graphics.getHeight() / 2f - yesButton.getHeight());
        noButton.setPosition(Gdx.graphics.getWidth() / 2f - noButton.getWidth() - 150 , Gdx.graphics.getHeight() / 2f - noButton.getHeight());
        stage.addActor(label);
        stage.addActor(noButton);
        stage.addActor(yesButton);
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
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.respondToStartTrade(username , true, lastView);
                stage.clear();
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                controller.respondToStartTrade(username , false, lastView);
                stage.clear();
            }
        });
    }
}
