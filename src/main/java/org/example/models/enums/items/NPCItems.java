package org.example.models.enums.items;

import org.example.models.Item;

public enum NPCItems implements Item {
    ;

    private final int price;

    NPCItems(int price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }
}
