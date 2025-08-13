package org.example.common.models.NPCs;

import org.example.common.models.Cell;
import org.example.common.models.Item;
import org.example.common.models.Player;
import org.example.common.models.Building;
import org.example.common.models.Relations.Relation;
import org.example.common.models.Features;
import org.example.common.models.NPCType;
import org.example.common.models.ShopType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class NPC {
    private final int daysForThirdQuest;
    private String name;
    private NPCType type;
    private Cell currentCell;
    private Features features;
    private ArrayList<Item> favorites;
    private ShopType shop;
    private Map<Player, Relation> relations;
    private Quest[] quests = new Quest[3];
    private Building home;
    private String dialogue = "Salam Chettori?";
    private AtomicBoolean isThinking = new AtomicBoolean(false);

    public NPC(NPCType type, int daysForThirdQuest) {
        this.name = type.getName();
        this.type = type;
        this.currentCell = type.getStandingCell();
        this.features = type.getFeatures();
        this.favorites = type.getFavorite();
        this.daysForThirdQuest = daysForThirdQuest;
        this.shop = type.getJob();
//        this.relations = relationMap();
        this.relations = new HashMap<>();
        this.quests = type.getQuests();
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
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

    public ShopType getShop() {
        return shop;
    }

    public int getDaysForThirdQuest() {
        return daysForThirdQuest;
    }

    public ArrayList<Item> getFavorites() {
        return favorites;
    }

    public Features getFeature() {
        return features;
    }

//    private Map<Player, Relation> relationMap() {
//        Map<Player, Relation> map = new HashMap<>();
//        for (Player player : ClientApp.getCurrentGame().getPlayers()) {
//            map.put(player, new Relation());
//        }
//        return map;
//    }

    public void addXP(Player player, int xp) {
        if (!relations.containsKey(player)) {
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int currentXp = relation.getXp();
        int level = relation.getLevel();
        currentXp += xp;
        if (currentXp >= 200) {
            if (level == 799) {
                currentXp = 200;
            } else {
                relation.setLevel(relation.getLevel() + 1);
                currentXp -= 200;
            }
        }
        relation.setXp(currentXp);
    }

    public void addLevel(Player player, int level) {
        if (!relations.containsKey(player)) {
            relations.put(player, new Relation());
        }
        Relation relation = relations.get(player);
        int currentLevel = relation.getLevel();
        currentLevel += level;
        relation.setLevel(currentLevel);
    }

    public Building getHome() {
        return home;
    }

    public void setHome(Building home) {
        this.home = home;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public String getDialogue() {
        return dialogue;
    }

    public boolean isThinking() {
        return isThinking.get();
    }

    public void setThinking(boolean thinking) {
        isThinking.set(thinking);
    }
}
