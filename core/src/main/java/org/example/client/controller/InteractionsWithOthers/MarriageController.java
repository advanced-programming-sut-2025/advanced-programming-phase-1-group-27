package org.example.client.controller.InteractionsWithOthers;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.ClientApp;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.App;
import org.example.server.models.Player;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Result;
import org.example.server.models.enums.DialogueType;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.tools.Backpack;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class MarriageController {
    public GraphicalResult askMarriage(String username) {
        Player currentPlayer = ClientApp.getCurrentGame().getCurrentPlayer();
        Backpack backpack = currentPlayer.getBackpack();
        if (currentPlayer.getGender() == Gender.Female) {
            return new GraphicalResult("You are not male!!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }
        if (!backpack.hasEnoughItem(ShopItems.WeddingRing, 2)) {
            return new GraphicalResult("You don't have 2 wedding rings!",
                    GameAssetManager.getGameAssetManager().getErrorColor());
        }
        Message canMarriedMessage = new Message(new HashMap<>() {{
            put("lobbyId" , ClientApp.getCurrentGame().getLobbyId());
            put("self" , currentPlayer.getUsername());
            put("other" , username);
        }} , Message.Type.can_marry);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(canMarriedMessage, TIMEOUT_MILLIS);
        if (response == null || response.getType() != Message.Type.response) {
            return new GraphicalResult(
                    "Failed to check",
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        if(result.hasError()){
            return new GraphicalResult(
                    result.getMessage().toString(),
                    GameAssetManager.getGameAssetManager().getErrorColor()
            );
        }
        Message message = new Message(new HashMap<>() {{
            put("lobbyId" , ClientApp.getCurrentGame().getLobbyId());
            put("self" , currentPlayer.getUsername());
            put("other" , username);
        }} , Message.Type.marriage_request);

        return null;
    }

    public Result showCouple() {
        if (App.getCurrentGame().getCurrentPlayer().getSpouse() == null) {
            return new Result(false, "You are not married");
        } else {
            return new Result(true, "You are married with " +
                    App.getCurrentGame().getCurrentPlayer().getSpouse().getUsername());
        }
    }

    public Result respond(String response, String username) {
        // TODO: function incomplete
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (player == null) {
            return new Result(false, "Player not found!");
        }
        Dialogue dialogue1 = null;
        for (Dialogue dialogue : currentPlayer.getDialogues()) {
            if (dialogue.getType() == DialogueType.Marriage) {
                if (dialogue.getSender().getUsername().equals(username)) {
                    dialogue1 = dialogue;
                }
            }
        }
        if (dialogue1 == null) {
            return new Result(false, "You don't have marriage request from " + player.getUsername());
        }
        if (response.equals("accept")) {
            if (!player.getBackpack().hasEnoughItem(ShopItems.WeddingRing, 2)) {
                return new Result(false, username + " doesn't have 2 wedding rings!");
            }
            player.setSpouse(currentPlayer);
            currentPlayer.setSpouse(player);
            currentPlayer.deleteMarriage();
            player.getBackpack().reduceItems(ShopItems.WeddingRing, 1);
            StackLevel stackLevel = player.getBackpack().getStackLevel(ShopItems.WeddingRing);
            currentPlayer.getBackpack().addItems(ShopItems.WeddingRing, stackLevel, 1);
            player.goNextLevel(currentPlayer);
            currentPlayer.goNextLevel(player);
            int newMoney = player.getMoney() + currentPlayer.getMoney();
            player.setMoney(newMoney);
            currentPlayer.setMoney(newMoney);
            return new Result(true, "Congratulations!");
        } else if (response.equals("reject")) {
            player.getRelations().put(currentPlayer, new Relation());
            currentPlayer.getRelations().put(player, new Relation());
            currentPlayer.deleteDialogue(dialogue1);
            //TODO : sobhan. Energy player bayad ta 7 rooz nesf she!
            return new Result(true, "Unfortunately!");
        } else {
            return new Result(false, "Invalid response!");
        }
    }

    private Player getPlayerWithUsername(String username) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equalsIgnoreCase(username)) {
                return player;
            }
        }
        return null;
    }
}
