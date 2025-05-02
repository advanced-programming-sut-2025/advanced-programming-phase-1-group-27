package org.example.controller.InteractionsWithOthers;

import org.example.models.*;
import org.example.models.NPCs.NPC;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;
import org.example.models.enums.items.ShopItems;
import org.example.models.tools.Backpack;
import org.example.models.tools.Tool;

public class InteractionsWithNPCController {
    public Result meetNPC(String npcName) {
        NPC npc = findNPC(npcName);
        if (npc == null) {
            return new Result(false, "NPC not found!");
        }
        if (!isNPCNear(npc, App.getCurrentGame().getCurrentPlayer())) {
            return new Result(false, "NPC is not near you!");
        }
        if (firstTimeMet(npc, App.getCurrentGame().getCurrentPlayer())) {
            npc.getRelations().computeIfAbsent(App.getCurrentGame().getCurrentPlayer(), k -> new Relation());
            npc.addXP(App.getCurrentGame().getCurrentPlayer(), 20);
            //Dialogues must be different
            return new Result(true, "Hi! How are you today?");
        }
        return new Result(true, "What do you want?");
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
                if (item == stack.getItem()) {
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
        for (NPC npc : App.getCurrentGame().getNpcs()) {
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
        for (NPC npc : App.getCurrentGame().getNpcs()) {
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
            relation.setLevel(Math.min(relation.getLevel() + ratio , 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        if (index == 1
                && npc.getName().equals("Harvey")) {
            Relation relation = npc.getRelations().get(App.getCurrentGame().getCurrentPlayer());
            relation.setLevel(Math.min(relation.getLevel() + ratio , 799));
            return new Result(true, npcName + " : Thank you! (You get " + ratio + " friendship level)");
        }
        Stacks reward = quests[index].getReward();
        if (reward.getItem() == ShopItems.Coin) {
            App.getCurrentGame().getCurrentPlayer().addMoney(reward.getQuantity() * ratio);
            int amount = reward.getQuantity() * ratio;
            return new Result(true, npcName + " : Thank you! ( You get " + amount + " money)");
        }
        //TODO : momken hast ja nadashte bashim
        int amount = backpack.addItems(stacks.getItem(), stacks.getQuantity() * ratio);
        return new Result(true, npcName + " : Thank you! ( You get " + stacks.getQuantity() * ratio +
                "*" + stacks.getItem().getName() + " )");
    }

    private boolean isNPCNear(NPC npc, Player player) {
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();
        int npcX = npc.getCurrentPosition().getX();
        int npcY = npc.getCurrentPosition().getY();
        int distance = Math.abs(playerX - npcX) + Math.abs(playerY - npcY);
        return distance < 2;
    }

    private NPC findNPC(String npcName) {
        for (NPC npc : App.getCurrentGame().getNpcs()) {
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
        //TODO : zaman bayad check shavad
        return true;
    }
}
