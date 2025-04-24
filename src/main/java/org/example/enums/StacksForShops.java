package org.example.enums;

import org.example.enums.items.*;
import org.example.enums.items.products.ProcessedProduct;

import java.util.HashMap;
import java.util.Map;

public enum StacksForShops {
    Blacksmith(),
    CarpenterShop(NPCType.Morris , 9 , 23),
    FishShop(NPCType.Pierre , 9 , 17),
    JojaMart(NPCType.Robin , 9 , 20),
    MarnieShop(),
    PierreGeneralStore(),
    TheStardropSaloon();

    private Map<Item, Integer> BlacksmithStorePrices(){
        Map<Item ,  Integer> BlacksmithStore = new HashMap<>();
        BlacksmithStore.put(MineralType.CopperOre , 75);
        BlacksmithStore.put(MineralType.IronOre , 150);
        BlacksmithStore.put(ProcessedProduct.Coal, 150);
        BlacksmithStore.put(MineralType.GoldOre , 400);
        return BlacksmithStore;
    }

    private Map<Item , Integer> BlacksmithStoreLimits(){
        Map<Item ,  Integer> BlacksmithStore = new HashMap<>();
        BlacksmithStore.put(MineralType.CopperOre , -1);
        BlacksmithStore.put(MineralType.IronOre , -1);
        BlacksmithStore.put(ProcessedProduct.Coal, -1);
        BlacksmithStore.put(MineralType.GoldOre , -1);
        return BlacksmithStore;
    }

    private Map<Item , Integer> MarnieShopPrices(){
        Map<Item ,  Integer> MarnieShop = new HashMap<>();
        MarnieShop.put(CropType.Hay , 50);
        MarnieShop.put(ToolType.MilkPail , 1000);
        MarnieShop.put(ToolType.Shear , 1000);
        return MarnieShop;
    }

    private Map<Item , Integer> MarnieShopLimits(){
        Map<Item ,  Integer> MarnieShop = new HashMap<>();
        MarnieShop.put(CropType.Hay , -1);
        MarnieShop.put(ToolType.MilkPail , 1);
        MarnieShop.put(ToolType.Shear , 1);
        return MarnieShop;
    }

    private Map<Item , Integer> PierreGeneralStorePrices(){
        Map<Item ,  Integer> PierreGeneralStore = new HashMap<>();

    }

}
