package org.example.server.controller;

import org.example.server.controller.InteractionsWithOthers.InteractionsWithNPCController;
import org.example.server.controller.InteractionsWithOthers.InteractionsWithUserController;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.ToolType;

public class CheatController {

    public Result cheatAddItem(String itemName, int count) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Item item = Game.getItemByName(itemName);
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
        Weather weather = null;
        for (Weather w : Weather.values()) {
            if (w.toString().equalsIgnoreCase(weatherString)) {
                weather = w;
            }
        }
        if (weather == null) {
            return new Result(false, "Please choose a valid weather type from " + Weather.values());
        }
        Game game = App.getCurrentGame();
        game.setTomorrowWeather(weather);
        return new Result(true, "Weather set to " + weather.toString() + " Weather!");
    }

    public Result cheatThor(String s, String t) {
        int i = Integer.parseInt(s), j = Integer.parseInt(t);
        FarmMap map = App.getCurrentGame().getCurrentPlayer().getFarmMap();
        Cell cell = map.getCell(i, j);
        if (cell.getBuilding() != null) {
            return new Result(false, "There is A Building!!");
        } else {
            cell.thor();
            return new Result(true, "The Cell [" + i + ", " + j + "] was hit by Thor!");
        }
    }

    public Result cheatSetEnergy(String energyString) {
        int energy = Integer.parseInt(energyString);

        App.getCurrentGame().getCurrentPlayer().setEnergy(energy);
        App.getCurrentGame().getCurrentPlayer().setDayEnergy(energy);
        return new Result(true, "Energy Set to " + energy);
    }

    public Result cheatEnergyUnlimited() {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.setCheater(true);
        return new Result(true, "Cheat Activated!!");
    }

    public Result cheatSetFriendship(String name, String amountString) {
        int val = Integer.parseInt(amountString);
        for (Animal animal : App.getCurrentGame().getCurrentPlayer().getFarmMap().getAnimals()) {
            if (animal.getName().equals(name)) {
                animal.cheatSetFriendShip(val);
                return new Result(true, "Cheat acitvated!");
            }
        }
        return new Result(false, "No animal found!");
    }

    public Result cheatAddMoney(String amountString) {
        int val = Integer.parseInt(amountString);
        App.getCurrentGame().getCurrentPlayer().addMoney(val);
        return new Result(true, "cheat Activated, You Now Have " +
                App.getCurrentGame().getCurrentPlayer().getMoney() + "$");
    }

    public Result cheatAdvanceTime(String hourString) {
        String oldTime = App.getCurrentGame().getTime().getDateTime();
        int hour = Integer.parseInt(hourString);
        App.getCurrentGame().getTime().cheatAdvanceTime(hour);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatAdvanceDate(String dayString) {
        String oldTime = App.getCurrentGame().getTime().getDateTime();
        int day = Integer.parseInt(dayString);
        App.getCurrentGame().getTime().cheatAdvanceDate(day);
        return new Result(true, "The old time was " + oldTime + "\n" +
                "The new time is " + App.getCurrentGame().getTime().getDateTime());
    }

    public Result cheatSetAbility(String abilityName, int level) {
        Player player = App.getCurrentGame().getCurrentPlayer();
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
        Player player = App.getCurrentGame().getCurrentPlayer();
        NPC npc = InteractionsWithNPCController.getNPCByName(NPCName);
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
        return new Result(true, "Level is added! ( " + finalAmount + " )");
    }

    public Result cheatAddPlayerLevel(String playerName, String quantityString) {
        int quantity = Integer.parseInt(quantityString);
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        Player player = InteractionsWithUserController.getPlayerByUsername(playerName);
        if (player == null) {
            return new Result(false, "Player not found");
        }
        if (!currentPlayer.getRelations().containsKey(player)) {
            currentPlayer.getRelations().put(player, new Relation());
        }
        if (!player.getRelations().containsKey(currentPlayer)) {
            player.getRelations().put(currentPlayer, new Relation());
        }
        Relation relation = currentPlayer.getRelations().get(player);
        if (relation.getLevel() + quantity > 4) {
            return new Result(false, "Level is too high");
        }
        int amount = relation.getLevel() + quantity;
        Relation relation2 = player.getRelations().get(currentPlayer);
        relation.setLevel(amount);
        relation2.setLevel(amount);
        return new Result(true, "Level has been added successfully (" + relation.getLevel() + ")");
    }

}
