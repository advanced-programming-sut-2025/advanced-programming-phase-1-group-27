package enums.product;

import models.Buff;
import enums.Item;
import models.Recipe;

public enum CookingProduct implements Product, Item {
    FriedEgg();
    // TODO: different cooking products
    private final Recipe recipe;
    private final Buff buff;

    CookingProduct(Recipe recipe, Buff buff) {
        this.recipe = recipe;
        this.buff = buff;
    }

    // TODO: create constructor
}
