package org.example.models;

import org.example.models.enums.Seasons.Season;
import org.example.models.enums.StackLevel;

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

    public int getSalePrice() {
        Season currentSeason = App.getCurrentGame().getTime().getSeason();
        if (currentSeason == this.saleSeason)
            return 2 * price / 3;
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        if (quantity != -1)
            quantity--;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StackLevel getStackLevel() {
        return stackLevel;
    }

    public Item getItem() {
        return item;
    }

    public Season getSaleSeason() {
        return saleSeason;
    }
}
