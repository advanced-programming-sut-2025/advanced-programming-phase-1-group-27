package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.common.models.Message;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Player;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.items.Recipe;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveController {
    public static void sendInfo() {
        ClientGame currentGame = ClientApp.getCurrentGame();
        Player player = currentGame.getCurrentPlayer();
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", currentGame.getLobbyId());
            put("playerName", player.getUsername());
            put("farmMapInfo", SaveController.getFarmMapInfo(currentGame.getFarmMap(), currentGame.getPlayerMapIndex(player.getUsername())));
            put("playerInfo", SaveController.getPlayerInfo(player));
        }}, Message.Type.client_game_info));
    }

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

    public static HashMap<String, Object> getFarmMapInfo(FarmMap farmMap, int farmId) {
        HashMap<String, Object> info = new HashMap<>();
        info.put("farmId", farmId);
        info.put("cellsInfo", getCellsInfo(farmMap));
        info.put("refrigerator", farmMap.getHut().getRefrigerator().getInfo());
        info.put("animalEnclosures", getAnimalEnclosuresInfo(farmMap));
        info.put("isGreenHouseRepaired", farmMap.getGreenHouse().isRepaired());
        return info;
    }

    private static ArrayList getAnimalEnclosuresInfo(FarmMap farmMap) {
        ArrayList result = new ArrayList();
        for (Barn barn : farmMap.getBarns()) {
            result.add(barn.getInfo());
        }
        for (Coop coop : farmMap.getCoops()) {
            result.add(coop.getInfo());
        }
        return result;
    }

    private static ArrayList getCellsInfo(FarmMap farmMap) {
        ArrayList result = new ArrayList();
        for (int i = 0; i < farmMap.getHeight(); i++)
            for (int j = 0; j < farmMap.getWidth(); j++) {
                result.add(farmMap.getCell(i, j).getInfo());
            }
        return result;
    }
}
