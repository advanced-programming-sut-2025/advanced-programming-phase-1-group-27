package org.example.client.view.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array; // Correct Array import
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.LobbyMenuController;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Lobby;

import java.util.ArrayList;
import java.util.Scanner;

public class LobbyMenuView extends AppMenu {
    private final LobbyMenuController controller;

    private final Label menuTitleLabel;
    private final Label idLabel;
    private final Label passwordLabel;
    private final Label publicGameLabel;
    private final Label privateGameLabel;

    private final SelectBox<String> publicGamesSelectBox;

    private final TextButton hostButton;
    private final TextButton joinPublicGameButton;
    private final TextButton backButton;
    private final TextButton refreshButton;
    private final TextButton joinPrivateGameButton;

    private final TextField gameIdTextField;
    private final TextField passwordTextField;

    private final GraphicalResult errorLabel;

//    private Table lobbyEntriesTable;
//    private ScrollPane scrollPane;
//    private Lobby selectedLobby = null;
    private float fadeInTimer = 0f;

    private Stage stage;


    public LobbyMenuView() {

        controller = new LobbyMenuController(this);

        errorLabel = new GraphicalResult();
        menuTitleLabel = new Label("Lobby Menu", skin);
        idLabel = new Label("Game ID:", skin);
        passwordLabel = new Label("Password:", skin);
        publicGameLabel = new Label("Public Games:", skin);
        privateGameLabel = new Label("Private Games:", skin);

        publicGamesSelectBox = new SelectBox<>(skin);

        hostButton = new TextButton("Host", skin);
        joinPublicGameButton = new TextButton("Join Public Game", skin);
        backButton = new TextButton("Back", skin);
        refreshButton = new TextButton("Refresh", skin);
        joinPrivateGameButton = new TextButton("Join Private Game", skin);

        gameIdTextField = new TextField("", skin);
        passwordTextField = new TextField("", skin);


//        idLabel = new Label("ID:", skin);
//        menuTitleLabel = new Label("Lobby Menu", skin);
//        hostButton = new TextButton("Host" , skin);
//        joinButton = new TextButton("Join" , skin);
//        backButton = new TextButton("Back" , skin);
//        refreshButton = new TextButton("Refresh", skin);
//        findButton = new TextButton("Find", skin);
//        idTextField = new TextField("", skin);
//        lobbyEntriesTable = new Table(skin);
//        lobbyEntriesTable.top().left();
//
//        // Wrap the lobbyEntriesTable in a ScrollPane
//        scrollPane = new ScrollPane(lobbyEntriesTable, skin);
//        scrollPane.setFadeScrollBars(false); // Keep scrollbars visible for debugging
//        scrollPane.setScrollbarsOnTop(true);
//
//
//        joinButton.setDisabled(true);

        setListeners();
    }


//    private void setupUI() {
//        Table table = new Table();
//        table.setFillParent(true);
//        table.center();
//
//
//        table.add(menuTitleLabel).padBottom(50).row();
//
//        table.add(new Label("Available Lobbies:", skin)).left().padBottom(10).row();
//        table.add(scrollPane).width(Gdx.graphics.getWidth() * 0.7f).height(Gdx.graphics.getHeight() * 0.4f).padBottom(30).row();
//
//        Table buttonTable = new Table();
//        buttonTable.add(backButton).width(200).height(50).padRight(20);
//        buttonTable.add(hostButton).width(200).height(50).padRight(20);
//        buttonTable.add(joinButton).width(200).height(50);
//        buttonTable.add(refreshButton).width(200).height(50);
//
//        table.add(buttonTable).padBottom(20).row();
//
//        stage.addActor(table);
//        stage.addActor(errorLabel.getMessage());
//
//
//        errorLabel.setPosition(Gdx.graphics.getWidth() / 2f - errorLabel.getMessage().getWidth() / 2f,
//                Gdx.graphics.getHeight() - 100); // Position at the top
//        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
//    }


    private void showMenuTitle() {

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);

    }

    private void showLabels(){

        publicGameLabel.setPosition(Gdx.graphics.getWidth() / 20f,  9 * Gdx.graphics.getHeight() / 12f);
        privateGameLabel.setPosition(Gdx.graphics.getWidth()/2f,9*Gdx.graphics.getHeight()/12f);

        idLabel.setPosition(Gdx.graphics.getWidth()/2f + Gdx.graphics.getWidth()/20f,7*Gdx.graphics.getHeight()/12f);
        passwordLabel.setPosition(Gdx.graphics.getWidth()/2f + Gdx.graphics.getWidth()/20f,6*Gdx.graphics.getHeight()/12f);

        stage.addActor(publicGameLabel);
        stage.addActor(privateGameLabel);
        stage.addActor(idLabel);
        stage.addActor(passwordLabel);

    }

    private void showInputFields(){

        gameIdTextField.setWidth(Gdx.graphics.getWidth()/5f);
        passwordTextField.setWidth(Gdx.graphics.getWidth()/5f);

        gameIdTextField.setPosition(Gdx.graphics.getWidth()/2f + 3 * Gdx.graphics.getWidth()/20f,7*Gdx.graphics.getHeight()/12f-20);
        passwordTextField.setPosition(Gdx.graphics.getWidth()/2f + 3 * Gdx.graphics.getWidth()/20f,6*Gdx.graphics.getHeight()/12f-20);

        stage.addActor(gameIdTextField);
        stage.addActor(passwordTextField);

    }

    private void showButtons(){

        joinPrivateGameButton.setWidth(Gdx.graphics.getWidth()/4f);
        joinPublicGameButton.setWidth(Gdx.graphics.getWidth()/4f);
        refreshButton.setWidth(Gdx.graphics.getWidth()/4f);
        backButton.setWidth(Gdx.graphics.getWidth()/4f);
        hostButton.setWidth(Gdx.graphics.getWidth()/4f);


        joinPrivateGameButton.setPosition(Gdx.graphics.getWidth()/2f + (Gdx.graphics.getWidth()/2f-joinPrivateGameButton.getWidth())/2,
                4*Gdx.graphics.getHeight()/12f);
        joinPublicGameButton.setPosition((Gdx.graphics.getWidth()/2f-joinPublicGameButton.getWidth())/2,
                4*Gdx.graphics.getHeight()/12f);
        refreshButton.setPosition((Gdx.graphics.getWidth()/2f-refreshButton.getWidth())/2,
                6*Gdx.graphics.getHeight()/12f);
        backButton.setPosition(5 * Gdx.graphics.getWidth()/24f,
                2*Gdx.graphics.getHeight()/12f);
        hostButton.setPosition(13 * Gdx.graphics.getWidth()/24f,
                2*Gdx.graphics.getHeight()/12f);

        stage.addActor(joinPrivateGameButton);
        stage.addActor(joinPublicGameButton);
        stage.addActor(refreshButton);
        stage.addActor(backButton);
        stage.addActor(hostButton);

    }

    private void showSelectBox(){

        ///  TODO: List game haro az server begire o neshon bede

        Array<String> gameList = new Array<>();
        gameList.add("Game1");
        gameList.add("Game2");
        gameList.add("Game3");
        gameList.add("Game4");
        gameList.add("Game5");
        gameList.add("Game6");
        gameList.add("Game7");
        gameList.add("Game8");


        publicGamesSelectBox.setItems(gameList);

        publicGamesSelectBox.setWidth(Gdx.graphics.getWidth()/5f);

        publicGamesSelectBox.setPosition((Gdx.graphics.getWidth() / 2f - publicGamesSelectBox.getWidth())/2f,  8 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(publicGamesSelectBox);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

//        setupUI();
    }

    @Override
    public void render(float delta) {

        errorLabel.update(delta);

//        if (fadeInTimer < 1f) {
//            fadeInTimer += delta;
//        } else {
//            fadeInTimer = 1f;
//        }
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showLabels();
        showInputFields();
        showButtons();
        showSelectBox();

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
                playClickSound(); // Assuming this method exists
                ///  TODO
            }
        });

        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO
            }
        });

        joinPublicGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO
            }
        });

        joinPrivateGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO
            }
        });


//        backButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playClickSound(); // Assuming this method exists
//                controller.exitMenu();
//            }
//        });
//
//        hostButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playClickSound();
////                controller.hostGame();
//            }
//        });
//
//        joinButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                playClickSound();
//                if (selectedLobby != null) {
////                    controller.joinLobby(selectedLobby);
//                } else {
////                    errorLabel.setMessage("Please select a lobby to join.");
//                }
//            }
//        });
//
////        lobbyListUI.addListener(new ClickListener() {
////            @Override
////            public void clicked(InputEvent event, float x, float y) {
////                selectedLobby = lobbyListUI.getSelected();
////                if (selectedLobby != null) {
////                    System.out.println("Selected Lobby: " + selectedLobby.toString());
////                    joinButton.setDisabled(false);
////                } else {
////                    joinButton.setDisabled(true);
////                }
////            }
////        });
//
//        refreshButton.addListener(new ClickListener() {
//            public void  clicked(InputEvent event, float x, float y) {
//                playClickSound();
//                //TODO : Update lobby list
//            }
//        });
    }

//    public void updateLobbyList(Array<Lobby> lobbies) {
//        lobbyEntriesTable.clearChildren(); // Clear existing entries
//
//        if (lobbies.size == 0) {
//            lobbyEntriesTable.add(new Label("No lobbies available.", skin)).center().pad(20);
//            joinButton.setDisabled(true);
//            selectedLobby = null;
//            return;
//        }
//
////        for (final Lobby lobby : lobbies) {
////            Table lobbyEntry = new Table(skin);
////            lobbyEntry.setBackground("default-rect");
////            lobbyEntry.pad(10);
////            lobbyEntry.left().top();
////
////            // Lobby Name Label
////            Label nameLabel = new Label(lobby.getName(), skin);
////            lobbyEntry.add(nameLabel).expandX().fillX().left().padRight(10);
////
////            // Private/Lock Icon (if private)
////            if (lobby.isPrivate()) {
////                Label lockIcon = new Label("🔒", skin);
////                lobbyEntry.add(lockIcon).padRight(10);
////            } else {
////                lobbyEntry.add().padRight(10);
////            }
////
////            // Player Count Label
////            Label playerCountLabel = new Label(lobby.getPlayerCount() + "/" + 4, skin);
////            lobbyEntry.add(playerCountLabel).right();
////
////            lobbyEntry.row();
////
////            // Add a click listener to the entire lobby entry row
////            lobbyEntry.addListener(new ClickListener() {
////                @Override
////                public void clicked(InputEvent event, float x, float y) {
////                    playClickSound();
////                    selectLobbyEntry(lobby);
////                }
////            });
////
////
////            lobbyEntriesTable.add(lobbyEntry).growX().padBottom(5).row();
////        }
//        // Ensure the scroll pane's internal table updates its layout
//        lobbyEntriesTable.invalidateHierarchy();
//        scrollPane.invalidateHierarchy();
//        joinButton.setDisabled(selectedLobby == null);
//    }
}
