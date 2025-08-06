package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stacks;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.CraftingProduct;

import java.util.ArrayList;
import java.util.Scanner;

public class TradeView extends AppMenu {
//    private final TradeController controller;
//
//    private final AppMenu lastView;
//
//    private ArrayList<Stacks> selectedCurrent;
//    private ArrayList<Stacks> selectedOther;
//    private ArrayList<Stacks> othersInventory;
//
//    private final String starter;
//    private final String other;
//
//    private Table stockTableCurrent;
//    private ScrollPane scrollPaneCurrent;
//
//    private Table stockTableOther;
//    private ScrollPane scrollPaneOther;
//
//    private Stage stage;
//
//    public TradeView(String starter, String other , AppMenu lastView) {
//        controller = new TradeController();
//        this.lastView = lastView;
//        othersInventory = InteractionsWithUserController.getInventory(
//                ClientApp.getLoggedInUser().getUsername().equals(starter) ? other : starter
//        );
//        selectedCurrent = new ArrayList<>();
//        selectedOther = new ArrayList<>();
//        this.starter = starter;
//        this.other = other;
//
//        stockTableCurrent = new Table();
//        stockTableOther = new Table();
//        scrollPaneCurrent = new ScrollPane(stockTableCurrent, skin);
//        scrollPaneCurrent.setFadeScrollBars(false);
//        scrollPaneCurrent.setScrollingDisabled(true, false);
//        scrollPaneOther = new ScrollPane(stockTableOther, skin);
//        scrollPaneOther.setFadeScrollBars(false);
//        scrollPaneOther.setScrollingDisabled(true, false);
//
//    }
//
//    private void displayCurrentItems() {
//        stockTableCurrent.clear();
//
//        float redAreaX = 10f;
//        float redAreaWidth = Gdx.graphics.getWidth()/2f - (2 * redAreaX);
//
//        Table firstRow = new Table();
//        Label nameLabel = new Label("Name", skin);
//        Label qualityLabel = new Label("Quality", skin);
//        Label quantityLabel = new Label("Quantity", skin);
//
//        nameLabel.setColor(Color.BLACK);
//        qualityLabel.setColor(Color.BLACK);
//        quantityLabel.setColor(Color.BLACK);
//
//        nameLabel.setFontScale(1.5f);
//        qualityLabel.setFontScale(1.5f);
//        quantityLabel.setFontScale(1.5f);
//
//        firstRow.add(nameLabel).width(70).left();
//        firstRow.add(qualityLabel).width(30).right();
//        firstRow.add(quantityLabel).width(10).right();
//        stockTableCurrent.add(firstRow).width(redAreaWidth).padBottom(5);
//        stockTableCurrent.row();
//        stockTableCurrent.add().colspan(3).height(2).fillX().padBottom(10);
//        stockTableCurrent.row();
//
//        for (Stacks stacks : selectedCurrent) {
//            Table row = new Table();
//            Label nameLabel1 = new Label(stacks.getItem().getName(), skin);
//            Label qualityLabel1 = new Label(stacks.getStackLevel().toString(), skin);
//            Label countLabel1 = new Label(stacks.getQuantity() + "", skin);
//
//            nameLabel1.setColor(Color.BLACK);
//            qualityLabel1.setColor(Color.BLACK);
//            countLabel1.setColor(Color.BLACK);
//
//            row.add(nameLabel1).pad(10).width(700).left();
//            row.add(qualityLabel1).pad(10).width(300).right();
//            row.add(countLabel1).pad(10).width(100).right();
//
//            float itemHeight = 50f;
//            row.setHeight(itemHeight);
//            stockTableCurrent.add(row).width(redAreaWidth).padBottom(5);
//            stockTableCurrent.row();
//            row.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    playClickSound();
//                    // TODO : delete
//                }
//            });
//        }
//
//        scrollPaneCurrent.setSize(redAreaWidth, 60);
//        scrollPaneCurrent.setPosition(-120, Gdx.graphics.getHeight() / 2f - 150);
//    }
//
//    private void displayOtherItems() {
//        stockTableOther.clear();
//
//        float redAreaX = 10f;
//        float redAreaWidth = Gdx.graphics.getWidth()/2f - (2 * redAreaX);
//
//        Table firstRow = new Table();
//        Label nameLabel = new Label("Name", skin);
//        Label qualityLabel = new Label("Quality", skin);
//        Label quantityLabel = new Label("Quantity", skin);
//
//        nameLabel.setColor(Color.BLACK);
//        qualityLabel.setColor(Color.BLACK);
//        quantityLabel.setColor(Color.BLACK);
//
//        nameLabel.setFontScale(1.5f);
//        qualityLabel.setFontScale(1.5f);
//        quantityLabel.setFontScale(1.5f);
//
//        firstRow.add(nameLabel).width(70).left();
//        firstRow.add(qualityLabel).width(30).right();
//        firstRow.add(quantityLabel).width(10).right();
//        stockTableOther.add(firstRow).width(redAreaWidth).padBottom(5);
//        stockTableOther.row();
//        stockTableOther.add().colspan(3).height(2).fillX().padBottom(10);
//        stockTableOther.row();
//
//        for (Stacks stacks : selectedOther) {
//            Table row = new Table();
//            Label nameLabel1 = new Label(stacks.getItem().getName(), skin);
//            Label qualityLabel1 = new Label(stacks.getStackLevel().toString(), skin);
//            Label countLabel1 = new Label(stacks.getQuantity() + "", skin);
//
//            nameLabel1.setColor(Color.BLACK);
//            qualityLabel1.setColor(Color.BLACK);
//            countLabel1.setColor(Color.BLACK);
//
//            row.add(nameLabel1).pad(10).width(700).left();
//            row.add(qualityLabel1).pad(10).width(300).right();
//            row.add(countLabel1).pad(10).width(100).right();
//
//            float itemHeight = 50f;
//            row.setHeight(itemHeight);
//            stockTableOther.add(row).width(redAreaWidth).padBottom(5);
//            stockTableOther.row();
//        }
//
//        scrollPaneOther.setSize(redAreaWidth, 60);
//        scrollPaneOther.setPosition(-120, Gdx.graphics.getHeight() / 2f - 150);
//    }
//
//    @Override
//    public void show() {
//        stage.addActor(scrollPaneCurrent);
//        stage.addActor(scrollPaneOther);
//    }
//
//    @Override
//    public void render(float v) {
//
//    }
//
//    @Override
//    public void resize(int i, int i1) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//    public TradeController getController() {
//        return controller;
//    }

//    @Override
//    public void executeCommands(Scanner scanner) {
//        if (controller.playerPassedOut()) {
//            System.out.println(App.getCurrentGame().getCurrentPlayer().getUsername() + " has passed out!");
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().nextTurn(scanner));
//            return;
//        }
//        String input = scanner.nextLine();
//        Matcher matcher;
//        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
//            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
//        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
//            System.out.println(controller.showCurrentMenu());
//        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
//            System.out.println(controller.exitMenu());
//        } else if (GameMenuCommands.TerminateGame.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().terminateGame(scanner));
//        } else if ((matcher = InteractionsWithUserCommands.Trade.getMatcher(input)) != null) {
//            System.out.println(controller.trade(
//                    matcher.group("username").trim(),
//                    matcher.group("type").trim(),
//                    matcher.group("item").trim(),
//                    matcher.group("amount").trim(),
//                    matcher.group("price"),
//                    matcher.group("targetItem"),
//                    matcher.group("targetAmount")
//            ));
//        } else if (InteractionsWithUserCommands.TradeList.getMatcher(input) != null) {
//            System.out.println(controller.tradeList());
//        } else if ((matcher = InteractionsWithUserCommands.TradeResponse.getMatcher(input)) != null) {
//            System.out.println(controller.tradeResponse(
//                    matcher.group("response").trim(),
//                    matcher.group("id").trim()
//            ));
//        } else if ((matcher = InteractionsWithUserCommands.TradeHistory.getMatcher(input)) != null) {
//            System.out.println(controller.tradeHistory());
//        } else if (GameMenuCommands.InventoryShow.getMatcher(input) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryShow());
//        } else if ((matcher = GameMenuCommands.InventoryTrash.getMatcher(input)) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().inventoryTrash(
//                    matcher.group("itemName").trim(),
//                    Integer.parseInt(matcher.group("number").trim())
//            ));
//        } else if ((matcher = CheatCommands.CheatAddItem.getMatcher(input)) != null) {
//            System.out.println(((GameView) Menu.GameMenu.getMenu()).getController().cheatAddItem(
//                    matcher.group("itemName").trim(),
//                    Integer.parseInt(matcher.group("count"))
//            ));
//        } else {
//            System.out.println(new Result(false, "invalid command!"));
//        }
//    }

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

    public TradeView(String starter, String other , AppMenu lastView) {

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
