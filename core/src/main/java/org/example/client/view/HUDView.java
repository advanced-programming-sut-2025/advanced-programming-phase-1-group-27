package org.example.client.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.common.models.GraphicalResult;
import org.example.server.controller.HUDController;
import org.example.server.models.App;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stacks;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HUDView extends AppMenu{

    private final HUDController controller;

    private Stage stage;

    private final Label dayInfo;
    private final Label moneyInfo;
    private final Label timeInfo;

    private Image clockImage;
    private final Image clockArrowImage;
    private final Image inventoryHotBarImage;
    private final Image inventorySelectSlotImage;
    private final Image craftingMenuBackground;
    private final Image inventoryMenuBackground;
    private final Image skillMenuBackground;
    private final Image exitMenuBackground;

    private final HashMap<Recipe, ImageButton> craftingProducts;

    private final TextField textInputField;

    private final GraphicalResult errorLabel;

    private boolean isInputFieldVisible;
    private boolean tJustPressed;

    private InGameMenuType currentMenu;


    public HUDView(Stage stage) {

        controller = new HUDController(this);
        craftingMenuBackground = GameAssetManager.getGameAssetManager().getCraftingMenuBackground();
        inventoryMenuBackground = GameAssetManager.getGameAssetManager().getInventoryMenuBackground();
        skillMenuBackground = GameAssetManager.getGameAssetManager().getSkillMenuBackground();
        exitMenuBackground = GameAssetManager.getGameAssetManager().getExitMenuBackground();
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        inventoryHotBarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotBar());
        inventorySelectSlotImage = new Image(GameAssetManager.getGameAssetManager().getInventorySelectSlot());
        textInputField = new TextField("",skin);
        currentMenu = InGameMenuType.NONE;
        isInputFieldVisible = false;
        tJustPressed = false;
        dayInfo = new Label("", skin);
        controller.setDayInfo(dayInfo);
        moneyInfo = new Label("", skin);
        controller.setMoneyInfo(moneyInfo);
        timeInfo = new Label("", skin);
        controller.setTimeInfo(timeInfo);
        errorLabel = new GraphicalResult();
        this.stage = stage;

        craftingProducts = new HashMap<>();

        int i = 0;
        for ( Recipe recipe : Recipe.values() ) {

            if ( recipe.getFinalProduct() instanceof CraftingProduct ){

                ImageButton productButton = new ImageButton(new TextureRegionDrawable(recipe.getFinalProduct().getTexture()));
                productButton.setSize(96,96);
                productButton.setPosition(520 + 110 * (i%7),(float)(680-(i/7)*100));
                productButton.setColor(productButton.getColor().r,productButton.getColor().g,productButton.getColor().b,
                        0.3f + ((App.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(recipe))? 0.7f:0f) );
                craftingProducts.put(recipe,productButton);
                i++;

            }
        }

        setListeners();

    }


    private void displayClock(){

        controller.updateClockImage();

        clockImage.setPosition(1800-clockImage.getWidth()-10,1080-clockImage.getHeight());


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

    private void displayDayInfo(){

        controller.setDayInfo(dayInfo);
        dayInfo.setPosition(clockImage.getX() + clockImage.getWidth()/2, clockImage.getY()+clockImage.getHeight()-35);
        dayInfo.setColor(new Color(0.86f,0.169f,0f,1f));
        stage.addActor(dayInfo);
        dayInfo.toFront();

    }

    private void displayMoneyInfo(){

        controller.setMoneyInfo(moneyInfo);
        moneyInfo.setPosition(clockImage.getX() + clockImage.getWidth()/2+25, clockImage.getY()+30);
        moneyInfo.setColor(new Color(0.86f,0.169f,0f,1f));
        stage.addActor(moneyInfo);
        moneyInfo.toFront();

    }

    private void displayTimeInfo(){

        controller.setTimeInfo(timeInfo);
        timeInfo.setPosition(clockImage.getX() + clockImage.getWidth()/2, clockImage.getY()+clockImage.getHeight()/2f-5);
        timeInfo.setColor(new Color(0.86f,0.169f,0f,1f));
        stage.addActor(timeInfo);
        timeInfo.toFront();

    }

    private void displayInventoryHotBar(){


        inventoryHotBarImage.setPosition( (Gdx.graphics.getWidth()-inventoryHotBarImage.getWidth())/2,10 );
        inventorySelectSlotImage.setPosition(inventoryHotBarImage.getX() + 18 + controller.getSlotPosition(),26);

        inventoryHotBarImage.setVisible(currentMenu == InGameMenuType.NONE);
        inventorySelectSlotImage.setVisible(currentMenu == InGameMenuType.NONE);
        stage.addActor(inventoryHotBarImage);
        stage.addActor(inventorySelectSlotImage);

    }

    private void showHotBarItems(){

        List<Stacks> items = App.getCurrentGame().getCurrentPlayer().getBackpack().getItems();

        for ( int i = 0 ; i < items.size() ; i++ ){

            Image image = items.get(i).getItem().getItemImage();
            image.setSize(48,48);
            image.setPosition(inventoryHotBarImage.getX() + 18 + controller.getItemPosition(i) + 5,26+5);
            image.setVisible(currentMenu == InGameMenuType.NONE);
            stage.addActor(image);


        }

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

    private void displayInventoryMenu(){

        inventoryMenuBackground.setPosition((Gdx.graphics.getWidth()-inventoryMenuBackground.getWidth())/2f, (Gdx.graphics.getHeight()-inventoryMenuBackground.getHeight())/2f);

        inventoryMenuBackground.setVisible(currentMenu == InGameMenuType.INVENTORY);
        stage.addActor(inventoryMenuBackground);

    }

    private void displaySkillMenu(){

        skillMenuBackground.setPosition((Gdx.graphics.getWidth()-skillMenuBackground.getWidth())/2f, (Gdx.graphics.getHeight()-skillMenuBackground.getHeight())/2f);

        skillMenuBackground.setVisible(currentMenu == InGameMenuType.SKILL);
        stage.addActor(skillMenuBackground);

    }

    private void displayCraftingMenu(){

        //  BACKGROUND
        craftingMenuBackground.setPosition((Gdx.graphics.getWidth()-craftingMenuBackground.getWidth())/2f, (Gdx.graphics.getHeight()-craftingMenuBackground.getHeight())/2f);
        craftingMenuBackground.setVisible(currentMenu == InGameMenuType.CRAFTING);
        stage.addActor(craftingMenuBackground);

        //  CRAFTING ITEMS
        for ( Recipe recipe : craftingProducts.keySet() ){

            ImageButton craftingProduct = craftingProducts.get(recipe);
            craftingProduct.setColor(craftingProduct.getColor().r,craftingProduct.getColor().g,craftingProduct.getColor().b,
                    0.3f + ((App.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(recipe))? 0.7f:0f) );

            if ( currentMenu == InGameMenuType.CRAFTING ){
                craftingProduct.setVisible(App.getCurrentGame().getCurrentPlayer().getAvailableCraftingRecipes().contains(recipe));
            }
            else{
                craftingProduct.setVisible(false);
            }

            stage.addActor(craftingProduct);
        }

        // INVENTORY ITEMS
        List<Stacks> craftingMenuInventory = new ArrayList<>(App.getCurrentGame().getCurrentPlayer().getBackpack().getItems());

        if ( currentMenu == InGameMenuType.CRAFTING ) {
            for (int i = 0; i < craftingMenuInventory.size() && i < 36; i++) {

                Image image = craftingMenuInventory.get(i).getItem().getItemImage();
                image.setSize(48, 48);
                image.setPosition(520 + controller.getItemPosition(i%12), 385);
                image.setVisible(true);
                stage.addActor(image);
                image.toFront();

            }
        }



    }

    private void displayExitMenu(){

        exitMenuBackground.setPosition((Gdx.graphics.getWidth()-exitMenuBackground.getWidth())/2f, (Gdx.graphics.getHeight()-exitMenuBackground.getHeight())/2f);

        exitMenuBackground.setVisible(currentMenu == InGameMenuType.EXIT);
        stage.addActor(exitMenuBackground);

    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
    }

    public void displayHUD(float delta) {


        errorLabel.update(delta);


        displayClock();
        displayInventoryHotBar();
        showErrorMessage();
        showHotBarItems();
        displayInputField();
        displayDayInfo();
        displayMoneyInfo();
        displayTimeInfo();
        displayInventoryMenu();
        displaySkillMenu();
        displayCraftingMenu();
        displayExitMenu();


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

                    else if ( keycode == Input.Keys.ESCAPE || keycode == Input.Keys.E) {

                        if ( currentMenu == InGameMenuType.NONE ) {
                            currentMenu = InGameMenuType.INVENTORY;
                        }
                        else{
                            currentMenu = InGameMenuType.NONE;
                        }

                    }

                    else if ( keycode == Input.Keys.B ){

                        if ( currentMenu == InGameMenuType.CRAFTING ) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else{
                            currentMenu = InGameMenuType.CRAFTING;
                        }

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

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (!isInputFieldVisible) {

                    if ( (525 < x && x < 580) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.INVENTORY;
                        return true;
                    }
                    else if ( (785 < x && x < 836) && ( 800 < y && y < 860 )){
                        currentMenu = InGameMenuType.CRAFTING;
                        return true;
                    }

//                    System.out.println("X: " + x + " Y: " + y);

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
