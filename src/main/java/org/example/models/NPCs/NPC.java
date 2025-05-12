package org.example.models.NPCs;

import org.example.models.*;
import org.example.models.Relations.Relation;
import org.example.models.enums.Features;
import org.example.models.enums.NPCType;
import org.example.models.enums.Shops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NPC {
    private String name;
    private NPCType type;
    private Cell currentPosition;
    private Features features;
    private ArrayList<Item> favorites;
    private final int daysForThirdQuest;
    private Shops shop;
    private Map<Player, Relation> relations = new HashMap<>();
    private Quest[] quests = new Quest[3];

    public NPC(NPCType type, int daysForThirdQuest) {
        this.name = type.getName();
        this.type = type;
        this.currentPosition = type.getStandingCell();
        this.features = type.getFeatures();
        this.favorites = type.getFavorite();
        this.daysForThirdQuest = daysForThirdQuest;
        this.shop = type.getJob();
        this.relations = relationMap();
        this.quests = type.getQuests();
    }

    public Cell getCurrentPosition() {
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

    public Shops getShop() {
        return shop;
    }

    public int getDaysForThirdQuest() {
        return daysForThirdQuest;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public Features getFeature(){
        return features;
    }

    private Map<Player , Relation> relationMap(){
        Map<Player , Relation> map = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers()){
            map.put(player, new Relation());
        }
        return map;
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
