package org.example.server.models.Shops;

import org.example.server.models.Item;
import org.example.server.models.Stock;
import org.example.server.models.enums.Seasons.Season;
import org.example.server.models.enums.ShopType;

import java.util.ArrayList;

public class Shop {
    private final ShopType shopType;
    private ArrayList<Stock> stock;

    public Shop(ShopType shopType, Season season) {
        this.shopType = shopType;
        this.stock = shopType.getStock(season);
    }

    public ShopType getShopType() {
        return shopType;
    }


    public Stock getStock(String itemName) {
        for (Stock slot : stock) {
            if (slot.getItem().getName().equalsIgnoreCase(itemName)) {
                return slot;
            }
        }
        return null;
    }

    public void reduce(Item item, int amount) {
        for (Stock slot : stock) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                int lastAmount = slot.getQuantity();
                if (lastAmount == -1) {
                    return;
                }
                lastAmount -= amount;
                slot.setQuantity(lastAmount);
                break;
            }
        }
    }

    public boolean hasEnough(Item item, int quantity) {
        for (Stock slot : stock) {
            if (slot.getItem().getName().equalsIgnoreCase(item.getName())) {
                if (slot.getQuantity() >= quantity || slot.getQuantity() == -1)
                    return true;
            }
        }
        return false;
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }
}


