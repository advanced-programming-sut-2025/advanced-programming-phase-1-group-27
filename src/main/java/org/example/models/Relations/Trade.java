package org.example.models.Relations;

import org.example.models.Item;
import org.example.models.Player;
import org.example.models.enums.DialogueType;

public class Trade extends Dialogue{

    private Item offeredItem;
    private Item requestedItem;
    private int offeredItemQuantity;
    private int requestedItemQuantity;
    private int moneyOffered;
    private int moneyRequested;
    private int id;
    private static int idCounter = 1;

    public Trade(Player responder, Player sender) {
        super(DialogueType.Trade, null , null , responder, sender);
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

    public int getMoneyOffered() {
        return moneyOffered;
    }

    public int getMoneyRequested() {
        return moneyRequested;
    }

    public int getId() {
        return id;
    }

    public void setOfferedItem(Item offeredItem , int offeredItemQuantity) {
        this.offeredItem = offeredItem;
        this.offeredItemQuantity = offeredItemQuantity;
    }

    public void setRequestedItem(Item requestedItem , int requestedItemQuantity) {
        this.requestedItem = requestedItem;
        this.requestedItemQuantity = requestedItemQuantity;
    }

    public void setMoneyOffered(int moneyOffered) {
        this.moneyOffered = moneyOffered;
    }

    public void setMoneyRequested(int moneyRequested) {
        this.moneyRequested = moneyRequested;
    }

}
