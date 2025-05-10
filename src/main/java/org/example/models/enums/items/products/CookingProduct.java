package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.Buff;
import org.example.models.enums.AbilityType;
import org.example.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(35, Recipe.FriedEggRecipe, null, 50),
    BakedFish(100, Recipe.BakedFishRecipe, null, 75),
    Salad(110, Recipe.SaladRecipe, null, 113),
    Omelette(125, Recipe.OmeletteRecipe, null, 100),
    PumpkinPie(385, Recipe.PumpkinPieRecipe, null, 225),
    Spaghetti(120, Recipe.SpaghettiRecipe, null, 75),
    Pizza(300, Recipe.PizzaRecipe, null, 150),
    Tortilla(50, Recipe.TortillaRecipe, null, 50),
    MakiRoll(220, Recipe.MakiRollRecipe, null, 100),
    TripleShotEspresso(450, Recipe.TortillaRecipe, new Buff(AbilityType.MaxEnergyUltimate, 5), 200),
    Cookie(140, Recipe.CookieRecipe, null, 90),
    HashBrowns(120, Recipe.HashbrownsRecipe, new Buff(AbilityType.Farming, 5), 90),
    Pancakes(80, Recipe.PancakeRecipe, new Buff(AbilityType.Foraging, 11), 90),
    FruitSalad(450, Recipe.FruitSaladRecipe, null, 263),
    RedPlate(400, Recipe.RedPlateRecipe, new Buff(AbilityType.MaxEnergyCommunity, 3), 240),
    Bread(60, Recipe.BreadRecipe, null, 50),
    SalmonDinner(300, Recipe.SalmonDinnerRecipe, null, 125),
    VegetableMedley(120, Recipe.VegetableMedleyRecipe, null, 165),
    FarmersLunch(150, Recipe.FarmerLunchRecipe, new Buff(AbilityType.Farming, 5), 200),
    SurvivalBurger(180, Recipe.SurvivalBurgerRecipe, new Buff(AbilityType.Foraging, 5), 125),
    DishOfTheSea(220, Recipe.DishOfTheSeaRecipe, new Buff(AbilityType.Fishing, 5), 150),
    SeaformPudding(300, Recipe.SeaformPuddingRecipe, new Buff(AbilityType.Fishing, 10), 175),
    MinersTreat(200, Recipe.MinerTreatRecipe, new Buff(AbilityType.Mining, 5), 125);

    private final int price;
    private final Recipe recipe;
    private final Buff buff;
    private final int energy;

    CookingProduct(int price, Recipe recipe, Buff buff, int energy) {
        this.price = price;
        this.recipe = recipe;
        this.buff = buff;
        this.energy = energy;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public static CookingProduct getItem(String itemName) {
        for (CookingProduct cookingProduct : CookingProduct.values()) {
            if (cookingProduct.getName().equalsIgnoreCase(itemName)) {
                return cookingProduct;
            }
        }
        return null;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Buff getBuff() {
        return buff;
    }

    public int getEnergy() {
        return energy;
    }
}
