package org.example.client.controller;

import org.example.server.models.Map.NPCMap;
import org.example.server.models.Player;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.items.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveController {
    public static HashMap<String, Object> getPlayerInfo(Player player) {
        HashMap<String, Object> info = new HashMap<>();
        // RECIPES
        info.put("availableCraftingRecipes", getRecipesInfo(player.getAvailableCraftingRecipes()));
        info.put("availableCookingRecipes", getRecipesInfo(player.getAvailableCookingRecipes()));
        // INVENTORY
        info.put("inventoryInfo", player.getBackpack().getInfo());
        // ENERGY
        info.put("energy", player.getEnergy());
        info.put("maxEnergy", player.getMaxEnergy());
        info.put("boostEnergy", player.getBoostEnergy());
        // ABILITY
        info.put("farming", player.getAbility(AbilityType.Farming).getInfo());
        info.put("fishing", player.getAbility(AbilityType.Fishing).getInfo());
        info.put("mining", player.getAbility(AbilityType.Mining).getInfo());
        info.put("foraging", player.getAbility(AbilityType.Foraging).getInfo());
        // Map
        info.put("isInNPCMap", player.getCurrentMap() instanceof NPCMap);
        // POSITION (cell not needed)
        info.put("position", player.getPosition().getInfo());
        // MONEY
        info.put("money", player.getMoney());
        // BUFF
        info.put("buff", player.getCurrentBuff() == null? null : player.getCurrentBuff().getInfo());
        return info;
    }

    private static ArrayList getRecipesInfo(ArrayList<Recipe> recipes) {
        ArrayList result = new ArrayList();
        for (Recipe recipe : recipes) {
            result.add(recipe.getName());
        }
        return result;
    }
}
