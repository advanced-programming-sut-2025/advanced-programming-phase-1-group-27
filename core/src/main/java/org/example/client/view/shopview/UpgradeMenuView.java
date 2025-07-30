package org.example.client.view.shopview;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import org.example.client.controller.shopControllers.PurchaseMenuController;
import org.example.client.controller.shopControllers.ShopController;
import org.example.client.controller.shopControllers.UpgradeMenuController;
import org.example.client.model.ClientApp;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Stock;
import org.example.server.models.enums.NPCType;

import java.util.ArrayList;
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

    private ArrayList<Stock> stockItems;
    private Table stockTable;
    private ScrollPane scrollPane;

    private Stage stage;

    public UpgradeMenuView(NPCType npc) {
        this.npc = npc;
//        controller = new ShopController(this , npc);
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

        stockItems = ClientApp.getCurrentGame().getShop("PierreGeneralStore").getStock();

        stockTable = new Table();
        scrollPane = new ScrollPane(stockTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        showOnlyAvailableCheckBox = new CheckBox("Filter", skin);
        showOnlyAvailableCheckBox.setChecked(true);
        moneyLabel.setText(String.valueOf(ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));

        exitButton = new TextButton("Exit", skin);

        setListeners();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
