package org.example.client.controller.InteractionsWithOthers;

import com.badlogic.gdx.Gdx;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.model.ClientApp;
import org.example.client.view.InteractionMenus.InteractionMenu;
import org.example.client.view.InteractionMenus.Trade.PreTradeMenuView;
import org.example.client.view.InteractionMenus.Trade.TradeHistoryView;
import org.example.client.view.InteractionMenus.Trade.TradeView;
import org.example.client.view.OutsideView;
import org.example.common.models.Message;
import org.example.common.models.Relations.Trade;
import org.example.common.models.Stacks;
import org.example.common.models.items.ToolType;
import org.example.common.models.tools.Backpack;

import java.util.ArrayList;
import java.util.HashMap;

import static org.example.client.model.ClientApp.TIMEOUT_MILLIS;

public class TradeController {

    public void goToTradeHistory(String username) {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new TradeHistoryView(username));
    }

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
        ArrayList<Stacks> targetInventory = answer ? InteractionsWithUserController.getInventory(username) : new ArrayList<>();
        Gdx.app.postRunnable(() -> {
            if (answer) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new TradeView(username, ClientApp.getCurrentGame().getCurrentPlayer().getUsername(), targetInventory));
            } else {
                ClientApp.setNonMainMenu(null);
                Main.getMain().getScreen().dispose();
                OutsideView newOutsideView = new OutsideView();
                ClientApp.setNonMainMenu(newOutsideView);
                Main.getMain().setScreen(newOutsideView);
            }
        });
    }

    public void checkRespondToStart(Message message) {
        boolean answer = message.getFromBody("answer");
        String starter = message.getFromBody("starter");
        String other = message.getFromBody("other");
        System.out.println("answer: " + answer);
        Gdx.app.postRunnable(() -> {
            if (answer && ClientApp.getNonMainMenu() instanceof PreTradeMenuView preTradeMenuView) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new TradeView(starter, other, preTradeMenuView.getTargetInventory()));
            } else {
                ClientApp.setNonMainMenu(null);
                Main.getMain().getScreen().dispose();
                OutsideView newOutsideView = new OutsideView();
                ClientApp.setNonMainMenu(newOutsideView);
                Main.getMain().setScreen(newOutsideView);
            }
        });
    }

    public void sendConfirmation(boolean answer, String starter, String other, ArrayList<Stacks> starterSelected, ArrayList<Stacks> otherSelected) {
        // TODO : age okay boodi ba in trade bayad in function seda beshe to nahayee she
        // from other
        if (answer) {
            // Inventory dorost she
            addToInventory(otherSelected);
            reduceFromInventory(starterSelected);
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
        ClientApp.setNonMainMenu(null);
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            OutsideView newOutsideView = new OutsideView();
            ClientApp.setNonMainMenu(newOutsideView);
            Main.getMain().setScreen(newOutsideView);
        });
    }

    public void checkConfirmation(Message message) {
        // check other's check
        ArrayList<Stacks> starterSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("starterSelected")).getItems();
        ArrayList<Stacks> otherSelected = new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("otherSelected")).getItems();
        boolean answer = message.getFromBody("answer");
        if (answer) {
            // Inventory dorost she
            addToInventory(starterSelected);
            reduceFromInventory(otherSelected);
        }
        ClientApp.setNonMainMenu(null);
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            OutsideView newOutsideView = new OutsideView();
            ClientApp.setNonMainMenu(newOutsideView);
            Main.getMain().setScreen(newOutsideView);

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
            backpack.addItems(stack.getItem(), stack.getStackLevel(), stack.getQuantity());
        }
    }

    private void reduceFromInventory(ArrayList<Stacks> selected) {
        Backpack backpack = ClientApp.getCurrentGame().getCurrentPlayer().getBackpack();
        for (Stacks stack : selected) {
            backpack.reduceItems(stack.getItem(), stack.getStackLevel(), stack.getQuantity());
        }
    }

    public ArrayList<Trade> getTradeHistory(String username) {
        int lobbyId = ClientApp.getCurrentGame().getLobbyId();
        Message message = new Message(new HashMap<>() {{
            put("mode", "getTradeHistory");
            put("lobbyId", lobbyId);
            put("starter", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("other", username);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }}, Message.Type.get_trade_history);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new ArrayList<>();
        }
        ArrayList<Trade> trades = new ArrayList<>();
        for (LinkedTreeMap<String, Object> ti : response.<ArrayList<LinkedTreeMap<String, Object>>>getFromBody("trades")) {
            trades.add(new Trade(ti));
        }
        return trades;
    }

    public void exit(String username) {
        Main.getMain().getScreen().dispose();
        InteractionMenu interactionMenu = new InteractionMenu(username);
        Main.getMain().setScreen(interactionMenu);
    }
}
