package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.LoadGameMenuController;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class LoadGameMenuView extends AppMenu {
    private final TextButton backButton;

    private final LoadGameMenuController controller;

    private final int lobbyId;

    private final Image creamImage;
    private final Image brownImage;

    private final Label label;

    private Stage stage;

    public LoadGameMenuView(int lobbyId) {
        this.lobbyId = lobbyId;
        controller = new LoadGameMenuController(this);
        backButton = new TextButton("Back", skin);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        label = new Label("Waiting for others to join the game", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(1.5f);

        setListeners();
    }

    private void setListeners() {
        backButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               playClickSound();
               controller.exitMenu();
           }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        backButton.setWidth(300);
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() + 50,  Gdx.graphics.getHeight() / 2f + label.getHeight() + 50);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - 2, Gdx.graphics.getHeight() / 2f - backButton.getHeight());
        stage.addActor(label);
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    private void displayBackground() {
        creamImage.setPosition(0, 0);
        brownImage.setPosition(0, 0);

        stage.addActor(brownImage);
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

    public int getLobbyId() {
        return lobbyId;
    }
}
