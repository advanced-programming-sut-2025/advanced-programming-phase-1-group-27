package org.example.server.controller.InteractionsWithOthers;

import org.example.client.controller.menus.MenuController;
import org.example.client.model.ClientApp;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Trade;
import org.example.server.models.enums.DialogueType;
import org.example.server.models.enums.Menu;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.tools.Backpack;
import org.example.client.view.InteractionMenus.TradeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.swing.UIManager.put;


public class TradeController {
    public static Message getTradesBetweenUsers(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        String starter = message.getFromBody("starter");
        String other = message.getFromBody("other");
        ArrayList<HashMap<String , Object>> selected = new ArrayList<>();
        for (Trade trade : ServerApp.getLobbyById(lobbyId).getGame().getTrades()) {
            if(starter.equals(trade.getStarter()) && other.equals(trade.getOther())) {
                selected.add(trade.getInfo());
            }else if(starter.equals(trade.getOther()) && other.equals(trade.getStarter())) {
                selected.add(trade.getInfo());
            }
        }
        return new Message(new HashMap<>() {{
            put("trades", selected);
        }}, Message.Type.response);
    }
}
