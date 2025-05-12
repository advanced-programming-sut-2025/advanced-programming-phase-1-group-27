package org.example.models.Relations;

import org.example.models.Item;
import org.example.models.Player;
import org.example.models.enums.DialogueType;

public class Trade extends Dialogue {

    private Item offeredItem;
    private Item requestedItem;
    private int offeredItemQuantity;
    private int requestedItemQuantity;
    private Integer moneyOffered;
    private DialogueType tradeType;
    private int id;
    private static int idCounter = 1;

    public Trade(Player responder, Player sender, Item offeredItem, int offeredItemQuantity, Item requestedItem,
                 int requestedItemQuantity, Integer moneyOffered , DialogueType tradeType) {
        super(DialogueType.Trade, null, null, responder, sender);
        this.offeredItem = offeredItem;
        this.offeredItemQuantity = offeredItemQuantity;
        this.requestedItem = requestedItem;
        this.requestedItemQuantity = requestedItemQuantity;
        this.moneyOffered = moneyOffered;
        this.tradeType = tradeType;
        id = idCounter;
        idCounter++;
    }

    public Item getOfferedItem() {
        return offeredItem;
    }

    public Item getRequestedItem() {
        return requestedItem;
    }

    public int getOfferedItemQuantity() {
        return offeredItemQuantity;
    }

    public int getRequestedItemQuantity() {
        return requestedItemQuantity;
    }

    public Integer getMoneyOffered() {
        return moneyOffered;
    }

    public DialogueType getTradeType() {
        return tradeType;
    }

    public int getId() {
        return id;
    }

    public void setOfferedItem(Item offeredItem, int offeredItemQuantity) {
        this.offeredItem = offeredItem;
        this.offeredItemQuantity = offeredItemQuantity;
    }

    public void setRequestedItem(Item requestedItem, int requestedItemQuantity) {
        this.requestedItem = requestedItem;
        this.requestedItemQuantity = requestedItemQuantity;
    }

    public void setMoneyOffered(Integer moneyOffered) {
        this.moneyOffered = moneyOffered;
    }
}
