package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.MarriageController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.client.view.OutsideView;
import org.example.common.models.Message;

import java.util.HashMap;
import java.util.Scanner;

public class MarriageRequestView extends AppMenu {
    private final String proposer;

    private final TextButton yesButton;
    private final TextButton noButton;

    private MarriageController marriageController;

    private final Image creamImage;
    private final Image brownImage;

    private final Label label;

    private Stage stage;

    public MarriageRequestView(String proposer) {
        this.proposer = proposer;

        yesButton = new TextButton("Yes" , skin);
        noButton = new TextButton("No" , skin);

        marriageController = new MarriageController();

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                30));
        label = new Label("Do you want to marry " + proposer + "?", skin);
        label.setColor(Color.BLACK);
        label.setFontScale(1.5f);

        setListeners();
    }

    private void setListeners() {
        yesButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                marriageController.accept(proposer);
            }
        });

        noButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                marriageController.decline(proposer);
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
        label.setPosition(Gdx.graphics.getWidth() / 2f - label.getWidth() + 50,  Gdx.graphics.getHeight() / 2f + label.getHeight() + 50);
        yesButton.setPosition(Gdx.graphics.getWidth() / 2f + yesButton.getWidth() - 150, Gdx.graphics.getHeight() / 2f - yesButton.getHeight());
        noButton.setPosition(Gdx.graphics.getWidth() / 2f - noButton.getWidth() - 150 , Gdx.graphics.getHeight() / 2f - noButton.getHeight());
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
