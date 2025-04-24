package org.example.models.tools;

import org.example.enums.Item;

public class Scythe extends Tool implements Item {

    public Scythe() {
        int level = 0;
        int energyUsage = 2;
        super(level , energyUsage , "Scythe");
    }

    @Override
    public void use() {

    }
}
