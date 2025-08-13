package org.example.client.controller.menus;

import com.badlogic.gdx.graphics.Color;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.common.models.Result;
import org.example.client.view.menu.*;
import org.example.client.model.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;

import java.util.HashMap;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

public class LoginMenuController extends MenuController {
    private LoginMenuView view;

    public LoginMenuController(LoginMenuView view) {
        this.view = view;
    }

    public GraphicalResult loginViaGraphics() {
        if (hasEmptyField())
            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );

        GraphicalResult loginAttempt = login(view.getUsernameField().getText(), view.getPasswordField().getText());

        if (loginAttempt.hasError())
            return loginAttempt;

        if (view.getStayLoggedInCheckBox().isChecked())
            ClientApp.setSavedUser(ClientApp.getUserByUsername(view.getUsernameField().getText()));

        ClientApp.setLoggedInUser(ClientApp.getUserByUsername(view.getUsernameField().getText()));

        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new MainMenuView());
        Main.getMain().setScreen(ClientApp.getCurrentMenu());

        return new GraphicalResult(String.valueOf(loginAttempt.getMessage()), Color.GREEN);
    }

    private boolean hasEmptyField() {
        return view.getUsernameField().getText().isEmpty() || view.getPasswordField().getText().isEmpty();
    }

    private GraphicalResult login(String username, String password) {
        Message message = new Message(new HashMap<>() {{
            put("username", username);
            put("password", password);
        }}, Message.Type.login_request);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to login",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
    }

    public void goToForgetPassword() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ForgetPasswordMenuView());
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
