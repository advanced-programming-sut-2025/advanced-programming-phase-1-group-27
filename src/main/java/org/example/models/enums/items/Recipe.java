package org.example.models.enums.items;

import org.example.models.Stack;
import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;

public enum Recipe implements Item {
    HoneyRecipe(, ProcessedProduct.Honey, new ArrayList<>()),
    FishSmoker(),
    DehydratorRecipe(),
    GrassStarterRecipe(),
    FriedEggRecipe(),
    BakedFishRecipe(),
    SaladRecipe(),
    OmeletteRecipe(),
    PumpkinPieRecipe(),
    SpaghettiRecipe(),
    PizzaRecipe(),
    TortillaRecipe(),
    MakiRollRecipe(),
    TripleShotEspressoRecipe(),
    CookieRecipe(),
    HashbrownsRecipe(),
    PancakeRecipe(),
    FruitSaladRecipe(),
    RedPlateRecipe(),
    BreadRecipe(),
    SalmonDinnerRecipe(),
    VegetableMedleyRecipe(),
    FarmerLunchRecipe(),
    SurvivalBurgerRecipe(),
    DishOfTheSeaRecipe(),
    SeaformPuddingRecipe(),
    TroutSoupRecipe(),
    MinerTreatRecipe();

    private final int price;
    private final Item finalProduct;
    private final ArrayList<Stack> ingredients;

    Recipe(int price, Item finalProduct, ArrayList<Stack> ingredients) {
        this.price = price;
        this.finalProduct = finalProduct;
        this.ingredients = ingredients;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
