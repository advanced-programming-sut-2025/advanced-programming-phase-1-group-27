package org.example.models.enums;

import org.example.models.Map.FarmMap;
import org.example.models.Item;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;

import java.util.ArrayList;

public enum NPCType {
    Sebastian("Sebastian" , ),
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
    private FarmMap placeOfHome;
    private FarmMap placeOfShop;

    NPCType(String name, Shops job, ArrayList<Dialogue> dialogues, ArrayList<Relation> relationsWithOthers
            , ArrayList<Quest> quests, ArrayList<Features> features, ArrayList<Item> favorite
            , FarmMap placeOfHome, FarmMap placeOfShop) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        this.relationsWithOthers = relationsWithOthers;
        Quests = quests;
        this.features = features;
        this.favorite = favorite;
        this.placeOfHome = placeOfHome;
        this.placeOfShop = placeOfShop;
    }
}
