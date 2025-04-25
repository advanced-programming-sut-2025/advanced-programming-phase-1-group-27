package org.example.models.enums.items;

import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;

public enum ArtisanTypes implements Item {
    BeeHouse,
    CheesePress,
    Keg,
    Dehydrator,
    CharcoalKiln;

    private final ArrayList<ProcessedProduct> products;
}
