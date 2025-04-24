package org.example.enums.items;

import org.example.enums.items.products.AnimalProduct;

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
    // TODO: different types of animal
    private final ArrayList<AnimalProduct> products;

    @Override
    public void use() {

    }

    @Override
    public int getPrice() {
        return 0;
    }
}
