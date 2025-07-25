package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;

import java.util.ArrayList;
import java.util.HashMap;

public class PregameMenuController {
    public static void createGame(Message message) {
        Lobby lobby = ServerApp.getLobbyById(new Lobby(message.getFromBody("lobbyInfo")).getId());
        LinkedTreeMap<String, Integer> usernameToMapId = message.getFromBody("usernameToMap");
        ArrayList<Player> players = new ArrayList<>();
        for (User user : lobby.getUsers()) {
            players.add(new Player(user));
        }
        Game game;
        lobby.setGame(game = new Game(lobby.getAdmin(), players));
        game.init();
        for (Player player : players) {
            int mapIndex = usernameToMapId.get(player.getUsername()) - 1;
            player.setFarmMap(game.getFarmMap(mapIndex));
            player.setCurrentCell(game.getFarmMap(mapIndex).getCell(8, 70));
        }
        notifyPlayers(players);
    }

    private static void notifyPlayers(ArrayList<Player> players) {
        for (Player player : players) {
            ServerApp.getClientConnectionThreadByUsername(player.getUsername()).sendMessage(
                    new Message(null, Message.Type.start_game)
            );
        }
    }

    public static Message chooseMap(Message message) {
        int mapId = message.getIntFromBody("mapId");
        String username = message.getFromBody("username");
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        if (lobby == null) {
            return new Message(new HashMap<>(), Message.Type.error);
        }
        HashMap<String, Integer> usersAndChosenMaps = lobby.getUsernameToMap();
        // TODO: parsa, reedi
        if (usersAndChosenMaps.containsValue(username)){
            return new Message(new HashMap<>(){{
                put("GraphicalResult", GraphicalResult.getInfo("You can't choose a new map!"));
            }}, Message.Type.response);
        }
        if (usersAndChosenMaps.get(mapId).equals("")) {
            lobby.setMap(username, mapId);
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Map selected successfully!", false));
            }}, Message.Type.response);
        } else {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("This map is selected already!"));
            }}, Message.Type.response);
        }
    }

    public static void leaveLobby(Message message) {
        String username = message.getFromBody("username");
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        if (lobby == null) {
            return;
        }
        HashMap<String, Integer> usersAndChosenMaps = lobby.getUsernameToMap();
        // TODO: parsa, reedi
//        for(Integer index : usersAndChosenMaps.keySet()) {
//            if(usersAndChosenMaps.get(index).equals(username)) {
//                usersAndChosenMaps.put(index, "");
//            }
//        }
        User selectedUser = null;
        for(User user : lobby.getUsers()) {
            if(user.getUsername().equals(username)) {
                selectedUser = user;
            }
        }

        if(selectedUser != null) {
            lobby.getUsers().remove(selectedUser);

            if (lobby.getUsers().isEmpty()) {
                ServerApp.getLobbies().remove(lobby);
                return;
            }

            if (lobby.getAdmin().getUsername().equals(username)) {
                lobby.setAdmin(lobby.getUsers().get(0));
            }
        }
    }
}
