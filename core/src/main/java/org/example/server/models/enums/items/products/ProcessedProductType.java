package org.example.server.models.enums.items.products;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.example.server.models.Item;
import org.example.server.models.enums.items.MineralType;
import org.example.server.models.enums.items.Recipe;

public enum ProcessedProductType implements Item {
    Honey(75, 350, 4 * 14 , "Items/Processed_products/Honey.png"),
    Cheese(100, 230, 3 , "Items/Processed_products/Cheese.png"),
    LargeCheese(100, 345, 3 , "Items/Processed_products/Cheese.png"),
    GoatCheese(100, 400, 3 , "Items/Processed_products/Goat_Cheese.png"),
    LargeGoatCheese(100, 600, 3 , "Items/Processed_products/Goat_Cheese.png"),
    Beer(50, 200, 14 , "Items/Processed_products/Beer.png"),
    Vinegar(13, 100, 10 , "Items/Processed_products/Vinegar.png"),
    Coffee(75, 150, 2 , "Items/Processed_products/Coffee.png"),
    Juice(null, null, 4 * 14 , "Items/Processed_products/Juice.png"),
    Mead(100, 300, 10 , "Items/Processed_products/Mead.png"),
    PaleAle(50, 300, 3 * 14 , "Items/Processed_products/Pale_Ale.png"),
    Wine(null, null, 7 * 14 , "Items/Processed_products/Wine.png"),
    DriedMushroom(null, null, null , "Items/Processed_products/Dried_Mushrooms.png"),
    DriedFruit(null, null, null , "Items/Processed_products/Dried_Fruit.png"),
    Raisins(125, 600, null , "Items/Processed_products/Raisins.png"),
    Coal(0, 50, 1 , "Items/Processed_products/Coal.png"),
    Cloth(0, 470, 4 , "Items/Processed_products/Cloth.png"),
    Mayonnaise(50, 190, 3 , "Items/Processed_products/Mayonnaise.png"),
    LargeMayonnaise(50, 237, 3 , "Items/Processed_products/Mayonnaise.png"),
    DuckMayonnaise(75, 37, 3 , "Items/Processed_products/Duck_Mayonnaise.png"),
    DinosaurMayonnaise(125, 800, 3 , "Items/Processed_products/Dinosaur_Mayonnaise.png"),
    TruffleOil(38, 1065, 6 , "Items/Processed_products/Truffle_Oil.png"),
    Oil(13, 100, 6 , "Items/Processed_products/Oil.png"),
    Pickle(null, null, 6 , "Items/Processed_products/Pickles.png"),
    Jelly(null, null, 3 * 14 , "Items/Processed_products/Jelly.png"),
    SmokedFish(null, null, 1 , "Items/Processed_products/Smoked_fish.png"),
    CopperMetalBar(0, getPriceWithMultiplier(MineralType.CopperOre, 10), 4 ,
            "Items/Processed_products/Copper_Bar.png"),
    IronMetalBar(0, getPriceWithMultiplier(MineralType.IronOre, 10), 4 ,
            "Items/Processed_products/Iron_Bar.png"),
    GoldMetalBar(0, getPriceWithMultiplier(MineralType.GoldOre, 10), 4 ,
            "Items/Processed_products/Gold_Bar.png"),
    IridiumMetalBar(0, getPriceWithMultiplier(MineralType.IridiumOre, 10), 4 ,
            "Items/Processed_products/Iridium_Bar.png");

    private final Integer energy;
    private final Integer price;
    private final Integer processingTime;
    private Recipe recipe;
    private final String address;

    ProcessedProductType(Integer energy, Integer price, Integer processingTime , String address) {
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
}
