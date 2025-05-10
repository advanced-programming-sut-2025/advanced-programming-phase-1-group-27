package org.example.models.NPCs;

import org.example.models.Item;
import org.example.models.Player;
import org.example.models.Position;
import org.example.models.Relations.Relation;
import org.example.models.enums.Features;
import org.example.models.enums.NPCType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPC {
    private String name;
    private NPCType type;
    private Position currentPosition;
    private Features features;
    private ArrayList<Item> favorites;
    private int daysForThirdQuest;
    private Map<Player, Relation> relations = new HashMap<>();
    private Quest[] quests = new Quest[3];


    public Position getCurrentPosition() {
        return currentPosition;
    }

    public String getName() {
        return name;
    }

    public NPCType getType() {
        return type;
    }

    public Map<Player, Relation> getRelations() {
        return relations;
    }

    public Quest[] getQuests() {
        return quests;
    }

    public Features getFeatures() {
        return features;
    }

    public int getDaysForThirdQuest() {
        return daysForThirdQuest;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public void addXP(Player player, int xp) {
        if (!relations.containsKey(player)) {
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int currentXp = relation.getXp();
        int level = relation.getLevel();
        currentXp += xp;
        if (currentXp > 200) {
            if (level == 799) {
                currentXp = 200;
            } else {
                relation.setLevel(relation.getLevel() + 1);
                currentXp -= 200;
            }
        }
        relation.setXp(currentXp);
    }

}
