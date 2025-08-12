package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.AnimalEnclosure;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.items.Recipe;
import org.example.server.models.tools.Backpack;

import java.util.ArrayList;

public class SaveController {
    public static void handleInfo(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        ServerGame game = lobby.getGame();
        Player player = game.getPlayerByUsername(message.getFromBody("playerName"));
        handleFarmInfo(game, message.getFromBody("farmMapInfo"));
        handlePlayerInfo(lobby, player, message.getFromBody("playerInfo"));
        // TODO
    }

    private static void handleFarmInfo(ServerGame game, LinkedTreeMap<String, Object> info) {
        FarmMap farmMap = game.getFarmMap(((Number) info.get("farmId")).intValue());
        farmMap.getCoops().clear();
        farmMap.getBarns().clear();
        farmMap.getAnimals().clear();
        farmMap.getShippingBins().clear();

        // handling cells info
        ArrayList cellsInfo = (ArrayList) info.get("cellsInfo");
        for (int i = 0; i < farmMap.getHeight(); i++)
            for (int j = 0; j < farmMap.getWidth(); j++) {
                farmMap.getCell(i, j).handleInfo((LinkedTreeMap<String, Object>) cellsInfo.get(i * farmMap.getWidth() + j));
            }

        // handling refrigerator
        farmMap.getHut().setRefrigerator(new Backpack((LinkedTreeMap<String, Object>) info.get("refrigerator")));

        // handling animal enclosures
        ArrayList<LinkedTreeMap<String, Object>> animalEnclosuresInfo =
                (ArrayList<LinkedTreeMap<String, Object>>) info.get("animalEnclosures");
        for (LinkedTreeMap<String, Object> enclosureInfo : animalEnclosuresInfo) {
            handleEnclosureInfo(farmMap, enclosureInfo);
        }

        // handling green house
        boolean isGreenHouseRepaired = (boolean) info.get("isGreenHouseRepaired");
        if (!farmMap.getGreenHouse().isRepaired() && isGreenHouseRepaired)
            farmMap.getGreenHouse().repair();
    }

    private static void handleEnclosureInfo(FarmMap farmMap, LinkedTreeMap<String, Object> info) {
        Position topLeftPosition = new Position((LinkedTreeMap<String, Object>) info.get("position"));
        Cell topLeftCell = farmMap.getCell(topLeftPosition.getX(), topLeftPosition.getY());
        AnimalEnclosure enclosure = AnimalEnclosure.handleInfo(topLeftCell, info);
        for (int i = 0; i < enclosure.getType().getHeight(); i++)
            for (int j = 0; j < enclosure.getType().getWidth(); j++) {
                Cell cell = farmMap.getCell(topLeftPosition.getX() + i, topLeftPosition.getY() + j);
                cell.setBuilding(enclosure);
                cell.setType(CellType.Building);
            }
        for (Animal animal : enclosure.getAnimals()) {
            farmMap.addAnimal(animal);
        }
        if (enclosure instanceof Coop coop)
            farmMap.getCoops().add(coop);
        else if (enclosure instanceof Barn barn)
            farmMap.getBarns().add(barn);
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
