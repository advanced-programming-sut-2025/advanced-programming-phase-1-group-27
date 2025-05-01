package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private ArrayList<Item> possibleIngredients;
    private int quantity;

    public Ingredient(ArrayList<Item> possibleIngredients, int quantity) {
        this.possibleIngredients = possibleIngredients;
        this.quantity = quantity;
    }

    public Ingredient(Item ingredient, int quantity) {
        this.possibleIngredients = new ArrayList<>(List.of(ingredient));
        this.quantity = quantity;
    }

    public ArrayList<Item> getPossibleIngredients() {
        return possibleIngredients;
    }

    public int getQuantity() {
        return quantity;
    }
}
