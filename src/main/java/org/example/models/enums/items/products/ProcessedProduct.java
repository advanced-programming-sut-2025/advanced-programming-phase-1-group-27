package org.example.models.enums.items.products;

import org.example.models.Recipe;
import org.example.models.enums.items.Item;

public enum ProcessedProduct implements Item {
    Honey,
    Cheese,
    LargeCheese,
    GoatCheese,
    LargeGoatCheese,
    Beer,
    Vinegar,
    Coffee,
    Juice, // TODO: different juices based on different vegetables
    Mead,
    PaleAle,
    Wine, // TODO: different wines based on different fruits
    DriedMushrooms, // TODO: different dried mushrooms based on different types of mushrooms
    DriedFruit, // TODO: different dried fruits based on different types of fruits
    Raisins,
    Coal,
    Cloth,
    Mayonnaise,
    LargeMayonnaise,
    DuckMayonnaise,
    DinosaurMayonnaise,
    Oil,
    TruffleOil,
    CornOil,
    SunflowerSeedOil,
    SunflowerOil,
    Pickles, // TODO: different pickles based on different types of vegetables
    Jelly, // TODO: different jellies based on different type of fruits
    SmokedFish, // TODO: different smoked fish based on different types of fish
    MetalBar; // TODO: different metal bars based on different ors;

    private final Recipe recipe;
    private final int energy;
}
