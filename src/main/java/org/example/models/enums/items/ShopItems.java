package org.example.models.enums.items;

import org.example.models.Item;

public enum ShopItems implements Item {
    Bouquet(1000, null),
    WeddingRing(10000, null),
    DeluxeRetainingSoil(150, null),
    SpeedGro(100, null),
    BasicRetainingSoil(100, null),
    QualityRetainingSoil(150, null),
    JojaCola(75, null),
    Sugar(125, null),
    WheatFlour(125, null),
    Hay(50, null),
    TroutSoup(250, null),
    Coin(1, null);

    private final int price;
    private final Recipe recipe;

    ShopItems(int price, Recipe recipe) {
        this.price = price;
        this.recipe = recipe;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    public static ShopItems getItem(String itemName) {
        for (ShopItems shopItems : ShopItems.values()) {
            if (shopItems.name().equalsIgnoreCase(itemName)) {
                return shopItems;
            }
        }
        return null;
    }
}
