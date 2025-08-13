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

    public static void cheatAddLevel(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Player player = findPlayer(message.getFromBody("self"), lobbyId);
        NPC npc = findNPC(message.getFromBody("other"), lobbyId);
        npc.getRelations().computeIfAbsent(player, k -> new Relation());
        npc.addLevel(player, message.getIntFromBody("amount"));
    }

    public static void cheatAddXP(Message message) {
        int lobbyId = message.getIntFromBody("lobbyId");
        Player player = findPlayer(message.getFromBody("self"), lobbyId);
        NPC npc = findNPC(message.getFromBody("other"), lobbyId);
        npc.getRelations().computeIfAbsent(player, k -> new Relation());
        npc.addLevel(player, message.getIntFromBody("amount"));
    }

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
