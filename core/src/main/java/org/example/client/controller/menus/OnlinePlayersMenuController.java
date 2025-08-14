package org.example.client.controller.menus;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.menu.LobbyMenuView;
import org.example.common.models.Lobby;
import org.example.common.models.Message;
import org.example.common.models.Result;
import org.example.common.models.User;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

public class OnlinePlayersMenuController extends MenuController {

    private static HashMap<String, String> getStringStringHashMap(ArrayList<String> usernames, ArrayList<Lobby> lobbies) {
        HashMap<String, String> usernames_map = new HashMap<>();

        for (String username : usernames) {
            Lobby lobbyName = null;
            for (Lobby lobby : lobbies) {
                for (User user : lobby.getUsers()) {
                    if (user.getUsername().equals(username)) {
                        lobbyName = lobby;
                    }
                }
            }
            if (lobbyName == null) {
                usernames_map.put(username, "");
            } else {
                usernames_map.put(username, lobbyName.getName());
            }
        }
        return usernames_map;
    }

    private static ArrayList<Lobby> getLobbies() {
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

    public HashMap<String, String> getOnlinePlayers() {
        Message message = new Message(new HashMap<>()
                , Message.Type.get_online_users);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new HashMap<>();
        }

        ArrayList<Lobby> lobbies = getLobbies();
        ArrayList<String> usernames = response.getFromBody("usernames");

        return getStringStringHashMap(usernames, lobbies);
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
