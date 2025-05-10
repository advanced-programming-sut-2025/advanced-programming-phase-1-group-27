package org.example.models.enums.Plants;

import org.example.models.Item;

import java.util.Random;

public class Fruit implements Item {
    private FruitType type;
    private int price, energy;
    private Quality quality;

    private enum Quality {
        Regular(1),
        Silver(1.25),
        Gold(1.5),
        Iridium(2);

        private double priceModifier;

        Quality(double priceModifier) {
            this.priceModifier = priceModifier;
        }
    }

    public Fruit(FruitType type) {
        this.type = type;
        this.price = type.getPrice();
        this.energy = type.getEnergy();
        quality = Quality.values()[((new Random(System.currentTimeMillis())).nextInt(4))];
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return type.toString();
    }

    public String getQuality() {
        return quality.toString();
    }
}
