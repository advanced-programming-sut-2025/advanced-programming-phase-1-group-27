package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.VoteController;
import org.example.client.model.RoundedRectangleTexture;

import java.util.Scanner;

public class VoteView extends AppMenu {
    private final VoteController controller;
    private final String voteMode;
    private final String username;

    private final TextButton yesButton;
    private final TextButton noButton;

    private final Image creamImage;
    private final Image brownImage;

    private final Label label;

    private Stage stage;

    public VoteView(String voteMode, String username) {
        this.voteMode = voteMode;
        this.username = username;

        controller = new VoteController();

        yesButton = new TextButton("Yes", skin);
        noButton = new TextButton("No", skin);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));
        if (voteMode.equals("askToTerminate")) {
            label = new Label("Do you want to terminate the game?", skin);
        } else {
            label = new Label("Do you want to kick " + username + "?", skin);
        }
        label.setColor(Color.BLACK);
        label.setFontScale(1.5f);

        setListeners();
    }

    private void setListeners() {
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.vote(voteMode, username, true);
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.vote(voteMode, username, false);
            }
        });
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
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() + 50, Gdx.graphics.getHeight() / 2f + label.getHeight() + 50);
        yesButton.setPosition(Gdx.graphics.getWidth() / 2f + yesButton.getWidth() - 150, Gdx.graphics.getHeight() / 2f - yesButton.getHeight());
        noButton.setPosition(Gdx.graphics.getWidth() / 2f - noButton.getWidth() - 150, Gdx.graphics.getHeight() / 2f - noButton.getHeight());
        stage.addActor(label);
        stage.addActor(noButton);
        stage.addActor(yesButton);
    }

    @Override
    public void render(float v) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
