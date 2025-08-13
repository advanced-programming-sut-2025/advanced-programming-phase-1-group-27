package org.example.client.controller.menus;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.ForgetPasswordMenuView;
import org.example.client.view.menu.LoginMenuView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.client.model.GameAssetManager;
import org.example.common.models.Result;
import org.example.common.models.User;

import java.util.HashMap;

public class ForgetPasswordMenuController extends MenuController {
    private ForgetPasswordMenuView view;
    private User attemptingUser;

    public ForgetPasswordMenuController(ForgetPasswordMenuView view) {
        this.view = view;
    }

    public GraphicalResult submitUsername() {
        attemptingUser = ClientApp.getUserByUsername(view.getUsernameField().getText());
        if (attemptingUser == null)
            return new GraphicalResult(
                    "Username not found!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        view.setUsernameSubmitted(true);
        return new GraphicalResult("", GameAssetManager.getGameAssetManager().getAcceptColor());
    }

    public GraphicalResult attemptToChangePassword() {

        String answer = view.getAnswerField().getText();
        String newPassword = view.getNewPasswordField().getText();
        if (answer.isEmpty() || newPassword.isEmpty())
            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        if (!answer.equals(attemptingUser.getRecoveryQuestion().getAnswer())) {
            view.getAnswerField().setText("");
            view.getNewPasswordField().setText("");
            return new GraphicalResult(
                    "Wrong answer Try again",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        Result passwordCheck = User.checkPassword(newPassword);
        if (!passwordCheck.success())
            return new GraphicalResult(passwordCheck.message(), GameAssetManager.getGameAssetManager().getErrorColor());

        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("username", attemptingUser.getUsername());
            put("password", User.hashPassword(view.getNewPasswordField().getText()));
        }}, Message.Type.set_password));
        exitMenu();
        return new GraphicalResult(
                "You successfully changed your password",
                GameAssetManager.getGameAssetManager().getAcceptColor()
        );
    }

    public void setRandomPassword() {
        view.getNewPasswordField().setText(ClientApp.generatePassword());
    }

    public User getAttemptingUser() {
        return attemptingUser;
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new LoginMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
        return new Result(true, "Redirecting to login menu ...");
    }
}
