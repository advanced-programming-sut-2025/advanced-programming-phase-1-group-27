package org.example.models.enums;

import org.example.models.Building;
import org.example.models.Map.FarmMap;
import org.example.models.Item;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;

import java.util.ArrayList;

public enum NPCType {
    Sebastian("Sebastian", null, SebastianQuests() , Features.Angry ,),
    Abigail("Abigail", null, AbigailQuests() , Features.Sad ,),
    Harvey("Harvey", null, HarveyQuests() , Features.Happy , ),
    Lia("Lia", null, LiaQuests() , Features.Lazy ,),
    Robbin("Robbin", null, RobbinQuests() , Features.Scared ,),
    Clint("Clint", Shops.Blacksmith, null , Features.Anxious ,),
    Pierre("Pierre", Shops.PierreGeneralStore, null , Features.Angry ,),
    Robin("Robin", Shops.CarpenterShop, null , Features.Happy ,),
    Willy("Willy", Shops.FishShop, null , Features.Sad ,),
    Marnie("Marnie", Shops.MarnieShop, null , Features.Lazy ,),
    Morris("Morris", Shops.JojaMart, null , Features.Sad , ),
    Gus("Gus", Shops.TheStardropSaloon, null , Features.Happy , );

    private String name;
    private Shops job;
    private ArrayList<Dialogue> dialogues;
    private Quest[] Quests;
    private Features features;
    private ArrayList<Item> favorite;
    private Building home;
    private FarmMap placeOfShop;

    NPCType(String name, Shops job, ArrayList<Dialogue> dialogues, ArrayList<Relation> relationsWithOthers
            , Quest[] quests, Features features, ArrayList<Item> favorite
            , FarmMap placeOfShop) {
        this.name = name;
        this.job = job;
        this.dialogues = dialogues;
        Quests = quests;
        this.features = features;
        this.favorite = favorite;
        this.placeOfShop = placeOfShop;
    }

    public void setHome(Building home) {
        this.home = home;
    }

    private static Quest[] AbigailQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.AbigailFirstQuest.getRequest()
                , QuestsForNPCs.AbigailFirstQuest.getReward());
        quests[1] = new Quest(QuestsForNPCs.AbigailSecondQuest.getRequest()
                , QuestsForNPCs.AbigailSecondQuest.getReward());
        quests[2] = new Quest(QuestsForNPCs.AbigailThirdQuest.getRequest()
                , QuestsForNPCs.AbigailThirdQuest.getReward());
        return quests;
    }

    private static Quest[] HarveyQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.HarveyFirstQuest.getRequest()
                , QuestsForNPCs.HarveyFirstQuest.getReward());
        quests[1] = new Quest(QuestsForNPCs.HarveySecondQuest.getRequest()
                , QuestsForNPCs.HarveySecondQuest.getReward());
        quests[2] = new Quest(QuestsForNPCs.HarveyThirdQuest.getRequest()
                , QuestsForNPCs.HarveyThirdQuest.getReward());
        return quests;
    }

    private static Quest[] LiaQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.LiaFirstQuest.getRequest()
                , QuestsForNPCs.LiaFirstQuest.getReward());
        quests[1] = new Quest(QuestsForNPCs.LiaSecondQuest.getRequest()
                , QuestsForNPCs.LiaSecondQuest.getReward());
        quests[2] = new Quest(QuestsForNPCs.LiaThirdQuest.getRequest()
                , QuestsForNPCs.LiaThirdQuest.getReward());
        return quests;
    }

    private static Quest[] RobbinQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.RobbinFirstQuest.getRequest()
                , QuestsForNPCs.RobbinFirstQuest.getReward());
        quests[1] = new Quest(QuestsForNPCs.RobbinSecondQuest.getRequest()
                , QuestsForNPCs.RobbinSecondQuest.getReward());
        quests[2] = new Quest(QuestsForNPCs.RobbinThirdQuest.getRequest()
                , QuestsForNPCs.RobbinThirdQuest.getReward());
        return quests;
    }

    private static Quest[] SebastianQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.SebastianFirstQuest.getRequest()
                , QuestsForNPCs.SebastianFirstQuest.getReward());
        quests[1] = new Quest(QuestsForNPCs.SebastianSecondQuest.getRequest()
                , QuestsForNPCs.SebastianSecondQuest.getReward());
        quests[2] = new Quest(QuestsForNPCs.SebastianThirdQuest.getRequest()
                , QuestsForNPCs.SebastianThirdQuest.getReward());
        return quests;
    }

}
