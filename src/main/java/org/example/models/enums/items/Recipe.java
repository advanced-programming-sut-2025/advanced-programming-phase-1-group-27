package org.example.models.enums.items;

import java.util.HashMap;

public enum Recipe implements Item {
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

    private final Item finalProduct;
    private final HashMap<Item, Integer> ingredients;

    @Override
    public int getPrice() {
        return 0;
    }
}
