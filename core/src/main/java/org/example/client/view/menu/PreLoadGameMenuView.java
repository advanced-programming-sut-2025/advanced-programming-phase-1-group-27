package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.PreLoadGameMenuController;
import org.example.client.view.AppMenu;
import org.example.common.models.Lobby;

import java.util.ArrayList;
import java.util.Scanner;

public class PreLoadGameMenuView extends AppMenu {
    private final PreLoadGameMenuController controller;

    private final SelectBox<String> selectedLobbiesToJoin;

    private final TextButton loadGameButton;
    private final TextButton backButton;

    private final ArrayList<Lobby> selectedLobbiesToJoinList;

    private Stage stage;

    public PreLoadGameMenuView() {
        this.controller = new PreLoadGameMenuController(this);

        selectedLobbiesToJoin = new SelectBox<>(skin);

        loadGameButton = new TextButton("Load Game", skin);
        backButton = new TextButton("Back", skin);

        selectedLobbiesToJoinList = controller.getLobbiesToJoin();

        setListeners();
    }


    private void showButtons() {
        loadGameButton.setPosition(Gdx.graphics.getWidth() / 2f - loadGameButton.getWidth() / 2f,
                4 * Gdx.graphics.getHeight() / 12f);
        backButton.setPosition(Gdx.graphics.getWidth() / 2f - backButton.getWidth() / 2f,
                2 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(loadGameButton);
        stage.addActor(backButton);
    }

    private void showSelectBoxJoin() {
        Array<String> lobbyInfos = new Array<>();

        for (Lobby lobby : selectedLobbiesToJoinList) {
            if (lobby.isVisible()) {
                StringBuilder s = new StringBuilder();
                s.append(lobby.getId()).append(" ").append(lobby.getName());
                if (!lobby.isPublic()) {
                    s.append(" private");
                } else {
                    s.append(" public");
                }
                lobbyInfos.add(s.toString());
            }
        }

        selectedLobbiesToJoin.setItems(lobbyInfos);

        selectedLobbiesToJoin.setWidth(Gdx.graphics.getWidth() / 5f);

        selectedLobbiesToJoin.setPosition((Gdx.graphics.getWidth() - selectedLobbiesToJoin.getWidth()) / 2f, 6 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(selectedLobbiesToJoin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);
        Label label = new Label("Choose a game to load", skin);
        label.setFontScale(3f);
        label.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        showButtons();
        showSelectBoxJoin();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    private void setListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.loadGame(Integer.parseInt(selectedLobbiesToJoin.getSelected().split(" ")[0]));
            }
        });

    }

    public SelectBox<String> getSelectedLobbiesForJoin() {
        return selectedLobbiesToJoin;
    }
}
