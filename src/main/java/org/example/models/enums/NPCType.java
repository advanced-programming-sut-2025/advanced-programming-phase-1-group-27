package org.example.models.enums;

import org.example.models.Map;
import org.example.models.Item;
import org.example.models.NPCs.Dialogue;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;

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
