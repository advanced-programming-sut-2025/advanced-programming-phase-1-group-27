package org.example.server.controller;

import org.example.client.Main;
import org.example.common.models.GraphicalResult;
import org.example.server.models.*;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.ForgetPasswordMenuView;
import org.example.client.view.menu.LoginMenuView;

public class ForgetPasswordMenuController extends MenuController {


    private ForgetPasswordMenuView view;
    private User attemptingUser;

    public ForgetPasswordMenuController(ForgetPasswordMenuView view) {
        this.view = view;
    }

    public GraphicalResult submitUsername() {
        attemptingUser = App.getUserByUsername(view.getUsernameField().getText());
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

        attemptingUser.setPassword(User.hashPassword(view.getNewPasswordField().getText()));
        exitMenu();
        return new GraphicalResult(
                "You successfully changed your password",
                GameAssetManager.getGameAssetManager().getAcceptColor()
        );
    }

    public void setRandomPassword() {
        view.getNewPasswordField().setText(App.generatePassword());
    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        App.setCurrentMenu(Menu.LoginMenu);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView());
        return new Result(true, "Redirecting to login menu ...");
    }

    public User getAttemptingUser() {
        return attemptingUser;
    }
}
