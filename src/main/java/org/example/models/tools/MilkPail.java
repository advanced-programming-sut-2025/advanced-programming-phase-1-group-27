package org.example.models.tools;

import org.example.models.Item;

public class MilkPail extends Tool implements Item {

    private int price;

    public MilkPail() {
        int level = 0;
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , "Milk pail");
    }

    @Override
    public void use() {

    }
}
