package org.example.models;

import org.example.models.enums.StackLevel;

public class Stock {
    private final int price;
    private int quantity;
    private StackLevel stackLevel = StackLevel.Basic;
    private Item item;

    public Stock(Item item, StackLevel stackLevel, int quantity, int price) {
        this.price = price;
        this.quantity = quantity;
        this.stackLevel = stackLevel;
        this.item = item;
    }

    public Stock(Item item, int quantity, int price) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    public Stock(Item item, int quantity) {
        this.price = item.getPrice();
        this.quantity = quantity;
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public StackLevel getStackLevel() {
        return stackLevel;
    }

    public Item getItem() {
        return item;
    }
}
