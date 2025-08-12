package org.example.client.view.InteractionMenus.Gift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.RoundedRectangleTexture;
import org.example.client.view.AppMenu;
import org.example.server.models.enums.NPCType;

import java.util.Scanner;

public class GiftMenuView extends AppMenu {
    private final InteractionsWithNPCController controller;
    private final NPCType npc;

    private final Image npcImage;
    private final Image creamImage;
    private final Image brownImage;

    private final TextButton GiftButton;
    private final TextButton exitButton;

    private Stage stage;

    public GiftMenuView(String npcName) {
        controller = new InteractionsWithNPCController();
//        npc = InteractionsWithNPCController.findNPC(npcName).getType();
        npc = NPCType.Sebastian;
        npcImage = new Image(new Texture(npc.getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);

        creamImage = new Image(RoundedRectangleTexture.createCreamRoundedRect(
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight() - (int) npcImage.getHeight() - 20,
                30));

        brownImage = new Image(RoundedRectangleTexture.createBrownRoundedRect(
                Gdx.graphics.getWidth(),
                (int) npcImage.getHeight(),
                30));

        GiftButton = new TextButton("Gift", skin);
        exitButton = new TextButton("Exit", skin);

        stage = new Stage(new ScreenViewport());
        setListeners();
    }


    private void displayButtons() {
        exitButton.setPosition(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() / 2f - 160);
        stage.addActor(exitButton);

        GiftButton.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2f - 40);
        stage.addActor(GiftButton);
    }

    private void setListeners() {
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playClickSound();
//                controller.exitMenu();
//            }
//        });
//
//       GiftButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playClickSound();
////                controller.giftNPC(npc.getName() , stock.getName());
//            }
//        });
    }

    private void displayNPC() {
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);
        stage.addActor(npcImage);
    }

    private void displayBackground() {
        brownImage.setPosition(0, 5);
        creamImage.setPosition(0, npcImage.getHeight() + 20);

        creamImage.setColor(1f, 1f, 1f, 0.5f);
        brownImage.setColor(1f, 1f, 1f, 1f);

        stage.addActor(brownImage);
        stage.addActor(creamImage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        displayBackground();
        displayButtons();
        displayNPC();
    }

    @Override
    public void render(float v) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Main.getBatch().begin();
        Main.getBatch().end();
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

    @Override
    public void executeCommands(Scanner scanner) {
    }
}
