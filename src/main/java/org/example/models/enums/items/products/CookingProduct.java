package org.example.models.enums.items.products;

import org.example.models.enums.items.Item;
import org.example.models.Buff;
import org.example.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(Recipe.FriedEggRecipe, ),
    BakedFish(Recipe.BakedFishRecipe, ),
    Salad(Recipe.SaladRecipe),
    Omelette(Recipe.OmeletteRecipe),
    PumpkinPie(Recipe.PumpkinPieRecipe),
    Spaghetti(Recipe.SpaghettiRecipe),
    Pizza(Recipe.PizzaRecipe),
    Tortilla(Recipe.TortillaRecipe),
    MakiRoll(Recipe.MakiRollRecipe),
    TripleShotEspresso(Recipe.TortillaRecipe),
    Cookie(Recipe.CookieRecipe),
    HashBrowns(Recipe.HashbrownsRecipe),
    Pancakes(Recipe.PancakeRecipe),
    FruitSalad(Recipe.FruitSaladRecipe),
    RedPlate(Recipe.RedPlateRecipe),
    Bread(Recipe.BreadRecipe),
    SalmonDinner(Recipe.SalmonDinnerRecipe),
    VegetableMedley(Recipe.VegetableMedleyRecipe),
    FarmersLunch(Recipe.FarmerLunchRecipe),
    SurvivalBurger(Recipe.SurvivalBurgerRecipe),
    DishOfTheSea(Recipe.DishOfTheSeaRecipe),
    SeaformPudding(Recipe.SeaformPuddingRecipe),
    TroutSoup(Recipe.TroutSoupRecipe), // TODO: ba parsa check shavad (aslan recipe dare? khalie ya null)
    MinersTreat(Recipe.MinerTreatRecipe);

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
