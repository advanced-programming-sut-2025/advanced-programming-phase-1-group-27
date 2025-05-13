package org.example.models.Shops;

import org.example.models.App;
import org.example.models.Item;
import org.example.models.Stock;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.ShopType;

import java.util.ArrayList;

public class Shop {
    private final ShopType shopType;
    private ArrayList<Stock> stock;

    public Shop(ShopType shopType) {
        this.shopType = shopType;
        Season season = App.getCurrentGame().getTime().getSeason();
        this.stock = shopType.getStock(season);
    }

    public ShopType getShopType() {
        return shopType;
    }


    public Stock getStack(String itemName) {
        for (Stock stack : stock) {
            if(stack.getItem().getName().equals(itemName)){
                return stack;
            }
        }
        return null;
    }

    public void reduce(Item item , int amount) {
        for (Stock stack : stock) {
            if(stack.getItem().getName().equals(item.getName())){
                int lastAmount = stack.getQuantity();
                if(lastAmount == -1){
                    return;
                }
                lastAmount -= amount;
                stack.setQuantity(lastAmount);
                break;
            }
        }
    }

    public ArrayList<Stock> getStock() {
        return stock;
    }
}


