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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.example.client.model.ClientApp;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.client.controller.HUDController;
import org.example.server.models.Player;
import org.example.server.models.Stacks;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.*;

public class HUDView extends AppMenu {

    private final HUDController controller;
    private final Label dayInfo;
    private final Label moneyInfo;
    private final Label timeInfo;
    private final Image clockArrowImage;
    private final Image inventoryHotBarImage;
    private final Image inventorySelectSlotImage;
    private final Image craftingMenuBackground;
    private final Image inventoryMenuBackground;
    private final Image skillMenuBackground;
    private final Image exitMenuBackground;
    private final HashMap<CraftingProduct, ImageButton> craftingProducts;
    private final TextField textInputField;
    private final GraphicalResult errorLabel;
    private final Stage stage;
    private Image clockImage;
    private boolean isInputFieldVisible;
    private boolean tJustPressed;
    private final Player player;
    private ArrayList<Stacks> inventoryItems;
    private ArrayList<Stacks> onScreenItems;
    private int rowCoEfficient;
    private Integer currentSlotInInventory;
    private InGameMenuType currentMenu;


    public HUDView(Stage stage) {

        controller = new HUDController(this);
        rowCoEfficient = 1;
        currentSlotInInventory = null;
        craftingMenuBackground = GameAssetManager.getGameAssetManager().getCraftingMenuBackground();
        inventoryMenuBackground = GameAssetManager.getGameAssetManager().getInventoryMenuBackground();
        skillMenuBackground = GameAssetManager.getGameAssetManager().getSkillMenuBackground();
        exitMenuBackground = GameAssetManager.getGameAssetManager().getExitMenuBackground();
        clockArrowImage = new Image(GameAssetManager.getGameAssetManager().getArrowTexture());
        inventoryHotBarImage = new Image(GameAssetManager.getGameAssetManager().getInventoryHotBar());
        inventorySelectSlotImage = new Image(GameAssetManager.getGameAssetManager().getInventorySelectSlot());
        textInputField = new TextField("", skin);
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
        player = ClientApp.getCurrentGame().getCurrentPlayer();
        inventoryItems = player.getBackpack().getItems();
        onScreenItems = new ArrayList<>();
        for ( Stacks stack : inventoryItems ) {
            addToScreen(Stacks.copy(stack));
        }

        craftingProducts = new HashMap<>();

        int i = 0;
        for (CraftingProduct craftingProduct : CraftingProduct.values()) {
            ImageButton productButton = new ImageButton(new TextureRegionDrawable(craftingProduct.getTexture()));
            productButton.setSize(96, 96);
            productButton.setPosition(520 + 110 * (i % 7), (float) (680 - (i / 7) * 100));
            productButton.setColor(productButton.getColor().r, productButton.getColor().g, productButton.getColor().b, 0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(craftingProduct.getRecipe())) ? 0.7f : 0f));
            craftingProducts.put(craftingProduct, productButton);
            i++;
        }

        setListeners();

    }


    private void displayClock() {

        controller.updateClockImage();

        clockImage.setPosition(1800 - clockImage.getWidth() - 10, 1080 - clockImage.getHeight());


        clockArrowImage.setPosition(clockImage.getX() + (0.255f * clockImage.getWidth()), clockImage.getY() + (0.37f * clockImage.getHeight()));
        clockArrowImage.setOrigin(clockArrowImage.getWidth() / 2, clockArrowImage.getHeight());
        clockArrowImage.setRotation(controller.getClockArrowDegree());


        stage.addActor(clockImage);
        stage.addActor(clockArrowImage);
        clockArrowImage.toFront();


    }

    private void displayDayInfo() {

        controller.setDayInfo(dayInfo);
        dayInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2, clockImage.getY() + clockImage.getHeight() - 35);
        dayInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        stage.addActor(dayInfo);
        dayInfo.toFront();

    }

    private void displayMoneyInfo() {

        controller.setMoneyInfo(moneyInfo);
        moneyInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2 + 25, clockImage.getY() + 30);
        moneyInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        stage.addActor(moneyInfo);
        moneyInfo.toFront();

    }

    private void displayTimeInfo() {

        controller.setTimeInfo(timeInfo);
        timeInfo.setPosition(clockImage.getX() + clockImage.getWidth() / 2, clockImage.getY() + clockImage.getHeight() / 2f - 5);
        timeInfo.setColor(new Color(0.86f, 0.169f, 0f, 1f));
        stage.addActor(timeInfo);
        timeInfo.toFront();

    }

    private void displayInputField() {

        if (tJustPressed) {
            tJustPressed = false;
            textInputField.setText("");
        }

        textInputField.setVisible(isInputFieldVisible);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle(textInputField.getStyle());
        style.fontColor = new Color(1, 1, 1, 1);
        textInputField.setStyle(style);

        textInputField.setColor(0, 0, 0, 0.5f);

        textInputField.setWidth(Gdx.graphics.getWidth());

        stage.addActor(textInputField);

    }

    private void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f * errorLabel.getDisplayTime() / 3, Gdx.graphics.getHeight() - 40);
        stage.addActor(errorLabel.getMessage());

    }

    private void displayInventoryHotBar() {

        if ( currentMenu == InGameMenuType.NONE ) {
            inventoryHotBarImage.setPosition((Gdx.graphics.getWidth() - inventoryHotBarImage.getWidth()) / 2, 10);
            inventorySelectSlotImage.setPosition(inventoryHotBarImage.getX() + 18 + controller.getSlotPosition(), 26);

            inventoryHotBarImage.setVisible(true);
            inventorySelectSlotImage.setVisible(true);
            stage.addActor(inventoryHotBarImage);
            stage.addActor(inventorySelectSlotImage);
        }
        else{
            inventoryHotBarImage.setVisible(false);
            inventorySelectSlotImage.setVisible(false);
        }

    }

    private void showHotBarItems() {


        if ( currentMenu == InGameMenuType.NONE ) {



            for ( int i = 0 ; i < Math.min(onScreenItems.size(),12); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().setPosition(inventoryHotBarImage.getX() + 18 + controller.getItemPosition(i) + 5, 26 + 5);
                onScreenItems.get(i).getItem().getItemImage().toFront();
            }

            for( int i = 12; i < onScreenItems.size(); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setVisible(false);
            }



        }

    }

    private void displayInventoryMenu() {

        inventoryMenuBackground.setPosition((Gdx.graphics.getWidth() - inventoryMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - inventoryMenuBackground.getHeight()) / 2f);

        inventoryMenuBackground.setVisible(currentMenu == InGameMenuType.INVENTORY);
        stage.addActor(inventoryMenuBackground);


        // INVENTORY ITEMS

        if ( currentMenu == InGameMenuType.INVENTORY ) {

            for ( int i = 0; i < Math.min(onScreenItems.size(),12); i++ ){
                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(i%12), 705);
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().toFront();
            }

            for ( int i = 12; i < onScreenItems.size(); i++ ){

                onScreenItems.get(i).getItem().getItemImage().setVisible(false);

            }

            int k = 12;

            for ( int i = 12 * rowCoEfficient; i < Math.min(12 * (rowCoEfficient+2),onScreenItems.size()); i++){
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(k%12), 705 - (float)((80-((k/12)-1)*5) * (k/12)));
                onScreenItems.get(i).getItem().getItemImage().toFront();
                k++;
            }


        }

        // DISPLAYING RED BOX

        if ( currentMenu == InGameMenuType.INVENTORY) {

            if (currentSlotInInventory != null) {
                inventorySelectSlotImage.setVisible(true);
                inventorySelectSlotImage.setSize(63,60);
                if ( currentSlotInInventory/12 == 0 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 700);
                }
                else if ( currentSlotInInventory/12 == 1 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 620);
                }
                else if ( currentSlotInInventory/12 == 2 ){
                    inventorySelectSlotImage.setPosition((520 + (currentSlotInInventory%12)*63), 550);
                }
                inventorySelectSlotImage.toFront();
            }
            else{
                inventorySelectSlotImage.setVisible(false);
            }


        }




    }

    private void displaySkillMenu() {

        skillMenuBackground.setPosition((Gdx.graphics.getWidth() - skillMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - skillMenuBackground.getHeight()) / 2f);

        skillMenuBackground.setVisible(currentMenu == InGameMenuType.SKILL);
        stage.addActor(skillMenuBackground);

    }

    private void displayCraftingMenu() {

        //  BACKGROUND
        craftingMenuBackground.setPosition((Gdx.graphics.getWidth() - craftingMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - craftingMenuBackground.getHeight()) / 2f);
        craftingMenuBackground.setVisible(currentMenu == InGameMenuType.CRAFTING);
        stage.addActor(craftingMenuBackground);

        //  ITEMS
        for (CraftingProduct craftingProduct : craftingProducts.keySet()) {

            ImageButton imageButton = craftingProducts.get(craftingProduct);

            imageButton.setColor(
                    imageButton.getColor().r,
                    imageButton.getColor().g,
                    imageButton.getColor().b,
                    0.3f + ((ClientApp.getCurrentGame().getCurrentPlayer().hasEnoughIngredients(craftingProduct.getRecipe())) ? 0.7f : 0f)
            );

            if (currentMenu == InGameMenuType.CRAFTING) {
                imageButton.setVisible(
                        ClientApp.getCurrentGame().getCurrentPlayer().getAvailableCraftingRecipes().contains(craftingProduct.getRecipe())
                );
            } else {
                imageButton.setVisible(false);
            }
            stage.addActor(imageButton);

        }

        // INVENTORY ITEMS

        if ( currentMenu == InGameMenuType.CRAFTING ) {

            for ( int i = 0; i < Math.min(onScreenItems.size(),36); i++ ){

                onScreenItems.get(i).getItem().getItemImage().setPosition(520 + controller.getItemPosition(i%12), 385 - (float)(70 * (i/12)) );
                onScreenItems.get(i).getItem().getItemImage().setVisible(true);
                onScreenItems.get(i).getItem().getItemImage().toFront();

            }

        }

    }

    private void displayExitMenu() {

        exitMenuBackground.setPosition((Gdx.graphics.getWidth() - exitMenuBackground.getWidth()) / 2f, (Gdx.graphics.getHeight() - exitMenuBackground.getHeight()) / 2f);

        exitMenuBackground.setVisible(currentMenu == InGameMenuType.EXIT);
        stage.addActor(exitMenuBackground);

    }

    private void updateOnScreenItems() {

        int commonPrefix = Math.min(onScreenItems.size(), inventoryItems.size());
        ArrayList<Stacks> removableStacks = new ArrayList<>();
        ArrayList<Stacks> addableStack = new ArrayList<>();

        for (int i = commonPrefix; i < onScreenItems.size(); i++) {
            removableStacks.add(onScreenItems.get(i));
        }

        for (int i = 0; i < commonPrefix; i++) {
            if (!Stacks.compare(onScreenItems.get(i), inventoryItems.get(i))) {
                removableStacks.add(onScreenItems.get(i));
                addableStack.add(Stacks.copy(inventoryItems.get(i)));
            }
        }

        for (int i = commonPrefix; i < inventoryItems.size(); i++) {
            addableStack.add(Stacks.copy(inventoryItems.get(i)));
        }

        for (Stacks removableStack : removableStacks) {
            removeFromScreen(removableStack);
        }

        for ( Stacks stack : addableStack ) {
            addToScreen(stack);
        }


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
        inventoryItems = player.getBackpack().getItems();

        updateOnScreenItems();


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

    public void displayOnlyClock() {
        displayClock();
        displayDayInfo();
        displayMoneyInfo();
        displayTimeInfo();
        makeOnScreenItemsInvisible();
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

                if (!isInputFieldVisible) {

                    if (keycode == Input.Keys.T) {
                        playClickSound();
                        isInputFieldVisible = true;
                        textInputField.setText("");
                        tJustPressed = true;
                        return true;
                    }
                    else if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.E) {

                        if (currentMenu != InGameMenuType.INVENTORY ) {
                            currentMenu = InGameMenuType.INVENTORY;
                        } else {
                            currentMenu = InGameMenuType.NONE;
                        }

                    } else if (keycode == Input.Keys.B) {

                        if (currentMenu == InGameMenuType.CRAFTING) {
                            currentMenu = InGameMenuType.NONE;
                        }
                        else {
                            currentMenu = InGameMenuType.CRAFTING;
                            makeOnScreenItemsInvisible();
                        }

                    }

                    if ( currentMenu == InGameMenuType.INVENTORY ) {

                        if ( keycode == Input.Keys.D ) {

                            if ( currentSlotInInventory != null ) {

                                int itemNumber = (currentSlotInInventory<12)? currentSlotInInventory:(currentSlotInInventory + (rowCoEfficient-1) * 12);

                                if ( itemNumber >= onScreenItems.size() ){
                                    errorLabel.set(new GraphicalResult("Selected slot is empty!"));
                                }
                                else{
                                    errorLabel.set(controller.removeFromInventory(onScreenItems.get(itemNumber)));
                                }
                            }

                        }

                    }

                    else if ( currentMenu == InGameMenuType.NONE ) {
                        if (keycode == Input.Keys.NUM_1) {

                            controller.quickSetHotBarIndex(0);

                        } else if (keycode == Input.Keys.NUM_2) {

                            controller.quickSetHotBarIndex(1);

                        } else if (keycode == Input.Keys.NUM_3) {

                            controller.quickSetHotBarIndex(2);

                        } else if (keycode == Input.Keys.NUM_4) {

                            controller.quickSetHotBarIndex(3);

                        } else if (keycode == Input.Keys.NUM_5) {

                            controller.quickSetHotBarIndex(4);

                        } else if (keycode == Input.Keys.NUM_6) {

                            controller.quickSetHotBarIndex(5);

                        } else if (keycode == Input.Keys.NUM_7) {

                            controller.quickSetHotBarIndex(6);

                        } else if (keycode == Input.Keys.NUM_8) {

                            controller.quickSetHotBarIndex(7);

                        } else if (keycode == Input.Keys.NUM_9) {

                            controller.quickSetHotBarIndex(8);

                        } else if (keycode == Input.Keys.NUM_0) {

                            controller.quickSetHotBarIndex(9);

                        } else if (keycode == Input.Keys.MINUS) {

                            controller.quickSetHotBarIndex(10);

                        } else if (keycode == Input.Keys.EQUALS) {

                            controller.quickSetHotBarIndex(11);

                        }
                    }


                } else {

                    if (keycode == Input.Keys.ENTER) {
                        playClickSound();
                        errorLabel.set(controller.handleTextInput());
                        return true;
                    } else if (keycode == Input.Keys.ESCAPE) {
                        playClickSound();
                        controller.closeTextInputField();
                        return true;
                    }

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
                        makeOnScreenItemsInvisible();
                        return true;
                    }

                    for ( int i = 0; i < 12; i++ ){

                        if ( (520 + i*63) < x && x < (520 + (i+1)*63) ){

                            if ( 700 < y && y < 760 ){
                                if ( currentSlotInInventory == null ){
                                    currentSlotInInventory = i;
                                }
                                else{
                                    if ( currentSlotInInventory != i ){
                                        currentSlotInInventory = i;
                                    }
                                    else{
                                        currentSlotInInventory = null;
                                    }
                                }
                            }
                            else if ( 620 < y && y < 680 ){
                                if ( currentSlotInInventory == null ){
                                    currentSlotInInventory = i+12;
                                }
                                else{
                                    if ( currentSlotInInventory != (i+12) ){
                                        currentSlotInInventory = i+12;
                                    }
                                    else{
                                        currentSlotInInventory = null;
                                    }
                                }
                            }
                            else if ( 550 < y && y < 610 ){
                                if ( currentSlotInInventory == null ){
                                    currentSlotInInventory = i+24;
                                }
                                else{
                                    if ( currentSlotInInventory != (i+24) ){
                                        currentSlotInInventory = i+24;
                                    }
                                    else{
                                        currentSlotInInventory = null;
                                    }
                                }
                            }


                        }

                    }


                }

                return false;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {

                if (!isInputFieldVisible && currentMenu == InGameMenuType.NONE) {

                    if (amountY > 0) {
                        controller.updateSlotIndex(-1);
                    } else if (amountY < 0) {
                        controller.updateSlotIndex(1);
                    }
                    return true;

                }
                else if ( currentMenu == InGameMenuType.INVENTORY ) {
                    if ( (495 < x && x < 1300) && (540 < y && y < 780) ){
                        if (amountY > 0 && (rowCoEfficient+2)*12 < onScreenItems.size()) {
                            rowCoEfficient += 1;
                            if ( currentSlotInInventory != null ){
                                if ( 12 <= currentSlotInInventory && currentSlotInInventory < 24 ){
                                    currentSlotInInventory = null;
                                }
                                else if ( 24 <= currentSlotInInventory ){
                                    currentSlotInInventory -= 12;
                                }
                            }
                        }
                        else if ( amountY < 0 ){
                            rowCoEfficient = Math.max(1, rowCoEfficient-1);
                            if ( currentSlotInInventory != null){
                                if ( 24 <= currentSlotInInventory ){
                                    currentSlotInInventory = null;
                                }
                                else if (12 <= currentSlotInInventory){
                                    currentSlotInInventory += 12;
                                }
                            }

                        }
                    }
                    return true;
                }
                return false;

            }

        });

        for (Map.Entry<CraftingProduct, ImageButton> entry : craftingProducts.entrySet()) {
            ImageButton imageButton = entry.getValue();
            imageButton.addListener(new ClickListener() {
               @Override
               public void clicked(InputEvent event, float x, float y) {
                   playClickSound();
                   if (imageButton.getColor().a > 0.3f) {   // has ingredient
                       errorLabel.set(controller.craft(entry.getKey()));
                   }
                   else {
                       errorLabel.set(new GraphicalResult("You don't have enough ingredient!"));

                   }
               }
            });
        }
    }


    public void setInputFieldVisible(boolean inputFieldVisible) {
        isInputFieldVisible = inputFieldVisible;
    }

    public void setClockImage(Image clockImage) {
        this.clockImage = clockImage;
    }

    public TextField getTextInputField() {
        return textInputField;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    public boolean isInputFieldVisible() {
        return isInputFieldVisible;
    }

    private void removeFromScreen(Stacks stack) {

        stack.getItem().getItemImage().remove();
        onScreenItems.remove(stack);

    }

    private void addToScreen(Stacks stack) {

        stack.getItem().getItemImage().setSize(48,48);
        stack.getItem().getItemImage().setVisible(false);
        stage.addActor(stack.getItem().getItemImage());
        onScreenItems.add(stack);


    }

    private void makeOnScreenItemsInvisible(){

        for (Stacks onScreenItem : onScreenItems) {
            onScreenItem.getItem().getItemImage().setVisible(false);
        }

    }

    public InGameMenuType getCurrentMenu() {
        return currentMenu;
    }

    public ArrayList<Stacks> getInventoryItems() {
        return inventoryItems;
    }
}
