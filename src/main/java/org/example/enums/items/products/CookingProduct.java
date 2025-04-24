package org.example.enums.items.products;

import org.example.enums.items.Item;
import org.example.models.Buff;
import org.example.models.Recipe;

public enum CookingProduct implements Product, Item {
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
    MinersTreat();
    // TODO: different cooking products
    private final Recipe recipe;
    private final Buff buff;

    CookingProduct(Recipe recipe, Buff buff) {
        this.recipe = recipe;
        this.buff = buff;
    }

    // TODO: create constructor
}
