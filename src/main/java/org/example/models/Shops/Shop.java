package org.example.models.Shops;

import org.example.models.App;
import org.example.models.Stacks;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.ShopType;

import java.util.ArrayList;

public class Shop {
    private final ShopType shopType;
    private ArrayList<Stacks> stock;

    public Shop(ShopType shopType) {
        this.shopType = shopType;
        Season season = App.getCurrentGame().getTime().getSeason();
        this.stock = shopType.getStock(season);
    }

    public ShopType getShopType() {
        return shopType;
    }

    public ArrayList<Stacks> getStock() {
        return stock;
    }
}


