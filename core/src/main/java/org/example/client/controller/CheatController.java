package org.example.client.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.client.model.ClientApp;
import org.example.client.view.OutsideView;
import org.example.common.models.GameAssetManager;
import org.example.common.models.GraphicalResult;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.ToolType;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;
import static org.example.server.models.ServerApp.startListening;

public class CheatController {

    public Result cheatAddItem(String itemName, int count) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        Item item = ItemManager.getItemByName(itemName);
        if (item == null)
            return new Result(false, "Item not found!");
        if (count < 0)
            return new Result(false, "Invalid quantity!");
        StackLevel level = item instanceof ToolType ? ((ToolType) item).getLevel() : StackLevel.Basic;
        if (!player.getBackpack().canAdd(item, level, count))
            return new Result(false, "You don't have enough space in your backpack!");
        player.getBackpack().addItems(item, level, count);
        return new Result(true, count + " of " + item.getName() + " added to the backpack!");
    }

    public Result cheatSetWeather(String weatherString) {
        Weather weather = Weather.getWeather(weatherString);
        if (weather == null) {
            return new Result(false, "Please choose a valid weather type from " + Weather.values());
        }
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("weather", weatherString);
        }}, Message.Type.set_weather));
        return new Result(true, "Weather set to " + weather.toString() + " Weather!");
    }

    public Result cheatThor(String s, String t) {
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        FarmMap map = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap();
        Cell cell = map.getCell(i, j);

        if (cell.getBuilding() != null) {
            return new Result(false, "There is A Building!!");
        } else {
            cell.thor();

            if ( ClientApp.getCurrentMenu() instanceof OutsideView outsideView
            && !(ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap npcMap)) {

                outsideView.displayThorAnimation(i,j);

            }

            return new Result(true, "The Cell [" + i + ", " + j + "] was hit by Thor!");
        }
    }

    public Result cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);

        ClientApp.getCurrentGame().getCurrentPlayer().setEnergy(energy);
        return new Result(true, "Energy Set to " + energy);
    }

    public Result cheatEnergyUnlimited() {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        player.setCheater(true);
        return new Result(true, "Cheat Activated!!");
    }

    public Result cheatSetFriendship(String name, String amountString) {
        int val = Integer.parseInt(amountString);
        for (Animal animal : ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals()) {
            if (animal.getName().equals(name)) {
                animal.cheatSetFriendShip(val);
                return new Result(true, "Cheat acitvated!");
            }
        }
        return new Result(false, "No animal found!");
    }

    public Result cheatAddMoney(String amountString) {
        int val = Integer.parseInt(amountString);
        ClientApp.getCurrentGame().getCurrentPlayer().addMoney(val);
        return new Result(true, "cheat Activated, You Now Have " +
                ClientApp.getCurrentGame().getCurrentPlayer().getMoney() + "$");
    }

    public Result cheatAdvanceTime(String hourString) {
        String oldTime = ClientApp.getCurrentGame().getTime().getDateTime();
        int hour = Integer.parseInt(hourString);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("unit", "hour");
                    put("value", hour);
                }}, Message.Type.advance_time),
                TIMEOUT_MILLIS
        );
        if (response == null)
            return new Result(false, "Cheat failed!");
        return new Result(true, "The old time was " + oldTime + ". " +
                "The new time is " + ClientApp.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatAdvanceDate(String dayString) {
        String oldTime = ClientApp.getCurrentGame().getTime().getDateTime();
        int day = Integer.parseInt(dayString);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(new HashMap<>() {{
                    put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                    put("unit", "day");
                    put("value", day);
                }}, Message.Type.advance_time),
                TIMEOUT_MILLIS
        );
        if (response == null)
            return new Result(false, "Cheat failed!");
        return new Result(true, "The old time was " + oldTime + ". " +
                "The new time is " + ClientApp.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatSetAbility(String abilityName, int level) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        AbilityType type = AbilityType.getAbilityType(abilityName);
        if (type == null)
            return new Result(false, "Invalid ability type!");
        if (level > 4)
            return new Result(false, "Cannot reach this level!");
        while (player.getAbility(type).getLevel() < level) {
            if (type == AbilityType.Farming)
                player.farmXp(1);
            else if (type == AbilityType.Mining)
                player.mineXp(1);
            else if (type == AbilityType.Fishing)
                player.fishXp(1);
            else { // foraging
                player.forageXp(1);
            }
        }
        return new Result(true, abilityName + " level set to " + level);
    }

    public Result cheatAddLevel(String NPCName, String amountString) {
        int amount = Integer.parseInt(amountString);
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        NPC npc = InteractionsWithNPCController.findNPC(NPCName);
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (!npc.getRelations().containsKey(player)) {
            npc.getRelations().put(player, new Relation());
        }
        Relation relation = npc.getRelations().get(player);
        if (relation.getLevel() + amount > 799) {
            return new Result(false, "Level is too high!");
        }
        relation.setLevel(relation.getLevel() + amount);
        int finalAmount = relation.getLevel();
        // TODO : message server
        return new Result(true, "Level is added! ( " + finalAmount + " )");
    }

    public Result cheatAddPlayerLevel(String playerName, String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        Message response = ClientApp.getServerConnectionThread().sendAndWaitForResponse(new Message(new  HashMap<>() {{
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("self" , ClientApp.getLoggedInUser().getUsername());
            put("other", playerName);
            put("amount" , quantity);
        }} , Message.Type.add_player_level) ,  TIMEOUT_MILLIS);
        if(response == null || response.getType() != Message.Type.response)
            return new Result(false, "can't add level!");
        GraphicalResult result = new GraphicalResult(response.<LinkedTreeMap<String, Object>>getFromBody("GraphicalResult"));
        return new Result(!result.hasError() , result.getMessage().getText().toString());
    }

}
