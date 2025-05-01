package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.Buff;
import org.example.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(35, Recipe.FriedEggRecipe, ,),
    BakedFish(100, Recipe.BakedFishRecipe, , ),
    Salad(110, Recipe.SaladRecipe, ,),
    Omelette(125, Recipe.OmeletteRecipe, ,),
    PumpkinPie(385, Recipe.PumpkinPieRecipe, ,),
    Spaghetti(120, Recipe.SpaghettiRecipe, ,),
    Pizza(300, Recipe.PizzaRecipe),
    Tortilla(50, Recipe.TortillaRecipe),
    MakiRoll(220, Recipe.MakiRollRecipe),
    TripleShotEspresso(450, Recipe.TortillaRecipe),
    Cookie(140, Recipe.CookieRecipe),
    HashBrowns(120, Recipe.HashbrownsRecipe),
    Pancakes(80, Recipe.PancakeRecipe),
    FruitSalad(450, Recipe.FruitSaladRecipe),
    RedPlate(400, Recipe.RedPlateRecipe),
    Bread(60, Recipe.BreadRecipe),
    SalmonDinner(300, Recipe.SalmonDinnerRecipe),
    VegetableMedley(120, Recipe.VegetableMedleyRecipe),
    FarmersLunch(150, Recipe.FarmerLunchRecipe),
    SurvivalBurger(180, Recipe.SurvivalBurgerRecipe),
    DishOfTheSea(220, Recipe.DishOfTheSeaRecipe),
    SeaformPudding(300, Recipe.SeaformPuddingRecipe),
    MinersTreat(200, Recipe.MinerTreatRecipe);

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
    public int getPrice() {
        return price;
    }
}
