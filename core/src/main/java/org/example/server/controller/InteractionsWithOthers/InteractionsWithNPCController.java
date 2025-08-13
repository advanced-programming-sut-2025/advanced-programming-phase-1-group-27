package org.example.server.controller.InteractionsWithOthers;

import org.example.common.models.Player;
import org.example.common.models.Result;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.common.models.NPCs.NPC;
import org.example.common.models.NPCs.Quest;
import org.example.common.models.Relations.Relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InteractionsWithNPCController {
    public static void handleMessage(Message message) {
        String mode = message.getFromBody("mode");
        int lobbyId = message.getIntFromBody("lobbyId");
        String username = message.getFromBody("username");
        String npcName = message.getFromBody("npcName");
        if (mode.equals("meet")) {
            meetNPC(npcName, lobbyId, username);
        } else if (mode.equals("gift")) {
            int xp = message.getIntFromBody("XP");
            giftNPC(npcName, lobbyId, username, xp);
        }
    }

    public static void meetNPC(String npcName, int lobbyId, String username) {
        NPC npc = findNPC(npcName, lobbyId);
        Player player = findPlayer(username, lobbyId);
        npc.getRelations().computeIfAbsent(player, k -> new Relation());
        npc.addXP(player, 20);
    }

    public static void giftNPC(String npcName, int lobbyId, String username, int amount) {
        NPC npc = findNPC(npcName, lobbyId);
        Player player = findPlayer(username, lobbyId);
        npc.getRelations().computeIfAbsent(player, k -> new Relation());
        npc.addXP(player, amount);
    }

//    public Result questList() {
//        StringBuilder result = new StringBuilder();
//        result.append("Active Quests : ");
//        result.append("\n");
//        for (NPC npc : App.getCurrentGame().getNPCs()) {
//            if (npc.getQuests() != null) {//Some NPCs doesn't have quests
//                result.append(npc.getName()).append(" : \n");
//                Quest[] quests = npc.getQuests();
//                if (!quests[0].isDone()) {
//                    if (quests[0].getRequest() == null) {
//                        result.append("1. ").append("12*Plant");
//                    } else {
//                        result.append("1. ").append(quests[0].getRequest().getQuantity());
//                        result.append("*").append(quests[0].getRequest().getItem().getName());
//                    }
//                    result.append(" -> ");
//                    if (quests[0].getReward() != null) {
//                        result.append(quests[0].getReward().getQuantity());
//                        result.append("*").append(quests[0].getReward().getItem().getName());
//                    } else { // Abigail give Friendship level
//                        if (npc.getName().equalsIgnoreCase("Abigail")) {
//                            result.append("+1 Friendship level");
//                        }
//                    }
//                    result.append("\n");
//                }
//                if (!quests[1].isDone()
//                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 0) {
//                    result.append("2. ").append(quests[1].getRequest().getQuantity());
//                    result.append("*").append(quests[1].getRequest().getItem().getName());
//                    result.append(" -> ");
//                    if (quests[1].getReward() != null) {
//                        result.append(quests[1].getReward().getQuantity());
//                        result.append("*").append(quests[1].getReward().getItem().getName());
//                    } else { // Harvey give Friendship level
//                        if (npc.getName().equalsIgnoreCase("Harvey")) {
//                            result.append("+1 Friendship level");
//                        }
//                    }
//                    result.append("\n");
//                }
//                if (!quests[2].isDone()
//                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel()
//                        > 0 && canActivateThirdQuest(npc)) {
//                    result.append("3. ").append(quests[2].getRequest().getQuantity());
//                    result.append("*").append(quests[2].getRequest().getItem().getName());
//                    result.append(" -> ");
//                    result.append(quests[2].getReward().getQuantity());
//                    result.append("*").append(quests[2].getReward().getItem().getName());
//                    result.append("\n");
//                }
//                result.append("---------------------------------\n");
//            }
//        }
//        return new Result(true, result.toString());
//    }

    public Result questsFinish(String npcName, String stringIndex) {
//        NPC npc = findNPC(npcName);
//        int index = Integer.parseInt(stringIndex);
//        index--;
//        if (index < 0 || index > 2) {
//            return new Result(false, "Invalid quest index!");
//        }
//        if (npc == null) {
//            return new Result(false, "NPC not found!");
//        }
//        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
//            return new Result(false, "NPC is not near you!");
//        }
//        Quest[] quests = npc.getQuests();
//        if (quests[index].isDone()) {
//            return new Result(false, "This quest is already done!");
//        }
//        Stacks stacks = quests[index].getRequest();
//        Backpack backpack = App.getCurrentGame().getCurrentPlayer().getBackpack();
//        if (index == 0 && npc.getName().equalsIgnoreCase("Harvey")) { // It gets 12 * any plant
//            Ingredient ingredient = new Ingredient(new ArrayList<>(), 12);
//            for (CropType cropType : CropType.values()) {
//                ingredient.addPossibleIngredients(cropType.getFruit());
//            }
//            Player player = App.getCurrentGame().getCurrentPlayer();
//            Item item = player.getAvailableIngredient(ingredient);
//            if (item == null) {
//                return new Result(false, "There is no such plant!");
//            }
//            player.getBackpack().reduceItems(item, 12);
//        } else {
//            if (!backpack.reduceItems(stacks.getItem(), stacks.getQuantity())) {
//                return new Result(false, "You do not have enough item in backpack!");
//            }
//        }
//        quests[index].setDone(true);
//        int ratio = 1;
//        if (npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 1) {
//            //If level is > 1 we get double reward
//            ratio = 2;
//        }
//        if (index == 0 && npc.getName().equalsIgnoreCase("Abigail")) {
//            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
//            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
//            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
//        }
//        if (index == 1 && npc.getName().equalsIgnoreCase("Harvey")) {
//            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
//            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
//            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
//        }
//        if (index == 1 && npc.getName().equalsIgnoreCase("Lia")) {
//            App.getCurrentGame().getCurrentPlayer().getAvailableCookingRecipes().add(Recipe.SalmonDinnerRecipe);
//            return new Result(true, npcName + " : Thank you! (You get 1 * dinner salmon recipe!)");
//        }
//        if (index == 2 && npc.getName().equalsIgnoreCase("Abigail")) {
//            Stacks deletedStack = null;
//            for (Stacks stacks1 : backpack.getItems()) {
//                if (stacks1.getItem().getName().equalsIgnoreCase("IridiumWateringCan")) {
//                    backpack.addItems(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity());
//                    quests[index].setDone(false);
//                    return new Result(true, npcName + " : You have Iridium watering can already!");
//                } else {
//                    deletedStack = stacks1;
//                }
//            }
//            if (deletedStack != null) {
//                backpack.getItems().remove(deletedStack);
//                backpack.addItems(ToolType.IridiumWateringCan, StackLevel.Iridium, 1);
//            }
//        }
//        Stacks reward = quests[index].getReward();
//        if (reward.getItem() == ShopItems.Coin) {
//            App.getCurrentGame().getCurrentPlayer().addMoney(reward.getQuantity() * ratio);
//            int amount = reward.getQuantity() * ratio;
//            return new Result(true, npcName + " : Thank you! ( You get " + amount + " money)");
//        }
//
//        int amount = backpack.addItems(reward.getItem(), reward.getStackLevel(), reward.getQuantity() * ratio);
//        return new Result(true, npcName + " : Thank you! ( You get " + reward.getQuantity() *
//                ratio + "*" + reward.getItem().getName() + " )");
        return null;
    }

    public static NPC findNPC(String npcName, int lobbyId) {
        for (NPC npc : ServerApp.getLobbyById(lobbyId).getGame().getNPCs()) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                return npc;
            }
        }
        return null;
    }

    private static Player findPlayer(String username, int lobbyId) {
        for (Player player : ServerApp.getLobbyById(lobbyId).getGame().getPlayers()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

//    private boolean canActivateThirdQuest(NPC npc) {
//        int daysPassed = App.getCurrentGame().getTime().getDaysPassed();
//        return npc.getDaysForThirdQuest() <= daysPassed;
//    }

    public static void cheatAddLevel(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Player player = findPlayer(message.getFromBody("self"), lobbyId);
        NPC npc = findNPC(message.getFromBody("other"), lobbyId);
        npc.getRelations().computeIfAbsent(player, k -> new Relation());
        npc.addLevel(player, message.getIntFromBody("amount"));
    }

//    public static void cheatAddXP(Message message) {
//        int lobbyId = message.getIntFromBody("lobbyId");
//        Player player = findPlayer(message.getFromBody("self"), lobbyId);
//        NPC npc = findNPC(message.getFromBody("other"), lobbyId);
//        npc.getRelations().computeIfAbsent(player, k -> new Relation());
//        npc.addLevel(player, message.getIntFromBody("amount"));
//    }

    public static Message getQuests(Message message){
        int lobbyId = message.getIntFromBody("lobbyId");
        String npcName = message.getFromBody("npcName");
        NPC npc = findNPC(npcName, lobbyId);
        ArrayList<HashMap<String , Object>> selected = new ArrayList<>();
        for(Quest quest : npc.getQuests()){
            selected.add(quest.getInfo());
        }
        System.out.println("selected.size() : " + selected.size());
        return new Message(new HashMap<>() {{
            put("quests", selected);
        }} , Message.Type.response);
    }

    public static Message doIHaveThisQuest(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Quest quest = new Quest(message.getFromBody("quest"));
        String self =  message.getFromBody("self");
        Player player =  findPlayer(self, lobbyId);
        boolean found = false;
        for(Quest quests : player.getActiveQuests()){
            if(Objects.equals(quests.getReward().getItem().getName(), quest.getReward().getItem().getName())){
                if(Objects.equals(quests.getRequest().getItem().getName(), quest.getRequest().getItem().getName())){
                    found = true;
                }
            }
        }
        boolean finalFound = found;
        return new Message(new HashMap<>() {{
            put("answer" , finalFound);
        }} , Message.Type.response);
    }

    public static void finishQuest(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Quest quest =  new Quest(message.getFromBody("quest"));
        String npcName = message.getFromBody("npcName");
        String playerName = message.getFromBody("self");
        NPC npc = findNPC(npcName, lobbyId);
        for(Quest quests : npc.getQuests()){
            if(quests.getReward().getItem().getName().equals(quest.getReward().getItem().getName())){
                if(Objects.equals(quests.getRequest().getItem().getName(), quest.getRequest().getItem().getName())){
                    quests.setDone(true);
                    quests.setPlayerName(playerName);
                    Player player = findPlayer(playerName, lobbyId);
                    player.addNumberOfQuestsDone();
                    System.out.println(quests.getRequest().getItem().getName() + " has been finished");
                }
            }
        }
    }

    public static Message getQuestsJournal(Message message){
        int lobbyId = message.getIntFromBody("lobbyId");
        ArrayList<HashMap<String , Object>> selected = new ArrayList<>();
        for(NPC npc : ServerApp.getLobbyById(lobbyId).getGame().getNPCs()){
            if(npc.getQuests() != null){
                for(Quest quest : npc.getQuests()){
                    selected.add(quest.getInfo());
                }
            }
        }
        return new Message(new HashMap<>() {{
            put("quests", selected);
        }} , Message.Type.response);
    }

}
