package org.example.models.enums;

import java.util.Arrays;

public enum StackLevel {
    Basic,
    Bronze,
    Silver(1.25),
    Iron,
    Gold(1.5),
    Iridium(2),
    Large,
    Deluxe,
    Training,
    Bamboo,
    Fiberglass;

    private double priceModifier = 1;

    StackLevel(double priceModifier) {
        this.priceModifier = priceModifier;
    }

    StackLevel() {
    }

    public boolean isBetterThan(StackLevel level) {
        //works for basic, bronze, ... , Iridium
        //uses the DECLARATION ORDER (BAMBOO > TRAINING)
        int index1 = this.ordinal(), index2 = level.ordinal();
        return index1 > index2;
    }

    public double getPriceModifier() {
        return priceModifier;
    }
}