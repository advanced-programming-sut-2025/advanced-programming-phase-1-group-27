package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.enums.items.*;

public enum ProcessedProductType implements Item {
    Honey(Recipe.HoneyRecipe , 75 , 350),
    Cheese(Recipe.CheeseRecipe , 100 , 230),
    LargeCheese(Recipe.LargeCheeseRecipe , 100 , 345),
    GoatCheese(Recipe.GoatCheeseRecipe , 100 , 400),
    LargeGoatCheese(Recipe.LargeGoatCheeseRecipe , 100 , 600),
    Beer(Recipe.BeerRecipe , 50 , 200),
    Vinegar(Recipe.VinegarRecipe , 13 , 100),
    Coffee(Recipe.CoffeeRecipe , 75 , 150),
    Juice(Recipe.JuiceRecipe, null, null),
    Mead(Recipe.MeadRecipe , 100 , 300),
    PaleAle(Recipe.PaleAleRecipe , 50 , 300),
    Wine(Recipe.WineRecipe, null, null),
    DriedMushroom(Recipe.DriedMushroomRecipe, null, null),
    DriedFruit(Recipe.DriedFruitRecipe, null, null),
    Raisins(Recipe.RaisinsRecipe , 125 , 600),
    Coal(Recipe.CoalRecipe , 0 , 50),
    Cloth(Recipe.ClothRecipe  , 0 , 470),
    Mayonnaise(Recipe.MayonnaiseRecipe , 50 , 190),
    LargeMayonnaise(Recipe.LargeMayonnaiseRecipe , 50 , 237),
    DuckMayonnaise(Recipe.DuckMayonnaiseRecipe , 75 , 37),
    DinosaurMayonnaise(Recipe.DinosaurMayonnaiseRecipe , 125 , 800),
    TruffleOil(Recipe.TruffleOilRecipe , 38 , 1065),
    Oil(Recipe.OilRecipe , 13, 100),
    Pickle(Recipe.PickleRecipe, null, null),
    Jelly(Recipe.JellyRecipe, null, null),
    SmokedFish(Recipe.SmokedFishRecipe, null, null),
    CopperMetalBar(Recipe.CopperMetalBarRecipe , 0 , getPriceWithMultiplier(MineralType.CopperOre , 10)),
    IronMetalBar(Recipe.IronMetalBarRecipe , 0 , getPriceWithMultiplier(MineralType.IronOre , 10)),
    GoldMetalBar(Recipe.GoldMetalBarRecipe , 0 , getPriceWithMultiplier(MineralType.GoldOre , 10)),
    IridiumMetalBar(Recipe.IridiumMetalBarRecipe , 0 , getPriceWithMultiplier(MineralType.IridiumOre , 10)),
    Rice(Recipe.RiceRecipe, 0, 250); // energy not defined!

    private final Recipe recipe;
    private final Integer energy;
    private final Integer price;

    ProcessedProductType(Recipe recipe, Integer energy, Integer price) {
        this.recipe = recipe;
        this.energy = energy;
        this.price = price;
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

    private static int getPriceWithMultiplier(Item item , double multiplier) {
        return (int)(item.getPrice() * multiplier);
    }

    public static ProcessedProductType getItem(String itemName) {
        for (ProcessedProductType processedProductType : ProcessedProductType.values()) {
            if (processedProductType.getName().equals(itemName)) {
                return processedProductType;
            }
        }
        return null;
    }
}
