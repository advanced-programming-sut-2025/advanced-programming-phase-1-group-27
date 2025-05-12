package org.example.controller.InteractionsWithOthers;

import org.example.models.App;
import org.example.models.Item;
import org.example.models.Player;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Trade;
import org.example.models.Result;
import org.example.models.enums.DialogueType;
import org.example.models.tools.Backpack;
import org.example.view.TradeView;

import java.util.ArrayList;

public class TradeController {
    private final TradeView view;

    public TradeController(TradeView view) {
        this.view = view;
    }

    public Result startTrade() {
        StringBuilder result = new StringBuilder();
        result.append("Available players:\n");
        for (Player player : App.getCurrentGame().getPlayers()) {
            result.append(player.getUsername()).append("\n");
            //TODO: Trade View
        }
        return new Result(true, result.toString());
    }

    public Result trade(String username, String type, String stringItem1, String stringItemAmount1, String stringPrice,
                        String stringItem2, String stringItemAmount2) {
        // TODO: function incomplete
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Player player = getPlayerWithUsername(username);
        Backpack currentBackpack = currentPlayer.getBackpack();
        Backpack playerBackpack = player.getBackpack();
        Item itemType1 = null;
        Item itemType2 = null;
        int amount1 = 0;
        int amount2 = 0;
        Integer price = null;
        if (stringPrice != null) {
            price = Integer.parseInt(stringPrice);
        }
        DialogueType typeTrade;
        if (type.equals("offer")) {// We give item and want another item or money
            typeTrade = DialogueType.TradeOffer;
            itemType1 = currentBackpack.getItemWithName(stringItem1);
            itemType2 = playerBackpack.getItemWithName(stringItem2);
            amount1 = Integer.parseInt(stringItemAmount1);
            if (itemType2 != null) {
                amount2 = Integer.parseInt(stringItemAmount2);
            }
            if (itemType1 == null) {
                return new Result(false, "No item found with name " + stringItem1);
            }
            if (stringItem2 != null) {
                if (itemType2 == null) {
                    return new Result(false, "No item found with name " + stringItem2);
                }
            }
            if (!currentBackpack.hasEnoughItem(itemType1, amount1)) {
                return new Result(false, "You don't have enough items to trade");
            }
            if (stringItem2 != null) {
                if (!playerBackpack.hasEnoughItem(itemType2, amount2)) {
                    return new Result(false, player.getUsername() + " doesn't have enough items to trade");
                }
            }
            if (price != null) {
                if (player.getMoney() < price) {
                    return new Result(false, player.getUsername() + " doesn't have enough" +
                            " money to trade");
                }
            }
        } else if (type.equals("request")) {// We want an item and then we will give money or another item
            typeTrade = DialogueType.TradeRequest;
            itemType1 = playerBackpack.getItemWithName(stringItem1);
            itemType2 = currentBackpack.getItemWithName(stringItem2);
            amount1 = Integer.parseInt(stringItemAmount1);
            if (itemType2 != null) {
                amount2 = Integer.parseInt(stringItemAmount2);
            }
            if (itemType1 == null) {
                return new Result(false, "No item found with name " + stringItem1);
            }
            if (stringItem2 != null) {
                if (itemType2 == null) {
                    return new Result(false, "No item found with name " + stringItem2);
                }
            }
            if (!playerBackpack.hasEnoughItem(itemType1, amount1)) {
                return new Result(false, player.getUsername() + " doesn't have enough items to trade");
            }
            if (stringItem2 != null) {
                if (!currentBackpack.hasEnoughItem(itemType2, amount2)) {
                    return new Result(false, "You don't have enough items to trade");
                }
            }
            if (price != null) {
                if (currentPlayer.getMoney() < price) {
                    return new Result(false, "You don't have enough money to trade");
                }
            }
        } else {
            return new Result(false, "Invalid type");
        }
        Trade trade = new Trade(player, currentPlayer, itemType1, amount1, itemType2, amount2, price, typeTrade);
        player.addDialogue(trade);
        App.getCurrentGame().addDialogue(trade);
        return null;
    }

    public Result tradeList() {
        // TODO: function incomplete
        StringBuilder result = new StringBuilder();
        result.append("Available Trades : \n");
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        ArrayList<Dialogue> trades = new ArrayList<>();
        for(Dialogue dialogue : currentPlayer.getDialogues()) {
            if(dialogue instanceof Trade){
                trades.add(dialogue);
                Trade trade = (Trade) dialogue;
                Player player = trade.getSender();
                Item item1 = trade.getOfferedItem();
                Item item2 = trade.getRequestedItem();
                int amount1 = trade.getOfferedItemQuantity();
                int amount2 = trade.getRequestedItemQuantity();
                Integer money = trade.getMoneyOffered();
                result.append(trade.getId()).append(". (from ").append(player.getUsername()).append(")\n");
                if(trade.getTradeType().equals(DialogueType.TradeOffer)){
                    // You will get an item and give money or item
                    result.append("You will get : ");
                    result.append(item1.getName()).append(" * ").append(amount1).append("\n");
                    result.append("You will give : ");
                    if(item2 != null){
                        result.append(item2.getName()).append(" * ").append(amount2).append("\n");
                    }
                    if(money != null){
                        result.append(money).append("$\n");
                    }
                }else if(trade.getTradeType().equals(DialogueType.TradeRequest)){
                    // You will give an item and get money or item
                    result.append("You will get : ");

                }
            }
        }
        return null;
    }

    public Result tradeResponse() {
        // TODO: function incomplete
        return null;
    }

    public Result tradeHistory() {
        // TODO: function incomplete
        return null;
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
