package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.common.models.Message;

import java.util.HashMap;

public class VoteController {
    public void vote(String voteMode, String username, boolean answer) {
        if (voteMode.equals("askToTerminate")) {
            ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                put("mode", "voteToTerminate");
                put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                put("vote", answer);
            }}, Message.Type.voting));
        }
        else {
            ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
                put("mode", "voteToKick");
                put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                put("vote", answer);
                put("playerName", username);
            }}, Message.Type.voting));
        }
    }
}
