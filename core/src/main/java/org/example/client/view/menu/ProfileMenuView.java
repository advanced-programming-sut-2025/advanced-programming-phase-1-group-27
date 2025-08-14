package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.menus.ProfileMenuController;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;

import java.util.Scanner;

public class ProfileMenuView extends AppMenu {

    private final ProfileMenuController controller;
    private final Label menuTitleLabel;
    private final Label newUsernameLabel;
    private final Label newPasswordLabel;
    private final Label newEmailLabel;
    private final Label newNicknameLabel;
    private final Label passwordLabel;
    private final TextField newUsernameTextField;
    private final TextField newPasswordTextField;
    private final TextField newEmailTextField;
    private final TextField newNicknameTextField;
    private final TextField passwordTextField;
    private final TextButton changeButton;
    private final TextButton avatarMenuButton;
    private final TextButton userInfoMenuButton;
    private final TextButton backButton;
    private final GraphicalResult errorLabel;
    private Stage stage;
    private float fadeInTimer = 0f;

    public ProfileMenuView() {

        controller = new ProfileMenuController(this);

        menuTitleLabel = new Label("Profile Menu", skin);
        newUsernameLabel = new Label("New Username:", skin);
        newPasswordLabel = new Label("New Password:", skin);
        newEmailLabel = new Label("New Email:", skin);
        newNicknameLabel = new Label("New Nickname:", skin);
        passwordLabel = new Label("Password:", skin);

        newUsernameTextField = new TextField("", skin);
        newPasswordTextField = new TextField("", skin);
        newEmailTextField = new TextField("", skin);
        newNicknameTextField = new TextField("", skin);
        passwordTextField = new TextField("", skin);

        changeButton = new TextButton("Change", skin);
        avatarMenuButton = new TextButton("Avatar Menu", skin);
        userInfoMenuButton = new TextButton("User Info", skin);
        backButton = new TextButton("Back", skin);

        errorLabel = new GraphicalResult();

        setListeners();

    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);

    }

    private void showLabels() {

        newUsernameLabel.setWidth(Gdx.graphics.getWidth() / 5f);
        newNicknameLabel.setWidth(Gdx.graphics.getWidth() / 5f);
        newEmailLabel.setWidth(Gdx.graphics.getWidth() / 5f);
        newPasswordLabel.setWidth(Gdx.graphics.getWidth() / 5f);
        passwordLabel.setWidth(Gdx.graphics.getWidth() / 5f);


        newUsernameLabel.setPosition(2 * Gdx.graphics.getWidth() / 10f, 16 * Gdx.graphics.getHeight() / 24f);
        newNicknameLabel.setPosition(2 * Gdx.graphics.getWidth() / 10f, 13 * Gdx.graphics.getHeight() / 24f);
        newEmailLabel.setPosition(2 * Gdx.graphics.getWidth() / 10f, 10 * Gdx.graphics.getHeight() / 24f);
        newPasswordLabel.setPosition(2 * Gdx.graphics.getWidth() / 10f, 7 * Gdx.graphics.getHeight() / 24f);
        passwordLabel.setPosition(2 * Gdx.graphics.getWidth() / 10f, 4 * Gdx.graphics.getHeight() / 24f);

        stage.addActor(newUsernameLabel);
        stage.addActor(newNicknameLabel);
        stage.addActor(newEmailLabel);
        stage.addActor(newPasswordLabel);
        stage.addActor(passwordLabel);

    }

    private void showFields() {

        newUsernameTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newNicknameTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newEmailTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newPasswordTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        passwordTextField.setWidth(Gdx.graphics.getWidth() / 5f);


        newUsernameTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 16 * Gdx.graphics.getHeight() / 24f - 20);
        newNicknameTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 13 * Gdx.graphics.getHeight() / 24f - 20);
        newEmailTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 10 * Gdx.graphics.getHeight() / 24f - 20);
        newPasswordTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 7 * Gdx.graphics.getHeight() / 24f - 20);
        passwordTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 4 * Gdx.graphics.getHeight() / 24f - 20);

        stage.addActor(newUsernameTextField);
        stage.addActor(newNicknameTextField);
        stage.addActor(newEmailTextField);
        stage.addActor(newPasswordTextField);
        stage.addActor(passwordTextField);

    }

    private void showButtons() {

        backButton.setWidth(Gdx.graphics.getWidth() / 5f);
        avatarMenuButton.setWidth(Gdx.graphics.getWidth() / 5f);
        userInfoMenuButton.setWidth(Gdx.graphics.getWidth() / 5f);
        changeButton.setWidth(Gdx.graphics.getWidth() / 5f);

        backButton.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - backButton.getWidth()) / 2,
                Gdx.graphics.getHeight() / 3f - 75);

        avatarMenuButton.setPosition(
                Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - avatarMenuButton.getWidth()) / 2,
                Gdx.graphics.getHeight() / 3f + 75
        );

        userInfoMenuButton.setPosition(
                Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - avatarMenuButton.getWidth()) / 2,
                Gdx.graphics.getHeight() / 3f + 225
        );

        changeButton.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - changeButton.getWidth()) / 2,
                Gdx.graphics.getHeight() / 3f + 375);

        stage.addActor(backButton);
        stage.addActor(avatarMenuButton);
        stage.addActor(userInfoMenuButton);
        stage.addActor(changeButton);

    }

    private void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth() / 8f, 6 * Gdx.graphics.getHeight() / 7f - Gdx.graphics.getHeight() / 9f + 20);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());

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
        showLabels();
        showFields();
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

        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.changeViaGraphics());
            }
        });

        avatarMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToAvatarMenu();
            }
        });

        userInfoMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                controller.goToUserInfoMenu();
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

    public TextField getNewUsernameTextField() {
        return newUsernameTextField;
    }

    public TextField getNewPasswordTextField() {
        return newPasswordTextField;
    }

    public TextField getNewEmailTextField() {
        return newEmailTextField;
    }

    public TextField getNewNicknameTextField() {
        return newNicknameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public void executeCommands(Scanner scanner) {
    }
}
