package org.example.models.enums;

import org.example.enums.items.*;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.ProcessedProduct;

import java.util.HashMap;
import java.util.Map;

public enum StacksForShops {
    Blacksmith(),
    CarpenterShop(),
    FishShop(),
    JojaMart(),
    MarnieShop(),
    PierreGeneralStore(),
    TheStardropSaloon();

    //Limits are daily and restock every morning
    //-1 means unlimited

//    private Map<Item, Integer> BlacksmithStorePrices(){
//        Map<Item ,  Integer> BlacksmithStore = new HashMap<>();
//        BlacksmithStore.put(MineralType.CopperOre , 75);
//        BlacksmithStore.put(MineralType.IronOre , 150);
//        BlacksmithStore.put(ProcessedProduct.Coal, 150);
//        BlacksmithStore.put(MineralType.GoldOre , 400);
//        return BlacksmithStore;
//    }

    private Map<Item, Integer> BlacksmithStoreLimits() {
        Map<Item, Integer> BlacksmithStore = new HashMap<>();
        BlacksmithStore.put(MineralType.CopperOre, -1);
        BlacksmithStore.put(MineralType.IronOre, -1);
        BlacksmithStore.put(ProcessedProduct.Coal, -1);
        BlacksmithStore.put(MineralType.GoldOre, -1);
        return BlacksmithStore;
    }

//    private Map<Item , Integer> MarnieShopPrices(){
//        Map<Item ,  Integer> MarnieShop = new HashMap<>();
//        MarnieShop.put(CropType.Hay , 50);
//        MarnieShop.put(ToolType.MilkPail , 1000);
//        MarnieShop.put(ToolType.Shear , 1000);
//        MarnieShop.put(AnimalType.Chicken , 800);
//        MarnieShop.put(AnimalType.Cow , 1500);
//        MarnieShop.put(AnimalType.Goat , 4000);
//        MarnieShop.put(AnimalType.Duck , 1200);
//        MarnieShop.put(AnimalType.Sheep , 8000);
//        MarnieShop.put(AnimalType.Rabbit , 8000);
//        MarnieShop.put(AnimalType.Dinosaur , 14000);
//        MarnieShop.put(AnimalType.Pig , 16000);
//        return MarnieShop;
//    }

    private Map<Item, Integer> MarnieShopLimits() {
        Map<Item, Integer> MarnieShop = new HashMap<>();
        MarnieShop.put(CropType.Hay, -1);
        MarnieShop.put(ToolType.MilkPail, 1);
        MarnieShop.put(ToolType.Shear, 1);
        MarnieShop.put(AnimalType.Chicken, 2);
        MarnieShop.put(AnimalType.Cow, 2);
        MarnieShop.put(AnimalType.Goat, 2);
        MarnieShop.put(AnimalType.Duck, 2);
        MarnieShop.put(AnimalType.Sheep, 2);
        MarnieShop.put(AnimalType.Rabbit, 2);
        MarnieShop.put(AnimalType.Dinosaur, 2);
        MarnieShop.put(AnimalType.Pig, 2);
        return MarnieShop;
    }

    private Map<Item, Integer> StardropSaloonLimits() {
        Map<Item, Integer> StardropSaloon = new HashMap<>();
        StardropSaloon.put(ProcessedProduct.Beer, -1);
        StardropSaloon.put(CookingProduct.Salad, -1);
        StardropSaloon.put(CookingProduct.Bread, -1);
        StardropSaloon.put(CookingProduct.Spaghetti, -1);
        StardropSaloon.put(CookingProduct.Pizza, -1);
        StardropSaloon.put(ProcessedProduct.Coffee, -1);
        StardropSaloon.put(RecipeType.HashbrownsRecipe, 1);
        StardropSaloon.put(RecipeType.OmeletteRecipe, 1);
        StardropSaloon.put(RecipeType.PancakeRecipe, 1);
        StardropSaloon.put(RecipeType.BreadRecipe, 1);
        StardropSaloon.put(RecipeType.TortillaRecipe, 1);
        StardropSaloon.put(RecipeType.PizzaRecipe, 1);
        StardropSaloon.put(RecipeType.MakiRollRecipe, 1);
        StardropSaloon.put(RecipeType.TripleShotEspressoRecipe, 1);
        StardropSaloon.put(RecipeType.CookieRecipe, 1);
        return StardropSaloon;
    }

    private Map<Item, Integer> CarpenterShopLimits() {
        Map<Item, Integer> CarpenterShop = new HashMap<>();
        CarpenterShop.put(CropType.Wood, -1);
        CarpenterShop.put(MineralType.Stone, -1);
        CarpenterShop.put(FarmBuildingType.Barn, 1);
        CarpenterShop.put(FarmBuildingType.BigBarn, 1);
        CarpenterShop.put(FarmBuildingType.DeluxeBarn, 1);
        CarpenterShop.put(FarmBuildingType.Coop, 1);
        CarpenterShop.put(FarmBuildingType.BigCoop, 1);
        CarpenterShop.put(FarmBuildingType.DeluxeCoop, 1);
        CarpenterShop.put(FarmBuildingType.Well, 1);
        CarpenterShop.put(FarmBuildingType.ShippingBin, -1);
        return CarpenterShop;
    }

    private Map<Item, Integer> PermanentJojaMartLimits() {
        Map<Item, Integer> JojaMart = new HashMap<>();
        JojaMart.put(ProcessedProduct.)
    }

}
