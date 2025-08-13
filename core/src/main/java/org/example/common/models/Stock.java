package org.example.common.models;

import org.example.common.models.Seasons.Season;

public class Stock {
    private final int price;
    private int quantity;
    private StackLevel stackLevel = StackLevel.Basic;
    private Item item;
    private Season saleSeason = null;

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

    public Stock(Item item, int quantity, int price, Season saleSeason) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
        this.saleSeason = saleSeason;
    }

    public Stock(Item item, int quantity) {
        this.price = item.getPrice();
        this.quantity = quantity;
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public int getSalePrice(Season currentSeason) {
        if (currentSeason == this.saleSeason)
            return 2 * price / 3;
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceQuantity() {
        if (quantity != -1)
            quantity--;
    }

    public StackLevel getStackLevel() {
        return stackLevel;
    }

    public void setStackLevel(StackLevel stackLevel) {
        this.stackLevel = stackLevel;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Season getSaleSeason() {
        return saleSeason;
    }

}
