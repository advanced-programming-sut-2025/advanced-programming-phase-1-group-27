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
import org.example.common.models.GraphicalResult;
import org.example.server.controller.ProfileMenuController;
import org.example.server.models.Result;
import org.example.server.models.enums.commands.MainMenuCommands;
import org.example.server.models.enums.commands.ProfileMenuCommands;
import org.example.client.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenuView extends AppMenu {

    private final ProfileMenuController controller;
    private Stage stage;

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
    private final TextButton backButton;

    private final GraphicalResult errorLabel;

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
        backButton = new TextButton("Back", skin);

        errorLabel = new GraphicalResult();

        setListeners();

    }

    private void showMenuTitle() {

        menuTitleLabel.setFontScale(3f);
        menuTitleLabel.setPosition(Gdx.graphics.getWidth() / 10f, 5 * Gdx.graphics.getHeight() / 6f);
        stage.addActor(menuTitleLabel);

    }

    private void showLabels(){

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

    private void showFields(){

        newUsernameTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newNicknameTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newEmailTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        newPasswordTextField.setWidth(Gdx.graphics.getWidth() / 5f);
        passwordTextField.setWidth(Gdx.graphics.getWidth() / 5f);


        newUsernameTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 16 * Gdx.graphics.getHeight() / 24f-20);
        newNicknameTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 13 * Gdx.graphics.getHeight() / 24f-20);
        newEmailTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 10 * Gdx.graphics.getHeight() / 24f-20);
        newPasswordTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 7 * Gdx.graphics.getHeight() / 24f-20);
        passwordTextField.setPosition(4 * Gdx.graphics.getWidth() / 10f, 4 * Gdx.graphics.getHeight() / 24f-20);

        stage.addActor(newUsernameTextField);
        stage.addActor(newNicknameTextField);
        stage.addActor(newEmailTextField);
        stage.addActor(newPasswordTextField);
        stage.addActor(passwordTextField);

    }

    private void showButtons(){

        backButton.setWidth(Gdx.graphics.getWidth() / 5f);
        changeButton.setWidth(Gdx.graphics.getWidth() / 5f);

        backButton.setPosition(Gdx.graphics.getWidth()/2f + (Gdx.graphics.getWidth()/2f-backButton.getWidth())/2,
                 Gdx.graphics.getHeight()/3f);

        changeButton.setPosition(Gdx.graphics.getWidth()/2f + (Gdx.graphics.getWidth()/2f-changeButton.getWidth())/2,
                Gdx.graphics.getHeight()/3f + 200);

        stage.addActor(backButton);
        stage.addActor(changeButton);

    }



    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        stage.addActor(menuBackground);

        stage.addActor(new Label("PROFILE", skin));

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

    private void setListeners() {

        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playClickSound();
                ///  TODO
            }
        });

    }

    public void executeCommands(Scanner scanner) {
        String input = scanner.nextLine().trim();
        Matcher matcher;
        if ((matcher = MainMenuCommands.EnterMenu.getMatcher(input)) != null) {
            System.out.println(controller.enterMenu(matcher.group("menuName").trim()));
        } else if (MainMenuCommands.ExitMenu.getMatcher(input) != null) {
            System.out.println(controller.exitMenu());
        } else if (MainMenuCommands.ShowCurrentMenu.getMatcher(input) != null) {
            System.out.println(controller.showCurrentMenu());
        } else if ((matcher = ProfileMenuCommands.ChangeUsername.getMatcher(input)) != null) {
            System.out.println(controller.changeUsername(
                    matcher.group("username").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangeNickname.getMatcher(input)) != null) {
            System.out.println(controller.changeNickname(
                    matcher.group("nickname").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangeEmail.getMatcher(input)) != null) {
            System.out.println(controller.changeEmail(
                    matcher.group("email").trim()
            ));
        } else if ((matcher = ProfileMenuCommands.ChangePassword.getMatcher(input)) != null) {
            System.out.println(controller.changePassword(
                    matcher.group("newPassword").trim(),
                    matcher.group("oldPassword").trim()
            ));
        } else if (ProfileMenuCommands.UserInfo.getMatcher(input) != null) {
            System.out.println(controller.showInfo());
        } else {
            System.out.println(new Result(false, "invalid command!"));
        }
    }
}
