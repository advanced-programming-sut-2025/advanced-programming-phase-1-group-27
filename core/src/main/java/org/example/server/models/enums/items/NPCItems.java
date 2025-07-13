package org.example.server.models.enums.items;

import org.example.server.models.Item;

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
