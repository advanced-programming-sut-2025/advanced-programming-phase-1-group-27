package org.example.server.models;

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

    public void addPossibleIngredients(Item item) {
        possibleIngredients.add(item);
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Item: ");
        for (int i = 0; i < possibleIngredients.size(); i++) {
            if (i > 0 && possibleIngredients.get(i).getName().equals(possibleIngredients.get(i - 1).getName()))
                continue;
            result.append(possibleIngredients.get(i).getName());
            if (i != possibleIngredients.size() - 1)
                result.append("|");
        }
        result.append("\tQuantity: ").append(quantity);
        return result.toString();
    }
}
