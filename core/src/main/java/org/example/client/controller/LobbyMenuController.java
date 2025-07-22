package org.example.client.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.*;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.GameAssetManager;
import org.example.server.models.Lobby;
import org.example.server.models.Result;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class LobbyMenuController extends MenuController {
    LobbyMenuView view;
    public LobbyMenuController(LobbyMenuView view) {
        this.view = view;
    }

    public ArrayList<Lobby> getLobbies() {
        Message message = new Message(new HashMap<>()
                , Message.Type.get_lobbies_list);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new ArrayList<>();
        }
        ArrayList<LinkedTreeMap<String, Object>> list = response.getFromBody("lobbiesInfo");
        ArrayList<Lobby> lobbies = new ArrayList<>();
        for (LinkedTreeMap<String, Object> info : list) {
            Lobby lobby = new Lobby(info);
            lobbies.add(lobby);
        }
        return lobbies;
    }

    public GraphicalResult findViaGraphicalResult() {
        if(view.getGameIdTextField().getText().isEmpty()){
            return new GraphicalResult(
                    "No id has been entered!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        int id =  Integer.parseInt(view.getGameIdTextField().getText());
        if(id < 0 || id > 9999){
            return new GraphicalResult(
                    "Id has 4 digits",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        return find(view.getGameIdTextField().getText());
    }

    public GraphicalResult join() {
        if(view.getPublicGamesSelectBox().getSelected().isEmpty()){
            return new GraphicalResult(
                    "No game has been selected!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        String selected = view.getPublicGamesSelectBox().getSelected();
        String gameId = selected.split(" ")[0];
        return find(gameId);
    }

    public void host(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new HostMenuView());
    }

    private static GraphicalResult find(String id){
        Message message = new Message(new HashMap<>(){{
            put("id", id);
        }} , Message.Type.find_lobby);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response) {
            return  new GraphicalResult(
                    "Failed to find",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        boolean found = response.getFromBody("found?");
        if(!found){
            return new GraphicalResult(
                    "Lobby not found!",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        Lobby lobby = new Lobby(response.getFromBody("lobbyInfo"));

        if(lobby.getPassword() != null){
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new PasswordMenuView(lobby));
            return new GraphicalResult(
                    "Lobby has password!",
                    GameAssetManager.getGameAssetManager().getAcceptColor(),
                    false
            );
        }

        Message joinMessage = new Message(new HashMap<>() {{
            put("username", ClientApp.getLoggedInUser().getUsername());
            put("id", lobby.getId());
        }}, Message.Type.join_lobby);
        Message responseForJoin = ClientApp.getServerConnectionThread().sendAndWaitForResponse(joinMessage, TIMEOUT_MILLIS);
        if(responseForJoin == null || responseForJoin.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to join",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = responseForJoin.getFromBody("GraphicalResult");
        if(result.hasError()){
            return new GraphicalResult(
                    result.getMessage().toString(),
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        lobby.addUser(ClientApp.getLoggedInUser());
        // TODO : Lobby nabayad pass bedim???
        Main.getMain().getScreen().dispose();
        ClientApp.setCurrentMenu(new PreGameMenuView());
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
        Main.getMain().setScreen(new MainMenuView());
        return new Result(true, "Redirecting to Main Menu ...");
    }

    //    // getLobbiesList
//    ArrayList<LinkedTreeMap<String, Object>> list = response.getFromBody("lobbiesInfo");
//        for (LinkedTreeMap<String, Object> info : list) {
//        Lobby lobby = new Lobby(info);
//        lobby.toString();
//    }
//    // createLobby
//    Lobby lobby = new Lobby(response.getFromBody("lobbyInfo"));
}
