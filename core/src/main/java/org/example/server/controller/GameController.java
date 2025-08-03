package org.example.server.controller;

import org.example.common.models.GraphicalResult;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.Weathers.Weather;

import java.util.HashMap;

import static org.example.server.models.ServerApp.TIMEOUT_MILLIS;

public class GameController {
    public static synchronized void setTomorrowWeather(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        Weather weather = Weather.getWeather(message.getFromBody("weather"));
        assert weather != null;
        lobby.getGame().setTomorrowWeather(weather);
    }

    public static synchronized Message purchase(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        ServerGame serverGame = lobby.getGame();
        Shop shop = serverGame.getShop(message.getFromBody("shopName"));
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

    public static Message getPlayerInventory(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        String playerUsername = message.getFromBody("username");
        return ServerApp.getClientConnectionThreadByUsername(playerUsername).sendAndWaitForResponse(
                new Message(null, Message.Type.get_player_inventory), TIMEOUT_MILLIS
        );
    }

    private static void updateShopStock(Lobby lobby, Item item, int quantity, Shop shop) {
        shop.reduce(item, quantity);
        lobby.notifyAll(new Message(new HashMap<>() {{
            put("shopName", shop.getShopType().name());
            put("itemName", item.getName());
            put("quantity", quantity);
        }}, Message.Type.update_shop));
    }

    public static void notifyExcept(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        String username = message.getFromBody("username");
        assert username != null;
        for (User user : lobby.getUsers()) {
            if (!user.getUsername().equals(username))
                lobby.notifyUser(user, message);
        }
    }
}
