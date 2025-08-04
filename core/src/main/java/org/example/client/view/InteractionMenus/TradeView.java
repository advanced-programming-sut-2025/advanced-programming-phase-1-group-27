package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.Scanner;

public class TradeView extends AppMenu {
    private final TradeController controller;

    private ArrayList<Stacks> selectedCurrent;
    private ArrayList<Stacks> selectedOther;
    private ArrayList<Stacks> othersInventory;

    private final String starter;
    private final String other;

    private Table stockTableCurrent;
    private ScrollPane scrollPaneCurrent;

    private Table stockTableOther;
    private ScrollPane scrollPaneOther;

    public TradeView(String starter, String other) {
        controller = new TradeController();
        othersInventory = InteractionsWithUserController.getInventory(
                ClientApp.getLoggedInUser().getUsername().equals(starter) ? other : starter
        );
        selectedCurrent = new ArrayList<>();
        selectedOther = new ArrayList<>();
        this.starter = starter;
        this.other = other;

        stockTableCurrent = new Table();
        stockTableOther = new Table();
        scrollPaneCurrent = new ScrollPane(stockTableCurrent, skin);
        scrollPaneCurrent.setFadeScrollBars(false);
        scrollPaneCurrent.setScrollingDisabled(true, false);
        scrollPaneOther = new ScrollPane(stockTableOther, skin);
        scrollPaneOther.setFadeScrollBars(false);
        scrollPaneOther.setScrollingDisabled(true, false);

    }

    private void displayCurrentItems() {
        stockTableCurrent.clear();

        float redAreaX = 10f;
        float redAreaWidth = Gdx.graphics.getWidth()/2f - (2 * redAreaX);

        Table firstRow = new Table();
        Label nameLabel = new Label("Name", skin);
        Label qualityLabel = new Label("Quality", skin);
        Label quantityLabel = new Label("Quantity", skin);

        nameLabel.setColor(Color.BLACK);
        qualityLabel.setColor(Color.BLACK);
        quantityLabel.setColor(Color.BLACK);

        nameLabel.setFontScale(1.5f);
        qualityLabel.setFontScale(1.5f);
        quantityLabel.setFontScale(1.5f);

        firstRow.add(nameLabel).width(70).left();
        firstRow.add(qualityLabel).width(30).right();
        firstRow.add(quantityLabel).width(10).right();
        stockTableCurrent.add(firstRow).width(redAreaWidth).padBottom(5);
        stockTableCurrent.row();
        stockTableCurrent.add().colspan(3).height(2).fillX().padBottom(10);
        stockTableCurrent.row();

        for (Stacks stacks : selectedCurrent) {
            Table row = new Table();
            Label nameLabel1 = new Label(stacks.getItem().getName(), skin);
            Label qualityLabel1 = new Label(stacks.getStackLevel().toString(), skin);
            Label countLabel1 = new Label(stacks.getQuantity() + "", skin);

            nameLabel1.setColor(Color.BLACK);
            qualityLabel1.setColor(Color.BLACK);
            countLabel1.setColor(Color.BLACK);

            row.add(nameLabel1).pad(10).width(700).left();
            row.add(qualityLabel1).pad(10).width(300).right();
            row.add(countLabel1).pad(10).width(100).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            stockTableCurrent.add(row).width(redAreaWidth).padBottom(5);
            stockTableCurrent.row();
            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    // TODO : delete
                }
            });
        }

        scrollPaneCurrent.setSize(redAreaWidth, 60);
        scrollPaneCurrent.setPosition(-120, Gdx.graphics.getHeight() / 2f - 150);
    }

    private void displayOtherItems() {
        stockTableOther.clear();

        float redAreaX = 10f;
        float redAreaWidth = Gdx.graphics.getWidth()/2f - (2 * redAreaX);

        Table firstRow = new Table();
        Label nameLabel = new Label("Name", skin);
        Label qualityLabel = new Label("Quality", skin);
        Label quantityLabel = new Label("Quantity", skin);

        nameLabel.setColor(Color.BLACK);
        qualityLabel.setColor(Color.BLACK);
        quantityLabel.setColor(Color.BLACK);

        nameLabel.setFontScale(1.5f);
        qualityLabel.setFontScale(1.5f);
        quantityLabel.setFontScale(1.5f);

        firstRow.add(nameLabel).width(70).left();
        firstRow.add(qualityLabel).width(30).right();
        firstRow.add(quantityLabel).width(10).right();
        stockTableOther.add(firstRow).width(redAreaWidth).padBottom(5);
        stockTableOther.row();
        stockTableOther.add().colspan(3).height(2).fillX().padBottom(10);
        stockTableOther.row();

        for (Stacks stacks : selectedOther) {
            Table row = new Table();
            Label nameLabel1 = new Label(stacks.getItem().getName(), skin);
            Label qualityLabel1 = new Label(stacks.getStackLevel().toString(), skin);
            Label countLabel1 = new Label(stacks.getQuantity() + "", skin);

            nameLabel1.setColor(Color.BLACK);
            qualityLabel1.setColor(Color.BLACK);
            countLabel1.setColor(Color.BLACK);

            row.add(nameLabel1).pad(10).width(700).left();
            row.add(qualityLabel1).pad(10).width(300).right();
            row.add(countLabel1).pad(10).width(100).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            stockTableOther.add(row).width(redAreaWidth).padBottom(5);
            stockTableOther.row();
        }

        scrollPaneOther.setSize(redAreaWidth, 60);
        scrollPaneOther.setPosition(-120, Gdx.graphics.getHeight() / 2f - 150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

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

    public TradeController getController() {
        return controller;
    }

    @Override
    public void executeCommands(Scanner scanner) {
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
    }
}
