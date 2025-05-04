package org.example.controller.InteractionsWithOthers;

import org.example.models.*;
import org.example.models.NPCs.NPC;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;
import org.example.models.enums.Features;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.StackLevel;
import org.example.models.enums.Weathers.Weather;
import org.example.models.enums.items.ShopItems;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;
import org.example.models.tools.WateringCan;

public class InteractionsWithNPCController {
    public Result meetNPC(String npcName) {
        NPC npc = findNPC(npcName);
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "NPC is not near you!");
        }
        StringBuilder result = new StringBuilder();
        result.append(dialogueForNPC(npc));
        if (firstTimeMet(npc, App.getCurrentGame().getCurrentPlayer())) {
            npc.getRelations().computeIfAbsent(App.getCurrentGame().getCurrentPlayer(), k -> new Relation());
            npc.addXP(App.getCurrentGame().getCurrentPlayer(), 20);
            //Dialogues must be different
            result.append(" (You get +20 XP)");
        }
        return new Result(true, result.toString());
    }

    private String dialogueForNPC(NPC npc) {
        int time = App.getCurrentGame().getTime().getHour();
        Weather weather = App.getCurrentGame().getCurrentWeather();
        int level = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel();
        Season season = App.getCurrentGame().getTime().getSeason();
        Features features = npc.getFeatures();
        StringBuilder result = new StringBuilder();
        result.append("Hi");
        if (level == 0) {
            result.append(" stranger! ");
        } else {
            result.append(" friend! ");
        }
        if (time >= 9
                && time <= 12) {
            result.append("Good Morning! ");
        } else if (time > 12
                && time <= 16) {
            result.append("Good Afternoon! ");
        } else if (time > 16
                && time <= 19) {
            result.append("Good Evening! ");
        } else { //time > 19 && time <= 24
            result.append("Good Night! ");
        }
        if (weather == Weather.Rain) {
            result.append("Do you have an umbrella? ");
        } else if (weather == Weather.Snow) {
            result.append("It's so cold! ");
        } else if (weather == Weather.Storm) {
            result.append("Weather is windy! ");
        } else {//weather == Weather.Sunny
            result.append("It's so hot! ");
        }
        result.append("I like ");
        if (season == Season.Spring) {
            result.append("Spring!");
        } else if (season == Season.Summer) {
            result.append("Summer!");
        } else if (season == Season.Fall) {
            result.append("Fall!");
        } else { // season == Season.Winter
            result.append("Winter!");
        }
        return result.toString();
    }

    public Result giftNPC(String npcName, String itemName) {
        Stacks stack = findItem(itemName);
        NPC npc = findNPC(npcName);
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (stack == null) {
            return new Result(false, "You don't have this item!");
        }
        if (stack.getItem() instanceof Tool) {
            return new Result(false, "You can't gift any tool!");
        }
        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "NPC is not near you!");
        }
        App.getCurrentGame().getCurrentPlayer().getBackpack().reduceItems(stack.getItem(), 1);
        int xp = 0;
        if (firstTimeGift(npc, App.getCurrentGame().getCurrentPlayer())) {
            boolean addItem = false;
            for (Item item : npc.getFavorites()) {
                if (item.getName().equals(stack.getItem().getName())) {
                    npc.addXP(App.getCurrentGame().getCurrentPlayer(), 200);
                    xp = 200;
                    addItem = true;
                }
            }
            if (!addItem) {
                npc.addXP(App.getCurrentGame().getCurrentPlayer(), 50);
                xp = 50;
            }
        }
        return new Result(true, npcName + " : Thank you! (You get " + xp + " xp)");
    }

    public Result friendshipNPC() {
        Player currentPlayer = App.getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        result.append("(Name) - (Level) - (Xp)");
        for (NPC npc : App.getCurrentGame().getNPCs()) {
            result.append("\n");
            result.append(npc.getName()).append(" - ");
            result.append(npc.getRelations().get(currentPlayer).getLevel()).append(" - ");
            result.append(npc.getRelations().get(currentPlayer).getXp());
        }
        return new Result(true, result.toString());
    }

    public Result questList() {
        StringBuilder result = new StringBuilder();
        result.append("Active Quests : ");
        result.append("\n");
        for (NPC npc : App.getCurrentGame().getNPCs()) {
            if (npc.getQuests() != null) {//Some NPCs doesn't have quests
                result.append(npc.getName()).append(" : ");
                Quest[] quests = npc.getQuests();
                if (!quests[0].isDone()) {
                    result.append("1. ").append(quests[0].getRequest().getQuantity());
                    result.append("*").append(quests[0].getRequest().getItem().getName());
                    result.append(" -> ");
                    if (quests[0].getReward() != null) {
                        result.append(quests[0].getReward().getQuantity());
                        result.append("*").append(quests[0].getReward().getItem().getName());
                    } else { // Abigail give Friendship level
                        if (npc.getName().equals("Abigail")) {
                            result.append("*").append("+1 Friendship level");
                        }
                    }
                    result.append("\n");
                }
                if (!quests[1].isDone()
                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 0) {
                    result.append("2. ").append(quests[1].getRequest().getQuantity());
                    result.append("*").append(quests[1].getRequest().getItem().getName());
                    result.append(" -> ");
                    if (quests[1].getReward() != null) {
                        result.append(quests[1].getReward().getQuantity());
                        result.append("*").append(quests[1].getReward().getItem().getName());
                    } else { // Harvey give Friendship level
                        if (npc.getName().equals("Harvey")) {
                            result.append("*").append("+1 Friendship level");
                        }
                    }
                    result.append("\n");
                }
                if (!quests[2].isDone()
                        && npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 0
                        && canActivateThirdQuest(npc)) {
                    result.append("3. ").append(quests[2].getRequest().getQuantity());
                    result.append("*").append(quests[2].getRequest().getItem().getName());
                    result.append(" -> ");
                    result.append(quests[2].getReward().getQuantity());
                    result.append("*").append(quests[2].getReward().getItem().getName());
                    result.append("\n");
                }
                result.append("---------------------------------\n");
            }
        }
        return new Result(true, result.toString());
    }

    public Result questsFinish(String stringIndex, String npcName) {
        NPC npc = findNPC(npcName);
        int index = Integer.parseInt(stringIndex);
        index--;
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "NPC is not near you!");
        }
        Quest[] quests = npc.getQuests();
        if (quests[index].isDone()) {
            return new Result(false, "This quest is already done!");
        }
        Stacks stacks = quests[index].getRequest();
        Backpack backpack = App.getCurrentGame().getCurrentPlayer().getBackpack();
        if (!backpack.reduceItems(stacks.getItem(), stacks.getQuantity())) {
            return new Result(false, "You do not have enough space in backpack!");
        }
        quests[index].setDone(true);
        int ratio = 1;
        if (npc.getRelations().get(App.getCurrentGame().getCurrentPlayer()).getLevel() > 1) {
            //If level is > 1 we get double reward
            ratio = 2;
        }
        if (index == 0
                && npc.getName().equals("Abigail")) {
            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        if (index == 1
                && npc.getName().equals("Harvey")) {
            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
            relation.setLevel(Math.min(relation.getLevel() + ratio, 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        if (index == 2
                && npc.getName().equals("Abigail")) {
            Stacks deletedStack = null;
            for (Stacks stacks1 : backpack.getItems()) {
                if (stacks1.getItem() instanceof WateringCan) {
                    if (stacks1.getItem().getName().equals("Iridium watering can")) {
                        backpack.addItems(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity());
                        quests[index].setDone(false);
                        return new Result(true, npcName + " : You have Iridium watering can already!");
                    } else {
                        deletedStack = stacks1;
                    }
                }
            }
            if (deletedStack != null) {
                backpack.getItems().remove(deletedStack);
                backpack.addItems(ToolType.IridiumWateringCan, StackLevel.Iridium, 1);
            }
        }
        Stacks reward = quests[index].getReward();
        if (reward.getItem() == ShopItems.Coin) {
            App.getCurrentGame().getCurrentPlayer().addMoney(reward.getQuantity() * ratio);
            int amount = reward.getQuantity() * ratio;
            return new Result(true, npcName + " : Thank you! ( You get " + amount + " money)");
        }
        //TODO : momken hast ja nadashte bashim
        int amount = backpack.addItems(stacks.getItem(), stacks.getStackLevel(), stacks.getQuantity() * ratio);
        return new Result(true, npcName + " : Thank you! ( You get " + stacks.getQuantity() * ratio +
                "*" + stacks.getItem().getName() + " )");
    }

    private boolean isNPCNear(NPC npc, Player player) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();
        int npcX = npc.getCurrentPosition().getX();
        int npcY = npc.getCurrentPosition().getY();
        int distanceX = Math.abs(playerX - npcX);
        int distanceY = Math.abs(playerY - npcY);
        return distanceY <= 1
                && distanceX <= 1;
    }

    private NPC findNPC(String npcName) {
        for (NPC npc : App.getCurrentGame().getNPCs()) {
            if (npc.getName().equals(npcName)) {
                return npc;
            }
        }
        return null;
    }

    private boolean firstTimeMet(NPC npc, Player player) {
        if (player.getNpcMetToday().get(npc) == null
                || player.getNpcMetToday().get(npc) == Boolean.FALSE) {
            player.getNpcMetToday().put(npc, Boolean.TRUE);
            return true;
        } else {
            return false;
        }
    }

    private boolean firstTimeGift(NPC npc, Player player) {
        if (player.getNpcGiftToday().get(npc) == null
                || player.getNpcGiftToday().get(npc) == Boolean.FALSE) {
            player.getNpcGiftToday().put(npc, Boolean.TRUE);
            return true;
        } else {
            return false;
        }
    }

    private Stacks findItem(String itemName) {
        for (Stacks stack : App.getCurrentGame().getCurrentPlayer().getBackpack().getItems()) {
            if (stack.getQuantity() != 0) {
                if (stack.getItem().getName().equals(itemName)) {
                    return stack;
                }
            }
        }
        return null;
    }

    private boolean canActivateThirdQuest(NPC npc) {
        int daysPassed = App.getCurrentGame().getTime().getDaysPassed();
        return npc.getDaysForThirdQuest() >= daysPassed;
    }
}
