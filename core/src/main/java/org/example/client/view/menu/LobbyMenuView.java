package org.example.client.view.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List; // Correct List import
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane; // ScrollPane import
import com.badlogic.gdx.scenes.scene2d.ui.Table; // Table import for layout
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array; // Correct Array import
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.LobbyMenuController;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Lobby;

import java.util.Scanner;

public class LobbyMenuView extends AppMenu {
    private final LobbyMenuController controller;

    private final Label menuTitleLabel;
    private final TextButton hostButton;
    private final TextButton joinButton;
    private final TextButton backButton;
    private final TextButton refreshButton;

    private final GraphicalResult errorLabel;

    private List<Lobby> lobbyListUI;
    private ScrollPane scrollPane;
    private Lobby selectedLobby = null;
    private float fadeInTimer = 0f;

    private Stage stage;


    public LobbyMenuView() {
        this.controller = new LobbyMenuController(this);

        errorLabel = new GraphicalResult();
        menuTitleLabel = new Label("Lobby Menu", skin);
        hostButton = new TextButton("Host" , skin);
        joinButton = new TextButton("Join" , skin);
        backButton = new TextButton("Back" , skin);
        refreshButton = new TextButton("Refresh", skin);

        lobbyListUI = new List<>(skin);
        scrollPane = new ScrollPane(lobbyListUI, skin);


        joinButton.setDisabled(true);

        setListeners();
    }


    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();


        table.add(menuTitleLabel).padBottom(50).row();

        table.add(new Label("Available Lobbies:", skin)).left().padBottom(10).row();
        table.add(scrollPane).width(Gdx.graphics.getWidth() * 0.7f).height(Gdx.graphics.getHeight() * 0.4f).padBottom(30).row();

        Table buttonTable = new Table();
        buttonTable.add(backButton).width(200).height(50).padRight(20);
        buttonTable.add(hostButton).width(200).height(50).padRight(20);
        buttonTable.add(joinButton).width(200).height(50);
        buttonTable.add(refreshButton).width(200).height(50);

        table.add(buttonTable).padBottom(20).row();

        stage.addActor(table);
        stage.addActor(errorLabel.getMessage());


        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - errorLabel.getMessage().getWidth() / 2f,
                Gdx.graphics.getHeight() - 100); // Position at the top
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        setupUI();

    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);

        if (fadeInTimer < 1f) {
            fadeInTimer += delta;
        } else {
            fadeInTimer = 1f;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

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

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void setListeners() {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound(); // Assuming this method exists
                controller.exitMenu();
            }
        });

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
//                controller.hostGame();
            }
        });

        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                if (selectedLobby != null) {
//                    controller.joinLobby(selectedLobby);
                } else {
//                    errorLabel.setMessage("Please select a lobby to join.");
                }
            }
        });

        lobbyListUI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedLobby = lobbyListUI.getSelected();
                if (selectedLobby != null) {
                    System.out.println("Selected Lobby: " + selectedLobby.toString());
                    joinButton.setDisabled(false);
                } else {
                    joinButton.setDisabled(true);
                }
            }
        });

        refreshButton.addListener(new ClickListener() {
            public void  clicked(InputEvent event, float x, float y) {
                playClickSound();
                //TODO : Update lobby list
            }
        });
    }

    public void updateLobbyList(Array<Lobby> lobbies) {
        lobbyListUI.setItems(lobbies);

        if (lobbies.size == 0) {
            joinButton.setDisabled(true);
            selectedLobby = null;
        }
    }
}
