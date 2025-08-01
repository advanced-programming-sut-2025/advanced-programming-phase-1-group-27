package org.example.client.controller.menus;

import com.badlogic.gdx.graphics.Color;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.AvatarMenuView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.ProfileMenuView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Result;
import org.example.server.models.User;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class ProfileMenuController extends MenuController {
    private ProfileMenuView view;

    public ProfileMenuController(ProfileMenuView view) {
        this.view = view;
    }

    public GraphicalResult changeViaGraphics() {
        String password = view.getPasswordTextField().getText();
        String newPassword = view.getNewPasswordTextField().getText();
        String newUsername = view.getNewUsernameTextField().getText();
        String newEmail = view.getNewEmailTextField().getText();
        String newNickname = view.getNewNicknameTextField().getText();
        if (!ClientApp.getLoggedInUser().passwordEquals(password)) {
            return new GraphicalResult(
                    "Incorrect password!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        if (hasEmptyField()) {
            return new GraphicalResult(
                    "Fill at least one section!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        GraphicalResult changeAttempt = changeAttempt(newUsername , newPassword , newNickname , newEmail);
        if (changeAttempt.hasError()) {
            return changeAttempt;
        }

        User newUser;
        if(!newUsername.isEmpty()){
            newUser = ClientApp.getUserByUsername(newUsername);
        }else {
            newUser = ClientApp.getUserByUsername(ClientApp.getLoggedInUser().getUsername());
        }
        ClientApp.setLoggedInUser(newUser);
        ClientApp.updateFile(newUser);

        return new GraphicalResult(String.valueOf(changeAttempt.getMessage()), Color.GREEN);
    }

    private static GraphicalResult changeAttempt(String username , String password , String nickname , String email) {
        if(username == null){
            username = "";
        }
        if(password == null){
            password = "";
        }
        if(nickname == null){
            nickname = "";
        }
        if(email == null){
            email = "";
        }
        String finalUsername = username;
        String finalPassword = password;
        String finalNickname = nickname;
        String finalEmail = email;

        Message message = new Message(new HashMap<>() {{
            put("username", finalUsername);
            put("password", finalPassword);
            put("nickname" , finalNickname);
            put("email" , finalEmail);
        }}, Message.Type.change_profile);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);

        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to change profile",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
    }

    private boolean hasEmptyField() {
        return view.getNewUsernameTextField().getText().isEmpty()
                && view.getNewNicknameTextField().getText().isEmpty()
                && view.getNewEmailTextField().getText().isEmpty()
                && view.getNewPasswordTextField().getText().isEmpty();
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    public void goToAvatarMenu() {
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new AvatarMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenuView());
        return new Result(true, "Redirecting to Main menu ...");
    }
}
