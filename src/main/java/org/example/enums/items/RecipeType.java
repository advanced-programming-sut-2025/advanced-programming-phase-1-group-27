package org.example.enums.items;

public enum RecipeType implements Item {
    HashbrownsRecipe(),
    OmeletteRecipe(),
    PancakeRecipe(),
    BreadRecipe(),
    TortillaRecipe(),
    PizzaRecipe(),
    MakiRollRecipe(),
    TripleShotEspressoRecipe(),
    CookieRecipe();

    @Override
    public void use() {

    }

    @Override
    public int getPrice() {
        return 0;
    }
}
