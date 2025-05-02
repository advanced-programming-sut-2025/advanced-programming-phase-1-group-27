package org.example.models.enums.items;

import org.example.models.Item;

public enum ShopItems implements Item {
    Bouquet,
    WeddingRing,
    BasicFertilizer,
    QualityFertilizer,
    SpeedGro,
    DeluxeSpeedGro,
    BasicRetainingSoil,
    QualityRetainingSoil,
    JojaCola,
    Sugar,
    WheatFlour,
    Hay,
    Coin,
    TroutSoup;

    private final int price;
    private final Recipe recipe;

    ShopItems(int price, Recipe recipe) {
        this.price = price;
        this.recipe = recipe;
    }

    @Override
    public int getPrice() {
        return 0;
    }
}
