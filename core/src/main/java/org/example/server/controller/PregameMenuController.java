package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
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
}
