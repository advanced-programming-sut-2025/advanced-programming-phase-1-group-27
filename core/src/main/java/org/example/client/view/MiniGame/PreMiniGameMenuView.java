package org.example.client.view.MiniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.MiniGameController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.common.models.items.ToolType;

import java.util.Scanner;

public class PreMiniGameMenuView extends AppMenu {

    private final MiniGameController controller;
    private final Stage stage;

    private final Label menuTitleLabel;

    private final TextButton startButton;
    private final TextButton backButton;

    private final ToolType fishingPole;

    public PreMiniGameMenuView(ToolType fishingPole) {

        ClientApp.setNonMainMenu(this);
        controller = new MiniGameController(fishingPole);
        this.fishingPole = fishingPole;
        stage = new Stage(new ScreenViewport());

        startButton = new TextButton("Start", skin);
        backButton = new TextButton("Back", skin);
        menuTitleLabel = new Label("Fishing Mini-Game!", skin);

        setListeners();

    }

    private void displayLabels(){

        menuTitleLabel.setPosition(stage.getWidth()/8, 5 * stage.getHeight()/6f);
        menuTitleLabel.setFontScale(3f);
        stage.addActor(menuTitleLabel);

    }

    private void displayButtons(){


        startButton.setWidth(stage.getWidth()/4f);
        backButton.setWidth(stage.getWidth()/4f);

        startButton.setPosition(3 * stage.getWidth()/8f, 3 * stage.getHeight()/6f);
        backButton.setPosition(3 * stage.getWidth()/8f, 2 * stage.getHeight()/6f);

        stage.addActor(startButton);
        stage.addActor(backButton);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);

    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

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

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.startMiniGame();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.backToOutside();
            }
        });

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }


}
