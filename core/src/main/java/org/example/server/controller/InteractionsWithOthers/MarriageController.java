package org.example.server.controller.InteractionsWithOthers;

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

public class MarriageController {

    public Result askMarriage(String username, String ring) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        //TODO : faghat zamani ke level 3 max
        if (player == null) {
            return new Result(false, "Player not found!");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near you!");
        }
        if (currentPlayer.getGender() == Gender.Female) {
            return new Result(false, "You are not male!!");
        }
        if (currentPlayer.isMarried()) {
            return new Result(false, "You are married!");
        }
        if (player.getGender() == Gender.Male) {
            return new Result(false, "Why are u gay?");
        }
        if (player.isMarried()) {
            return new Result(false, player.getUsername() + " is married!");
        }
        if (!currentPlayer.canMarried(player)) {
            return new Result(false, "Your relation level is too low!");
        }
        Backpack backpack = currentPlayer.getBackpack();
        if (!backpack.hasEnoughItem(ShopItems.WeddingRing, 2)) {
            return new Result(false, "You don't have 2 wedding rings!");
        }
        Dialogue dialogue1 = null;
        for (Dialogue dialogue : player.getDialogues()) {
            if (dialogue.getType() == DialogueType.Marriage) {
                if (dialogue.getSender().getUsername().equals(currentPlayer.getUsername())) {
                    dialogue1 = dialogue;
                }
            }
        }
        if (dialogue1 != null) {
            return new Result(false, "You are already ask her!");
        }
        Dialogue dialogue = new Dialogue(DialogueType.Marriage, null, "Will you marry me?"
                , player, currentPlayer);
        player.addDialogue(dialogue);
        App.getCurrentGame().addDialogue(dialogue);
        return new Result(true, "Wait for her respond!");
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

    private boolean isPlayerNear(Player player) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();
        int currentX = App.getCurrentGame().getCurrentPlayer().getPosition().getX();
        int currentY = App.getCurrentGame().getCurrentPlayer().getPosition().getY();
        int distanceX = Math.abs(playerX - currentX);
        int distanceY = Math.abs(playerY - currentY);
        return distanceY <= 1
                && distanceX <= 1;
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
