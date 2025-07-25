package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import org.example.client.Main;
import org.example.common.models.GraphicalResult;
import org.example.server.controller.HUDController;
import org.example.server.models.App;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Stacks;
import org.example.server.models.tools.Backpack;

import java.util.List;
import java.util.Scanner;

public class HUDView extends AppMenu{

    private final HUDController controller;

    private Stage stage;

    private Image clockImage;
    private final Image clockArrowImage;
    private final Image inventoryHotBarImage;
    private final Image inventorySelectSlotImage;

    private final TextField textInputField;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;

    private final GraphicalResult errorLabel;

    public HUDView(Stage stage) {

        controller = new HUDController(this);
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        inventoryHotBarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotBar());
        inventorySelectSlotImage = new Image(GameAssetManager.getGameAssetManager().getInventorySelectSlot());
        textInputField = new TextField("",skin);
        isInputFieldVisible = false;
        tJustPressed = false;
        errorLabel = new GraphicalResult();
        this.stage = stage;
        setListeners();

    }


    private void displayClock(){

        controller.updateClockImage();

        clockImage.setPosition(1920-clockImage.getWidth()-10,1080-clockImage.getHeight());


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

    private void displayInventoryHotBar(){


        inventoryHotBarImage.setPosition( (Gdx.graphics.getWidth()-inventoryHotBarImage.getWidth())/2,10 );

        inventorySelectSlotImage.setPosition(inventoryHotBarImage.getX() + 18 + controller.getSlotPosition(),26);

        stage.addActor(inventoryHotBarImage);
        stage.addActor(inventorySelectSlotImage);

    }

    private void displayInputField(){

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

    private void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth()/2f * errorLabel.getDisplayTime() / 3, Gdx.graphics.getHeight()-40);
        stage.addActor(errorLabel.getMessage());

    }

    private void showInventoryItem(){

        List<Stacks> items = App.getCurrentGame().getCurrentPlayer().getBackpack().getItems();

        for ( int i = 0 ; i < items.size() ; i++ ){

//            Image image = new Image(items.get(i).getItem().getTexture());
//            image.setSize(48,48);
//            image.setPosition(inventoryHotBarImage.getX() + 18 + controller.getItemPosition(i) + 5,26+5);
//            stage.addActor(image);

            Main.getBatch().draw(
                    items.get(i).getItem().getTexture(),
                    inventoryHotBarImage.getX() + 18 + controller.getItemPosition(i) + 5,31,
                    48,48


            );

        }

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
    }

    public void sobhanAllah(float delta) {


        errorLabel.update(delta);

        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        //Main.getBatch().begin();
//        stage.draw();

        showInventoryItem();
        //Main.getBatch().end();

        displayClock();
        displayInventoryHotBar();
        displayInputField();
        showErrorMessage();



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

                    else if ( keycode == Input.Keys.DOWN ){

                        controller.updateSlotIndex(-1);

                    }

                    else if ( keycode == Input.Keys.NUM_1 ){

                        controller.quickSetHotBarIndex(0);

                    }

                    else if ( keycode == Input.Keys.NUM_2 ){

                        controller.quickSetHotBarIndex(1);

                    }


                    else if ( keycode == Input.Keys.NUM_3 ){

                        controller.quickSetHotBarIndex(2);

                    }

                    else if ( keycode == Input.Keys.NUM_4 ){

                        controller.quickSetHotBarIndex(3);

                    }

                    else if ( keycode == Input.Keys.NUM_5 ){

                        controller.quickSetHotBarIndex(4);

                    }

                    else if ( keycode == Input.Keys.NUM_6 ){

                        controller.quickSetHotBarIndex(5);

                    }

                    else if ( keycode == Input.Keys.NUM_7 ){

                        controller.quickSetHotBarIndex(6);

                    }

                    else if ( keycode == Input.Keys.NUM_8 ){

                        controller.quickSetHotBarIndex(7);

                    }

                    else if ( keycode == Input.Keys.NUM_9 ){

                        controller.quickSetHotBarIndex(8);

                    }

                    else if ( keycode == Input.Keys.NUM_0 ){

                        controller.quickSetHotBarIndex(9);

                    }

                    else if ( keycode == Input.Keys.MINUS ){

                        controller.quickSetHotBarIndex(10);

                    }

                    else if ( keycode == Input.Keys.EQUALS ){

                        controller.quickSetHotBarIndex(11);

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

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {

                if (!isInputFieldVisible) {

                    if (amountY > 0) {
                        controller.updateSlotIndex(-1);
                    } else if (amountY < 0) {
                        controller.updateSlotIndex(1);
                    }
                    return true;
                }
                return false;

            }

        });



    }

    public boolean isInputFieldVisible() {
        return isInputFieldVisible;
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
