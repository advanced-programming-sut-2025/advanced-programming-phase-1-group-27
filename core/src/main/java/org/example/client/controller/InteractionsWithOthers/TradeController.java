package org.example.client.controller.InteractionsWithOthers;

import com.badlogic.gdx.Gdx;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.AppMenu;
import org.example.client.view.HomeView;
import org.example.client.view.InteractionMenus.PreTradeMenuView;
import org.example.client.view.InteractionMenus.TradeView;
import org.example.common.models.Message;
import org.example.server.models.Relations.Trade;
import org.example.server.models.Stacks;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

import java.io.StringReader;
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
        Gdx.app.postRunnable(() -> {
            if (answer) {
                Main.getMain().dispose();
                ClientApp.setCurrentMenu(new TradeView(username, ClientApp.getCurrentGame().getCurrentPlayer().getUsername(), lastView));
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
            } else {
                Main.getMain().dispose();
                ClientApp.setCurrentMenu(lastView);
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
            }
        });
    }

    public void checkRespondToStart(Message message, AppMenu lastView) {
        boolean answer = message.getFromBody("answer");
        String starter = message.getFromBody("starter");
        String other = message.getFromBody("other");
        Gdx.app.postRunnable(() -> {
            if (answer) {
                Main.getMain().dispose();
                ClientApp.setCurrentMenu(new TradeView(starter, other, lastView));
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
            } else {
                Main.getMain().dispose();
                ClientApp.setCurrentMenu(lastView);
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
            }
        });
    }

    public void getSuggestedTrade(Message message) {
        // TODO : bayad begi ke in trade mored ghabool hast ya na
        // other dariaft mikone
        // inja bayad false she
        TradeView tradeView = (TradeView) ClientApp.getCurrentMenu();
        tradeView.setTradeDoneByStarterSide(false);
    }
    public void sendConfirmation(boolean answer, String starter, String other , ArrayList<Stacks> starterSelected
            , ArrayList<Stacks> otherSelected , AppMenu lastView) {
        // TODO : age okay boodi ba in trade bayad in function seda beshe to nahayee she
        // from other
        if (answer) {
            // Inventory dorost she
            addToInventory(starterSelected);
            reduceFromInventory(otherSelected);
        } else {
            // trade namovafagh bood
        }
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "confirmTrade");
            put("lobbyId", lobbyId);
            put("answer", answer);
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("starterSelected", new Backpack(ToolType.BasicBackpack, starterSelected).getInfo());
            put("otherSelected", new Backpack(ToolType.BasicBackpack, otherSelected).getInfo());
        }}, Message.Type.interaction_p2p));
        // XP
        Gdx.app.postRunnable(() -> {
            Main.getMain().dispose();
            ClientApp.setCurrentMenu(lastView);
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        });
    }

    public void checkConfirmation(Message message, AppMenu lastView) {
        // check other's check
        ArrayList<Stacks> starterSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("starterSelected")).getItems();
        ArrayList<Stacks> otherSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("otherSelected")).getItems();
        boolean answer = message.getFromBody("answer");
        if (answer) {
            // Inventory dorost she
            addToInventory(otherSelected);
            reduceFromInventory(starterSelected);
        } else {
            // trade namovafagh bood
        }
        // XP
        Gdx.app.postRunnable(() -> {
            Main.getMain().dispose();
            ClientApp.setCurrentMenu(lastView);
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
        });
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
        // from starter
        // inja bayad false she
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "suggestTrade");
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }}, Message.Type.interaction_p2p));
    }

    public void decline(Message message) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        String username = message.getFromBody("starter");
        Message decline = new Message(new HashMap<>() {{
            put("mode", "respondToStartTrade");
            put("lobbyId", lobbyId);
            put("starter", username);
            put("other", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("answer", false);
        }}, Message.Type.interaction_p2p);
        ClientApp.getServerConnectionThread().sendMessage(decline);
    }

    private void addToInventory(ArrayList<Stacks> selected) {
        Backpack backpack = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack();
        for (Stacks stack : selected) {
            backpack.addItems(stack.getItem() , stack.getStackLevel() , stack.getQuantity());
        }
    }

    private void reduceFromInventory(ArrayList<Stacks> selected) {
        Backpack backpack = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack();
        for (Stacks stack : selected) {
            backpack.reduceItems(stack.getItem() , stack.getStackLevel() , stack.getQuantity());
        }
    }

    private ArrayList<Trade> getTradeHistory(String username){
        Message message = new Message(new HashMap<>() {{
            put("mode", "getTradeHistory");
            put("starter", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("other", username);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }} , Message.Type.interaction_p2p);
        // TODO : incomplete
        return null;
    }
}
