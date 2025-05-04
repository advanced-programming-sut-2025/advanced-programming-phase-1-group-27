package org.example.models.enums;

import org.example.models.Building;
import org.example.models.Map.FarmMap;
import org.example.models.Item;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.Quest;
import org.example.models.Relations.Relation;
import org.example.models.enums.Plants.FruitType;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.products.AnimalProduct;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;

public enum NPCType {
    Sebastian("Sebastian", null, SebastianQuests(), Features.Angry, SebastianFavouriteItems()),
    Abigail("Abigail", null, AbigailQuests(), Features.Sad, AbigailFavouriteItems()),
    Harvey("Harvey", null, HarveyQuests(), Features.Happy, HarveyFavouriteItems()),
    Lia("Lia", null, LiaQuests(), Features.Lazy, LiaFavouriteItems()),
    Robbin("Robbin", null, RobbinQuests(), Features.Scared, RobbinFavouriteItems()),
    Clint("Clint", Shops.Blacksmith, null, Features.Anxious, ClinicFavouriteItems()),
    Pierre("Pierre", Shops.PierreGeneralStore, null, Features.Angry, PierreFavouriteItems()),
    Robin("Robin", Shops.CarpenterShop, null, Features.Happy, RobinFavouriteItems()),
    Willy("Willy", Shops.FishShop, null, Features.Sad, WillyFavouriteItems()),
    Marnie("Marnie", Shops.MarnieShop, null, Features.Lazy, MarnieFavouriteItems()),
    Morris("Morris", Shops.JojaMart, null, Features.Sad, MorrisFavouriteItems()),
    Gus("Gus", Shops.TheStardropSaloon, null, Features.Happy, GusFavouriteItems());

    private String name;
    private Shops job;
    private Quest[] Quests;
    private Features features;
    private ArrayList<Item> favorite;
    private Building home;
    private FarmMap placeOfShop;

    NPCType(String name, Shops job , Quest[] quests, Features features, ArrayList<Item> favorite) {
        this.name = name;
        this.job = job;
        Quests = quests;
        this.features = features;
        this.favorite = favorite;
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

    private static ArrayList<Item> SebastianFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(AnimalProduct.Wool);
        favouriteItems.add(CookingProduct.PumpkinPie);
        favouriteItems.add(CookingProduct.Pizza);
        return favouriteItems;
    }

    private static ArrayList<Item> AbigailFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(MineralType.Stone);
        favouriteItems.add(MineralType.IronOre);
        favouriteItems.add(ProcessedProductType.Coffee);
        return favouriteItems;
    }

    private static ArrayList<Item> HarveyFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(ProcessedProductType.Coffee);
        favouriteItems.add(ProcessedProductType.Pickle);
        favouriteItems.add(ProcessedProductType.Wine);
        return favouriteItems;
    }

    private static ArrayList<Item> LiaFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(CookingProduct.Salad);
        favouriteItems.add(FruitType.Grape);
        favouriteItems.add(ProcessedProductType.Wine);
        return favouriteItems;
    }

    private static ArrayList<Item> RobbinFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(CookingProduct.Spaghetti);
        favouriteItems.add(MineralType.Wood);
        favouriteItems.add(ProcessedProductType.IronMetalBar);
        return favouriteItems;
    }

    private static ArrayList<Item> ClinicFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(MineralType.IronOre);
        favouriteItems.add(ProcessedProductType.CopperMetalBar);
        favouriteItems.add(ProcessedProductType.Coal);
        return favouriteItems;
    }

    private static ArrayList<Item> PierreFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(CookingProduct.Bread);
        favouriteItems.add(CookingProduct.FriedEgg);
        favouriteItems.add(FruitType.Apple);
        return favouriteItems;
    }

    private static ArrayList<Item> RobinFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(FruitType.PowderMelon);
        favouriteItems.add(FruitType.AncientFruit);
        favouriteItems.add(FruitType.Eggplant);
        return favouriteItems;
    }

    private static ArrayList<Item> WillyFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(AnimalProduct.DinosaurEgg);
        favouriteItems.add(AnimalProduct.RabbitLeg);
        favouriteItems.add(AnimalProduct.LargeGoatMilk);
        return favouriteItems;
    }

    private static ArrayList<Item> MarnieFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(CookingProduct.Pizza);
        favouriteItems.add(ProcessedProductType.Cheese);
        favouriteItems.add(ProcessedProductType.Cloth);
        return favouriteItems;
    }

    private static ArrayList<Item> MorrisFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(ProcessedProductType.DinosaurMayonnaise);
        favouriteItems.add(CookingProduct.Cookie);
        favouriteItems.add(FruitType.Banana);
        return favouriteItems;
    }

    private static ArrayList<Item> GusFavouriteItems() {
        ArrayList<Item> favouriteItems = new ArrayList<>();
        favouriteItems.add(FruitType.Pomegranate);
        favouriteItems.add(CookingProduct.SurvivalBurger);
        favouriteItems.add(CookingProduct.HashBrowns);
        return favouriteItems;
    }
}
