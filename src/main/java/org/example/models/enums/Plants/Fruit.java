package org.example.models.enums.Plants;

import org.example.models.Item;

public class Fruit implements Item {
    private FruitType type;
    private int price, energy;

    public Fruit(FruitType type) {
        this.type = type;
        this.price = type.getPrice();
        this.energy = type.getEnergy();
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return type.toString();
    }
}
