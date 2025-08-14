package org.example.client.controller.menus;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.model.GameAssetManager;
import org.example.client.view.menu.LobbyMenuView;
import org.example.client.view.menu.PasswordMenuView;
import org.example.client.view.menu.PregameMenuView;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Lobby;
import org.example.common.models.Message;
import org.example.common.models.Result;

import java.util.HashMap;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

public class PasswordMenuController extends MenuController {
    private PasswordMenuView view;

    public PasswordMenuController(PasswordMenuView view) {
        this.view = view;
    }

    public GraphicalResult joinGraphicalResult(Lobby lobby) {
        if (view.getPasswordField().getText().isEmpty()) {
            return new GraphicalResult(
                    "No password entered",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        String password = view.getPasswordField().getText();
        if (!lobby.getPassword().equals(password)) {
            return new GraphicalResult(
                    "Wrong password",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        Message message = new Message(new HashMap<>() {{
            put("username", ClientApp.getLoggedInUser().getUsername());
            put("id", lobby.getId());
        }}, Message.Type.join_lobby);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to loadGame",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        if (result.hasError()) {
            return new GraphicalResult(
                    result.getMessage().toString(),
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }

        lobby.addUser(ClientApp.getLoggedInUser());

        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new PregameMenuView(lobby));
        Main.getMain().setScreen(ClientApp.getCurrentMenu());


        return new GraphicalResult(
                result.getMessage().toString(),
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false
        );
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LobbyMenuView());
        return new Result(true, "Redirecting to Lobby Menu ...");
    }
}
