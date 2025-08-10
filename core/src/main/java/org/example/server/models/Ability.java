package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.items.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ability {
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

    private int xp = 0, level = 0;

    public static ArrayList<Recipe> getRecipeList(AbilityType type, int level) {
        return switch (type) {
            case Farming -> farmingRecipes.get(level - 1);
            case Foraging -> foragingRecipes.get(level - 1);
            case Fishing -> fishingRecipes.get(level - 1);
            case Mining -> miningRecipes.get(level - 1);
            default -> new ArrayList<>();
        };
    }

    public Ability() {}

    public Ability(LinkedTreeMap<String, Object> info) {
        this.xp = ((Number) info.get("xp")).intValue();
        this.level = ((Number) info.get("level")).intValue();
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("xp", xp);
        info.put("level", level);
        return info;
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

    public int getLevel() {
        return level;
    }
}
