package org.example.models;

import org.example.models.enums.items.Item;

import java.util.HashMap;

public class Recipe {
    // shows the amount of each material needed
    private HashMap<Item, Integer> amount = new HashMap<>();
    private int processingTime;
    private int energyConsumed;
}
