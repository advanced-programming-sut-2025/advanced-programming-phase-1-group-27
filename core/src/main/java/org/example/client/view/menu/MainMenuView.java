package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.menus.MainMenuController;
import org.example.client.view.AppMenu;

import java.util.Scanner;

public class MainMenuView extends AppMenu {

    private final MainMenuController controller;
    private final Label menuTitleLabel;
    private final TextButton lobbyMenuButton;
    private final TextButton profileMenuButton;
    private final TextButton userInfoMenuButton;
    private final TextButton logoutButton;
    private Stage stage;
    private float fadeInTimer = 0f;

    public MainMenuView() {

        controller = new MainMenuController(this);

        menuTitleLabel = new Label("Main Menu", skin);

        lobbyMenuButton = new TextButton("Lobby Menu", skin);
        profileMenuButton = new TextButton("Profile Menu", skin);
        userInfoMenuButton = new TextButton("User Info", skin);
        logoutButton = new TextButton("Logout", skin);

        setListeners();
    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(4f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f);
        stage.addActor(menuTitleLabel);

    }

    private void showGameLogo() {

        stardewValleyText.setScale(fadeInTimer * 2);

        stardewValleyText.setRotation(fadeInTimer * 3 * 360);
        stardewValleyText.setColor(stardewValleyText.getColor().r, stardewValleyText.getColor().g, stardewValleyText.getColor().b, fadeInTimer);

        stardewValleyText.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - stardewValleyText.getWidth() * fadeInTimer * 2) / 2,
                (Gdx.graphics.getHeight() - stardewValleyText.getHeight() * fadeInTimer * 2) / 2
        );
        stage.addActor(stardewValleyText);
    }

    private void showButtons() {

        lobbyMenuButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        profileMenuButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        userInfoMenuButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        logoutButton.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);

        lobbyMenuButton.setPosition(Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 7f);
        profileMenuButton.setPosition(Gdx.graphics.getWidth() / 8f, 3 * Gdx.graphics.getHeight() / 7f);
        userInfoMenuButton.setPosition(Gdx.graphics.getWidth() / 8f, 2 * Gdx.graphics.getHeight() / 7f);
        logoutButton.setPosition(Gdx.graphics.getWidth() / 8f, 1 * Gdx.graphics.getHeight() / 7f);

        stage.addActor(lobbyMenuButton);
        stage.addActor(profileMenuButton);
        stage.addActor(userInfoMenuButton);
        stage.addActor(logoutButton);
    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);
    }

    @Override
    public void render(float delta) {
        if (fadeInTimer < 1f) {
            fadeInTimer += delta;
        } else {
            fadeInTimer = 1f;
        }

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showGameLogo();
        showButtons();
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

    private void setListeners() {
        lobbyMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToLobbyMenu();
            }
        });

        profileMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToProfileMenu();
            }
        });

        userInfoMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToUserInfoMenu();
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.logout();
            }
        });


    }

    public void executeCommands(Scanner scanner) {

    }
}
