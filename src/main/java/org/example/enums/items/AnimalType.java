package org.example.enums.items;

import org.example.enums.items.products.AnimalProduct;

import java.util.ArrayList;

public enum AnimalType {
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
}
