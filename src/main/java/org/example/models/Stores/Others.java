package org.example.models.Stores;

import org.example.models.Item;
import org.example.models.enums.NPCType;

import java.util.ArrayList;

public class Others extends Store{
    private ArrayList<Item> products;

    public Others(NPCType manager, int start, int end, ArrayList<Item> products) {
        super(manager, start, end);
        this.products = products;
    }
}
