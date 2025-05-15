package org.example.models.enums;

import org.example.models.Stock;
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
    private HashMap<Season, ArrayList<Stock>> stock = new HashMap<>();

    ShopType(NPCType manager , int startTime, int endTime) {
        this.manager = manager;
        this.startTime = startTime;
        this.endTime = endTime;
        if (manager == NPCType.Clint) {
            stock.put(Season.Spring, StocksForShops.BlackSmithStock.getStock());
            stock.put(Season.Summer, StocksForShops.BlackSmithStock.getStock());
            stock.put(Season.Fall, StocksForShops.BlackSmithStock.getStock());
            stock.put(Season.Winter, StocksForShops.BlackSmithStock.getStock());
        }
        else if (manager == NPCType.Robin) {
            stock.put(Season.Spring, StocksForShops.CarpenterShopStock.getStock());
            stock.put(Season.Summer, StocksForShops.CarpenterShopStock.getStock());
            stock.put(Season.Fall, StocksForShops.CarpenterShopStock.getStock());
            stock.put(Season.Winter, StocksForShops.CarpenterShopStock.getStock());
        }
        else if (manager == NPCType.Willy) {
            stock.put(Season.Spring, StocksForShops.FishShopStock.getStock());
            stock.put(Season.Summer, StocksForShops.FishShopStock.getStock());
            stock.put(Season.Fall, StocksForShops.FishShopStock.getStock());
            stock.put(Season.Winter, StocksForShops.FishShopStock.getStock());
        }
        else if (manager == NPCType.Morris) {
            stock.put(Season.Spring, StocksForShops.SpringJojaMartStock.getStock());
            stock.put(Season.Summer, StocksForShops.SummerJojaMartStock.getStock());
            stock.put(Season.Fall, StocksForShops.FallJojaMartStock.getStock());
            stock.put(Season.Winter, StocksForShops.WinterJojaMartStock.getStock());
        }
        else if (manager == NPCType.Marnie) {
            stock.put(Season.Spring, StocksForShops.MarnieRanchStock.getStock());
            stock.put(Season.Summer, StocksForShops.MarnieRanchStock.getStock());
            stock.put(Season.Fall, StocksForShops.MarnieRanchStock.getStock());
            stock.put(Season.Winter, StocksForShops.MarnieRanchStock.getStock());
        }
        else if (manager == NPCType.Pierre) {
            stock.put(Season.Spring, StocksForShops.PierreGeneralStoreStock.getStock());
            stock.put(Season.Summer, StocksForShops.PierreGeneralStoreStock.getStock());
            stock.put(Season.Fall, StocksForShops.PierreGeneralStoreStock.getStock());
            stock.put(Season.Winter, StocksForShops.PierreGeneralStoreStock.getStock());
        }
        else if (manager == NPCType.Gus) {
            stock.put(Season.Spring, StocksForShops.StardropSaloonStock.getStock());
            stock.put(Season.Summer, StocksForShops.StardropSaloonStock.getStock());
            stock.put(Season.Fall,  StocksForShops.StardropSaloonStock.getStock());
            stock.put(Season.Winter, StocksForShops.StardropSaloonStock.getStock());
        }
    }

    public static ShopType getShopType(String shopName) {
        return switch (shopName) {
            case "blacksmith" -> ShopType.Blacksmith;
            case "joja mart" -> ShopType.JojaMart;
            case "pierre general store" -> ShopType.PierreGeneralStore;
            case "carpenter shop" -> ShopType.CarpenterShop;
            case "fish shop" -> ShopType.FishShop;
            case "marnie ranch" -> ShopType.MarnieRanch;
            case "stardrop saloon" -> ShopType.StardropSaloon;
            default -> null;
        };
    }

    public ArrayList<Stock> getStock(Season season) {
        // creating a copy ...
        ArrayList<Stock> result = new ArrayList<>();
        for (Stock stock : stock.get(season)) {
            result.add(new Stock(stock.getItem(), stock.getQuantity(), stock.getPrice()));
        }
        return result;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}
