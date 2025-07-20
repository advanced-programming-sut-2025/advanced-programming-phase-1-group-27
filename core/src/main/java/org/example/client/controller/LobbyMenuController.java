package org.example.client.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.ForgetPasswordMenuView;
import org.example.client.view.menu.LobbyMenuView;
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
        Message message = new Message(new HashMap<>() , Message.Type.get_lobbies_list);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return  new ArrayList<>();
        }
        ArrayList<LinkedTreeMap<String, Object>> list = response.getFromBody("lobbiesInfo");
        ArrayList<Lobby> lobbies = new ArrayList<>();
        for (LinkedTreeMap<String, Object> info : list) {
            Lobby lobby = new Lobby(info);
            lobbies.add(lobby);
        }
        return lobbies;
    }

    public GraphicalResult find(int id) {
        if(id < 0 || id > 9999){
            return new GraphicalResult(
                    "Id has 4 digits",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        Message message = new Message(new HashMap<>(){{
            put("id", id);
        }} , Message.Type.find_lobby);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        Lobby lobby = new Lobby(response.getFromBody("lobbyInfo"));
    }


    @Override
    public Result enterMenu(String menuName) {
        return null;
    }

    @Override
    public Result exitMenu() {
        return null;
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
