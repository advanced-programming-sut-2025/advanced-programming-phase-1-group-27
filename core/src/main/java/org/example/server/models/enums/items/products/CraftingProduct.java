package org.example.server.models.enums.items.products;

import org.example.server.models.Item;
import org.example.server.models.enums.items.Recipe;

public enum CraftingProduct implements Item {
    CherryBomb(50),
    Bomb(50),
    MegaBomb(50),
    Sprinkler(0),
    QualitySprinkler(0),
    IridiumSprinkler(0),
    CharcoalKlin(0),
    Furnace(0),
    Scarecrow(0),
    DeluxeScarecrow(0),
    BeeHouse(0),
    CheesePress(0),
    Keg(0),
    Loom(0),
    MayonnaiseMachine(0),
    OilMaker(0),
    PreservesJar(0),
    GrassStarter(0),
    Dehydrator(0),
    FishSmoker(0),
    MysticTreeSeed(100);


    private final int price;
    private Recipe recipe;

    CraftingProduct(int price) {
        this.recipe = null;
        this.price = price;
    }

    public static CraftingProduct getItem(String itemName) {
        for (CraftingProduct craftingProduct : CraftingProduct.values()) {
            if (craftingProduct.getName().equalsIgnoreCase(itemName)) {
                return craftingProduct;
            }
        }
        return null;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        if (recipe == null) {
            for (Recipe recipe : Recipe.values()) {
                if (recipe.getFinalProduct() == this) {
                    return this.recipe = recipe;
                }
            }
        }
        return recipe;
    }
}
