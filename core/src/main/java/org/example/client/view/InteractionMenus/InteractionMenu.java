package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionController;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.OutsideView;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.Scanner;

public class InteractionMenu extends AppMenu {

    private final InteractionController controller;

    private final String targetUsername;

    private final TextButton tradeButton;
    private final TextButton giftButton;
    private final TextButton flowerButton;
    private final TextButton marriageButton;
    private final TextButton backButton;

    private final Label menuLabel;
    private final Label targetPlayerLabel;

    private Stage stage;

    public InteractionMenu(String username) {

        controller = new InteractionController();
        ClientApp.setNonMainMenu(this);
        targetUsername = username;

        tradeButton = new TextButton("Trade" , skin);
        giftButton = new TextButton("Gift" , skin);
        flowerButton = new TextButton("Flower" , skin);
        marriageButton = new TextButton("Marriage" , skin);
        backButton = new TextButton("Back" , skin);

        menuLabel = new Label("Interaction Menu", skin);
        targetPlayerLabel = new Label("Target Player: " + username, skin);

        setListeners();
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        menuLabel.setFontScale(3f);
        menuLabel.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 6f);

        targetPlayerLabel.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 6f);

        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        tradeButton.setWidth(Gdx.graphics.getWidth() / 4f);
        giftButton.setWidth(Gdx.graphics.getWidth() / 4f);
        flowerButton.setWidth(Gdx.graphics.getWidth() / 4f);
        marriageButton.setWidth(Gdx.graphics.getWidth() / 4f);


        tradeButton.setPosition(4*(Gdx.graphics.getWidth()-tradeButton.getWidth())/5f, 5 * Gdx.graphics.getHeight() / 6f - tradeButton.getHeight()/2f);
        giftButton.setPosition(4*(Gdx.graphics.getWidth()-giftButton.getWidth())/5f, 4 * Gdx.graphics.getHeight() / 6f - tradeButton.getHeight()/2f);
        flowerButton.setPosition(4*(Gdx.graphics.getWidth()-flowerButton.getWidth())/5f, 3 * Gdx.graphics.getHeight() / 6f - tradeButton.getHeight()/2f);
        marriageButton.setPosition(4*(Gdx.graphics.getWidth()-marriageButton.getWidth())/5f, 2 * Gdx.graphics.getHeight() / 6f - tradeButton.getHeight()/2f);
        backButton.setPosition(4*(Gdx.graphics.getWidth()-backButton.getWidth())/5f, Gdx.graphics.getHeight() / 6f - tradeButton.getHeight()/2f);

        stage.addActor(menuBackground);
        stage.addActor(tradeButton);
        stage.addActor(giftButton);
        stage.addActor(flowerButton);
        stage.addActor(marriageButton);
        stage.addActor(backButton);
        stage.addActor(menuLabel);
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

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                Main.getMain().getScreen().dispose();
                OutsideView newOutsideView = new OutsideView();
                ClientApp.setCurrentMenu(newOutsideView);
                Main.getMain().setScreen(newOutsideView);
                ClientApp.setNonMainMenu(null);
            }
        });

        tradeButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.openTradeMenu2(targetUsername);

            }
        });


    }

    public InteractionController getController() {
        return controller;
    }

}
