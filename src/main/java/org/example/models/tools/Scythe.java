package org.example.models.tools;

import org.example.models.Item;

public class Scythe extends Tool implements Item {

    public Scythe() {
        int level = 0;
        int energyUsage = 2;
        super(level , energyUsage , "Scythe");
    }

    public void use() {

    }
}
