package org.example.models.enums.items;

public enum RecipeType implements Item {
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

    @Override
    public void use() {

    }

    @Override
    public int getPrice() {
        return 0;
    }
}
