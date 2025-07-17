package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.RegisterMenuController;
//import org.example.server.controller.RegisterMenuController;
import org.example.server.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.client.view.AppMenu;
import org.example.server.models.enums.commands.RegisterMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class RegisterMenuView extends AppMenu {

    private final RegisterMenuController controller;
    private final Label menuTitle;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final Label nicknameLabel;
    private final Label emailLabel;
    private final Label genderLabel;
    private final GraphicalResult errorLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField nicknameField;
    private final TextField emailField;
    private final SelectBox<String> genderBox;
    private final TextButton backButton;
    private final TextButton registerButton;
    private final TextButton randomPasswordButton;
    private final TextButton acceptSuggestedUsernameButton;
    private final TextButton declineSuggestedUsernameButton;
    private Stage stage;
    private float fadeInCoEfficient = 0;
    private float errorTimer = 5;

    private boolean reRegister = false;

    public RegisterMenuView() {

        controller = new RegisterMenuController(this);

        menuTitle = new Label("Register Menu", skin);
        usernameLabel = new Label("Username:", skin);
        passwordLabel = new Label("Password:", skin);
        nicknameLabel = new Label("Nickname:", skin);
        emailLabel = new Label("Email:", skin);
        genderLabel = new Label("Gender:", skin);
        errorLabel = new GraphicalResult();

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        nicknameField = new TextField("", skin);
        emailField = new TextField("", skin);

        genderBox = new SelectBox<>(skin);
        genderBox.setItems("Male", "Female");

        backButton = new TextButton("Back", skin);
        registerButton = new TextButton("Register", skin);
        randomPasswordButton = new TextButton("???", skin);
        acceptSuggestedUsernameButton = new TextButton("Y", skin);
        declineSuggestedUsernameButton = new TextButton("N", skin);

        acceptSuggestedUsernameButton.setVisible(reRegister);
        declineSuggestedUsernameButton.setVisible(reRegister);

        setListeners();


    }

    private void showMenuTitle() {

        menuTitle.setFontScale(4f);
        menuTitle.setColor(0.878f, 0.627f, 0f, 1f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f);
        stage.addActor(menuTitle);

    }

    private void showErrorMessage() {

        errorLabel.setPosition(Gdx.graphics.getWidth() / 9f, 6 * Gdx.graphics.getHeight() / 7f - Gdx.graphics.getHeight() / 9f);
        errorLabel.setColor(GameAssetManager.getGameAssetManager().getErrorColor());
        stage.addActor(errorLabel.getMessage());

    }

    private void showFields() {

        //  LABELS

        usernameLabel.setPosition((2 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 2 * Gdx.graphics.getHeight() / 9f);
        passwordLabel.setPosition((2 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 3 * Gdx.graphics.getHeight() / 9f);
        nicknameLabel.setPosition((2 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 4 * Gdx.graphics.getHeight() / 9f);
        emailLabel.setPosition((2 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 5 * Gdx.graphics.getHeight() / 9f);
        genderLabel.setPosition((2 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 6 * Gdx.graphics.getHeight() / 9f);

        usernameLabel.setColor(0, 0, 0, fadeInCoEfficient);
        passwordLabel.setColor(0, 0, 0, fadeInCoEfficient);
        genderLabel.setColor(0, 0, 0, fadeInCoEfficient);
        nicknameLabel.setColor(0, 0, 0, fadeInCoEfficient);
        emailLabel.setColor(0, 0, 0, fadeInCoEfficient);

        // FIELDS

        usernameField.setWidth(Gdx.graphics.getWidth() / 5f);
        passwordField.setWidth(Gdx.graphics.getWidth() / 5f);
        nicknameField.setWidth(Gdx.graphics.getWidth() / 5f);
        emailField.setWidth(Gdx.graphics.getWidth() / 5f);
        genderBox.setWidth(Gdx.graphics.getWidth() / 5f);

        usernameField.setColor(usernameField.getColor().r, usernameField.getColor().g, usernameField.getColor().b, fadeInCoEfficient);
        passwordField.setColor(passwordField.getColor().r, passwordField.getColor().g, passwordField.getColor().b, fadeInCoEfficient);
        nicknameField.setColor(nicknameField.getColor().r, nicknameField.getColor().g, nicknameField.getColor().b, fadeInCoEfficient);
        emailField.setColor(emailField.getColor().r, emailField.getColor().g, emailField.getColor().b, fadeInCoEfficient);
        genderBox.setColor(genderBox.getColor().r, genderBox.getColor().g, genderBox.getColor().b, fadeInCoEfficient);


        usernameField.setPosition((3 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 2 * Gdx.graphics.getHeight() / 9f - 20);
        passwordField.setPosition((3 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 3 * Gdx.graphics.getHeight() / 9f - 20);
        nicknameField.setPosition((3 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 4 * Gdx.graphics.getHeight() / 9f - 20);
        emailField.setPosition((3 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 5 * Gdx.graphics.getHeight() / 9f - 20);
        genderBox.setPosition((3 * Gdx.graphics.getWidth() / 10f - 150) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 6 * Gdx.graphics.getHeight() / 9f - 20);


        // RANDOM PASSWORD

        randomPasswordButton.setSize(120, passwordField.getHeight());
        randomPasswordButton.setColor(randomPasswordButton.getColor().r, randomPasswordButton.getColor().g, randomPasswordButton.getColor().b, fadeInCoEfficient);
        randomPasswordButton.setPosition((5 * Gdx.graphics.getWidth() / 10f - 100) * fadeInCoEfficient, 6 * Gdx.graphics.getHeight() / 7f - 3 * Gdx.graphics.getHeight() / 9f - 20);

        // SUGGEST USERNAME

        acceptSuggestedUsernameButton.setSize(75, usernameField.getHeight());
        declineSuggestedUsernameButton.setSize(75, usernameField.getHeight());

        acceptSuggestedUsernameButton.setPosition(usernameField.getX() + Gdx.graphics.getWidth() / 5f + 50, usernameField.getY());
        declineSuggestedUsernameButton.setPosition(usernameField.getX() + Gdx.graphics.getWidth() / 5f + 150, usernameField.getY());

        acceptSuggestedUsernameButton.setVisible(reRegister);
        declineSuggestedUsernameButton.setVisible(reRegister);


        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(nicknameLabel);
        stage.addActor(emailLabel);
        stage.addActor(genderLabel);

        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(nicknameField);
        stage.addActor(emailField);
        stage.addActor(genderBox);

        stage.addActor(randomPasswordButton);
        stage.addActor(acceptSuggestedUsernameButton);
        stage.addActor(declineSuggestedUsernameButton);

    }

    private void showGameLogo() {

        stardewValleyText.setScale(fadeInCoEfficient * 2);

        stardewValleyText.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - stardewValleyText.getWidth() * fadeInCoEfficient * 2) / 2,
                (Gdx.graphics.getHeight() - stardewValleyText.getHeight() * fadeInCoEfficient * 2) / 2 + 2 * Gdx.graphics.getHeight() / 10f
        );

        stage.addActor(stardewValleyText);

    }

    private void showButtons() {


        backButton.setWidth(Gdx.graphics.getWidth() / 5f);
        registerButton.setWidth(Gdx.graphics.getWidth() / 5f);

        backButton.setColor(backButton.getColor().r, backButton.getColor().g, backButton.getColor().b, fadeInCoEfficient);
        registerButton.setColor(registerButton.getColor().r, registerButton.getColor().g, registerButton.getColor().b, fadeInCoEfficient);


        registerButton.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - registerButton.getWidth()) / 2,
                (Gdx.graphics.getHeight() - registerButton.getHeight()) / 2 - Gdx.graphics.getHeight() / 20f
        );

        backButton.setPosition(Gdx.graphics.getWidth() / 2f + (Gdx.graphics.getWidth() / 2f - backButton.getWidth()) / 2,
                (Gdx.graphics.getHeight() - backButton.getHeight()) / 2 - 2f * Gdx.graphics.getHeight() / 10f
        );

        stage.addActor(registerButton);
        stage.addActor(backButton);


    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        showMenuTitle();
        showErrorMessage();

    }

    @Override
    public void render(float delta) {

        errorLabel.update(delta);

        if (fadeInCoEfficient < 1f) {
            fadeInCoEfficient += delta;
        } else {
            fadeInCoEfficient = 1f;
        }

        Main.getBatch().begin();
        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        showGameLogo();
        showFields();
        showButtons();

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

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextField getNicknameField() {
        return nicknameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public SelectBox<String> getGenderBox() {
        return genderBox;
    }

    public void setReRegister(boolean reRegister) {
        this.reRegister = reRegister;
    }

    private void setListeners() {

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.exitMenu();

            }
        });

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                errorLabel.set(controller.register());

            }
        });

        randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.setRandomPassword();

            }
        });

        genderBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                playClickSound();
            }
        });

        acceptSuggestedUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.acceptSuggestedUsername());
            }
        });

        declineSuggestedUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                errorLabel.set(controller.declineSuggestedUsername());
            }
        });


    }


    @Override
    public void executeCommands(Scanner scanner) {}

}