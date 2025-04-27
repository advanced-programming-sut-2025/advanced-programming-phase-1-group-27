package org.example.models.enums.items;

import org.example.models.Stack;
import org.example.models.enums.items.products.ProcessedProduct;

import java.util.ArrayList;
import java.util.HashMap;

public enum Recipe implements Item {
    HoneyRecipe(ProcessedProduct.Honey , new ArrayList<>()),
    FishSmoker(),
    HashbrownsRecipe(),
    OmeletteRecipe(),
    PancakeRecipe(),
    BreadRecipe(),
    TortillaRecipe(),
    PizzaRecipe(),
    MakiRollRecipe(),
    TripleShotEspressoRecipe(),
    DehydratorRecipe(),
    GrassStarterRecipe(),
    CookieRecipe();

    private final int price;
    private final Item finalProduct;
    private final ArrayList<Stack> ingredients;

    Recipe(Item finalProduct, ArrayList<Stack> ingredients) {
        this.finalProduct = finalProduct;
        this.ingredients = ingredients;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
