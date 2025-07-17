package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.common.models.GraphicalResult;
import org.example.server.controller.HUDController;
import org.example.server.controller.HomeController;
import org.example.server.controller.HomePlayerController;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;

import java.util.Scanner;

public class HUDView extends AppMenu{

    private final HUDController controller;

    private Stage stage;

    private Image clockImage;
    private final Image clockArrowImage;

    private final TextField textInputField;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;

    private final GraphicalResult errorLabel;


    public HUDView(Stage stage) {

        controller = new HUDController(this);
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        textInputField = new TextField("",skin);
        isInputFieldVisible = false;
        tJustPressed = false;
        errorLabel = new GraphicalResult();
        this.stage = stage;
        setListeners();

    }


    public void displayClock(){

        controller.updateClockImage();

        clockImage.setPosition(1920-clockImage.getWidth(),1080-clockImage.getHeight());


        clockArrowImage.setPosition(
                clockImage.getX() + (0.255f * clockImage.getWidth()),
                clockImage.getY() + (0.37f * clockImage.getHeight())
        );
        clockArrowImage.setOrigin(
                clockArrowImage.getWidth() / 2,
                clockArrowImage.getHeight()
        );
        clockArrowImage.setRotation(controller.getClockArrowDegree());



        stage.addActor(clockImage);
        stage.addActor(clockArrowImage);
        clockArrowImage.toFront();



    }
    public void displayInputField(){

        if ( tJustPressed ){
            tJustPressed = false;
            textInputField.setText("");
        }

        textInputField.setVisible(isInputFieldVisible);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle(textInputField.getStyle());
        style.fontColor = new Color(1,1,1,1);
        textInputField.setStyle(style);

        textInputField.setColor(0,0,0,0.5f);

        textInputField.setWidth(Gdx.graphics.getWidth());

        stage.addActor(textInputField);

    }

    public void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth()/2f * errorLabel.getDisplayTime() / 3, Gdx.graphics.getHeight()-40);
        stage.addActor(errorLabel.getMessage());

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {


        errorLabel.update(delta);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        Main.getBatch().begin();
        Main.getBatch().end();

        displayClock();
        displayInputField();
        showErrorMessage();

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



    private void setListeners(){

        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if ( !isInputFieldVisible ){

                    if ( keycode == Input.Keys.T ) {
                        playClickSound();
                        isInputFieldVisible = true;
                        textInputField.setText("");
                        tJustPressed = true;
                        return true;
                    }

                }

                else {

                    if ( keycode == Input.Keys.ENTER ) {
                        playClickSound();
                        errorLabel.set(controller.handleTextInput());
                        return true;
                    }
                    else if ( keycode == Input.Keys.ESCAPE ) {
                        playClickSound();
                        controller.closeTextInputField();
                        return true;
                    }

                }
                return false;
            }
        });



    }

    public void setClockImage(Image clockImage) {
        this.clockImage = clockImage;
    }

    public TextField getTextInputField() {
        return textInputField;
    }

    public void setInputFieldVisible(boolean inputFieldVisible) {
        isInputFieldVisible = inputFieldVisible;
    }


    @Override
    public void executeCommands(Scanner scanner) {

    }

}
