package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.controller.menus.PasswordMenuController;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Lobby;

import java.util.Scanner;

public class PasswordMenuView extends AppMenu {

    private final PasswordMenuController controller;
    private final Lobby lobby;

    private final Label menuDescriptionLabel;

    private final TextField passwordField;

    private final TextButton joinButton;
    private final TextButton backButton;

    private final GraphicalResult errorLabel;

    private Stage stage;

    public PasswordMenuView(Lobby lobby) {

        controller = new PasswordMenuController(this);
        this.lobby = lobby;

        menuDescriptionLabel = new Label("Enter password for Lobby: " + lobby.getName(), skin);

        passwordField = new TextField("", skin);
        joinButton = new TextButton("Join", skin);
        backButton = new TextButton("Back", skin);

        errorLabel = new GraphicalResult();

        setListeners();
    }

    private void showErrorMessage() {
        errorLabel.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f - Gdx.graphics.getHeight() / 9f);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());
    }

    private void showMenuTitle() {
        menuDescriptionLabel.setFontScale(3f);
        menuDescriptionLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuDescriptionLabel);
    }

    private void showInputFields() {
        passwordField.setWidth(Gdx.graphics.getWidth() / 4f);
        passwordField.setPosition((Gdx.graphics.getWidth() - passwordField.getWidth()) / 2f, 7 * Gdx.graphics.getHeight() / 15f - 20);
        stage.addActor(passwordField);
    }

    private void showButtons() {
        backButton.setWidth(Gdx.graphics.getWidth() / 4f);
        joinButton.setWidth(Gdx.graphics.getWidth() / 4f);

        backButton.setPosition(5 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);
        joinButton.setPosition(13 * Gdx.graphics.getWidth() / 24f,
                2 * Gdx.graphics.getHeight() / 12f);

        stage.addActor(backButton);
        stage.addActor(joinButton);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);
        showErrorMessage();
    }

    @Override
    public void render(float delta) {
        errorLabel.update(delta);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showMenuTitle();
        showInputFields();
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

    @Override
    public void executeCommands(Scanner scanner) {

    }

    private void setListeners() {
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.joinGraphicalResult(lobby));
            }
        });


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.exitMenu();
            }
        });
    }

    public TextField getPasswordField() {
        return passwordField;
    }
}
