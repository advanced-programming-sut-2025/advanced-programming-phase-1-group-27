package org.example.models.enums;

import org.example.models.Ingredient;
import org.example.models.Stacks;
import org.example.models.enums.Plants.CropType;
import org.example.models.enums.Plants.FruitType;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

public enum QuestsForNPCs {
    SebastianFirstQuest(new Stacks(MineralType.IronOre , 50) , new Stacks(MineralType.Diamond , 2)),
    SebastianSecondQuest(new Stacks(CookingProduct.PumpkinPie , 1) , new Stacks(ShopItems.Coin , 5_000)),
    SebastianThirdQuest(new Stacks(MineralType.Stone , 150) , new Stacks(MineralType.Quartz , 50)),
    // +1 FriendShip
    AbigailFirstQuest(new Stacks(ProcessedProductType.GoldMetalBar , 1) , null),
    AbigailSecondQuest(new Stacks(FruitType.Pumpkin , 1) , new Stacks(ShopItems.Coin , 500)),
    AbigailThirdQuest(new Stacks(FruitType.Wheat , 50) , null),
    HarveyFirstQuest(null , new Stacks(ShopItems.Coin , 750)),
    HarveySecondQuest(new Stacks(FishType.Salmon , 1) , null),
    HarveyThirdQuest(new Stacks(ProcessedProductType.Wine , 1) , new Stacks(CookingProduct.Salad , 5)),
    LiaFirstQuest(new Stacks(MineralType.Wood , 10) , new Stacks(ShopItems.Coin , 500)),
    // Recipe
    LiaSecondQuest(new Stacks(FishType.Salmon , 1) , new Stacks(Recipe.SalmonDinnerRecipe , 1)),
    LiaThirdQuest(new Stacks(MineralType.Wood , 200) , new Stacks(CraftingProduct.Scarecrow , 3)),
    RobbinFirstQuest(new Stacks(MineralType.Wood , 80) , new Stacks(ShopItems.Coin , 1000)),
    RobbinSecondQuest(new Stacks(ProcessedProductType.IronMetalBar , 10) ,
            new Stacks(CraftingProduct.BeeHouse , 3)),
    RobbinThirdQuest(new Stacks(MineralType.Wood , 1000) , new Stacks(ShopItems.Coin , 25000)),;

    private final Stacks request;
    private final Stacks reward;

    QuestsForNPCs(Stacks request, Stacks reward) {
        this.request = request;
        this.reward = reward;
    }

    public Stacks getReward() {
        return reward;
    }

    public Stacks getRequest() {
        return request;
    }
}
