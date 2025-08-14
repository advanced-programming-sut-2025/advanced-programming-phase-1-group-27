package org.example.common.models.items.products;

import org.example.common.models.Edible;
import org.example.common.models.Item;
import org.example.common.models.items.MineralType;
import org.example.common.models.items.Recipe;

public enum ProcessedProductType implements Item, Edible {
    Honey(75, 350, 4 * 14, "Items/Processed_products/Honey.png", true),
    Cheese(100, 230, 3, "Items/Processed_products/Cheese.png", true),
    LargeCheese(100, 345, 3, "Items/Processed_products/Cheese.png", true),
    GoatCheese(100, 400, 3, "Items/Processed_products/Goat_Cheese.png", true),
    LargeGoatCheese(100, 600, 3, "Items/Processed_products/Goat_Cheese.png", true),
    Beer(50, 200, 14, "Items/Processed_products/Beer.png", true),
    Vinegar(13, 100, 10, "Items/Processed_products/Vinegar.png", true),
    Coffee(75, 150, 2, "Items/Processed_products/Coffee.png", true),
    Juice(null, null, 4 * 14, "Items/Processed_products/Juice.png", true),
    Mead(100, 300, 10, "Items/Processed_products/Mead.png", true),
    PaleAle(50, 300, 3 * 14, "Items/Processed_products/Pale_Ale.png", true),
    Wine(null, null, 7 * 14, "Items/Processed_products/Wine.png", true),
    DriedMushroom(null, null, null, "Items/Processed_products/Dried_Mushrooms.png", true),
    DriedFruit(null, null, null, "Items/Processed_products/Dried_Fruit.png", true),
    Raisins(125, 600, null, "Items/Processed_products/Raisins.png", true),
    Coal(0, 50, 1, "Items/Processed_products/Coal.png", true),
    Cloth(0, 470, 4, "Items/Processed_products/Cloth.png", false),
    Mayonnaise(50, 190, 3, "Items/Processed_products/Mayonnaise.png", true),
    LargeMayonnaise(50, 237, 3, "Items/Processed_products/Mayonnaise.png", true),
    DuckMayonnaise(75, 37, 3, "Items/Processed_products/Duck_Mayonnaise.png", true),
    DinosaurMayonnaise(125, 800, 3, "Items/Processed_products/Dinosaur_Mayonnaise.png", true),
    TruffleOil(38, 1065, 6, "Items/Processed_products/Truffle_Oil.png", true),
    Oil(13, 100, 6, "Items/Processed_products/Oil.png", true),
    Pickle(null, null, 6, "Items/Processed_products/Pickles.png", true),
    Jelly(null, null, 3 * 14, "Items/Processed_products/Jelly.png", true),
    SmokedFish(null, null, 1, "Items/Processed_products/Smoked_fish.png", true),
    CopperMetalBar(0, getPriceWithMultiplier(MineralType.CopperOre, 10), 4,
            "Items/Processed_products/Copper_Bar.png", false),
    IronMetalBar(0, getPriceWithMultiplier(MineralType.IronOre, 10), 4,
            "Items/Processed_products/Iron_Bar.png", false),
    GoldMetalBar(0, getPriceWithMultiplier(MineralType.GoldOre, 10), 4,
            "Items/Processed_products/Gold_Bar.png", false),
    IridiumMetalBar(0, getPriceWithMultiplier(MineralType.IridiumOre, 10), 4,
            "Items/Processed_products/Iridium_Bar.png", false);

    private final Integer energy;
    private final Integer price;
    private final Integer processingTime;
    private final String address;
    private final boolean isEdible;
    private Recipe recipe;

    ProcessedProductType(Integer energy, Integer price, Integer processingTime, String address, boolean isEdible) {
        this.isEdible = isEdible;
        this.recipe = null;
        this.energy = energy;
        this.price = price;
        this.processingTime = processingTime;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public Integer getProcessingTime() {
        return processingTime;
    }

    @Override
    public boolean isEdible() {
        return isEdible;
    }
}
