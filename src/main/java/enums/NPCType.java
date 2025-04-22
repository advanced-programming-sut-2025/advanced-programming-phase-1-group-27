package enums;

import models.NPCs.Dialogue;
import models.NPCs.Quest;
import models.Relations.Relation;

import java.util.ArrayList;

public enum NPCType {
    Sebastian,
    Abigail,
    Harvey,
    Lia,
    Robbin,
    Clint,
    Pierre,
    Robin,
    Willy,
    Marnie,
    Morris,
    Gus;

    private String name;
    private Shops job;
    private ArrayList<Dialogue> dialogues;
    private ArrayList<Relation> relationsWithOthers;
    private ArrayList<Quest> Quests;
    private ArrayList<Features> features;
    private ArrayList<Item> favorite;
    private Map placeOfHome;
    private Map placeOfShop;

}
