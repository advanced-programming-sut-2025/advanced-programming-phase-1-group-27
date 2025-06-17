package org.example.models.enums.Plants;

import org.example.models.Item;
import org.example.models.enums.StackLevel;

import java.util.Random;

public class Fruit implements Item {
    private FruitType type;
    private int price, energy;
    private StackLevel quality;

    public Fruit(FruitType type) {
        this.type = type;
        this.price = type.getPrice();
        this.energy = type.getEnergy();
        quality = new StackLevel[] {StackLevel.Basic, StackLevel.Silver, StackLevel.Gold, StackLevel.Iridium}
                [((new Random( )).nextInt(4))];
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
