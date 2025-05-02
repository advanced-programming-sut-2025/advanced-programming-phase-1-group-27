package org.example.models.enums.items;

import org.example.models.Item;

public enum BuildingType implements Item {
    Barn(6000, 7, 4, Recipe.BarnRecipe),
    BigBarn(12000, 7, 4, Recipe.BigBarnRecipe),
    DeluxeBarn(25000, 7, 4, Recipe.DeluxeBarnRecipe),
    Coop(4000, 6, 3, Recipe.CoopRecipe),
    BigCoop(10000, 6, 3, Recipe.BigCoopRecipe),
    DeluxeCoop(20000, 6, 3, Recipe.DeluxeCoopRecipe),
    Well(1000, 3, 3, Recipe.WellRecipe),
    ShippingBin(250, 1, 1, Recipe.ShippingBinRecipe);

    private final int price;
    private final int width;
    private final int height;
    private final Recipe recipe;

    BuildingType(int price, int width, int height, Recipe recipe) {
        this.price = price;
        this.width = width;
        this.height = height;
        this.recipe = recipe;
    }

    @Override
    public Integer getPrice() {
        return price;
    }
}
