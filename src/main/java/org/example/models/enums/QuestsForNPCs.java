package org.example.models.enums;

import org.example.models.Stacks;
import org.example.models.enums.items.CropType;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.ShopItems;
import org.example.models.enums.items.ToolType;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

public enum QuestsForNPCs {
    SebastianFirstQuest(new Stacks(MineralType.IronOre , 50) , new Stacks(MineralType.Diamond , 2)),
    SebastianSecondQuest(new Stacks(CookingProduct.PumpkinPie , 1) , new Stacks(ShopItems.Coin , 5_000)),
    SebastianThirdQuest(new Stacks(MineralType.Stone , 150) , new Stacks(MineralType.Quartz , 50)),
    // +1 FriendShip
    AbigailFirstQuest(new Stacks(ProcessedProductType.GoldMetalBar , 1) , null),
    AbigailSecondQuest(new Stacks(CropType.Pumpkin , 1) , new Stacks(ShopItems.Coin , 500)),
    AbigailThirdQuest(new Stacks(CropType.Wheat , 50) , new Stacks(ToolType.IridiumWateringCan , 1)),
    HarveyFirstQuest()),
    HarveySecondQuest(),
    HarveyThirdQuest(),
    LiaFirstQuest(),
    LiaSecondQuest(),
    LiaThirdQuest(),
    RobbinFirstQuest(),
    RobbinSecondQuest(),
    RobbinThirdQuest();

    private final Stacks request;
    private final Stacks reward;


    QuestsForNPCs(Stacks request, Stacks reward) {
        this.request = request;
        this.reward = reward;
    }
}
