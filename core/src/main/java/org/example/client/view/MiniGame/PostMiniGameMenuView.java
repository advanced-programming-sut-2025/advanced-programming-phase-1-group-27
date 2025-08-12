package org.example.client.view.MiniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.MiniGameController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.server.models.enums.items.FishType;
import org.example.server.models.enums.items.ToolType;

import java.util.Scanner;

public class PostMiniGameMenuView extends AppMenu {

    private final MiniGameController controller;
    private final Stage stage;

    private final Label menuTitleLabel;
    private final Label lostLabel;
    private final Label wonLabel;
    private final Label perfectLabel;
    private final TextButton doneButton;
    private final Image caughtFishImage;

    private final FishType caughtFish;
    private final boolean isPerfect;

    public PostMiniGameMenuView(MiniGameController miniGameController, FishType caughtFish, boolean isPerfect) {

        ClientApp.setNonMainMenu(this);
        controller = miniGameController;
        stage = new Stage(new ScreenViewport());

        doneButton = new TextButton("Done", skin);
        menuTitleLabel = new Label("Fishing Mini-Game!", skin);
        lostLabel = new Label("You Lost ):", skin);
        wonLabel = new Label("You successfully caught x" + controller.getNumberOfCaughtFish()+" "+controller.getCaughtFish().getName(), skin);
        perfectLabel = new Label("In a PERFECT WAYYYY!!!!",skin);
        caughtFishImage = controller.getCaughtFish().getItemImage();

        this.caughtFish = caughtFish;
        this.isPerfect = isPerfect;

        setListeners();

    }

    private void displayLabels(){

        menuTitleLabel.setPosition(stage.getWidth()/8, 5 * stage.getHeight()/6f);
        menuTitleLabel.setFontScale(3f);

        lostLabel.setVisible(caughtFish == null);
        lostLabel.setPosition(Gdx.graphics.getWidth()/4f,4 * stage.getHeight()/6f);
        lostLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        lostLabel.setFontScale(2f);

        wonLabel.setVisible(caughtFish != null);
        wonLabel.setPosition(Gdx.graphics.getWidth()/4f,4 * stage.getHeight()/6f);
        wonLabel.setColor(GameAssetManager.getGameAssetManager().getAcceptColor());
        wonLabel.setFontScale(2f);

        perfectLabel.setVisible(caughtFish != null && isPerfect);
        perfectLabel.setPosition(Gdx.graphics.getWidth()/4f,3 * stage.getHeight()/6f);



        stage.addActor(menuTitleLabel);
        stage.addActor(lostLabel);
        stage.addActor(wonLabel);
        stage.addActor(perfectLabel);

    }

    private void displayButtons(){

        doneButton.setWidth(stage.getWidth()/4f);
        doneButton.setPosition(3 * stage.getWidth()/8f,stage.getHeight()/6f);
        stage.addActor(doneButton);

    }

    private void displayImages(){

        caughtFishImage.setPosition((Gdx.graphics.getWidth()-caughtFishImage.getWidth())/2f,3 * stage.getHeight()/6f);
        caughtFishImage.setVisible(caughtFish != null);

        stage.addActor(caughtFishImage);
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);

        displayLabels();
        displayButtons();
        displayImages();

    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

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

    private void setListeners() {

        doneButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                playClickSound();
//                caughtFishImage.remove();
                controller.backToOutside();
            }
        });

    }

    @Override
    public void executeCommands(Scanner scanner) {

    }


}
