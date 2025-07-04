package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.example.Main;
import org.example.controller.LoginMenuController;
import org.example.models.Result;
import org.example.models.enums.commands.LoginMenuCommands;
import org.example.models.enums.commands.MainMenuCommands;
import org.example.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuView extends AppMenu {

    private final LoginMenuController controller;
    private Stage stage;

    private final Label menuTitle;
    private final Label usernameLabel;
    private final Label passwordLabel;
    private final Label errorLabel;

    private final TextField usernameField;
    private final TextField passwordField;

    private final TextButton loginButton;
    private final TextButton backButton;
    private final TextButton forgetPasswordButton;

    private final CheckBox stayLoggedInCheckBox;

    private float fadeInTimer = 0f;
    private float errorTimer = 5f;

    public LoginMenuView() {

        controller = new LoginMenuController(this);

        menuTitle = new Label("Login Menu", skin);
        usernameLabel = new Label("Username:", skin);
        passwordLabel = new Label("Password:", skin);
        errorLabel = new Label("", skin);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);

        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);

        stayLoggedInCheckBox = new CheckBox("Stay LoggedIn", skin);

        setListeners();

    }

    private void showMenuTitle(){

        menuTitle.setFontScale(3f);
        menuTitle.setPosition(Gdx.graphics.getWidth()/8f, 5*Gdx.graphics.getHeight()/6f);
        stage.addActor(menuTitle);

    }

    private void showLabels(){

        usernameLabel.setPosition( Gdx.graphics.getWidth()/4f * fadeInTimer,  4 * Gdx.graphics.getHeight()/6f );
        passwordLabel.setPosition( Gdx.graphics.getWidth()/4f * fadeInTimer,  3 * Gdx.graphics.getHeight()/6f );

        stage.addActor(usernameLabel);
        stage.addActor(passwordLabel);

    }

    private void showFields(){

        usernameField.setWidth(Gdx.graphics.getWidth()/4f * fadeInTimer);
        passwordField.setWidth(Gdx.graphics.getWidth()/4f * fadeInTimer);
        stayLoggedInCheckBox.setWidth(Gdx.graphics.getWidth()/4f);

        stayLoggedInCheckBox.setColor(stayLoggedInCheckBox.getColor().r,stayLoggedInCheckBox.getColor().g,stayLoggedInCheckBox.getColor().b,fadeInTimer);


        usernameField.setPosition( 3 * Gdx.graphics.getWidth()/8f,  4 * Gdx.graphics.getHeight()/6f - 20);
        passwordField.setPosition( 3 * Gdx.graphics.getWidth()/8f,  3 * Gdx.graphics.getHeight()/6f - 20);

        stayLoggedInCheckBox.setPosition(

                2 * Gdx.graphics.getWidth()/8f,
                2 * Gdx.graphics.getHeight()/6f

        );

        stage.addActor(usernameField);
        stage.addActor(passwordField);
        stage.addActor(stayLoggedInCheckBox);

    }

    private void showButtons(){

        loginButton.setWidth(Gdx.graphics.getWidth()/8f);
        backButton.setWidth(Gdx.graphics.getWidth()/8f);
        forgetPasswordButton.setWidth(Gdx.graphics.getWidth()/4f - 40);

        loginButton.setColor(loginButton.getColor().r,loginButton.getColor().g,loginButton.getColor().b,fadeInTimer);
        backButton.setColor(backButton.getColor().r,backButton.getColor().g,backButton.getColor().b,fadeInTimer);
        forgetPasswordButton.setColor(forgetPasswordButton.getColor().r,forgetPasswordButton.getColor().g,forgetPasswordButton.getColor().b,fadeInTimer);


        loginButton.setPosition(

                Gdx.graphics.getWidth()/2f,
                2 * Gdx.graphics.getHeight()/6f - 30

        );

        forgetPasswordButton.setPosition(

                2 * Gdx.graphics.getWidth()/8f,
                2 * Gdx.graphics.getHeight()/6f - 50 - forgetPasswordButton.getHeight()

        );

        backButton.setPosition(

                Gdx.graphics.getWidth()/2f,
                2 * Gdx.graphics.getHeight()/6f - 50 - backButton.getHeight()

        );



        stage.addActor(loginButton);
        stage.addActor(backButton);
        stage.addActor(forgetPasswordButton);

    }

    private void showErrorMessage(){

        errorLabel.setPosition(Gdx.graphics.getWidth()/8f, 6 * Gdx.graphics.getHeight()/7f - Gdx.graphics.getHeight()/9f + 20);
        errorLabel.setColor(1,0.31f,0,errorTimer/5);
        stage.addActor(errorLabel);

    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

    }

    @Override
    public void render(float delta) {

        if ( !errorLabel.getText().isEmpty()){

            errorTimer -= delta;

            if ( errorTimer <= 0 ){
                errorLabel.setText("");
                errorTimer = 5;
            }

        }

        if ( fadeInTimer < 1f ){
            fadeInTimer += delta;
        }
        else{
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

    private void setListeners(){

        loginButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.loginViaGraphics();

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

    public CheckBox getStayLoggedInCheckBox() {
        return stayLoggedInCheckBox;
    }

    public float getErrorTimer() {
        return errorTimer;
    }

    public void setErrorTimer(float errorTimer) {
        this.errorTimer = errorTimer;
    }

    public void setErrorLabel(String message) {
        this.errorLabel.setText(message);
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextButton getForgetPasswordButton() {
        return forgetPasswordButton;
    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = LoginMenuCommands.Login.getMatcher(input)) != null) {
            System.out.println(controller.login(
                    matcher.group("username").trim(),
                    matcher.group("password").trim(),
                    matcher.group("stay") != null
            ));
        } else if ((matcher = LoginMenuCommands.ForgetPassword.getMatcher(input)) != null) {
            System.out.println(controller.forgetPassword(
                    matcher.group("username").trim(),
                    scanner
            ));
        } else if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }

    public Matcher getAnswer(Scanner scanner) {
        String input = scanner.nextLine().trim();
        return LoginMenuCommands.AnswerQuestion.getMatcher(input);
    }
}
