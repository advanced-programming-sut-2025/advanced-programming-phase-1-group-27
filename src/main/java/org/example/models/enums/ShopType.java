package org.example.models.enums;

import org.example.models.Stacks;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;

public enum ShopType {
    Blacksmith(NPCType.Clint , 9 , 16),
    JojaMart(NPCType.Morris , 9 , 23),
    PierreGeneralStore(NPCType.Pierre , 9 , 17),
    CarpenterShop(NPCType.Robin , 9 , 20),
    FishShop(NPCType.Willy , 9 , 17),
    MarnieRanch(NPCType.Marnie , 9 , 16),
    StardropSaloon(NPCType.Gus , 12 , 24);

    private final NPCType manager;
    private final int startTime;
    private final int endTime;
    private HashMap<Season, ArrayList<Stacks>> stock = new HashMap<>();

    ShopType(NPCType manager , int startTime, int endTime) {
        this.manager = manager;
        this.startTime = startTime;
        this.endTime = endTime;
        if (manager == NPCType.Clint) {
            stock.put(Season.Spring, StocksForShops.BlackSmithStock.getStacks());
            stock.put(Season.Summer, StocksForShops.BlackSmithStock.getStacks());
            stock.put(Season.Fall, StocksForShops.BlackSmithStock.getStacks());
            stock.put(Season.Winter, StocksForShops.BlackSmithStock.getStacks());
        }
        else if (manager == NPCType.Robin) {
            stock.put(Season.Spring, StocksForShops.CarpenterShopStock.getStacks());
            stock.put(Season.Summer, StocksForShops.CarpenterShopStock.getStacks());
            stock.put(Season.Fall, StocksForShops.CarpenterShopStock.getStacks());
            stock.put(Season.Winter, StocksForShops.CarpenterShopStock.getStacks());
        }
        else if (manager == NPCType.Willy) {
            stock.put(Season.Spring, StocksForShops.FishShopStock.getStacks());
            stock.put(Season.Summer, StocksForShops.FishShopStock.getStacks());
            stock.put(Season.Fall, StocksForShops.FishShopStock.getStacks());
            stock.put(Season.Winter, StocksForShops.FishShopStock.getStacks());
        }
        else if (manager == NPCType.Morris) {
            stock.put(Season.Spring, StocksForShops.SpringJojaMartStock.getStacks());
            stock.put(Season.Summer, StocksForShops.SummerJojaMartStock.getStacks());
            stock.put(Season.Fall, StocksForShops.FallJojaMartStock.getStacks());
            stock.put(Season.Winter, StocksForShops.WinterJojaMartStock.getStacks());
        }
        else if (manager == NPCType.Marnie) {
            stock.put(Season.Spring, StocksForShops.MarnieRanchStock.getStacks());
            stock.put(Season.Summer, StocksForShops.MarnieRanchStock.getStacks());
            stock.put(Season.Fall, StocksForShops.MarnieRanchStock.getStacks());
            stock.put(Season.Winter, StocksForShops.MarnieRanchStock.getStacks());
        }
        else if (manager == NPCType.Pierre) {
            stock.put(Season.Spring, StocksForShops.SpringPierreGeneralStoreStock.getStacks());
            stock.put(Season.Summer, StocksForShops.SummerPierreGeneralStoreStock.getStacks());
            stock.put(Season.Fall, StocksForShops.FallPierreGeneralStoreStock.getStacks());
            stock.put(Season.Winter, StocksForShops.WinterPierreGeneralStoreStock.getStacks());
        }
        else if (manager == NPCType.Gus) {
            stock.put(Season.Spring, StocksForShops.StardropSaloonStock.getStacks());
            stock.put(Season.Summer, StocksForShops.StardropSaloonStock.getStacks());
            stock.put(Season.Fall,  StocksForShops.StardropSaloonStock.getStacks());
            stock.put(Season.Winter, StocksForShops.StardropSaloonStock.getStacks());
        }
    }

    public ArrayList<Stacks> getStock(Season season) {
        // creating a copy ...
        ArrayList<Stacks> result = new ArrayList<>();
        for (Stacks stacks : stock.get(season)) {
            result.add(new Stacks(stacks.getItem(), stacks.getQuantity()));
        }
        return result;
    }
}
