package org.example.controller.InteractionsWithOthers;

import org.example.controller.MenuController;
import org.example.models.App;
import org.example.models.Item;
import org.example.models.Player;
import org.example.models.Relations.Dialogue;
import org.example.models.Relations.Trade;
import org.example.models.Result;
import org.example.models.enums.DialogueType;
import org.example.models.enums.StackLevel;
import org.example.models.tools.Backpack;
import org.example.view.TradeView;


public class TradeController extends MenuController {
    private final TradeView view;

    public TradeController(TradeView view) {
        this.view = view;
    }

    public Result trade(String username, String type, String stringItem1, String stringItemAmount1, String stringPrice,
                        String stringItem2, String stringItemAmount2) {
        // TODO: function incomplete
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Player player = getPlayerWithUsername(username);
        if (player == null) {
            return new Result(false, "No player found with username " + username);
        }
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
        if (stringPrice != null
                && stringItem2 != null) {
            return new Result(false, "You can choose money or item to trade, not both");
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
        } else if (type.equals("request")) {// We want an item ,and then we will give money or another item
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
        if (currentPlayer.getPlayerTradeToday().get(player) == Boolean.FALSE ||
                currentPlayer.getPlayerTradeToday().get(player) == null) {
            currentPlayer.getPlayerTradeToday().put(player, Boolean.TRUE);
        }
        return new Result(true, "Trade request sent to " + player.getUsername());
    }

    public Result tradeList() {
        StringBuilder result = new StringBuilder();
        result.append("Available Trades : \n");
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        for (Dialogue dialogue : currentPlayer.getDialogues()) {
            if (dialogue instanceof Trade) {
                Trade trade = (Trade) dialogue;
                Player player = trade.getSender();
                Item item1 = trade.getOfferedItem();
                Item item2 = trade.getRequestedItem();
                int amount1 = trade.getOfferedItemQuantity();
                int amount2 = trade.getRequestedItemQuantity();
                Integer money = trade.getMoneyOffered();
                result.append(trade.getId()).append(". (from ").append(player.getUsername()).append(")\n");
                if (trade.getTradeType().equals(DialogueType.TradeOffer)) {
                    // You will get an item and give money or item
                    result.append("You will get : ");
                    result.append(item1.getName()).append(" * ").append(amount1).append("\n");
                    result.append("You will give : ");
                    if (item2 != null) {
                        result.append(item2.getName()).append(" * ").append(amount2).append("\n");
                    }
                    if (money != null) {
                        result.append(money).append("$\n");
                    }
                } else if (trade.getTradeType().equals(DialogueType.TradeRequest)) {
                    // You will give an item and get money or item
                    result.append("You will get : ");
                    if (item2 != null) {
                        result.append(item2.getName()).append(" * ").append(amount2).append("\n");
                    }
                    if (money != null) {
                        result.append(money).append("$\n");
                    }
                    result.append("You will give : ");
                    result.append(item1.getName()).append(" * ").append(amount1).append("\n");
                }
            }
        }
        currentPlayer.deleteTrades();
        return new Result(true, result.toString());
    }

    public Result tradeResponse(String response, String stringId) {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        int id = Integer.parseInt(stringId);
        Trade trade = getTradeWithId(id);
        if (trade == null) {
            return new Result(false, "Trade with id " + id + " not found");
        }
        if (trade.getRespond().equals("reject")
                || trade.getRespond().equals("accept")) {
            return new Result(false, "Trade with id " + id + " has already Done");
        }
        if (!trade.getResponder().equals(currentPlayer)) {
            return new Result(false, "Trade with id " + id + " is not for you!");
        }
        Player player = trade.getResponder();
        if (response.equals("accept")) {
            Item item1 = trade.getOfferedItem();
            Item item2 = trade.getRequestedItem();
            int amount1 = trade.getOfferedItemQuantity();
            int amount2 = trade.getRequestedItemQuantity();
            Integer money = trade.getMoneyOffered();
            Backpack currentBackpack = currentPlayer.getBackpack();
            Backpack playerBackpack = player.getBackpack();
            if (trade.getTradeType().equals(DialogueType.TradeOffer)) {
                // You will get an item and give money or item
                if (!playerBackpack.hasEnoughItem(item1, amount1)) {
                    return new Result(false, player.getUsername() + " doesn't have " +
                            "enough items to trade");
                }
                if (item2 != null) {
                    if (!currentBackpack.hasEnoughItem(item2, amount2)) {
                        return new Result(false, "You don't have enough items to trade");
                    }
                    playerBackpack.reduceItems(item1, amount1);
                    StackLevel stackLevel1 = playerBackpack.getStackLevel(item1);
                    currentBackpack.addItems(item1, stackLevel1, amount1);
                    currentBackpack.reduceItems(item2, amount2);
                    StackLevel stackLevel2 = currentBackpack.getStackLevel(item2);
                    playerBackpack.addItems(item2, stackLevel2, amount2);
                }
                if (money != null) {
                    if (currentPlayer.getMoney() < money) {
                        return new Result(false, "You don't have enough money to trade");
                    }
                    playerBackpack.reduceItems(item1, amount1);
                    StackLevel stackLevel1 = playerBackpack.getStackLevel(item1);
                    currentBackpack.addItems(item1, stackLevel1, amount1);
                    currentPlayer.spendMoney(money);
                    player.addMoney(money);
                }
            } else if (trade.getTradeType().equals(DialogueType.TradeRequest)) {
                // You will give an item and get money or item
                if (!currentBackpack.hasEnoughItem(item1, amount1)) {
                    return new Result(false, "You don't have enough items to trade");
                }
                if (item2 != null) {
                    if (playerBackpack.hasEnoughItem(item2, amount2)) {
                        return new Result(false, player.getUsername() + " doesn't have " +
                                "enough items to trade");
                    }
                    currentBackpack.reduceItems(item1, amount1);
                    StackLevel stackLevel1 = currentBackpack.getStackLevel(item1);
                    playerBackpack.addItems(item1, stackLevel1, amount1);
                    playerBackpack.reduceItems(item2, amount2);
                    StackLevel stackLevel2 = playerBackpack.getStackLevel(item2);
                    currentBackpack.addItems(item2, stackLevel2, amount2);
                }
                if (money != null) {
                    if (player.getMoney() < money) {
                        return new Result(false, player.getUsername() + " doesn't have " +
                                "enough money to trade");
                    }
                    currentBackpack.reduceItems(item1, amount1);
                    StackLevel stackLevel1 = currentBackpack.getStackLevel(item1);
                    playerBackpack.addItems(item1, stackLevel1, amount1);
                    player.spendMoney(money);
                    currentPlayer.addMoney(money);
                }
            }
            player.addXP(currentPlayer, 10);
            currentPlayer.addXP(player, 10);
            trade.setRespond("accept");
            return new Result(true, "Trade with id " + id + " accepted");
        } else if (response.equals("reject")) {
            trade.setRespond("reject");
            //Amount?
            player.decreaseXP(currentPlayer, 10);
            currentPlayer.decreaseXP(player, 10);
            return new Result(false, "Trade with id " + id + " rejected");
        } else {
            return new Result(false, "Wrong response");
        }
    }

    public Result tradeHistory() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        result.append("Trade History : \n");
        for (Dialogue dialogue : App.getCurrentGame().getDialogues()) {
            if (dialogue instanceof Trade) {
                Trade trade = (Trade) dialogue;
                if (trade.getResponder() == currentPlayer
                        || trade.getSender() == currentPlayer) {
                    result.append("Trade ID: ").append(trade.getId()).append("\n");
                    if(trade.getTradeType() == DialogueType.TradeOffer){
                        result.append("Trade Type: offer").append("\n");
                    }else {
                        result.append("Trade Type: request").append("\n");
                    }
                    if(trade.getRespond() == null){
                        result.append("condition : unknown\n");
                    }else {
                        result.append("condition : ").append(trade.getRespond()).append("\n");
                    }
                    result.append("From : ").append(trade.getSender().getUsername()).append("\n");
                    result.append("To : ").append(trade.getResponder().getUsername()).append("\n");
                    result.append("Item : ").append(trade.getOfferedItem().getName()).append(" * ");
                    result.append("Quantity :").append(trade.getOfferedItemQuantity()).append(" for ");
                    if (trade.getRequestedItem() != null) {
                        result.append("Item : ").append(trade.getRequestedItem().getName()).append(" * ");
                        result.append("Quantity : ").append(trade.getRequestedItemQuantity()).append("\n");
                    }
                    if (trade.getMoneyOffered() != null) {
                        result.append("Money : ").append(trade.getMoneyOffered()).append(" $\n");
                    }
                    result.append("-------------------------\n");
                }
            }
        }
        return new Result(true, result.toString());
    }

    private Player getPlayerWithUsername(String username) {
        for (Player player : App.getCurrentGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    private Trade getTradeWithId(int id) {
        for (Dialogue dialogue : App.getCurrentGame().getDialogues()) {
            if (dialogue instanceof Trade) {
                Trade trade = (Trade) dialogue;
                if (trade.getId() == id) {
                    return trade;
                }
            }
        }
        return null;
    }
}
