package org.example.client.view.MiniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.MiniGameController;
import org.example.client.model.ClientApp;
import org.example.client.model.enums.FishMovementType;
import org.example.client.view.AppMenu;
import org.example.client.model.GameAssetManager;
import org.example.common.models.items.FishType;

import java.util.Random;
import java.util.Scanner;

public class MiniGameView extends AppMenu {

    private final MiniGameController controller;
    private final Stage stage;

    private final Label menuTitleLabel;
    private final Image miniGameBackground;
    private final Image greenBar;
    private Image fishIcon;
    private final Image bar;
    private final Image progressBar;
    private final Image crownImage;
    private final Image sonarImage;

    private final FishType caughtFish;

    private final int greenBarDY = 5;
    private float barY;
    private float fishDY;
    private float fishY;
    private float timer;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean sonarActivated;


    public MiniGameView(MiniGameController miniGameController) {

        ClientApp.setNonMainMenu(this);
        controller = miniGameController;
        caughtFish = controller.getCaughtFish();
        stage = new Stage(new ScreenViewport());

        controller.setMiniGameView(this);

        menuTitleLabel = new Label("Fishing Mini-Game!", skin);
        miniGameBackground = GameAssetManager.getGameAssetManager().getFishingMiniGameBackground();
        greenBar = GameAssetManager.getGameAssetManager().getFishingGreenBar();
        fishIcon = GameAssetManager.getGameAssetManager().getFishIcon();
        bar = GameAssetManager.getGameAssetManager().getFishingBar();
        progressBar = GameAssetManager.getGameAssetManager().getFishingProgressBar();
        crownImage = GameAssetManager.getGameAssetManager().getCrown();
        sonarImage = GameAssetManager.getGameAssetManager().getSonarBobberWindow();
        barY = 126 + new Random().nextInt(0,979-(int)greenBar.getHeight()-126);
        fishY = barY +  (greenBar.getHeight()-fishIcon.getHeight())/2f;
        fishDY = 0;
        sonarActivated = false;

        setListeners();

    }

    private void displayGameBackground(){



        miniGameBackground.setSize(miniGameBackground.getWidth()*2, miniGameBackground.getHeight()*2);
        miniGameBackground.setPosition((stage.getWidth()-miniGameBackground.getWidth())/2f,
                (stage.getHeight()- miniGameBackground.getHeight())/2f);

        bar.setHeight(miniGameBackground.getHeight());
        bar.setPosition(miniGameBackground.getX() + miniGameBackground.getWidth() + 50,
                miniGameBackground.getY() );

        stage.addActor(miniGameBackground);
        stage.addActor(bar);

    }

    private void displayGreenBar(){

        greenBar.setWidth(54);
        greenBar.setX(890);
        stage.addActor(greenBar);

    }

    private void updateGreenBar(){

        if (upPressed) {
            barY = Math.min(barY + greenBarDY, 979 - greenBar.getHeight());
        }
        if (downPressed) {
            barY = Math.max(barY - greenBarDY, 126);
        }

        greenBar.setY(barY);
    }

    private void displayFishIcon(){

        fishIcon.setX(greenBar.getX() + (greenBar.getWidth()-fishIcon.getWidth())/2f);
        stage.addActor(fishIcon);

    }

    private void updateFish(){

        if ( controller.getCaughtFishMovement() == FishMovementType.SINKER) {
            if ( timer <= 0.25f ){
                fishY += fishDY;
            }
        }
        else{
            fishY += fishDY;
        }

        fishIcon.setY(fishY);
        fishIcon.toFront();


    }

    private void displayProgressBar(){

        progressBar.setPosition(bar.getX()+11,bar.getY()+9);
        progressBar.setHeight(880 * controller.getProgress()/100);
        stage.addActor(progressBar);

    }

    private void updateProgressBar(){
        progressBar.setHeight(Math.min(880,880 * controller.getProgress()/100));
        progressBar.setColor(1-controller.getProgress()/100,controller.getProgress()/100,0,1);
    }

    private void displayCrown(){

        if (caughtFish.isLegendary() ){
            stage.addActor(crownImage);
        }

    }

    private void updateCrown(){

        crownImage.setPosition(890, fishIcon.getY()+fishIcon.getHeight());

    }

    private void displaySonar(){

        sonarImage.setColor(sonarImage.getColor().r,sonarImage.getColor().g,sonarImage.getColor().b,0.5f);
        sonarImage.setVisible(sonarActivated);
        stage.addActor(sonarImage);

    }

    private void updateSonar(){

        sonarImage.setVisible(sonarActivated);
        sonarImage.setPosition(fishIcon.getX()-40, fishIcon.getY()-15);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);

        displayGameBackground();
        displayGreenBar();
        displayProgressBar();
        displaySonar();
        displayFishIcon();
        displayCrown();

    }

    @Override
    public void render(float delta) {

        if ( timer >= 0.5f ){
            timer = 0;
            fishDY = controller.getFishMovement(fishY,126,979- greenBar.getHeight());
        }
        else{
            timer += delta;
        }


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        controller.checkIfFishIsIn(barY,fishY,greenBar.getHeight(),fishIcon.getHeight());
        updateGreenBar();
        updateProgressBar();
        updateSonar();
        updateFish();
        updateCrown();

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

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if ( keycode == Input.Keys.UP ) {
                    upPressed = true;
                    return true;
                }
                else if ( keycode == Input.Keys.DOWN ) {
                    downPressed = true;
                    return true;
                }
                else if ( keycode == Input.Keys.S ) {
                    sonarActivated = true;
                    fishIcon.remove();
                    fishIcon = new Image(caughtFish.getItemImage().getDrawable());
                    displayFishIcon();
                    return true;
                }
                else if ( keycode == Input.Keys.Q ) {
                    controller.finishGame(false);
                    return true;
                }

                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {

                if ( keycode == Input.Keys.UP ) {
                    upPressed = false;
                    return true;
                }
                else if ( keycode == Input.Keys.DOWN ) {
                    downPressed = false;
                    return true;
                }

                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                System.out.println(x + " " + y);
                return false;
            }


        });

    }



    @Override
    public void executeCommands(Scanner scanner) {

    }

    public Image getFishIcon() {
        return fishIcon;
    }

    public Stage getStage() {
        return stage;
    }


}
