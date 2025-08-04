package org.example.client.controller.InteractionsWithOthers;

import org.example.client.model.ClientApp;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.Stacks;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class TradeController {
    public void startTrade(String username) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>() {{
            put("mode", "startTrade");
            put("lobbyId", lobbyId);
            put("starter", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("other", username);
        }}, Message.Type.InteractionP2P);
        ClientApp.getServerConnectionThread().sendMessage(message);
    }

    public void respondToStartTrade(String username, boolean answer) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>() {{
            put("mode", "respondToStartTrade");
            put("lobbyId", lobbyId);
            put("starter", username);
            put("other", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("answer", answer);
        }}, Message.Type.InteractionP2P);
        ClientApp.getServerConnectionThread().sendMessage(message);
        if (answer) {
            // TODO : Go to TradeView
        } else {
            // TODO : Go to last Menu
        }
    }

    public GraphicalResult getRespondToStart(Message message) {
        boolean answer = message.getFromBody("answer");
        if (answer) {
            //TODO : Go to TradeView
        } else {
            // TODO : Go to last Menu
        }
        return null;
    }

    public void sendSelected(ArrayList<Stacks> selected) {
        // TODO : Rassa
    }

    public ArrayList<Stacks> updateSelected(Message message) {
        //TODO : Rassa
        return null;
    }

}
