package org.example.server.models.enums.items.products;

import org.example.server.models.Buff;
import org.example.server.models.Item;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(35, null, 50 , "Items/Cooking_products/Fried_Egg.png"),
    BakedFish(100, null, 75 , "Items/Cooking_products/Baked_Fish.png"),
    Salad(110, null, 113 , "Items/Cooking_products/Salad.png"),
    Omelette(125, null, 100 , "Items/Cooking_products/Omelet.png"),
    PumpkinPie(385, null, 225 , "Items/Cooking_products/Pumpkin_Pie.png"),
    Spaghetti(120, null, 75 , "Items/Cooking_products/Spaghetti.png"),
    Pizza(300, null, 150 , "Items/Cooking_products/Pizza.png"),
    Tortilla(50, null, 50 , "Items/Cooking_products/Tortilla.png"),
    MakiRoll(220, null, 100 ,  "Items/Cooking_products/Maki_Roll.png"),
    TripleShotEspresso(450, new Buff(AbilityType.MaxEnergyUltimate, 5), 200 ,
            "Items/Cooking_products/Triple_Shot_Espresso.png"),
    Cookie(140, null, 90 , "Items/Cooking_products/Cookie.png"),
    HashBrowns(120, new Buff(AbilityType.Farming, 5), 90 ,
            "Items/Cooking_products/Hashbrowns.png"),
    Pancakes(80, new Buff(AbilityType.Foraging, 11), 90 ,
            "Items/Cooking_products/Pancakes.png"),
    FruitSalad(450, null, 263 , "Items/Cooking_products/Fruit_Salad.png"),
    RedPlate(400, new Buff(AbilityType.MaxEnergyCommunity, 3), 240 ,
            "Items/Cooking_products/Red_Plate.png"),
    Bread(60, null, 50 , "Items/Cooking_products/Bread.png"),
    SalmonDinner(300, null, 125 , "Items/Cooking_products/Salmon_Dinner.png"),
    VegetableMedley(120, null, 165 , "Items/Cooking_products/Vegetable_Medley.png"),
    FarmersLunch(150, new Buff(AbilityType.Farming, 5), 200 ,
            "Items/Cooking_products/Farmers_Lunch.png"),
    SurvivalBurger(180, new Buff(AbilityType.Foraging, 5), 125 ,
            "Items/Cooking_products/Survival_Burger.png"),
    DishOfTheSea(220, new Buff(AbilityType.Fishing, 5), 150 ,
            "Items/Cooking_products/Dish_Of_The_Sea.png"),
    SeafoamPudding(300, new Buff(AbilityType.Fishing, 10), 175 ,
            "Items/Cooking_products/Seafoam_Pudding.png"),
    MinersTreat(200, new Buff(AbilityType.Mining, 5), 125 ,
            "Items/Cooking_products/Miners_Treat.png"),;

    private final int price;
    private final Buff buff;
    private final int energy;
    private Recipe recipe;
    private final String address;

    CookingProduct(int price, Buff buff, int energy , String address) {
        this.price = price;
        this.recipe = null;
        this.buff = buff;
        this.energy = energy;
        this.address = address;
    }

    public static CookingProduct getItem(String itemName) {
        for (CookingProduct cookingProduct : CookingProduct.values()) {
            if (cookingProduct.getName().equalsIgnoreCase(itemName)) {
                return cookingProduct;
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

    public Buff getBuff() {
        return buff;
    }

    public int getEnergy() {
        return energy;
    }

//    @Override
//    public String getAddress() {
//        return this.address;
//    }

}
