package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.DialogueType;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.tools.Backpack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;


public class InteractionsWithUserController {

//    public Result friendship() {
//
//    }
//
//    public Result talk(String username, String message) {
//
//    }
//
//    public Result talkHistory(String username) {
//
//    }
//
//    public Result gift(String username, String stringItem, String stringAmount) {
//
//    }
//
//    public Result giftList() {
//
//    }
//
//    public Result giftRate(String giftNumber, String stringRate) {
//
//    }
//
//    public Result giftHistory(String username) {
//
//    }
//
//    public Result hug(String username) {
//
//    }
//
//    public Result flower(String username) {
//
//    }
//
//    private boolean isPlayerNear(Player player) {
//
//    }
//
//    private Player getPlayerWithUsername(String username) {
//
//    }
//
//    public static Player getPlayerByUsername(String username) {
//
//    }
//
//    public Result cheatAddPlayerLevel(String playerName, String quantityString) {
//
//    }

    public static Relation getRelation(String username) {
        Message message = new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username1", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            put("username2", username);
        }}, Message.Type.get_player_relation);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new Relation();
        }
        int level = response.getIntFromBody("Level");
        int xp = response.getIntFromBody("XP");
        Relation relation = new Relation();
        relation.setLevel(level);
        relation.setXp(xp);
        return relation;
    }

    public static ArrayList<Stacks> getInventory(String username) {
        Message message = new Message(new HashMap<>(){{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("username", username);
        }} , Message.Type.get_player_inventory);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS * 2);
        if (response == null || response.getType() != Message.Type.response) {
            System.out.println("Inventory failed!");
            return new ArrayList<Stacks>();
        }
        return new Backpack(response.<LinkedTreeMap<String, Object>>getFromBody("inventoryInfo")).getItems();
    }

}
