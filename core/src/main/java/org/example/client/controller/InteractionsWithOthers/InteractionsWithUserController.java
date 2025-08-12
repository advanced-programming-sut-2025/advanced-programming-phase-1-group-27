package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.common.models.GraphicalResult;
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
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(message, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            System.out.println("Inventory failed!");
            return new ArrayList<>();
        }
        return new Backpack(response.<LinkedTreeMap<String, Object>>getFromBody("inventoryInfo")).getItems();
    }

    public static void sendInventory(Backpack inventory, String starter, String other) {
        // Rassa Karet Tamoome
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "sendInventory");
            put("inventoryInfo", inventory.getInfo());
            put("starter", starter);
            put("other", other);
            put("self", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
        }}, Message.Type.interaction_p2p));
    }

    public static GraphicalResult flower(String username) {
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        Relation relation = getRelation(username);
        Backpack backpack = currentPlayer.getBackpack();
        //TODO : faghat zamani gol mishe dad ke level 2 max bashe!
        if (!backpack.hasEnoughItem(ShopItems.Bouquet, 1)) {
            return new GraphicalResult("You don't have any Bouquet!");
        }
        if (canFlowered(relation)) {
            return new GraphicalResult("You can't give flower");
        }
        backpack.reduceItems(ShopItems.Bouquet, 1);
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "flower");
            put("starter", ClientApp.getLoggedInUser().getUsername());
            put("other", username);
            put("self", ClientApp.getLoggedInUser().getUsername());
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
        }}, Message.Type.interaction_p2p));
        return new GraphicalResult("You have give your friend flower!" , false);
    }

    public static GraphicalResult hug(String username) {
        Relation relation = getRelation(username);
        if (relation.getLevel() < 2) {
            return new GraphicalResult("Your level is too low");
        }
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "hug");
            put("starter", ClientApp.getLoggedInUser().getUsername());
            put("other", username);
            put("self", ClientApp.getLoggedInUser().getUsername());
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
        }}, Message.Type.interaction_p2p));
        return new GraphicalResult("You hugged your friend!" , false);
    }

    private static boolean canFlowered(Relation relation) {
        int level = relation.getLevel();
        int xp = relation.getXp();
        int max = (level + 1) * 100;
        if (level == 2
                && xp == max) {
            return true;
        }
        return false;
    }
}
