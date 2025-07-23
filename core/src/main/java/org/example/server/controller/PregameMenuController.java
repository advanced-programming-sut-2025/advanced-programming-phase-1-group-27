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
        int mapId = message.getFromBody("mapId");
        String username = message.getFromBody("username");
        int lobbyId = message.getFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        if (lobby == null) {
            return new Message(new HashMap<>()
                    , Message.Type.error);
        }
        HashMap<Integer, String> usersAndChosenMaps = lobby.getUsersAndChosenMaps();
        if (usersAndChosenMaps.containsValue(username)){
            return new Message(new HashMap<>(){{
                put("successful?", false);
                put("GraphicalResult", GraphicalResult.getInfo("You can't choose a new map!"));
            }}, Message.Type.response);
        }
        if (usersAndChosenMaps.get(lobbyId).equals("")) {
            lobby.setMaps(mapId, username);
            return new Message(new HashMap<>() {{
                put("successful?", true);
                put("GraphicalResult", GraphicalResult.getInfo("Map selected successfully!"));
            }}, Message.Type.response);
        } else {
            return new Message(new HashMap<>() {{
                put("successful?", false);
                put("GraphicalResult", GraphicalResult.getInfo("This map is selected already!"));
            }}, Message.Type.response);
        }
    }
}
