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
        assert lobby != null;
        ArrayList<Player> players = new ArrayList<>();
        for (User user : lobby.getUsers()) {
            players.add(new Player(user));
        }
        Game game;
        lobby.setGame(game = new Game(lobby.getAdmin(), players));
        game.init();
        for (Player player : players) {
            int mapIndex = lobby.getUsernameToMap().get(player.getUsername());
            player.setFarmMap(game.getFarmMap(mapIndex));
            player.setCurrentCell(game.getFarmMap(mapIndex).getCell(8, 70));
        }
        notifyPlayers(players, game);
    }

    private static void notifyPlayers(ArrayList<Player> players, Game game) {
        for (Player player : players) {
            ServerApp.getClientConnectionThreadByUsername(player.getUsername()).sendMessage(
                    new Message(new HashMap<>() {{
                        put("farmInfo", game.getFarmInfo());
                    }}, Message.Type.start_game)
            );
            System.out.println("GOFTAM");
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
        if(!usersAndChosenMaps.containsKey(username)){
            usersAndChosenMaps.put(username, -1);
        }
        int pastId = usersAndChosenMaps.get(username);
        if(pastId == mapId){
            return new Message(new HashMap<>(){{
                put("GraphicalResult", GraphicalResult.getInfo("You have already chosen this map!"));
            }}, Message.Type.response);
        }
        for(String key : usersAndChosenMaps.keySet()){
            if(usersAndChosenMaps.get(key) == mapId){
                return new Message(new HashMap<>(){{
                    put("GraphicalResult", GraphicalResult.getInfo("This map is selected already!"));
                }}, Message.Type.response);
            }
        }
        lobby.setMap(username , mapId);
        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo("Map selected successfully!", false));
        }}, Message.Type.response);
    }

    public static void leaveLobby(Message message) {
        String username = message.getFromBody("username");
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        if (lobby == null) {
            return;
        }
        lobby.removeUsernameMap(username);
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
