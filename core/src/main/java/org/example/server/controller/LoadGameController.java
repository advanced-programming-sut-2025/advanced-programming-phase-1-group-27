package org.example.server.controller;

import org.example.common.models.Message;
import org.example.common.models.Lobby;
import org.example.server.models.ServerApp;
import org.example.common.models.User;

import java.util.HashMap;

public class LoadGameController {
    // indicates the number of users which are waiting for the game to load
    public static HashMap<Integer, Integer> lobbyIdToNumberOfWaiting = new HashMap<>();

    public static void handleLoadRequest(Message message) {
        String mode = message.getFromBody("mode");
        if (mode.equals("join"))
            handleJoinRequest(message);
        else if (mode.equals("leave"))
            handleLeaveRequest(message);
    }

    private static void handleLeaveRequest(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        assert lobby != null;
        lobbyIdToNumberOfWaiting.put(lobbyId, lobbyIdToNumberOfWaiting.get(lobbyId) - 1);
    }

    private static void handleJoinRequest(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        assert lobby != null;
        if (!lobbyIdToNumberOfWaiting.containsKey(lobbyId))
            lobbyIdToNumberOfWaiting.put(lobbyId, 0);
        lobbyIdToNumberOfWaiting.put(lobbyId, lobbyIdToNumberOfWaiting.get(lobbyId) + 1);
        if (lobby.getUsers().size() == lobbyIdToNumberOfWaiting.get(lobbyId))
            loadGame(lobby);
    }

    private static void loadGame(Lobby lobby) {
        lobby.getGame().resume();
        for (User user : lobby.getUsers()) {
            SaveController.sendInfo(lobby, user.getUsername());
        }
        lobbyIdToNumberOfWaiting.put(lobby.getId(), 0);
    }
}
