package org.example.server.controller;


import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.Lobby;
import org.example.server.models.ServerApp;
import org.example.server.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LobbyController {
    public static Message createLobby(Message message) {
        String username = message.getFromBody("username");
        User admin = ServerApp.getUserByUsername(username);
        assert admin != null;
        boolean isPublic = message.getFromBody("isPublic");
        boolean isVisible = message.getFromBody("isVisible");
        String name = message.getFromBody("name");
        String password = message.getFromBody("password");
        int id = generateLobbyId();
        Lobby lobby = new Lobby(admin, isPublic, password, isVisible, id, name);
        ServerApp.addLobby(lobby);
        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo("Lobby created successfully!"));
            put("lobbyInfo", lobby.getInfo());
        }}, Message.Type.response);
    }

    public static Message joinLobby(Message message) {
        String username = message.getFromBody("username");
        User user = ServerApp.getUserByUsername(username);
        assert user != null;
        int id = message.getIntFromBody("id");
        Lobby lobby = ServerApp.getLobbyById(id);
        if (!lobby.addUser(user))
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Lobby capacity is full"));
            }}, Message.Type.response);

        return new Message(new HashMap<>(){{
            put("GraphicalResult", GraphicalResult.getInfo("You have successfully joined!", false));
        }}, Message.Type.response);
    }

    public static Message getLobbiesList(Message message) {
        ArrayList<Lobby> lobbies = ServerApp.getLobbies();
        ArrayList<HashMap<String, Object>> lobbiesInfo = new ArrayList<>();
        for (Lobby lobby : lobbies) {
            lobbiesInfo.add(lobby.getInfo());
        }
        return new Message(new HashMap<>() {{
            put("lobbiesInfo", lobbiesInfo);
        }}, Message.Type.response);
    }

    public static Message findLobbyById(Message message) {
        String id = message.getFromBody("id");
        int lobbyId = Integer.parseInt(id);
        ArrayList<Lobby> lobbies = ServerApp.getLobbies();
        Lobby selectedLobby = null;
        for(Lobby lobby : lobbies){
            if(lobby.getId() == lobbyId){
                selectedLobby = lobby;
            }
        }
        if(selectedLobby != null){
            Lobby finalSelectedLobby = selectedLobby;
            return new Message(new HashMap<>() {{
                put("found?" , true);
                put("lobbyInfo" , finalSelectedLobby.getInfo());
            }} , Message.Type.response);
        }else {
            return new Message(new HashMap<>() {{
                put("found?" , false);
                put("lobbyInfo" , null);
            }} , Message.Type.response);
        }
    }

    private static int generateLobbyId() {
        Random random = new Random();
        while (true) {
            int id = random.nextInt(10_000);
            if (ServerApp.getLobbyById(id) == null)
                return id;
        }
    }
}
