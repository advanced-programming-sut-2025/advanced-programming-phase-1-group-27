package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.Recipe;

public enum ProcessedProductType implements Item {
    Honey(75, 350, 4 * 14),
    Cheese(100, 230, 3),
    LargeCheese(100, 345, 3),
    GoatCheese(100, 400, 3),
    LargeGoatCheese(100, 600, 3),
    Beer(50, 200, 14),
    Vinegar(13, 100, 10),
    Coffee(75, 150, 2),
    Juice(null, null, 4 * 14),
    Mead(100, 300, 10),
    PaleAle(50, 300, 3 * 14),
    Wine(null, null, 7 * 14),
    DriedMushroom(null, null, null),
    DriedFruit(null, null, null),
    Raisins(125, 600, null),
    Coal(0, 50, 1),
    Cloth(0, 470, 4),
    Mayonnaise(50, 190, 3),
    LargeMayonnaise(50, 237, 3),
    DuckMayonnaise(75, 37, 3),
    DinosaurMayonnaise(125, 800, 3),
    TruffleOil(38, 1065, 6),
    Oil(13, 100, 6),
    Pickle(null, null, 6),
    Jelly(null, null, 3 * 14),
    SmokedFish(null, null, 1),
    CopperMetalBar(0, getPriceWithMultiplier(MineralType.CopperOre, 10), 4),
    IronMetalBar(0, getPriceWithMultiplier(MineralType.IronOre, 10), 4),
    GoldMetalBar(0, getPriceWithMultiplier(MineralType.GoldOre, 10), 4),
    IridiumMetalBar(0, getPriceWithMultiplier(MineralType.IridiumOre, 10), 4);

    private final Integer energy;
    private final Integer price;
    private final Integer processingTime;
    private Recipe recipe;

    ProcessedProductType(Integer energy, Integer price, Integer processingTime) {
        this.recipe = null;
        this.energy = energy;
        this.price = price;
        this.processingTime = processingTime;
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

    public Recipe getRecipe() {
        if (recipe == null) { // ajab shahkari hasti
            for (Recipe recipe : Recipe.values()) {
                if (recipe.getFinalProduct() == this)
                    return this.recipe = recipe;
            }
        }
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
}
