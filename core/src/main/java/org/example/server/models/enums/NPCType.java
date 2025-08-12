package org.example.server.models.enums;

import org.example.server.models.Cell;
import org.example.server.models.Item;
import org.example.server.models.NPCs.Quest;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.items.MineralType;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;

public enum NPCType {
    Abigail("Abigail", AbigailQuests(), Features.Sad, AbigailFavouriteItems(),
            "NPCs/Other/Abigail.png", 10, 19),
    Harvey("Harvey", HarveyQuests(), Features.Happy, HarveyFavouriteItems(),
            "NPCs/Other/Harvey.png", 10, 18),
    Lia("Lia", LiaQuests(), Features.Lazy, LiaFavouriteItems(),
            "NPCs/Other/Lia.png", 12, 20),
    Robbin("Robbin", RobbinQuests(), Features.Scared, RobbinFavouriteItems(),
            "NPCs/Other/Robbin.png", 10, 16),
    Sebastian("Sebastian", SebastianQuests(), Features.Angry, SebastianFavouriteItems(),
            "NPCs/Other/Sebastian.png", 11, 16),
    Clint("Clint", null, Features.Anxious, ClinicFavouriteItems(),
            "NPCs/Shop/Clint.png", 0,0 ),
    Pierre("Pierre", null, Features.Angry, PierreFavouriteItems(),
            "NPCs/Shop/Pierre.png", 0, 0),
    Robin("Robin", null, Features.Happy, RobinFavouriteItems(),
            "NPCs/Shop/Robin.png", 0, 0),
    Willy("Willy", null, Features.Sad, WillyFavouriteItems(),
            "NPCs/Shop/Willy.png", 0,0 ),
    Marnie("Marnie", null, Features.Lazy, MarnieFavouriteItems(),
            "NPCs/Shop/Marnie.png", 0,0 ),
    Morris("Morris", null, Features.Sad, MorrisFavouriteItems(),
            "NPCs/Shop/Morris.png", 0,0 ),
    Gus("Gus", null, Features.Happy, GusFavouriteItems(),
            "NPCs/Shop/Gus.png", 0,0 ),
    ;

    private final String name;
    private final Quest[] Quests;
    private final Features features;
    private final ArrayList<Item> favorite;
    private Cell StandingCell;
    private String address;
    private final int startDayTime, wonderOffTime;

    NPCType(String name, Quest[] quests, Features features, ArrayList<Item> favorite, String address, int startDayTime, int wonderOffTime) {
        this.name = name;
        this.Quests = quests;
        this.features = features;
        this.favorite = favorite;
        this.address = address;
        this.startDayTime = startDayTime;
        this.wonderOffTime = wonderOffTime;
    }

    private static Quest[] AbigailQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.AbigailFirstQuest.getRequest()
                , QuestsForNPCs.AbigailFirstQuest.getReward(), 0);
        quests[1] = new Quest(QuestsForNPCs.AbigailSecondQuest.getRequest()
                , QuestsForNPCs.AbigailSecondQuest.getReward(), 1);
        quests[2] = new Quest(QuestsForNPCs.AbigailThirdQuest.getRequest()
                , QuestsForNPCs.AbigailThirdQuest.getReward(), 2);
        return quests;
    }

    private static Quest[] HarveyQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.HarveyFirstQuest.getRequest()
                , QuestsForNPCs.HarveyFirstQuest.getReward(), 0);
        quests[1] = new Quest(QuestsForNPCs.HarveySecondQuest.getRequest()
                , QuestsForNPCs.HarveySecondQuest.getReward(), 1);
        quests[2] = new Quest(QuestsForNPCs.HarveyThirdQuest.getRequest()
                , QuestsForNPCs.HarveyThirdQuest.getReward() , 2);
        return quests;
    }

    private static Quest[] LiaQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.LiaFirstQuest.getRequest()
                , QuestsForNPCs.LiaFirstQuest.getReward() , 0);
        quests[1] = new Quest(QuestsForNPCs.LiaSecondQuest.getRequest()
                , QuestsForNPCs.LiaSecondQuest.getReward() , 1);
        quests[2] = new Quest(QuestsForNPCs.LiaThirdQuest.getRequest()
                , QuestsForNPCs.LiaThirdQuest.getReward() , 2);
        return quests;
    }

    private static Quest[] RobbinQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.RobbinFirstQuest.getRequest()
                , QuestsForNPCs.RobbinFirstQuest.getReward() , 0);
        quests[1] = new Quest(QuestsForNPCs.RobbinSecondQuest.getRequest()
                , QuestsForNPCs.RobbinSecondQuest.getReward() , 1);
        quests[2] = new Quest(QuestsForNPCs.RobbinThirdQuest.getRequest()
                , QuestsForNPCs.RobbinThirdQuest.getReward() , 2);
        return quests;
    }

    private static Quest[] SebastianQuests() {
        Quest[] quests = new Quest[3];
        quests[0] = new Quest(QuestsForNPCs.SebastianFirstQuest.getRequest()
                , QuestsForNPCs.SebastianFirstQuest.getReward() , 0);
        quests[1] = new Quest(QuestsForNPCs.SebastianSecondQuest.getRequest()
                , QuestsForNPCs.SebastianSecondQuest.getReward() , 1);
        quests[2] = new Quest(QuestsForNPCs.SebastianThirdQuest.getRequest()
                , QuestsForNPCs.SebastianThirdQuest.getReward() , 2);
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

    public String getName() {
        return name;
    }

    public ShopType getJob() {
        for (ShopType shopType : ShopType.values()) {
            if (shopType.getManager() == this) {
                return shopType;
            }
        }
        return null;
    }

    public Quest[] getQuests() {
        return Quests;
    }

    public Features getFeatures() {
        return features;
    }

    public ArrayList<Item> getFavorite() {
        return favorite;
    }

    public Cell getStandingCell() {
        return StandingCell;
    }

    public void setStandingCell(Cell standingCell) {
        StandingCell = standingCell;
    }

    public String getAddress() {
        return address;
    }

    public int getStartDayTime() {
        return startDayTime;
    }

    public int getWonderOffTime() {
        return wonderOffTime;
    }
}
