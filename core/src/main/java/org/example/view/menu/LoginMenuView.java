package org.example.view.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private final TextField usernameField;
    private final TextField passwordField;

    private final TextButton loginButton;
    private final TextButton backButton;
    private final TextButton forgetPasswordButton;

    public LoginMenuView() {
        controller = new LoginMenuController(this);

        menuTitle = new Label("Login Menu", skin);
        usernameLabel = new Label("Username:", skin);
        passwordLabel = new Label("Password:", skin);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);

        loginButton = new TextButton("Login", skin);
        backButton = new TextButton("Back", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);

        setListeners();

    }


    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        stage.addActor(new Label("LOGIN", skin));


    }

    private void showMenuTitle(){

        menuTitle.setFontScale(2f);
        menuTitle.setPosition(Gdx.graphics.getWidth()/8f, 5*Gdx.graphics.getHeight()/6f);
        stage.addActor(menuTitle);

    }

    private void showLabels(){

    }

    private void showFields(){

        usernameField.setPosition(0,800);
        passwordField.setPosition(0,400);

        stage.addActor(usernameField);
        stage.addActor(passwordField);

    }

    private void showButtons(){

        loginButton.setPosition(0,0);
        stage.addActor(loginButton);

    }

    @Override
    public void render(float v) {


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

    private void setListeners(){

        loginButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                playClickSound();
                controller.loginViaGraphics();

            }

        });

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
