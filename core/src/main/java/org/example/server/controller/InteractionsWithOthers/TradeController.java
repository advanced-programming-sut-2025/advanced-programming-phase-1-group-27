package org.example.server.controller.InteractionsWithOthers;

import org.example.common.models.Message;
import org.example.common.models.Relations.Trade;
import org.example.server.models.ServerApp;

import java.util.ArrayList;
import java.util.HashMap;


public class TradeController {
    public static Message getTradesBetweenUsers(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        String starter = message.getFromBody("starter");
        String other = message.getFromBody("other");
        ArrayList<HashMap<String, Object>> selected = new ArrayList<>();
        for (Trade trade : ServerApp.getLobbyById(lobbyId).getGame().getTrades()) {
            if (starter.equals(trade.getStarter()) && other.equals(trade.getOther())) {
                selected.add(trade.getInfo());
            } else if (starter.equals(trade.getOther()) && other.equals(trade.getStarter())) {
                selected.add(trade.getInfo());
            }
        }
        return new Message(new HashMap<>() {{
            put("trades", selected);
        }}, Message.Type.response);
    }
}
