package org.example.models.enums.items.products;

import org.example.models.Item;
import org.example.models.enums.items.Recipe;

public enum CraftingProduct implements Item {
    CherryBomb(Recipe.CherryBomobRecipe, 50),
    Bomb(Recipe.BombRecipe, 50),
    MegaBomb(Recipe.MegaBomb, 50),
    Sprinkler(Recipe.SprinklerRecipe, 0),
    QualitySprinkler(Recipe.QualitySprinkler, 0),
    IridiumSprinkler(Recipe.IridiumSprinkler, 0),
    CharcoalKlin(Recipe.CharcoalKlin, 0),
    Furnace(Recipe.FurnaceRecipe, 0),
    Scarecrow(Recipe.ScarecrowRecipe, 0),
    DeluxeScarecrow(Recipe.DeluxeScarecrowRecipe, 0),
    BeeHouse(Recipe.BeeHouseRecipe, 0),
    CheesePress(Recipe.CheesePressRecipe, 0),
    Keg(Recipe.KegRecipe, 0),
    Loom(Recipe.LoomRecipe, 0),
    MayonnaiseMachine(Recipe.MayonnaiseMachineRecipe, 0),
    OilMaker(Recipe.OilMakerRecipe, 0),
    PreservesJar(Recipe.PreservesJar, 0),
    GrassStarter(Recipe.GrassStarterRecipe, 0),
    Dehydrator(Recipe.DehydratorRecipe, 0),
    FishSmoker(Recipe.FishSmokerRecipe, 0),
    MysticTreeSeed(Recipe.MysticTreeSeed, 100),;

    private final Recipe recipe;
    private final int price;

    CraftingProduct(Recipe recipe, int price) {
        this.recipe = recipe;
        this.price = price;
    }

    @Override
    public Integer getPrice() {
        return price;
    }
}
