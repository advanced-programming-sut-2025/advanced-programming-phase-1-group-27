package org.example.client.controller;

import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class HostMenuController extends MenuController{
    private HostMenuView view;

    public HostMenuController(HostMenuView view) {
        this.view = view;
    }

    public GraphicalResult createGraphicalResult(){
        boolean isPublic = view.getIsPublicCheckBox().isChecked();
        boolean isVisible = view.getIsVisibleCheckBox().isChecked();
        if(view.getNameTextField().getText().isEmpty()){
            return new GraphicalResult(
                    "No name entered!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        if(!isPublic && view.getPasswordField().getText().isEmpty()){
            return new GraphicalResult(
                    "Private lobbies should have password!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        String name = view.getNameTextField().getText();
        String password;
        if(isPublic){
            password = "";
        } else {
            password = view.getPasswordField().getText();
        }
        Message message = new Message(new HashMap<>(){{
            put("username" , ClientApp.getLoggedInUser().getUsername());
            put("isPublic" , isPublic);
            put("isVisible" , isVisible);
            put("password" , password);
            put("name" , name);
        }} , Message.Type.create_lobby);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response) {
            return  new GraphicalResult(
                    "Failed to create",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        Lobby lobby = new Lobby(response.getFromBody("lobbyInfo"));

        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new PregameMenuView(lobby));
        Main.getMain().setScreen(ClientApp.getCurrentMenu());

        return new GraphicalResult(response.getFromBody("GraphicalResult").toString(),
                GameAssetManager.getGameAssetManager().getAcceptColor(),
                false);
    }

    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LobbyMenuView());
        return new Result(true, "Redirecting to Lobby Menu ...");    }
}
