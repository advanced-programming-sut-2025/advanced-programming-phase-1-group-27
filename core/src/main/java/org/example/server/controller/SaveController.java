package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.database.DataBaseHelper;
import org.example.common.models.*;
import org.example.common.models.AnimalProperty.Animal;
import org.example.common.models.AnimalProperty.AnimalEnclosure;
import org.example.common.models.AnimalProperty.Barn;
import org.example.common.models.AnimalProperty.Coop;
import org.example.common.models.Map.FarmMap;
import org.example.common.models.Map.NPCMap;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.Relations.Relation;
import org.example.common.models.items.Recipe;
import org.example.common.models.tools.Backpack;
import org.example.server.models.ServerApp;
import org.example.server.models.ServerGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaveController {
    public static void sendInfo(Lobby lobby, String username) {
        int farmId = lobby.getUsernameToMap().get(username);
        FarmMap farmMap = lobby.getGame().getFarmMap(farmId);
        Player player = lobby.getGame().getPlayerByUsername(username);
        ServerApp.getClientConnectionThreadByUsername(username).sendMessage(new Message(new HashMap<>() {{
            put("lobbyInfo", lobby.getInfo());
            put("farmMapInfo", getFarmMapInfo(farmMap, farmId));
            put("playerInfo", getPlayerInfo(player));
            put("weather", lobby.getGame().getCurrentWeather().name());
            put("time", lobby.getGame().getTime().getInfo());
            put("npcsInfo", getNPCsInfo(lobby.getGame()));
        }}, Message.Type.client_game_info));
    }

    private static ArrayList getNPCsInfo(ServerGame game) {
        ArrayList npcsInfo = new ArrayList<>();
        for (NPC npc : game.getNPCs()) {
            npcsInfo.add(npc.getRelationsInfo());
        }
        return npcsInfo;
    }

    private static HashMap<String, Object> getPlayerInfo(Player player) {
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
        info.put("buff", player.getCurrentBuff() == null ? null : player.getCurrentBuff().getInfo());
        // NUMBER OF QUESTS
        info.put("numberOfQuestsDone", player.getNumberOfQuestsDone());
        return info;
    }

    private static ArrayList getRecipesInfo(ArrayList<Recipe> recipes) {
        ArrayList result = new ArrayList();
        for (Recipe recipe : recipes) {
            result.add(recipe.getName());
        }
        return result;
    }

    private static HashMap<String, Object> getFarmMapInfo(FarmMap farmMap, int farmId) {
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

    public static void handleInfo(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        DataBaseHelper.saveClientGameInfo(lobby, message);
        ServerGame game = lobby.getGame();
        Player player = game.getPlayerByUsername(message.getFromBody("playerName"));
        handleFarmInfo(game, message.getFromBody("farmMapInfo"));
        handlePlayerInfo(lobby, player, message.getFromBody("playerInfo"));
    }

    public static void loadGameFromDB(Message message, Lobby lobby) {
        ServerGame game = lobby.getGame();
        Player player = game.getPlayerByUsername(message.getFromBody("playerName"));
        handleFarmInfo(game, message.getFromBody("farmMapInfo"));
        handlePlayerInfo(lobby, player, message.getFromBody("playerInfo"));
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
        // NUMBER OF QUESTS
        player.setNumberOfQuestsDone(((Number) info.get("numberOfQuestsDone")).intValue());
    }

    private static ArrayList<Recipe> handleRecipeInfo(ArrayList<String> info) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        for (String recipeName : info) {
            recipes.add(Recipe.getItem(recipeName));
        }
        return recipes;
    }


    public static Message getGameInfo(ServerGame game) {
        Message result = new Message(new HashMap<>(), Message.Type.save);
        ArrayList npcsInfo = getNPCsInfo(game);
        result.addToBody("npcsInfo", npcsInfo);
        for (Player player : game.getPlayers()) {
            result.addToBody(player.getUsername(), player.getRelationsInfo());
        }
        return result;
    }

    public static void loadRelations(Message message, ServerGame game) {
        ArrayList<LinkedTreeMap<String, Object>> npcsInfo = message.getFromBody("npcsInfo");
        for (int i = 0; i < game.getNPCs().size(); i++) {
            NPC npc = game.getNPCs().get(i);
            LinkedTreeMap<String, Object> npcInfo = npcsInfo.get(i);
            for (Map.Entry<String, Object> entry : npcInfo.entrySet()) {
                Player player = game.getPlayerByUsername(entry.getKey());
                Relation relation = new Relation((LinkedTreeMap<String, Object>) entry.getValue());
                npc.setRelation(player, relation);
            }
        }
        for (Player player : game.getPlayers()) {
            LinkedTreeMap<String, Object> relationInfo = message.getFromBody(player.getUsername());
            for (Map.Entry<String, Object> entry : relationInfo.entrySet()) {
                Player otherPlayer = game.getPlayerByUsername(entry.getKey());
                Relation relation = new Relation((LinkedTreeMap<String, Object>) entry.getValue());
                player.setRelation(otherPlayer, relation);
            }
        }
    }
}
