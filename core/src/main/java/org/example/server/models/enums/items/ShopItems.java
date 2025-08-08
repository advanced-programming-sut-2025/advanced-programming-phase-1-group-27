package org.example.server.models.enums.items;

import org.example.server.models.Edible;
import org.example.server.models.Item;

public enum ShopItems implements Item, Edible {
    Bouquet(1000, null , "Items/Shop_Items/Bouquet.png", false),
    WeddingRing(10000, null , "Items/Shop_Items/Wedding_Ring.png", false),
    DeluxeRetainingSoil(150, null , "Items/Shop_Items/Deluxe_Retaining_Soil.png", false),
    SpeedGro(100, null , "Items/Shop_Items/Speed_Gro.png", false),
    BasicRetainingSoil(100, null , "Items/Shop_Items/Basic_Retaining_Soil.png", false),
    QualityRetainingSoil(150, null , "Items/Shop_Items/Quality_Retaining_Soil.png", false),
    JojaCola(75, null , "Items/Shop_Items/Joja_Cola.png", true),
    Sugar(125, null , "Items/Shop_Items/Sugar.png", true),
    WheatFlour(125, null , "Items/Shop_Items/Wheat_Flour.png", true),
    Hay(50, null , "Items/Shop_Items/Hay.png", false),
    TroutSoup(250, null , "Items/Shop_Items/Trout_Soup.png", true),
    Coin(1, null , "Items/Shop_Items/Coin.png", false),
    Rice(250, null , "Items/Shop_Items/Rice.png", true),;

    private final int price;
    private final Recipe recipe;
    private final String address;
    private final boolean isEdible;

    ShopItems(int price, Recipe recipe , String address, boolean isEdible) {
        this.price = price;
        this.recipe = recipe;
        this.address = address;
        this.isEdible = isEdible;
    }



    public static ShopItems getItem(String itemName) {
        for (ShopItems shopItems : ShopItems.values()) {
            if (shopItems.getName().equalsIgnoreCase(itemName)) {
                return shopItems;
            }
        }
        return null;
    }


    public String getAddress() {
        return this.address;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }

    @Override
    public boolean isEdible() {
        return isEdible;
    }

    @Override
    public int getEnergy() {
        return 0;
    }
}
