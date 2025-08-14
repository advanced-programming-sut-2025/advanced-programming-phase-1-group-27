package org.example.client.view.InteractionMenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.InteractionMenus.Gift.GiftNpcMenuView;
import org.example.client.view.OutsideView;
import org.example.common.models.NPCs.NPC;

import java.util.Scanner;

public class NpcMenuView extends AppMenu {

    private final InteractionsWithNPCController controller;
    private final NPC npc;
    private final Label npcManuLabel;
    private final TextButton backButton;
    private final TextButton questMenuButton;
    private final TextButton giftButton;
    private final Image npcImage;
    private Stage stage;


    public NpcMenuView(String npcName) {

        this.controller = new InteractionsWithNPCController();
        npc = InteractionsWithNPCController.findNPC(npcName);
        controller.meetNPC(npcName);
        ClientApp.setNonMainMenu(this);
        npcImage = new Image(new Texture(npc.getType().getAddress()));
        npcImage.setSize(npcImage.getWidth() * 2.5f, npcImage.getHeight() * 2.5f);


        npcManuLabel = new Label("NPC Menu", skin);
        backButton = new TextButton("Back", skin);
        questMenuButton = new TextButton("Quest Menu", skin);
        giftButton = new TextButton("Gift Menu", skin);

        setListeners();

    }

    private void displayLabels() {

        npcManuLabel.setFontScale(3f);
        npcManuLabel.setPosition(Gdx.graphics.getWidth() / 8f, 3 * Gdx.graphics.getHeight() / 4f);

        stage.addActor(npcManuLabel);

    }

    private void displayButtons() {

        giftButton.setWidth(Gdx.graphics.getWidth() / 4f);
        questMenuButton.setWidth(Gdx.graphics.getWidth() / 4f);
        backButton.setWidth(Gdx.graphics.getWidth() / 4f);

        giftButton.setPosition(3 * Gdx.graphics.getWidth() / 16f, 4 * Gdx.graphics.getHeight() / 8f);
        questMenuButton.setPosition(3 * Gdx.graphics.getWidth() / 16f, 3 * Gdx.graphics.getHeight() / 8f);
        backButton.setPosition(3 * Gdx.graphics.getWidth() / 16f, 2 * Gdx.graphics.getHeight() / 8f);


        stage.addActor(backButton);
        stage.addActor(questMenuButton);
        stage.addActor(giftButton);

    }

    private void displayNPC() {
        npcImage.setPosition(Gdx.graphics.getWidth() - npcImage.getWidth() - 20, 0);
        stage.addActor(npcImage);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menuBackground);

        displayButtons();
        displayLabels();
        displayNPC();

    }

    @Override
    public void render(float v) {

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    public void setListeners() {

        giftButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new GiftNpcMenuView(npc.getType()));
            }
        });

        questMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new QuestNPCMenuView(npc.getType().getName()));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new OutsideView());
            }
        });

    }


    @Override
    public void executeCommands(Scanner scanner) {

    }

}
