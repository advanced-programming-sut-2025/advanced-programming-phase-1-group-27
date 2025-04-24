package org.example.models.Stores;

import java.util.ArrayList;

public class Others extends Store{
    private ArrayList<Item> products;

    public Others(NPC manager, int start, int end, ArrayList<Item> products) {
        super(manager, start, end);
        this.products = products;
    }
}
