package org.example.controller.InteractionsWithOthers;

import org.example.models.App;
import org.example.models.Item;
import org.example.models.Player;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Trade;
import org.example.models.Result;
import org.example.models.enums.DialogueType;
import org.example.models.tools.Backpack;

public class TradeController {
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
        Item itemType1 = currentBackpack.getItemWithName(stringItem1);
        int amount = Integer.parseInt(stringItemAmount1);
        int price = 0;
        if (stringPrice != null) {
            price = Integer.parseInt(stringPrice);

        } else {

        }
        int amount2 = 0;
        Item itemType2 = null;
        if (stringItemAmount2 != null) {
            amount2 = Integer.parseInt(stringItemAmount2);
            itemType2 = currentBackpack.getItemWithName(stringItem2);

        } else {

        }
        DialogueType typeTrade;
        if (type.equals("offer")) {// We give item and want another item ot money
            typeTrade = DialogueType.TradeOffer;
        } else if (type.equals("request")) {// We want an item and then we will give money or another item
            typeTrade = DialogueType.TradeRequest;
        } else {
            return new Result(false, "Invalid type");
        }
        return null;
    }

    public Result tradeList() {
        // TODO: function incomplete
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
