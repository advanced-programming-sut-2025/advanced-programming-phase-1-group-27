package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionController;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.MarriageController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;

import java.util.Scanner;

public class InteractionMenu extends AppMenu {

    private final InteractionController controller;
    private final InteractionsWithUserController userController;
    private final MarriageController marriageController;
    private final Image decoration1;
    private final Image decoration2;

    private boolean playersAreClose;

    private final String targetUsername;

    private final TextButton tradeButton;
    private final TextButton giftButton;
    private final TextButton flowerButton;
    private final TextButton marriageButton;
    private final TextButton hugButton;
    private final TextButton backButton;

    private final Label menuLabel;
    private final Label targetPlayerLabel;

    private final GraphicalResult errorLabel;

    private Stage stage;

    public InteractionMenu(String username) {
        userController = new InteractionsWithUserController();
        controller = new InteractionController();
        marriageController = new MarriageController();
        ClientApp.setNonMainMenu(this);
        targetUsername = username;
        playersAreClose = false;
        decoration1 = GameAssetManager.getGameAssetManager().getInteractionMenuDecoration1();
        decoration2 = GameAssetManager.getGameAssetManager().getInteractionMenuDecoration2();

        errorLabel = new GraphicalResult();

        tradeButton = new TextButton("Trade" , skin);
        giftButton = new TextButton("Gift" , skin);
        flowerButton = new TextButton("Flower" , skin);
        marriageButton = new TextButton("Marriage" , skin);
        hugButton = new TextButton("Hug" , skin);
        backButton = new TextButton("Back" , skin);

        menuLabel = new Label("Interaction Menu", skin);
        targetPlayerLabel = new Label("Target Player: " + username, skin);

        setListeners();
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - 175, Gdx.graphics.getHeight() - 40);
    }

    private void updateButtons(){

        hugButton.setColor(hugButton.getColor().r,hugButton.getColor().g,hugButton.getColor().b,
                (playersAreClose ? 1.0f : 0.3f));

        flowerButton.setColor(flowerButton.getColor().r,flowerButton.getColor().g,flowerButton.getColor().b,
                (playersAreClose ? 1.0f : 0.3f));

        hugButton.setDisabled(!playersAreClose);
        flowerButton.setDisabled(!playersAreClose);

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
        hugButton.setWidth(Gdx.graphics.getWidth() / 4f);
        marriageButton.setWidth(Gdx.graphics.getWidth() / 4f);


        tradeButton.setPosition(4*(Gdx.graphics.getWidth()-tradeButton.getWidth())/5f, 15 * Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);
        giftButton.setPosition(4*(Gdx.graphics.getWidth()-giftButton.getWidth())/5f, 13 * Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);
        flowerButton.setPosition(4*(Gdx.graphics.getWidth()-flowerButton.getWidth())/5f, 11 * Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);
        hugButton.setPosition(4*(Gdx.graphics.getWidth()-flowerButton.getWidth())/5f, 9 * Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);
        marriageButton.setPosition(4*(Gdx.graphics.getWidth()-marriageButton.getWidth())/5f, 7 * Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);
        backButton.setPosition(4*(Gdx.graphics.getWidth()-backButton.getWidth())/5f, 5*Gdx.graphics.getHeight() / 18f - tradeButton.getHeight()/2f);

        decoration1.setSize(252,252);
        decoration2.setSize(252,252);
        decoration1.setPosition(200,250);
        decoration2.setPosition(500,300);


        stage.addActor(menuBackground);
        stage.addActor(tradeButton);
        stage.addActor(giftButton);
        stage.addActor(flowerButton);
        stage.addActor(marriageButton);
        stage.addActor(hugButton);
        stage.addActor(backButton);
        stage.addActor(menuLabel);
        stage.addActor(targetPlayerLabel);
        stage.addActor(decoration1);
        stage.addActor(decoration2);
        stage.addActor(errorLabel.getMessage());

    }

    @Override
    public void render(float delta) {

        playersAreClose = controller.checkIfPlayersAreClose(targetUsername);
        errorLabel.update(delta);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        showErrorMessage();
        updateButtons();
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
                controller.openTradeMenu(targetUsername);
            }
        });

        giftButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
                controller.openGiftMenu(targetUsername);
            }
        });

        marriageButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if ( !playersAreClose ) return;
                playClickSound();
                errorLabel.set(marriageController.askMarriage(targetUsername));
            }
        });

        hugButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if ( !playersAreClose ) return;
                playClickSound();
                errorLabel.set(userController.hug(targetUsername));
            }
        });

        flowerButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if ( !playersAreClose ) return;
                playClickSound();
                errorLabel.set(userController.flower(targetUsername));
            }
        });


    }

    public InteractionController getController() {
        return controller;
    }

}
