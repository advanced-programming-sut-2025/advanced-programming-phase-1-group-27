package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.Stacks;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TradeController {
    public void startTrade(String username) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>() {{
            put("mode", "startTrade");
            put("lobbyId", lobbyId);
            put("starter", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("other", username);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }}, Message.Type.interaction_p2p);
        ClientApp.getServerConnectionThread().sendMessage(message);
    }

    public void respondToStartTrade(String username, boolean answer) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>() {{
            put("mode", "respondToStartTrade");
            put("lobbyId", lobbyId);
            put("starter", username);
            put("other", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("answer", answer);
        }}, Message.Type.interaction_p2p);
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

    public void sendSelected(ArrayList<Stacks> selected, String starter, String other) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "updateSelected");
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("selectedInfo", new Backpack(ToolType.BasicBackpack, selected).getInfo());
        }}, Message.Type.interaction_p2p));
    }

    public ArrayList<Stacks> updateSelected(Message message) {
        return new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("selectedInfo")).getItems();
    }

}
