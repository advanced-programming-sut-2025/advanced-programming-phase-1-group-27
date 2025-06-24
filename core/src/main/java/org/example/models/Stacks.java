package org.example.models;

import org.example.models.enums.StackLevel;

public class Stacks {
    private Item item;
    private StackLevel stackLevel;
    private int quantity;

    public Stacks(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
        this.stackLevel = StackLevel.Basic;
    }

    public Stacks(Item item, StackLevel stackLevel, int quantity) {
        this.item = item;
        this.stackLevel = stackLevel;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public StackLevel getStackLevel() {
        return stackLevel;
    }

    public void setStackLevel(StackLevel stackLevel) {
        this.stackLevel = stackLevel;
    }

    public int getPrice() {
        return (int) ((double) item.getPrice() * this.stackLevel.getPriceModifier());
    }

    public int getTotalPrice() {
        return quantity * (int) ((double) item.getPrice() * this.stackLevel.getPriceModifier());
    }
}
