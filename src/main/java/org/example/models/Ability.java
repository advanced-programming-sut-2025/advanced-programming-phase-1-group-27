package org.example.models;

import org.example.models.enums.AbilityType;
import org.example.models.enums.items.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Ability {
    private int xp = 0, level = 0;

    public static ArrayList<ArrayList<Recipe>> farmingRecipes, foragingRecipes, fishingRecipes, miningRecipes;

    static {
        farmingRecipes = new ArrayList<>();
        foragingRecipes = new ArrayList<>();
        fishingRecipes = new ArrayList<>();
        miningRecipes = new ArrayList<>();
        // level 1 farming recipes
        farmingRecipes.add(new ArrayList<>(List.of(
                Recipe.SprinklerRecipe,
                Recipe.BeeHouseRecipe,
                Recipe.FarmerLunchRecipe
        )));
        // level 2 farming recipes
        farmingRecipes.add(new ArrayList<>(List.of(
                Recipe.QualitySprinkler,
                Recipe.DeluxeScarecrowRecipe,
                Recipe.CheeseRecipe,
                Recipe.PreservesJar
        )));
        // level 3 farming recipes
        farmingRecipes.add(new ArrayList<>(List.of(
                Recipe.IridiumSprinkler,
                Recipe.KegRecipe,
                Recipe.LoomRecipe,
                Recipe.OilMakerRecipe
        )));
        // level 4 farming recipes
        farmingRecipes.add(new ArrayList<>(List.of(

        )));
        // level 1 foraging recipes
        foragingRecipes.add(new ArrayList<>(List.of(
                Recipe.CharcoalKlinRecipe
        )));
        // level 2 foraging recipes
        foragingRecipes.add(new ArrayList<>(List.of(
                Recipe.VegetableMedleyRecipe
        )));
        // level 3 foraging recipes
        foragingRecipes.add(new ArrayList<>(List.of(
                Recipe.SurvivalBurgerRecipe
        )));
        // level 4 foraging recipes
        foragingRecipes.add(new ArrayList<>(List.of(
                Recipe.MysticTreeSeed
        )));
        // level 1 fishing recipes
        fishingRecipes.add(new ArrayList<>(List.of(

        )));
        // level 2 fishing recipes
        fishingRecipes.add(new ArrayList<>(List.of(
                Recipe.DishOfTheSeaRecipe
        )));
        // level 3 fishing recipes
        fishingRecipes.add(new ArrayList<>(List.of(
                Recipe.SeaformPuddingRecipe
        )));
        // level 4 fishing recipes
        fishingRecipes.add(new ArrayList<>(List.of(

        )));
        // level 1 mining recipes
        miningRecipes.add(new ArrayList<>(List.of(
                Recipe.CherryBomobRecipe,
                Recipe.MinerTreatRecipe
        )));
        // level 2 mining recipes
        miningRecipes.add(new ArrayList<>(List.of(
                Recipe.BombRecipe
        )));
        // level 3 mining recipes
        miningRecipes.add(new ArrayList<>(List.of(
                Recipe.MegaBombRecipe
        )));
        // level 4 mining recipes
        miningRecipes.add(new ArrayList<>(List.of(

        )));
    }

    public void addXp(int xp) {
        this.xp += xp;
        if (this.level < 4 && this.xp >= this.level * 100 + 150) {
            this.levelUp();
        }
    }

    public void levelUp() {
        this.xp -= this.level * 100 + 150;
        this.level++;
    }

    public void addLevel() {
        this.level++;
    }

    public void reduceLevel() {
        this.level--;
    }

    public int getLevel(){
        return level;
    }

    public static ArrayList<Recipe> getRecipeList(AbilityType type, int level) {
        return switch (type) {
            case AbilityType.Farming -> farmingRecipes.get(level - 1);
            case AbilityType.Foraging -> foragingRecipes.get(level - 1);
            case AbilityType.Fishing -> fishingRecipes.get(level - 1);
            case AbilityType.Mining -> miningRecipes.get(level - 1);
            default -> new ArrayList<>();
        };
    }
}
