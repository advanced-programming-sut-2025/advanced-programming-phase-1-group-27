package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stacks;
import org.example.server.models.enums.InGameMenuType;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.ArrayList;
import java.util.Scanner;

public class TradeView2 extends AppMenu {

    private final TradeController controller;

    private boolean tradeDoneByStarterSide;
    private final boolean starterSide;
    private final boolean targetSide;

    private final AppMenu lastView;

    private final String starter;
    private final String other;


    private final ArrayList<Stacks> targetPlayerInventory;
    private final ArrayList<ImageButton> targetPlayerInventoryImages;


    private final Label itemCountLabel;
    private final Label starterWaitForResponse;
    private final Label acceptOrDeclineTradeLabel;

    private final Image inventoryBackground;
    private final Image selectBox;

    private final TextButton increaseAmountButton;
    private final TextButton decreaseAmountButton;
    private final TextButton finishTrade;
    private final TextButton acceptTrade;
    private final TextButton declineTrade;
    private final TextButton addButton;

    private int itemCount;
    private int rowNum;

    private final Stage stage;

    public TradeView2(String starter, String other , AppMenu lastView) {

        controller = new TradeController();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        itemCount = 1;
        rowNum = 0;

        tradeDoneByStarterSide = false;
//        starterSide = ClientApp.getLoggedInUser().getUsername().equals(starter);
        starterSide = true;
        targetSide = !starterSide;



        this.lastView = lastView;
        this.starter = starter;
        this.other = other;

        increaseAmountButton = new TextButton("+",skin);
        decreaseAmountButton = new TextButton("-",skin);
        finishTrade = new TextButton("Finish",skin);
        acceptTrade = new TextButton("Accept",skin);
        declineTrade = new TextButton("Decline",skin);
        addButton = new TextButton("Add",skin);


        inventoryBackground = GameAssetManager.getGameAssetManager().getTradeInventoryBackground();
        selectBox = GameAssetManager.getGameAssetManager().getSelectSlot();

        targetPlayerInventory = new ArrayList<>();
        targetPlayerInventoryImages = new ArrayList<>();
        ///  GET TARGET PLAYER INVENTORY FROM SERVER
        // TEMP:
        targetPlayerInventory.add(new Stacks(FruitType.Apple,20));
        targetPlayerInventory.add(new Stacks(ToolType.BambooPole,20));
        for (CraftingProduct cp : CraftingProduct.values()) {
            targetPlayerInventory.add(new Stacks(cp,10));
        }
        for (FruitType cp : FruitType.values()) {
            targetPlayerInventory.add(new Stacks(cp,10));
        }

        for ( Stacks item: targetPlayerInventory ){
            createImage(item);
        }


        itemCountLabel = new Label(Integer.toString(itemCount), skin);
        starterWaitForResponse = new Label("Dear " + starter +" please wait for " + other +"s response.", skin);
        acceptOrDeclineTradeLabel = new Label("Dear " + other + " please accept or decline " + starter +"s offer", skin);

        setListeners();

    }

    private void displayInventory(){

        for( ImageButton imageButton: targetPlayerInventoryImages ){
            imageButton.toFront();
            imageButton.setVisible(false);
        }

        for ( int i = 0 ; i < Math.min(targetPlayerInventory.size() - 12 * rowNum,48)  ; i++ ){
            targetPlayerInventoryImages.get(i + 12 * rowNum).setPosition(520 + 64*(i % 12),840 - 67 * ((int)(i / 12)));
            targetPlayerInventoryImages.get(i + 12 * rowNum).setVisible(true);
        }



    }

    private void displayButtons(){

        finishTrade.setVisible(starterSide && !tradeDoneByStarterSide);
        acceptTrade.setVisible(targetSide && tradeDoneByStarterSide);
        declineTrade.setVisible(targetSide && tradeDoneByStarterSide);
        addButton.setVisible(!tradeDoneByStarterSide);
        increaseAmountButton.setVisible(!tradeDoneByStarterSide);
        decreaseAmountButton.setVisible(!tradeDoneByStarterSide);


        finishTrade.setWidth(Gdx.graphics.getWidth()/5f);
        finishTrade.setPosition((Gdx.graphics.getWidth()-finishTrade.getWidth())/2f,inventoryBackground.getY()-finishTrade.getHeight()-20);

        acceptTrade.setWidth(Gdx.graphics.getWidth()/5f);
        declineTrade.setWidth(Gdx.graphics.getWidth()/5f);

        acceptTrade.setPosition((Gdx.graphics.getWidth()-(acceptTrade.getWidth() + declineTrade.getWidth() + Gdx.graphics.getWidth()/20f))/2f,
                inventoryBackground.getY()-finishTrade.getHeight()-20);

        declineTrade.setPosition(acceptTrade.getX() + acceptTrade.getWidth() + Gdx.graphics.getWidth()/20f,
                inventoryBackground.getY()-finishTrade.getHeight()-20);

        increaseAmountButton.setPosition(inventoryBackground.getX()+inventoryBackground.getWidth()+40, itemCountLabel.getY() + itemCountLabel.getHeight()+20);
        decreaseAmountButton.setPosition(inventoryBackground.getX()+inventoryBackground.getWidth()+40, itemCountLabel.getY() - decreaseAmountButton.getHeight() - 20);
        addButton.setPosition(inventoryBackground.getX()+inventoryBackground.getWidth()+20, decreaseAmountButton.getY()-addButton.getHeight()-20);

    }

    private void displayLabels(){

        itemCountLabel.setText(Integer.toString(itemCount));

        starterWaitForResponse.setVisible(tradeDoneByStarterSide && starterSide);
        acceptOrDeclineTradeLabel.setVisible(tradeDoneByStarterSide && targetSide);
        itemCountLabel.setVisible(!tradeDoneByStarterSide);

        starterWaitForResponse.setPosition((Gdx.graphics.getWidth()-starterWaitForResponse.getWidth())/2f,inventoryBackground.getY()-starterWaitForResponse.getHeight()-20);
        acceptOrDeclineTradeLabel.setPosition((Gdx.graphics.getWidth()-acceptOrDeclineTradeLabel.getWidth())/2f,inventoryBackground.getY()+inventoryBackground.getHeight()+20);
        itemCountLabel.setPosition(inventoryBackground.getX()+inventoryBackground.getWidth()+80,inventoryBackground.getY()+inventoryBackground.getHeight()/2f-itemCountLabel.getHeight()/2f);

    }

    @Override
    public void show() {

        inventoryBackground.setPosition((Gdx.graphics.getWidth()-inventoryBackground.getWidth())/2f,
                2 * (Gdx.graphics.getHeight()-inventoryBackground.getHeight())/3f);


        stage.addActor(menuBackground);
        stage.addActor(increaseAmountButton);
        stage.addActor(decreaseAmountButton);
        stage.addActor(addButton);
        stage.addActor(finishTrade);
        stage.addActor(acceptTrade);
        stage.addActor(declineTrade);
        stage.addActor(itemCountLabel);
        stage.addActor(acceptOrDeclineTradeLabel);
        stage.addActor(starterWaitForResponse);
        stage.addActor(inventoryBackground);


    }

    @Override
    public void render(float v) {

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        displayInventory();
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

    private void setListeners(){


        stage.addListener(new InputListener() {


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                System.out.println(x + " " + y);

                return true;
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {

                if ( (495 < x && x < 1295) && (615 < y && y < 912) ) {

                    if ( amountY > 0 ){
                        if ( targetPlayerInventory.size() - ( 12 * rowNum) >48 ){
                            rowNum ++;
                        }
                    } else if ( amountY < 0 ) {
                        rowNum = Math.max(0,rowNum-1);
                    }

                }
                return false;

            }

        });

        finishTrade.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                tradeDoneByStarterSide = true;
                ///  TODO: bara target ham in field true she!!!

            }

        });

        acceptTrade.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                ///  TODO

            }

        });

        declineTrade.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                ///  TODO

            }

        });

        increaseAmountButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                itemCount ++;

            }

        });

        decreaseAmountButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                itemCount = Math.max(1, itemCount - 1);

            }

        });



    }

    public TradeController getController() {
        return controller;
    }

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void createImage(Stacks item) {

        ImageButton itemButton = new ImageButton( item.getItem().getItemImage().getDrawable() );
        itemButton.setSize(48,48);
        stage.addActor(itemButton);
        targetPlayerInventoryImages.add(itemButton);

    }

}
