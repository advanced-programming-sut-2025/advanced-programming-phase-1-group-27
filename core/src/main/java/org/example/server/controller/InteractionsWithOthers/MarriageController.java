package org.example.server.controller.InteractionsWithOthers;

import org.example.common.models.*;
import org.example.common.models.Relations.Relation;
import org.example.server.models.ServerApp;

import java.util.HashMap;

public class MarriageController {
    public static Message canMarry(Message message) {
        String self = message.getFromBody("self");
        String other = message.getFromBody("other");
        int lobbyId = message.getIntFromBody("lobbyId");
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        Player currentPlayer = lobby.getGame().getPlayerByUsername(self);
        Player otherPlayer = lobby.getGame().getPlayerByUsername(other);
        if (currentPlayer.isMarried()) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("You are married!", true));
            }}, Message.Type.response);
        }
        if (otherPlayer.getGender() == Gender.Male) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Why are u gay?", true));
            }}, Message.Type.response);
        }
        if (otherPlayer.isMarried()) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo(otherPlayer.getUsername() + " is married!",
                        true));
            }}, Message.Type.response);
        }
        if (!currentPlayer.canMarried(otherPlayer)) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Your relation level is too low!",
                        true));
            }}, Message.Type.response);
        }
        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo("Everything is ok!",
                    false));
        }}, Message.Type.response);
    }

    public static void sendMarriageRequest(Message message) {
        ServerApp.getClientConnectionThreadByUsername(message.getFromBody("other")).sendMessage(message);
    }

    public static void sendMarriageResponse(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        String proposer = message.getFromBody("proposer");
        String self = message.getFromBody("self");
        boolean answer = message.getFromBody("answer");
        Player player1 = ServerApp.getLobbyById(lobbyId).getGame().getPlayerByUsername(self);
        Player player2 = ServerApp.getLobbyById(lobbyId).getGame().getPlayerByUsername(proposer);
        if (answer) {
            player1.setSpouse(player2);
            player2.setSpouse(player1);
            player1.goNextLevel(player2);
            player2.goNextLevel(player1);
        } else {
            player1.getRelations().put(player2, new Relation());
            player2.getRelations().put(player1, new Relation());
        }
        ServerApp.getClientConnectionThreadByUsername(proposer).sendMessage(message);
    }
}
