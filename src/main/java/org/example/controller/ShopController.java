package org.example.controller;

import org.example.models.Result;
import org.example.models.enums.Shops;

public class ShopController {
    //TODO : Bayad baad az vorood avaz shavad
    //TODO : Bayad baad az khooroj null shavad
    static Shops shop;

    public Result buildBuilding(String buildingName, int x, int y) {
        // TODO: function incomplete
        if(shop != Shops.CarpenterShop){
            return new Result(false , "You can do this only in CarpenterShop");
        }

        return null;
    }

    public Result buyAnimal(String animalType, String animalName) {
        // TODO: function incomplete
        if(shop != Shops.MarnieShop){
            return new Result(false , "You can do this only in MarnieShop");
        }

        return null;
    }


}
