package org.example.models;

import org.example.models.enums.NPCType;
import org.example.models.Map.FarmMap;
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
}


