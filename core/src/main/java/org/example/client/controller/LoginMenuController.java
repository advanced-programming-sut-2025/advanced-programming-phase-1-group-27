package org.example.client.controller;

import com.badlogic.gdx.graphics.Color;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.Menu;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class LoginMenuController extends MenuController {
    private LoginMenuView view;

    public LoginMenuController(LoginMenuView view) {
        this.view = view;
    }

    public GraphicalResult loginViaGraphics() {
        GraphicalResult loginAttempt1 = checkAllFieldsAreFilled();
        if (loginAttempt1.hasError()) {
            return loginAttempt1;
        }

        GraphicalResult loginAttempt2 = login(view.getUsernameField().getText(), view.getPasswordField().getText(),
                view.getStayLoggedInCheckBox().isChecked());

        if (loginAttempt2.hasError()) {
            return loginAttempt2;
        }

//        App.setCurrentMenu(Menu.MainMenu);
        Main.getMain().getScreen().dispose();
//        App.setLoggedInUser(App.getUserByUsername(view.getUsernameField().getText()));
        Main.getMain().setScreen(new MainMenuView());
        return new GraphicalResult(String.valueOf(loginAttempt2.getMessage()), Color.GREEN);
    }

    private GraphicalResult checkAllFieldsAreFilled() {
        if (view.getUsernameField().getText().isEmpty() ||
                view.getPasswordField().getText().isEmpty()) {
            return new GraphicalResult(
                    "Please fill all the required fields",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return new GraphicalResult("", GameAssetManager.getGameAssetManager().getAcceptColor(), false);
    }

    public GraphicalResult login(String username, String password, boolean stayLoggedIn) {
        Message message = new Message(new HashMap<>() {{
            put("username", username);
            put("password", password);
            put("stayLoggedIn", stayLoggedIn);
        }}, Message.Type.login_request);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            System.out.println("Response: " + response);
            return new GraphicalResult(
                    "Failed to login",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        return new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
    }

    public void goToForgetPassword() {
        App.setCurrentMenu(Menu.ForgetPasswordMenu);
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
