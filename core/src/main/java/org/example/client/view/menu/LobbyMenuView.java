package org.example.client.view.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.LobbyMenuController;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Lobby;

import java.util.ArrayList;
import java.util.Scanner;

public class LobbyMenuView extends AppMenu {
    private final LobbyMenuController controller;

    private final Label menuTitleLabel;
    private final Label idLabel;
    private final Label publicGameLabel;
    private final Label privateGameLabel;
    private final Label noActiveLobbyLabel;

    private final SelectBox<String> publicGamesSelectBox;

    private final TextButton hostButton;
    private final TextButton joinPublicGameButton;
    private final TextButton backButton;
    private final TextButton refreshButton;
    private final TextButton findGameButton;
    private final TextButton playersButton;

    private final TextField gameIdTextField;

    private final GraphicalResult errorLabel;

    private ArrayList<Lobby> lobbies;

    private Stage stage;


    public LobbyMenuView() {
        controller = new LobbyMenuController(this);

        errorLabel = new GraphicalResult();
        menuTitleLabel = new Label("Lobby Menu", skin);
        idLabel = new Label("Game ID:", skin);
        publicGameLabel = new Label("Public Games:", skin);
        privateGameLabel = new Label("Private Games:", skin);
        noActiveLobbyLabel = new Label("No Active Lobby ):", skin);

        publicGamesSelectBox = new SelectBox<>(skin);

        hostButton = new TextButton("Host", skin);
        joinPublicGameButton = new TextButton("Join Game", skin);
        backButton = new TextButton("Back", skin);
        refreshButton = new TextButton("Refresh", skin);
        findGameButton = new TextButton("Find Game", skin);
        playersButton = new TextButton("Players", skin);


        gameIdTextField = new TextField("", skin);

        lobbies = controller.getLobbies();
        setListeners();
    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);

    }

    private void showLabels() {

        publicGameLabel.setPosition(Gdx.graphics.getWidth() / 20f, 9 * Gdx.graphics.getHeight() / 12f);
        privateGameLabel.setPosition(Gdx.graphics.getWidth() / 2f, 9 * Gdx.graphics.getHeight() / 12f);
        noActiveLobbyLabel.setPosition((Gdx.graphics.getWidth() / 2f - noActiveLobbyLabel.getWidth()) / 2f, 8 * Gdx.graphics.getHeight() / 12f);

        idLabel.setPosition(Gdx.graphics.getWidth() / 2f + Gdx.graphics.getWidth() / 20f, 7 * Gdx.graphics.getHeight() / 12f);

        noActiveLobbyLabel.setVisible(lobbies.isEmpty());

        stage.addActor(idLabel);
        stage.addActor(noActiveLobbyLabel);

    }

    private void showInputFields() {

        gameIdTextField.setWidth(Gdx.graphics.getWidth() / 5f);

        gameIdTextField.setPosition(Gdx.graphics.getWidth() / 2f + 3 * Gdx.graphics.getWidth() / 20f, 7 * Gdx.graphics.getHeight() / 12f - 20);

        stage.addActor(gameIdTextField);

    }

    private void showButtons() {
        findGameButton.setWidth(Gdx.graphics.getWidth() / 4f);
        joinPublicGameButton.setWidth(Gdx.graphics.getWidth() / 4f);
        refreshButton.setWidth(Gdx.graphics.getWidth() / 4f);
        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        hostButton.setWidth(Gdx.graphics.getWidth() / 4f);
        playersButton.setWidth(Gdx.graphics.getWidth() / 4f);

        findGameButton.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - findGameButton.getWidth()) / 2,
                4 * Gdx.graphics.getHeight() / 12f);
        joinPublicGameButton.setPosition((Gdx.graphics.getWidth() / 2f - joinPublicGameButton.getWidth()) / 2,
                4 * Gdx.graphics.getHeight() / 12f);
        refreshButton.setPosition((Gdx.graphics.getWidth() / 2f - refreshButton.getWidth()) / 2,
                6 * Gdx.graphics.getHeight() / 12f);
        backButton.setPosition(2 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);
        hostButton.setPosition(9 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);
        playersButton.setPosition(16 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(findGameButton);
        stage.addActor(joinPublicGameButton);
        stage.addActor(refreshButton);
        stage.addActor(backButton);
        stage.addActor(hostButton);
        stage.addActor(playersButton);
    }

    private void showSelectBox() {
        Array<String> lobbyInfos = new Array<>();

        for (Lobby lobby : lobbies) {
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

        publicGamesSelectBox.setItems(lobbyInfos);

        publicGamesSelectBox.setWidth(Gdx.graphics.getWidth() / 5f);

        publicGamesSelectBox.setPosition((Gdx.graphics.getWidth() / 2f - publicGamesSelectBox.getWidth()) / 2f, 8 * Gdx.graphics.getHeight() / 12f);

        publicGamesSelectBox.setVisible(!lobbies.isEmpty());

        stage.addActor(publicGamesSelectBox);

    }

    private void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth() / 8f, 9 * Gdx.graphics.getHeight() / 12f);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());

    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

    }

    @Override
    public void render(float delta) {

        errorLabel.update(delta);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showLabels();
        showInputFields();
        showButtons();
        showSelectBox();
        showErrorMessage();

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
                playClickSound();
                controller.exitMenu();
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                lobbies = controller.getLobbies();
                showSelectBox();
            }
        });

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.host();
            }
        });

        joinPublicGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.join());
            }
        });

        findGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.findViaGraphicalResult());
            }
        });

        playersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToPlayersMenu();
            }
        });
    }

    public TextField getGameIdTextField() {
        return gameIdTextField;
    }

    public SelectBox<String> getPublicGamesSelectBox() {
        return publicGamesSelectBox;
    }
}
