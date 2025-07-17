package org.example.client.controller;

import com.badlogic.gdx.graphics.Color;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.WelcomeMenuView;
import org.example.common.models.GraphicalResult;
import org.example.client.view.menu.RegisterMenuView;
import org.example.client.view.menu.SecurityQuestionMenuView;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class RegisterMenuController extends MenuController {
    private RegisterMenuView view;
    private String suggestedUsername = null;

    public RegisterMenuController(RegisterMenuView view) {
        this.view = view;
    }

    public GraphicalResult register() {
        GraphicalResult registerAttempt = checkAllFieldsAreFilled();
        if (registerAttempt.hasError())
            return registerAttempt;

        String username = view.getUsernameField().getText();
        String password = view.getPasswordField().getText();
        String email = view.getEmailField().getText();
        String nickname = view.getNicknameField().getText();

        GraphicalResult registerAttemptResult = getRegisterResult(username, password, email, nickname);
        if (registerAttemptResult.hasError())
            return registerAttemptResult;

        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SecurityQuestionMenuView(new User(username, User.hashPassword(password), nickname, email, Gender.values()[view.getGenderBox().getSelectedIndex()])));
        return new GraphicalResult("Successful registration!", Color.GREEN);
    }

    private GraphicalResult checkAllFieldsAreFilled() {
        if (view.getUsernameField().getText().isEmpty() ||
            view.getPasswordField().getText().isEmpty() ||
            view.getEmailField().getText().isEmpty() ||
            view.getNicknameField().getText().isEmpty()) {
            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return new GraphicalResult("", GameAssetManager.getGameAssetManager().getAcceptColor(), false);
    }

    private GraphicalResult getRegisterResult(String username, String password, String email, String nickname) {
        Message message = new Message(new HashMap<>() {{
            put("username", username);
            put("password", password);
            put("email", email);
            put("nickname", nickname);
        }}, Message.Type.register_request);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to register",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        view.setReRegister(response.getFromBody("reRegister"));
        suggestedUsername = response.getFromBody("suggestedUsername");
        return new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
    }

    public void setRandomPassword() {
        view.getPasswordField().setText(ClientApp.generatePassword());
    }

    public GraphicalResult acceptSuggestedUsername() {
        view.setReRegister(false);
        view.getUsernameField().setText(suggestedUsername);
        return new GraphicalResult(
                "Suggested username accepted!",
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    public GraphicalResult declineSuggestedUsername() {
        view.setReRegister(false);
        view.getUsernameField().setText("");
        return new GraphicalResult(
                "Suggested username declined!",
                GameAssetManager.getGameAssetManager().getErrorColor(),
                false
        );
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        playClickSound();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new WelcomeMenuView());
        return new Result(true, "Redirecting to welcome menu ...");
    }
}
