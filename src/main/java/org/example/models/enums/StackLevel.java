package org.example.models.enums;

import java.util.Arrays;

public enum StackLevel {
    Basic,
    Bronze,
    Silver,
    Iron,
    Gold,
    Iridium,
    Large,
    Deluxe,
    Training,
    Bamboo,
    Fiberglass;

    public boolean isBetterThan(StackLevel level) {
        //works for basic, bronze, ... , Iridium
        //uses the DECLARATION ORDER (BAMBOO > TRAINING)
        int index1 = this.ordinal(), index2 = level.ordinal();
        return index1 > index2;
    }
}