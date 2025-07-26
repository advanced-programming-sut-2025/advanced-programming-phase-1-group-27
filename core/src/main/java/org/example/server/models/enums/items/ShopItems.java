package org.example.server.models.enums.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.example.server.models.Item;

public enum ShopItems implements Item {
    Bouquet(1000, null , "Items/Shop_Items/Bouquet.png"),
    WeddingRing(10000, null , "Items/Shop_Items/Wedding_Ring.png"),
    DeluxeRetainingSoil(150, null , "Items/Shop_Items/Deluxe_Retaining_Soil.png"),
    SpeedGro(100, null , "Items/Shop_Items/Speed_Gro.png"),
    BasicRetainingSoil(100, null , "Items/Shop_Items/Basic_Retaining_Soil.png"),
    QualityRetainingSoil(150, null , "Items/Shop_Items/Quality_Retaining_Soil.png"),
    JojaCola(75, null , "Items/Shop_Items/Joja_Cola.png"),
    Sugar(125, null , "Items/Shop_Items/Sugar.png"),
    WheatFlour(125, null , "Items/Shop_Items/Wheat_Flour.png"),
    Hay(50, null , "Items/Shop_Items/Hay.png"),
    TroutSoup(250, null , "Items/Shop_Items/Trout_Soup.png"),
    Coin(1, null , "Items/Shop_Items/Coin.png"),
    Rice(250, null , "Items/Shop_Items/Rice.png"),;

    private final int price;
    private final Recipe recipe;
    private final Texture texture;

    ShopItems(int price, Recipe recipe , String address) {
        this.price = price;
        this.recipe = recipe;
        this.texture = new Texture(Gdx.files.internal(address));
    }



    public static ShopItems getItem(String itemName) {
        for (ShopItems shopItems : ShopItems.values()) {
            if (shopItems.getName().equalsIgnoreCase(itemName)) {
                return shopItems;
            }
        }
        return null;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public Integer getPrice() {
        return 0;
    }
}
