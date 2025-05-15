package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.enums.items.*;

public enum ProcessedProductType implements Item {
    Honey(Recipe.HoneyRecipe, 75, 350, 4 * 14),
    Cheese(Recipe.CheeseRecipe, 100, 230, 3),
    LargeCheese(Recipe.LargeCheeseRecipe, 100, 345, 3),
    GoatCheese(Recipe.GoatCheeseRecipe, 100, 400, 3),
    LargeGoatCheese(Recipe.LargeGoatCheeseRecipe, 100, 600, 3),
    Beer(Recipe.BeerRecipe, 50, 200, 14),
    Vinegar(Recipe.VinegarRecipe, 13, 100, 10),
    Coffee(Recipe.CoffeeRecipe, 75, 150, 2),
    Juice(Recipe.JuiceRecipe, null, null, 4 * 14),
    Mead(Recipe.MeadRecipe, 100, 300, 10),
    PaleAle(Recipe.PaleAleRecipe, 50, 300, 3 * 14),
    Wine(Recipe.WineRecipe, null, null, 7 * 14),
    DriedMushroom(Recipe.DriedMushroomRecipe, null, null, null),
    DriedFruit(Recipe.DriedFruitRecipe, null, null, null),
    Raisins(Recipe.RaisinsRecipe, 125, 600, null),
    Coal(Recipe.CoalRecipe, 0, 50, 1),
    Cloth(Recipe.ClothRecipe, 0, 470, 4),
    Mayonnaise(Recipe.MayonnaiseRecipe, 50, 190, 3),
    LargeMayonnaise(Recipe.LargeMayonnaiseRecipe, 50, 237, 3),
    DuckMayonnaise(Recipe.DuckMayonnaiseRecipe, 75, 37, 3),
    DinosaurMayonnaise(Recipe.DinosaurMayonnaiseRecipe, 125, 800, 3),
    TruffleOil(Recipe.TruffleOilRecipe, 38, 1065, 6),
    Oil(Recipe.OilRecipe, 13, 100, 6),
    Pickle(Recipe.PickleRecipe, null, null, 6),
    Jelly(Recipe.JellyRecipe, null, null, 3 * 14),
    SmokedFish(Recipe.SmokedFishRecipe, null, null, 1),
    CopperMetalBar(Recipe.CopperMetalBarRecipe, 0, getPriceWithMultiplier(MineralType.CopperOre, 10), 4),
    IronMetalBar(Recipe.IronMetalBarRecipe, 0, getPriceWithMultiplier(MineralType.IronOre, 10), 4),
    GoldMetalBar(Recipe.GoldMetalBarRecipe, 0, getPriceWithMultiplier(MineralType.GoldOre, 10), 4),
    IridiumMetalBar(Recipe.IridiumMetalBarRecipe, 0, getPriceWithMultiplier(MineralType.IridiumOre, 10), 4);

    private final Recipe recipe;
    private final Integer energy;
    private final Integer price;
    private final Integer processingTime;

    ProcessedProductType(Recipe recipe, Integer energy, Integer price, Integer processingTime) {
        this.recipe = recipe;
        this.energy = energy;
        this.price = price;
        this.processingTime = processingTime;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }

    public Integer getProcessingTime() {
        return processingTime;
    }

    private static int getPriceWithMultiplier(Item item, double multiplier) {
        return (int) (item.getPrice() * multiplier);
    }

    public static ProcessedProductType getItem(String itemName) {
        for (ProcessedProductType processedProductType : ProcessedProductType.values()) {
            if (processedProductType.getName().equalsIgnoreCase(itemName)) {
                return processedProductType;
            }
        }
        return null;
    }
}
