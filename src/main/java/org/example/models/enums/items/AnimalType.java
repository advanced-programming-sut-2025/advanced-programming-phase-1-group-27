package org.example.models.enums.items;

import org.example.models.enums.items.products.AnimalProduct;

import java.util.ArrayList;

public enum AnimalType implements Item {
    Rabbit,
    Chicken,
    Cow,
    Goat,
    Duck,
    Sheep,
    Dinosaur,
    Pig;

    private final ArrayList<AnimalProduct> products;

    @Override
    public int getPrice() {
        return 0;
    }
}
