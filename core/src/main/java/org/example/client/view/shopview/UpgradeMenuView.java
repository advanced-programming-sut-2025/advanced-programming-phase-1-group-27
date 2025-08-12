package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.shopControllers.UpgradeMenuController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.client.view.HUDView;
import org.example.common.models.GameAssetManager;
import org.example.server.models.App;
import org.example.server.models.Stacks;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class UpgradeMenuView extends AppMenu {
    private final UpgradeMenuController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image coinImage;
    private final Image creamImage;
    private final Image brownImage;
    private final Image backgroundImage;

    private Label moneyLabel;

    private final TextButton exitButton;

    private final HUDView hudView;

    private ArrayList<Stacks> stockItems;
    private Table stockTable;
    private ScrollPane scrollPane;

    private final Map<String, Integer> upgradeLimit;

    private float timer = 0f;

    private Stage stage;

    public UpgradeMenuView(NPCType npc , AppMenu shopMenu) {
        this.npc = npc;
        controller = new UpgradeMenuController(this , shopMenu);
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);
        coinImage = new Image(GameAssetManager.getGameAssetManager().getCoinTexture());
        coinImage.setSize(coinImage.getWidth() * 3f, coinImage.getHeight() * 3f);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() - (int) npcImage.getHeight() - 20,
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                (int) npcImage.getHeight(),
                30));

        backgroundImage = new Image(GameAssetManager.getGameAssetManager().getPierresGeneralTexture());

        stockItems = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems();

        stockTable = new Table();
        scrollPane = new ScrollPane(stockTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        this.moneyLabel = new Label("", skin);
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));

        exitButton = new TextButton("Exit", skin);

        stage = new Stage(new ScreenViewport());
        hudView = new HUDView(stage);

        upgradeLimit = ClientApp.getCurrentGame().getBlacksmith().getUpgradeLimit();

        setListeners();
    }

    private void displayItems() {
        stockItems = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getItems();
        stockTable.clear();

        float redAreaX = 100f;
        float redAreaWidth = Gdx.graphics.getWidth() - (2 * redAreaX);

        Table firstRow = new Table();
        Label nameLabel = new Label("Name", skin);
        Label qualityLabel = new Label("Quality", skin);
        Label priceLabel = new Label("Price", skin);

        nameLabel.setColor(Color.BLACK);
        priceLabel.setColor(Color.BLACK);
        qualityLabel.setColor(Color.BLACK);

        nameLabel.setFontScale(1.5f);
        priceLabel.setFontScale(1.5f);
        qualityLabel.setFontScale(1.5f);

        firstRow.add(nameLabel).width(700).left();
        firstRow.add(qualityLabel).width(300).right();
        firstRow.add(priceLabel).width(100).right();
        stockTable.add(firstRow).width(redAreaWidth).padBottom(5);
        stockTable.row();
        stockTable.add().colspan(3).height(2).fillX().padBottom(10);
        stockTable.row();

        for (Stacks stacks : stockItems) {
            if (!(stacks.getItem() instanceof ToolType)) {
                continue;
            }
            ToolType toolType = (ToolType) stacks.getItem();
            if (toolType == ToolType.TrainingRod
                    || toolType == ToolType.BambooPole
                    || toolType == ToolType.FiberglassRod
                    || toolType == ToolType.IridiumRod) {
                continue;
            }
            if (toolType == ToolType.BasicBackpack
                    || toolType == ToolType.LargeBackpack
                    || toolType == ToolType.DeluxeBackpack) {
                continue;
            }
            if (toolType == ToolType.MilkPail
                    || toolType == ToolType.Shear
                    || toolType == ToolType.Scythe) {
                continue;
            }
            int price;
            String quality;
            Table row = new Table();
            if (toolType.getLevel() == StackLevel.Iridium) {
                price = 0;
                quality = "Iridium";
            } else if (toolType.getLevel() == StackLevel.Gold) {
                price = 12500;
                quality = "Gold";
            } else if (toolType.getLevel() == StackLevel.Iron) {
                price = 5000;
                quality = "Iron";
            } else if (toolType.getLevel() == StackLevel.Copper) {
                price = 2500;
                quality = "Copper";
            } else { // toolType.getLevel() == StackLevel.Basic
                price = 1000;
                quality = "Basic";
            }
            Label nameLabel1 = new Label(toolType.getName(), skin);
            Label priceLabel1 = new Label(price + " G", skin);
            Label qualityLabel1 = new Label(quality, skin);

            nameLabel1.setColor(Color.BLACK);
            priceLabel1.setColor(Color.BLACK);
            qualityLabel1.setColor(Color.BLACK);

            row.add(nameLabel1).pad(10).width(700).left();
            row.add(qualityLabel1).pad(10).width(300).right();
            row.add(priceLabel1).pad(10).width(100).right();

            float itemHeight = 50f;
            row.setHeight(itemHeight);
            stockTable.add(row).width(redAreaWidth).padBottom(5);
            stockTable.row();

            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playClickSound();
                    //TODO : Harf bezane!
                    controller.upgradeTool(toolType.getName());
                }
            });
        }

        scrollPane.setSize(redAreaWidth, 600);
        scrollPane.setPosition(-120, Gdx.graphics.getHeight() / 2f - 150);
    }

    private void displayMoney(){
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
        moneyLabel.setFontScale(1.5f);
        moneyLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 100);
        coinImage.setPosition(Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - 50);
        stage.addActor(moneyLabel);
        stage.addActor(coinImage);
    }

    private void displayButtons(){
        exitButton.setPosition(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight()/2f - 40);
        stage.addActor(exitButton);
    }

    private void setListeners(){
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });
    }

    private void displayNPC(){
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);
        stage.addActor(npcImage);
    }

    private void displayBackground(){
        brownImage.setPosition(0 , 5);
        creamImage.setPosition( 0, npcImage.getHeight() + 20);
        backgroundImage.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight() - brownImage.getHeight());
        backgroundImage.setPosition(0, brownImage.getHeight() + 20);

        creamImage.setColor(1f, 1f, 1f, 0.5f);
        brownImage.setColor(1f, 1f, 1f, 1f);

//        stage.addActor(backgroundImage);
        stage.addActor(brownImage);
        stage.addActor(creamImage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();
        displayNPC();
        stage.addActor(scrollPane);
        displayItems();
    }

    @Override
    public void render(float v) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        if (timer > 2f) {
            timer = 0f;
            displayItems();
        } else {
            timer += v;
        }
        Main.getBatch().begin();
        hudView.displayOnlyClock();
        Main.getBatch().end();
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
}
