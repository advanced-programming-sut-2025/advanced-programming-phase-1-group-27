package org.example.server.models.enums.items.products;

import org.example.server.models.Item;
import org.example.server.models.enums.items.Recipe;

public enum CraftingProduct implements Item {
    //Some of them have extra for On/Off
    CherryBomb(50 , "Items/Crafting_products/Cherry_Bomb.png"),
    Bomb(50 , "Items/Crafting_products/Bomb.png"),
    MegaBomb(50 , "Items/Crafting_products/Mega_Bomb.png"),
    Sprinkler(0 , "Items/Crafting_products/Sprinkler.png"),
    QualitySprinkler(0 , "Items/Crafting_products/Quality_Sprinkler.png"),
    IridiumSprinkler(0 , "Items/Crafting_products/Iridium_Sprinkler.png"),
    CharcoalKlin(0 , "Items/Crafting_products/Charcoal_Klin.png"),
    Furnace(0 , "Items/Crafting_products/Furnace.png"),
    Scarecrow(0 , "Items/Crafting_products/Scarecrow.png"),
    DeluxeScarecrow(0 , "Items/Crafting_products/Deluxe_Scarecrow.png"),
    BeeHouse(0 , "Items/Crafting_products/Bee_house.png"),
    CheesePress(0 , "Items/Crafting_products/Cheese_Press.png"),
    Keg(0 , "Items/Crafting_products/Keg.png"),
    Loom(0 , "Items/Crafting_products/Loom.png"),
    MayonnaiseMachine(0 , "Items/Crafting_products/Mayonnaise_Machine.png"),
    OilMaker(0 , "Items/Crafting_products/Oil_Maker.png"),
    PreservesJar(0 , "Items/Crafting_products/Preserves_Jar.png"),
    GrassStarter(0 , "Items/Crafting_products/Grass_Starter.png"),
    Dehydrator(0 , "Items/Crafting_products/Dehydrator.png"),
    FishSmoker(0 , "Items/Crafting_products/Fish_Smoker.png"),
    MysticTreeSeed(100 , "Items/Crafting_products/Mystic_Tree_Seed.png");


    private final int price;
    private Recipe recipe;
    private final String address;

    CraftingProduct(int price ,  String address) {
        this.recipe = null;
        this.price = price;
        this.address = address;
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
//
//    @Override
//    public String getAddress() {
//        return this.address;
//    }

}

