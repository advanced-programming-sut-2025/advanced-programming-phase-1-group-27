package org.example.client.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.client.Main;
import org.example.client.controller.menus.LoginMenuController;
import org.example.client.model.GameAssetManager;
import org.example.client.view.AppMenu;
import org.example.common.models.GraphicalResult;
import org.example.common.models.commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuView extends AppMenu {

    private final LoginMenuController controller;
    private final Label menuTitle;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final GraphicalResult errorLabel;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextButton loginButton;
    private final TextButton backButton;
    private final TextButton forgetPasswordButton;
    private final CheckBox stayLoggedInCheckBox;
    private Stage stage;
    private float fadeInTimer = 0f;
    private float errorTimer = 5f;

    public LoginMenuView() {

        controller = new LoginMenuController(this);

        menuTitle = new Label("Login Menu", skin);
        usernameLabel = new Label("Username:", skin);
        passwordLabel = new Label("Password:", skin);
        errorLabel = new GraphicalResult();

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);

        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);

        stayLoggedInCheckBox = new CheckBox("Stay LoggedIn", skin);

        setListeners();

    }

    private void showMenuTitle() {

        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth() / 8f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitle);

    }

    private void showLabels() {

        usernameLabel.setPosition(Gdx.graphics.getWidth() / 4f * fadeInTimer, 4 * Gdx.graphics.getHeight() / 6f);
        passwordLabel.setPosition(Gdx.graphics.getWidth() / 4f * fadeInTimer, 3 * Gdx.graphics.getHeight() / 6f);

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);

    }

    private void showFields() {

        usernameField.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        passwordField.setWidth(Gdx.graphics.getWidth() / 4f * fadeInTimer);
        stayLoggedInCheckBox.setWidth(Gdx.graphics.getWidth() / 4f);

        stayLoggedInCheckBox.setColor(stayLoggedInCheckBox.getColor().r, stayLoggedInCheckBox.getColor().g, stayLoggedInCheckBox.getColor().b, fadeInTimer);


        usernameField.setPosition(3 * Gdx.graphics.getWidth() / 8f, 4 * Gdx.graphics.getHeight() / 6f - 20);
        passwordField.setPosition(3 * Gdx.graphics.getWidth() / 8f, 3 * Gdx.graphics.getHeight() / 6f - 20);

        stayLoggedInCheckBox.setPosition(
                2 * Gdx.graphics.getWidth() / 8f,
                2 * Gdx.graphics.getHeight() / 6f
        );

        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(stayLoggedInCheckBox);
    }

    private void showButtons() {

        loginButton.setWidth(Gdx.graphics.getWidth() / 8f);
        backButton.setWidth(Gdx.graphics.getWidth() / 8f);
        forgetPasswordButton.setWidth(Gdx.graphics.getWidth() / 4f - 40);

        loginButton.setColor(loginButton.getColor().r, loginButton.getColor().g, loginButton.getColor().b, fadeInTimer);
        backButton.setColor(backButton.getColor().r, backButton.getColor().g, backButton.getColor().b, fadeInTimer);
        forgetPasswordButton.setColor(forgetPasswordButton.getColor().r, forgetPasswordButton.getColor().g, forgetPasswordButton.getColor().b, fadeInTimer);


        loginButton.setPosition(

                Gdx.graphics.getWidth() / 2f,
                2 * Gdx.graphics.getHeight() / 6f - 30

        );

        forgetPasswordButton.setPosition(

                2 * Gdx.graphics.getWidth() / 8f,
                2 * Gdx.graphics.getHeight() / 6f - 50 - forgetPasswordButton.getHeight()

        );

        backButton.setPosition(

                Gdx.graphics.getWidth() / 2f,
                2 * Gdx.graphics.getHeight() / 6f - 50 - backButton.getHeight()

        );


        stage.addActor(loginButton);
        stage.addActor(backButton);
        stage.addActor(forgetPasswordButton);

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

        loginButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                errorLabel.set(controller.loginViaGraphics());

            }

        });

        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.exitMenu();

            }

        });

        forgetPasswordButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.goToForgetPassword();

            }

        });


    }

    public CheckBox getStayLoggedInCheckBox() {
        return stayLoggedInCheckBox;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public void executeCommands(Scanner scanner) {
//
    }

    public Matcher getAnswer(Scanner scanner) {
        String input = scanner.nextLine().trim();
        return LoginMenuCommands.AnswerQuestion.getMatcher(input);
    }
}
