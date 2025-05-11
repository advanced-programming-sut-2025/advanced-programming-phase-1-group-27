package org.example.controller.InteractionsWithOthers;

import org.example.models.App;
import org.example.models.Player;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Relation;
import org.example.models.Result;
import org.example.models.enums.DialogueType;
import org.example.models.enums.Gender;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ShopItems;
import org.example.models.tools.Backpack;

public class MarriageController {

    public Result askMarriage(String username, String ring) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (player == null) {
            return new Result(false, "Player not found!");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near you!");
        }
        if (currentPlayer.getGender() == Gender.Female) {
            return new Result(false, "You are not male!!");
        }
        if (!currentPlayer.isMarried()) {
            return new Result(false, "You are married!");
        }
        if (player.getGender() == Gender.Male) {
            return new Result(false, "Why are u gay?");
        }
        if (!player.isMarried()) {
            return new Result(false, player.getUsername() + " is married!");
        }
        if (!currentPlayer.canMarried(player)) {
            return new Result(false, "Your relation level is too low!");
        }
        Backpack backpack = currentPlayer.getBackpack();
        if (!backpack.hasEnoughItem(ShopItems.WeddingRing, 2)) {
            return new Result(false, "You don't have 2 wedding rings!");
        }
        Dialogue dialogue = new Dialogue(DialogueType.Marriage, null, "Will you marry me?"
                , player, currentPlayer);
        player.addDialogue(dialogue);
        App.getCurrentGame().addDialogue(dialogue);
        return new Result(true, "Wait for her respond!");
    }

    public Result respond(String response, String username) {
        // TODO: function incomplete
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (player == null) {
            return new Result(false, "Player not found!");
        }
        if (response.equals("accept")) {
            player.marryWIth(currentPlayer);
            currentPlayer.marryWIth(player);
            currentPlayer.deleteMarriage();
            player.getBackpack().reduceItems(ShopItems.WeddingRing, 1);
            StackLevel stackLevel = player.getBackpack().getStackLevel(ShopItems.WeddingRing);
            currentPlayer.getBackpack().addItems(ShopItems.WeddingRing, stackLevel, 1);
            player.goNextLevel(currentPlayer);
            currentPlayer.goNextLevel(player);
            //TODO : Rassa inja ro chikar konam???
            return new Result(true, "Congratulations!You married!");
        } else if (response.equals("reject")) {
            player.getRelations().put(currentPlayer, new Relation());
            currentPlayer.getRelations().put(player, new Relation());
            //TODO : Energy player bayad ta 7 rooz nesf she!
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
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
}
