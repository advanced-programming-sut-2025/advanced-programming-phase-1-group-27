package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;

public class SaveController {
    public static void handleInfo(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        ServerGame game = lobby.getGame();
        Player player = game.getPlayerByUsername(message.getFromBody("playerName"));
        // TODO : handle map
        handlePlayerInfo(lobby, player, message.getFromBody("playerInfo"));
        // TODO
    }

    private static void handlePlayerInfo(Lobby lobby, Player player, LinkedTreeMap<String, Object> info) {
        // RECIPES
        player.setAvailableCraftingRecipes(handleRecipeInfo((ArrayList<String>) info.get("availableCraftingRecipes")));
        player.setAvailableCookingRecipes(handleRecipeInfo((ArrayList<String>) info.get("availableCookingRecipes")));
        // INVENTORY
        player.setBackpack(new Backpack((LinkedTreeMap<String, Object>) info.get("inventoryInfo")));
        // ENERGY
        player.setEnergy(((Number) info.get("energy")).intValue());
        player.setMaxEnergy(((Number) info.get("maxEnergy")).intValue());
        player.setBoostEnergy(((Number) info.get("boostEnergy")).intValue());
        // ABILITY
        player.setAbility(AbilityType.Farming, new Ability((LinkedTreeMap<String, Object>) info.get("farming")));
        player.setAbility(AbilityType.Fishing, new Ability((LinkedTreeMap<String, Object>) info.get("fishing")));
        player.setAbility(AbilityType.Mining, new Ability((LinkedTreeMap<String, Object>) info.get("mining")));
        player.setAbility(AbilityType.Foraging, new Ability((LinkedTreeMap<String, Object>) info.get("foraging")));
        // MAP
        if ((boolean) info.get("isInNPCMap"))
            player.setCurrentMap(lobby.getGame().getNpcMap());
        else
            player.setCurrentMap(lobby.getGame().getFarmMap(lobby.getUsernameToMap().get(player.getUsername())));
        // MONEY
        player.setMoney(((Number) info.get("money")).intValue());
        // BUFF
        if (info.get("buff") == null)
            player.setBuff(null);
        else
            player.setBuff(new Buff((LinkedTreeMap<String, Object>) info.get("buff")));
    }

    private static ArrayList<Recipe> handleRecipeInfo(ArrayList<String> info) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (String recipeName : info) {
            recipes.add(Recipe.getItem(recipeName));
        }
        return recipes;
    }
}
