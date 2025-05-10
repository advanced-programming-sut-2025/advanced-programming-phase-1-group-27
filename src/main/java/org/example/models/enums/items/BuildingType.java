package org.example.models.enums.items;

import org.example.models.Item;

public enum BuildingType implements Item {
    Barn(6000, 7, 4, 4, Recipe.BarnRecipe),
    BigBarn(12000, 7, 4, 8, Recipe.BigBarnRecipe),
    DeluxeBarn(25000, 7, 4, 12, Recipe.DeluxeBarnRecipe),
    Coop(4000, 6, 3, 4, Recipe.CoopRecipe),
    BigCoop(10000, 6, 3, 8, Recipe.BigCoopRecipe),
    DeluxeCoop(20000, 6, 3, 12, Recipe.DeluxeCoopRecipe),
    Well(1000, 3, 3, null, Recipe.WellRecipe),
    ShippingBin(250, 1, 1, null, Recipe.ShippingBinRecipe);

    private final int price;
    private final int width;
    private final int height;
    private final Integer capacity;
    private final Recipe recipe;

    BuildingType(int price, int width, int height, Integer capacity, Recipe recipe) {
        this.price = price;
        this.width = width;
        this.height = height;
        this.capacity = capacity;
        this.recipe = recipe;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public boolean isBarn() {
        return this == Barn || this == BigBarn || this == DeluxeBarn;
    }

    public boolean isCoop() {
        return this == Coop || this == BigCoop || this == DeluxeCoop;
    }

    public static BuildingType getItem(String itemName) {
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType.getName().equalsIgnoreCase(itemName)) {
                return buildingType;
            }
        }
        return null;
    }

    public static BuildingType getEnclosureType(String buildingName) {
        return switch (buildingName) {
            case "Barn" -> Barn;
            case "Big Barn" -> BigBarn;
            case "Deluxe Barn" -> DeluxeBarn;
            case "Coop" -> Coop;
            case "Big Coop" -> BigCoop;
            case "Deluxe Coop" -> DeluxeCoop;
            default -> null;
        };
    }
}
