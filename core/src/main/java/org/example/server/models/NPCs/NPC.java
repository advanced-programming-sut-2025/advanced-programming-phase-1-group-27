package org.example.server.models.NPCs;

import org.example.server.models.*;
import org.example.server.models.Relations.Relation;
import org.example.server.models.enums.Features;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.ShopType;

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
    private Map<Player, Relation> relations = new HashMap<>();
    private Quest[] quests = new Quest[3];
    private Building home;
    private String dialogue = "hello, do you—know the doesn't of isn't? I'm gl'ad to k'now all of them! very very good and thanks ";
    private AtomicBoolean isThinking = new AtomicBoolean(false);

    public NPC(NPCType type, int daysForThirdQuest) {
        this.name = type.getName();
        this.type = type;
        this.currentCell = type.getStandingCell();
        this.features = type.getFeatures();
        this.favorites = type.getFavorite();
        this.daysForThirdQuest = daysForThirdQuest;
        this.shop = type.getJob();
        this.relations = relationMap();
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

    private Map<Player, Relation> relationMap() {
        Map<Player, Relation> map = new HashMap<>();
        for (Player player : App.getCurrentGame().getPlayers()) {
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
