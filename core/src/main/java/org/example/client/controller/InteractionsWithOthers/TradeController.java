package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.InteractionMenus.PreTradeMenuView;
import org.example.client.view.InteractionMenus.TradeView;
import org.example.common.models.Message;
import org.example.server.models.Stacks;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void respondToStartTrade(String username, boolean answer, AppMenu lastView) {
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

            Main.getMain().dispose();
            ClientApp.setCurrentMenu(new TradeView(username,ClientApp.getCurrentGame().getCurrentPlayer().getUsername(),lastView));
            Main.getMain().setScreen(ClientApp.getCurrentMenu());


        } else {
            Main.getMain().dispose();
            ClientApp.setCurrentMenu(lastView);
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        }
    }

    public void checkRespondToStart(Message message) {
        boolean answer = message.getFromBody("answer");
        if (answer) {
            //TODO : Go to TradeView
        } else {
            // TODO : Go to last Menu
        }
    }

    public void getSuggestedTrade(Message message) {
        // TODO : bayad begi ke in trade mored ghabool hast ya na
    }

    public void sendConfirmation(boolean answer, String starter, String other , ArrayList<Stacks> starterInventory,
                                 ArrayList<Stacks> otherInventory) {
        // TODO : age okay boodi ba in trade bayad in function seda beshe to nahayee she
        if(answer){
            // Inventory dorost she
        }
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "confirmTrade");
            put("answer", answer);
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("starterInventory" , new Backpack(ToolType.BasicBackpack , starterInventory).getInfo());
            put("otherInventory" , new  Backpack(ToolType.BasicBackpack , otherInventory).getInfo());
        }}, Message.Type.interaction_p2p));
    }

    public void checkConfirmation(Message message) {
        boolean answer = message.getFromBody("answer");
        if (answer) {
            // TODO: trade tamam shod va bayad variz shavad be do traf
        }
        else {
            // TODO : trade namovafagh bood
        }
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

    public void suggestTrade(String starter, String other) {
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "suggestTrade");
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }}, Message.Type.interaction_p2p));
    }

}
