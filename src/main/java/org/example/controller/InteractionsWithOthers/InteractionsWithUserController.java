package org.example.controller.InteractionsWithOthers;

import org.example.models.*;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Relation;
import org.example.models.enums.DialogueType;
import org.example.models.enums.Plants.FruitType;
import org.example.models.tools.Backpack;

import java.util.ArrayList;


public class InteractionsWithUserController {

    public Result getNotification() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        ArrayList<Player> talk = new ArrayList<>();
        ArrayList<Player> trade = new ArrayList<>();
        ArrayList<Player> gift = new ArrayList<>();
        ArrayList<Player> marriage = new ArrayList<>();
        for (Dialogue dialogue : currentPlayer.getDialogues()) {
            if (dialogue.getType() == DialogueType.talk) {
                if (!talk.contains(dialogue.getSender())) {
                    talk.add(dialogue.getSender());
                }
            }
            if (dialogue.getType() == DialogueType.gift) {
                if (!gift.contains(dialogue.getSender())) {
                    gift.add(dialogue.getSender());
                }
            }
            if (dialogue.getType() == DialogueType.Trade) {
                if (!trade.contains(dialogue.getSender())) {
                    trade.add(dialogue.getSender());
                }
            }
            if (dialogue.getType() == DialogueType.Marriage) {
                if (!marriage.contains(dialogue.getSender())) {
                    marriage.add(dialogue.getSender());
                }
            }
        }
        if (!talk.isEmpty()) {
            result.append("Talk : \n");
            for (Player player : talk) {
                result.append(player.getUsername()).append("\n");
            }
            currentPlayer.deleteTalk();
        }
        if (!gift.isEmpty()) {
            result.append("Gift : \n");
            for (Player player : gift) {
                result.append(player.getUsername()).append("\n");
            }
        }
        if (!trade.isEmpty()) {
            result.append("Trade : \n");
            for (Player player : trade) {
                result.append(player.getUsername()).append("\n");
            }
        }
        if (!marriage.isEmpty()) {
            result.append("Marriage : \n");
            for (Player player : marriage) {
                result.append(player.getUsername()).append("\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result friendship() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        result.append("Relations with other players:\n");
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equals(currentPlayer.getUsername())) {
                continue;
            }
            if (!currentPlayer.getRelations().containsKey(player)) {
                currentPlayer.getRelations().put(player, new Relation());
            }
            Relation relation = currentPlayer.getRelations().get(player);
            result.append(player.getUsername()).append(" : Level = ").append(relation.getLevel())
                    .append(" XP = ").append(relation.getXp()).append("\n");
        }
        return new Result(true, result.toString());
    }

    public Result talk(String username, String message) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near you");
        }
        if (currentPlayer.getPlayerMetToday().get(player) == Boolean.FALSE
                || currentPlayer.getPlayerMetToday().get(player) == null) {
            currentPlayer.getPlayerMetToday().put(player, Boolean.TRUE);
            player.getPlayerMetToday().put(currentPlayer, Boolean.TRUE);
            currentPlayer.addXP(player, 20);
            player.addXP(currentPlayer, 20);
        }
        Dialogue dialogue = new Dialogue(DialogueType.talk, null, message, player, currentPlayer);
        App.getCurrentGame().addDialogue(dialogue);
        currentPlayer.addDialogue(dialogue);
        return new Result(true, "Message sent to " + player.getUsername());
    }

    public Result talkHistory(String username) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near you");
        }
        StringBuilder result = new StringBuilder();
        result.append("All messages with ").append(username).append(" :\n");
        for (Dialogue dialogue : App.getCurrentGame().getDialogues()) {
            if (dialogue.getType() == DialogueType.talk) {
                if (dialogue.getSender().getUsername().equals(username)
                        && dialogue.getResponder().getUsername().equals(currentPlayer.getUsername())) {
                    result.append(dialogue.getSender().getUsername()).append(" to ")
                            .append(dialogue.getResponder().getUsername()).append("\n");
                    result.append(dialogue.getInput()).append("\n");
                } else if (dialogue.getSender().getUsername().equals(currentPlayer.getUsername())
                        && dialogue.getResponder().getUsername().equals(username)) {
                    result.append(dialogue.getSender().getUsername()).append(" to ")
                            .append(dialogue.getResponder().getUsername()).append("\n");
                    result.append(dialogue.getInput()).append("\n");
                }
            }
        }
        return new Result(true, result.toString());
    }

    public Result gift(String username, String stringItem, String stringAmount) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        int amount = Integer.parseInt(stringAmount);
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near you");
        }
        if (!currentPlayer.getRelations().containsKey(player)) {
            currentPlayer.getRelations().put(player, new Relation());
        }
        Relation relation = currentPlayer.getRelations().get(player);
        if (relation.getLevel() == 0) {
            return new Result(false, "Relation level is 0");
        }
        Backpack backpack1 = currentPlayer.getBackpack();
        Item itemType = backpack1.getItemWithName(stringItem);
        if (itemType == null) {
            return new Result(false, "Item not found");
        }
        if (!backpack1.hasEnoughItem(itemType, amount)) {
            return new Result(false, "Item is not enough");
        }
        String respond = null;
        if (currentPlayer.getPlayerGiftToday().get(player) == Boolean.FALSE
                || currentPlayer.getPlayerGiftToday().get(player) == null) {
            currentPlayer.getPlayerGiftToday().put(player, Boolean.TRUE);
            player.getPlayerGiftToday().put(currentPlayer, Boolean.TRUE);
            respond = "1";//That means first time in day
        }
        Dialogue dialogue = new Dialogue(DialogueType.gift, respond, "You have been gifted "
                + amount + " * " + itemType.getName(), player, currentPlayer);
        App.getCurrentGame().addDialogue(dialogue);
        player.addDialogue(dialogue);
        backpack1.reduceItems(itemType, amount);
        player.getBackpack().addItems(itemType, backpack1.getStackLevel(itemType), amount);
        return new Result(true, "Gift has been send successfully");
    }

    public Result giftList() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        result.append("All gifts : \n");
        int i = 1;
        for (Dialogue dialogue : currentPlayer.getDialogues()) {
            if (dialogue.getType() == DialogueType.gift) {
                if (dialogue.getResponder().getUsername().equals(currentPlayer.getUsername())) {
                    String message = dialogue.getInput();
                    String prefix = "You have been gifted ";
                    if (message.startsWith(prefix)) {
                        message = message.substring(prefix.length());
                    }
                    result.append(i).append("-");
                    result.append(message).append("\n");
                    i++;
                }
            }
        }
        return new Result(true, result.toString());
    }

    public Result giftRate(String giftNumber, String stringRate) {
        int number = Integer.parseInt(giftNumber);
        int rate = Integer.parseInt(stringRate);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        if (rate > 5
                || rate < 0) {
            return new Result(false, "Rate must be between 0 and 5");
        }
        Dialogue dialogue1 = null;
        for (Dialogue dialogue : currentPlayer.getDialogues()) {
            if (dialogue.getType() == DialogueType.gift) {
                if (dialogue.getResponder().getUsername().equals(currentPlayer.getUsername())) {
                    if (number == 1) {
                        dialogue1 = dialogue;
                    } else {
                        number--;
                    }
                }
            }
        }
        if (dialogue1 == null) {
            return new Result(false, "Gift number is invalid");
        }
        Player player1 = dialogue1.getResponder();
        Player player2 = dialogue1.getSender();
        String add = "";
        if (dialogue1.getRespond().equals("1")) {//It is first time
            int xp = (rate - 3) * 30 + 15;
            add = String.valueOf(xp);
            if (xp < 0) {
                xp *= -1;
                player1.decreaseXP(player2, xp);
                player2.decreaseXP(player1, xp);
            } else {
                player1.addXP(player2, xp);
                player2.addXP(player1, xp);
            }
        }
        player2.deleteDialogue(dialogue1);
        player1.deleteDialogue(dialogue1);
        return new Result(true, "Your rate has been added " + add);
    }

    public Result giftHistory(String username) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        result.append("All Gifts with ").append(username).append(" :\n");
        for (Dialogue dialogue : App.getCurrentGame().getDialogues()) {
            if (dialogue.getType().equals(DialogueType.gift)) {
                if (dialogue.getSender().getUsername().equals(player.getUsername())
                        && dialogue.getResponder().getUsername().equals(currentPlayer.getUsername())) {
                    result.append(dialogue.getSender().getUsername()).append(" to ").append(dialogue.getResponder().getUsername()).append(" : ").
                            append(dialogue.getInput()).append("\n");
                } else if (dialogue.getSender().getUsername().equals(currentPlayer.getUsername())
                        && dialogue.getResponder().getUsername().equals(player.getUsername())) {
                    result.append(dialogue.getSender().getUsername()).append(" to ").append(dialogue.getResponder().getUsername()).append(" : ").
                            append(dialogue.getInput()).append("\n");
                }
            }
        }
        return new Result(true, result.toString());
    }

    public Result hug(String username) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Player player = getPlayerWithUsername(username);
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near");
        }
        if (!currentPlayer.getRelations().containsKey(player)) {
            currentPlayer.getRelations().put(player, new Relation());
        }
        Relation relation = currentPlayer.getRelations().get(player);
        if (relation.getLevel() < 2) {
            return new Result(false, "Your level is too low");
        }
        if (currentPlayer.getPlayerHuggedToday().get(player) == null
                || currentPlayer.getPlayerHuggedToday().get(player) == Boolean.FALSE) {
            currentPlayer.getPlayerHuggedToday().put(player, Boolean.TRUE);
            player.getPlayerHuggedToday().put(player, Boolean.TRUE);
            currentPlayer.addXP(player, 60);
            player.addXP(currentPlayer, 60);
        }
        return new Result(true, "You hugged your friend!");
    }

    public Result flower(String username) {
        Player player = getPlayerWithUsername(username);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Backpack backpack = currentPlayer.getBackpack();
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!isPlayerNear(player)) {
            return new Result(false, "Player is not near");
        }
        if (!backpack.hasEnoughItem(FruitType.FairyRose, 1)) {
            return new Result(false, "You don't have Rose!");
        }
        backpack.reduceItems(FruitType.FairyRose, 1);
        player.getBackpack().addItems(FruitType.FairyRose, backpack.getStackLevel(FruitType.FairyRose), 1);
        String add = "";
        if (currentPlayer.canFlowered(player)) {
            currentPlayer.goNextLevel(player);
            player.goNextLevel(currentPlayer);
            add = "Relation is in level 3";
        }
        return new Result(true, "You have give your friend flower!" + add);
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
