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
    TroutSoup;

    private final int price;

    ShopItems(int price) {
        this.price = price;
    }

    @Override
    public int getPrice() {
        return 0;
    }
}
