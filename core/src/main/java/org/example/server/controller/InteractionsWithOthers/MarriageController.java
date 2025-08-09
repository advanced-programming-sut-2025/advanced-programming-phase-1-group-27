package org.example.server.controller.InteractionsWithOthers;

import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.DialogueType;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.tools.Backpack;

import java.util.HashMap;

public class MarriageController {
    public static Message canMarry(Message message) {
        String self = message.getFromBody("self");
        String other = message.getFromBody("other");
        int lobbyId = Integer.parseInt(message.getFromBody("lobbyId"));
        Lobby lobby = ServerApp.getLobbyById(lobbyId);
        Player currentPlayer = lobby.getGame().getPlayerByUsername(self);
        Player otherPlayer = lobby.getGame().getPlayerByUsername(other);
        if (currentPlayer.isMarried()) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", new GraphicalResult("You are married!", true));
            }}, Message.Type.response);
        }
        if (otherPlayer.getGender() == Gender.Male) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", new GraphicalResult("Why are u gay?", true));
            }}, Message.Type.response);
        }
        if (otherPlayer.isMarried()) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", new GraphicalResult(otherPlayer.getUsername() + " is married!",
                        true));
            }}, Message.Type.response);
        }
        if (!currentPlayer.canMarried(otherPlayer)) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", new GraphicalResult("Your relation level is too low!",
                        true));
            }}, Message.Type.response);
        }
        return new Message(new HashMap<>() {{
            put("GraphicalResult", new GraphicalResult("Everything is ok!",
                    false));
        }}, Message.Type.response);
    }

    public static void sendMarriageRequest(Message message) {
        ServerApp.getClientConnectionThreadByUsername(message.getFromBody("other")).sendMessage(message);
    }

    public static void sendMarriageResponse(Message message) {
        int lobbyId = Integer.parseInt(message.getFromBody("lobbyId"));
        String proposer = message.getFromBody("proposer");
        String self = message.getFromBody("self");
        boolean answer = message.getFromBody("answer");
        Player player1 = ServerApp.getLobbyById(lobbyId).getGame().getPlayerByUsername(self);
        Player player2 = ServerApp.getLobbyById(lobbyId).getGame().getPlayerByUsername(proposer);
        if (answer) {
            player1.setSpouse(player2);
            player2.setSpouse(player1);
            player2.getBackpack().reduceItems(ShopItems.WeddingRing, 1);
            StackLevel stackLevel = player2.getBackpack().getStackLevel(ShopItems.WeddingRing);
            player1.getBackpack().addItems(ShopItems.WeddingRing, stackLevel, 1);
            player1.goNextLevel(player2);
            player2.goNextLevel(player1);
            int newMoney = player1.getMoney() + player2.getMoney();
            player1.setMoney(newMoney);
            player2.setMoney(newMoney);
        } else {
            player1.getRelations().put(player2, new Relation());
            player2.getRelations().put(player1, new Relation());
        }
        ServerApp.getClientConnectionThreadByUsername(proposer).sendMessage(message);
    }

    public Result showCouple() {
        if (App.getCurrentGame().getCurrentPlayer().getSpouse() == null) {
            return new Result(false, "You are not married");
        } else {
            return new Result(true, "You are married with " +
                    App.getCurrentGame().getCurrentPlayer().getSpouse().getUsername());
        }
    }

//    public Result respond(String response, String username) {
//        // TODO: function incomplete
//        Player player = getPlayerWithUsername(username);
//        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
//        if (player == null) {
//            return new Result(false, "Player not found!");
//        }
//        Dialogue dialogue1 = null;
//        for (Dialogue dialogue : currentPlayer.getDialogues()) {
//            if (dialogue.getType() == DialogueType.Marriage) {
//                if (dialogue.getSender().getUsername().equals(username)) {
//                    dialogue1 = dialogue;
//                }
//            }
//        }
//        if (dialogue1 == null) {
//            return new Result(false, "You don't have marriage request from " + player.getUsername());
//        }
//        if (response.equals("accept")) {
//            if (!player.getBackpack().hasEnoughItem(ShopItems.WeddingRing, 2)) {
//                return new Result(false, username + " doesn't have 2 wedding rings!");
//            }
//            player.setSpouse(currentPlayer);
//            currentPlayer.setSpouse(player);
//            currentPlayer.deleteMarriage();
//            player.getBackpack().reduceItems(ShopItems.WeddingRing, 1);
//            StackLevel stackLevel = player.getBackpack().getStackLevel(ShopItems.WeddingRing);
//            currentPlayer.getBackpack().addItems(ShopItems.WeddingRing, stackLevel, 1);
//            player.goNextLevel(currentPlayer);
//            currentPlayer.goNextLevel(player);
//            int newMoney = player.getMoney() + currentPlayer.getMoney();
//            player.setMoney(newMoney);
//            currentPlayer.setMoney(newMoney);
//            return new Result(true, "Congratulations!");
//        } else if (response.equals("reject")) {
//            player.getRelations().put(currentPlayer, new Relation());
//            currentPlayer.getRelations().put(player, new Relation());
//            currentPlayer.deleteDialogue(dialogue1);
//            //TODO : sobhan. Energy player bayad ta 7 rooz nesf she!
//            return new Result(true, "Unfortunately!");
//        } else {
//            return new Result(false, "Invalid response!");
//        }
//    }
//
//    private Player getPlayerWithUsername(String username) {
//        for (Player player : App.getCurrentGame().getPlayers()) {
//            if (player.getUsername().equalsIgnoreCase(username)) {
//                return player;
//            }
//        }
//        return null;
//    }
}
