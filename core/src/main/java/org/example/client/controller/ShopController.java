package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.Item;
import org.example.server.models.Shops.Shop;

public class ShopController { // handles updates sent by server
    public static void updateShopStock(Message message) {
        Shop shop = ClientApp.getCurrentGame().getShop(message.getFromBody("shopName"));
        Item item = ItemManager.getItemByName(message.getFromBody("itemName"));
        int quantity = message.getIntFromBody("quantity");
        shop.reduce(item, quantity);
    }
}
