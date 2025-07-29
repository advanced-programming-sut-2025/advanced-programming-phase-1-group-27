package org.example.server.controller;

import org.example.common.models.GraphicalResult;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.Shops.Shop;

import java.util.HashMap;

public class ShopController {
    public static Message purchase(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        Game game = lobby.getGame();
        Shop shop = game.getShop(message.getFromBody("shopName"));
        assert shop != null;
        Item item = ItemManager.getItemByName(message.getFromBody("itemName"));
        assert item != null;
        int quantity = message.getIntFromBody("quantity");
        if (!shop.hasEnough(item, quantity))
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Sorry! we are out of stock."));
            }}, Message.Type.response);
        updateShopStock(lobby, item, quantity, shop);
        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo(
                    "You have successfully purchased " + quantity + " of " + item.getName() + ".",
                    false));
        }}, Message.Type.response);
    }

    private static void updateShopStock(Lobby lobby, Item item, int quantity, Shop shop) {
        shop.reduce(item, quantity);
        lobby.notifyAll(new Message(new HashMap<>() {{
            put("shopName", shop.getShopType().name());
            put("itemName", item.getName());
            put("quantity", quantity);
        }}, Message.Type.update_shop));
    }
}
