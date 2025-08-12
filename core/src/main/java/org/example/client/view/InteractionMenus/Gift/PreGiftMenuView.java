package org.example.client.view.InteractionMenus.Gift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.GiftController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.InteractionMenus.InteractionMenu;
import org.example.common.models.GraphicalResult;

import java.util.Scanner;

public class PreGiftMenuView extends AppMenu {

    private final GiftController controller;

    private final String targetUsername;

    private final TextButton giftButton;
    private final TextButton historyButton;
    private final TextButton backButton;

    private final Label giftMenuLabel;
    private final Label targetPlayerLabel;

    private final GraphicalResult errorLabel;

    private Stage stage;

    public PreGiftMenuView(String username) {

        controller = new GiftController();
        ClientApp.setNonMainMenu(this);
        targetUsername = username;
        errorLabel = new GraphicalResult();
        giftButton = new TextButton("Gift" , skin);
        historyButton = new TextButton("History Gift" , skin);
        backButton = new TextButton("Back" , skin);

        giftMenuLabel = new Label("Gift Menu", skin);
        targetPlayerLabel = new Label("Target Player: " + username, skin);


        setListeners();
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - 175, Gdx.graphics.getHeight() - 40);
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        giftMenuLabel.setFontScale(3f);
        giftMenuLabel.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 6f);

        targetPlayerLabel.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 6f);

        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        giftButton.setWidth(Gdx.graphics.getWidth() / 4f);
        historyButton.setWidth(Gdx.graphics.getWidth() / 4f);

        giftButton.setPosition((Gdx.graphics.getWidth()-giftButton.getWidth())/2f, 3 * Gdx.graphics.getHeight() / 6f);
        historyButton.setPosition((Gdx.graphics.getWidth()-historyButton.getWidth())/2f, 2 * Gdx.graphics.getHeight() / 6f);
        backButton.setPosition((Gdx.graphics.getWidth()-backButton.getWidth())/2f, Gdx.graphics.getHeight() / 6f);

        stage.addActor(menuBackground);
        stage.addActor(giftButton);
        stage.addActor(historyButton);
        stage.addActor(backButton);
        stage.addActor(giftMenuLabel);
        stage.addActor(targetPlayerLabel);
        stage.addActor(errorLabel.getMessage());

    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        showErrorMessage();
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

        giftButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.openGiftMenu(targetUsername);
                GraphicalResult openGiftMenuResult = controller.openGiftMenu(targetUsername);
                if (openGiftMenuResult.hasError() ){
                    errorLabel.set(openGiftMenuResult);
                }
            }
        });

        historyButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.openGiftHistoryMenu(targetUsername);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new InteractionMenu(targetUsername));
            }
        });

    }

    public GiftController getController() {
        return controller;
    }

}
