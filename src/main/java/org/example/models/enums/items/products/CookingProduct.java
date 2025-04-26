package org.example.models.enums.items.products;

import org.example.models.enums.items.Item;
import org.example.models.Buff;
import org.example.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(),
    BakedFish(),
    Salad(),
    Olmelet(),
    PumpkinPie(),
    Spaghetti(),
    Pizza(),
    Tortilla(),
    MakiRoll(),
    TripleShotEspresso(),
    Cookie(),
    HashBrowns(),
    Pancakes(),
    FruitSalad(),
    RedPlate(),
    Bread(),
    SalmonDinner(),
    VegetableMedley(),
    FarmersLunch(),
    SurvivalBurger(),
    DishOTheSea(),
    SeaformPudding(),
    TroutSoup(),
    MinersTreat();
    private final Recipe recipe;
    private final Buff buff;
    private final int energy;

    CookingProduct(Recipe recipe, Buff buff, int energy) {
        this.recipe = recipe;
        this.buff = buff;
        this.energy = energy;
    }

    // TODO: create constructor
}
