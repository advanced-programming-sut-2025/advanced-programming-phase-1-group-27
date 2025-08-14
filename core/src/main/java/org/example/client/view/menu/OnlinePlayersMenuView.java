package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.OnlinePlayersMenuController;
import org.example.client.view.AppMenu;

import java.util.HashMap;
import java.util.Scanner;

public class OnlinePlayersMenuView extends AppMenu {
    private final OnlinePlayersMenuController controller;
    private final Label menuTitle;
    private final ScrollPane onlinePlayersScrollPane;
    private final TextButton backButton;
    private final Table playersTable;

    private Stage stage;

    public OnlinePlayersMenuView() {
        this.controller = new OnlinePlayersMenuController();

        this.menuTitle = new Label("Online Players Menu", skin);
        this.backButton = new TextButton("Back", skin);
        this.playersTable = new Table();
        this.onlinePlayersScrollPane = new ScrollPane(playersTable, skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });
    }

    private void showMenuTitle() {
        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitle);
    }


    public void updatePlayersList() {
        playersTable.clear();

        playersTable.add(new Label("Player", skin)).pad(10);
        playersTable.add(new Label("Lobby", skin)).pad(10);
        playersTable.row();

        playersTable.add().colspan(2).height(2).fillX().padBottom(10);
        playersTable.row();

        for (HashMap.Entry<String, String> entry : controller.getOnlinePlayers().entrySet()) {
            playersTable.add(new Label(entry.getKey(), skin)).pad(10).left().width(200);
            playersTable.add(new Label(entry.getValue(), skin)).pad(10).right().width(200);
            playersTable.row();
        }

        onlinePlayersScrollPane.setFadeScrollBars(false);
        onlinePlayersScrollPane.setScrollbarsVisible(true);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        stage.addActor(mainTable);

        mainTable.add(onlinePlayersScrollPane).colspan(2).expand().fill().pad(10).row();
        mainTable.add(backButton).width(200).pad(20);
    }

    @Override
    public void render(float delta) {

        updatePlayersList();
        showMenuTitle();

        stage.act(delta);
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

    @Override
    public void executeCommands(Scanner scanner) {

    }
}
