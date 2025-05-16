package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.Buff;
import org.example.models.enums.AbilityType;
import org.example.models.enums.items.Recipe;

public enum CookingProduct implements Item {
    FriedEgg(35, null, 50),
    BakedFish(100, null, 75),
    Salad(110, null, 113),
    Omelette(125, null, 100),
    PumpkinPie(385, null, 225),
    Spaghetti(120, null, 75),
    Pizza(300, null, 150),
    Tortilla(50, null, 50),
    MakiRoll(220, null, 100),
    TripleShotEspresso(450, new Buff(AbilityType.MaxEnergyUltimate, 5), 200),
    Cookie(140, null, 90),
    HashBrowns(120, new Buff(AbilityType.Farming, 5), 90),
    Pancakes(80, new Buff(AbilityType.Foraging, 11), 90),
    FruitSalad(450, null, 263),
    RedPlate(400, new Buff(AbilityType.MaxEnergyCommunity, 3), 240),
    Bread(60, null, 50),
    SalmonDinner(300, null, 125),
    VegetableMedley(120, null, 165),
    FarmersLunch(150, new Buff(AbilityType.Farming, 5), 200),
    SurvivalBurger(180, new Buff(AbilityType.Foraging, 5), 125),
    DishOfTheSea(220, new Buff(AbilityType.Fishing, 5), 150),
    SeaformPudding(300, new Buff(AbilityType.Fishing, 10), 175),
    MinersTreat(200, new Buff(AbilityType.Mining, 5), 125);

    private final int price;
    private Recipe recipe;
    private final Buff buff;
    private final int energy;

    CookingProduct(int price, Buff buff, int energy) {
        this.price = price;
        this.recipe = null;
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
}
