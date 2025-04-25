package org.example.models.enums.items.products;

import org.example.models.enums.items.Item;

public enum AnimalProduct implements Item {
    CowMilk,
    LargeCowMilk,
    GoatMilk,
    LargeGoatMilk,
    Egg,
    LargeEgg,
    DuckEgg,
    DuckFeather,
    Wool,
    RabbitLeg,
    DinosaurEgg,
    Truffle;

    private final int price;
}
