package org.example.models;

import org.example.models.enums.items.Item;
import org.example.models.enums.items.RecipeType;

import java.util.HashMap;

public class Recipe {
    private Item finalProduct;
    // shows the amount of each material needed
    private HashMap<Item, Integer> amount = new HashMap<>();
    // in case we need to first buy the recipe from a shop or ...
    private RecipeType requiredRecipe;
}
