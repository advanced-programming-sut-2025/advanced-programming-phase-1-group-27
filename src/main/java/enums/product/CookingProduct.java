package enums.product;

import enums.Buff;
import enums.Item;
import models.Recipe;
import models.*;

public enum CookingProduct implements Product, Item {
    FriedEgg;
    // TODO: different cooking products
    private final Recipe recipe;
    private final Buff buff;
    // TODO: create constructor
}
