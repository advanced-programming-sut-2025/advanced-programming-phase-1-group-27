package org.example.client.controller;

import org.example.client.model.ClientApp;
import org.example.client.model.ClientGame;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.Cell;
import org.example.server.models.Item;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.Weathers.Weather;

import java.util.Random;

public class ServerUpdatesController { // handles updates sent by server
    public static void updateShopStock(Message message) {
        Shop shop = ClientApp.getCurrentGame().getShop(message.getFromBody("shopName"));
        Item item = ItemManager.getItemByName(message.getFromBody("itemName"));
        int quantity = message.getIntFromBody("quantity");
        shop.reduce(item, quantity);
    }

    public static void passAnHour() {
        ClientApp.getCurrentGame().getTime().passAnHour();
    }

    private static void applyThor() {
        Cell[][] cells = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap().getCells();
        for (int i = 0; i < 3; i++) {
            int x = (new Random()).nextInt(cells.length);
            int y = (new Random()).nextInt(cells[0].length);
            cells[x][y].thor();
        }
    }

    private static void applyRain() {
        Cell[][] cells = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap().getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getObject() instanceof Plant && cells[i][j].getBuilding() == null) {
                    Plant plant = (Plant) cells[i][j].getObject();
                    plant.water();
                }
            }
        }
    }

    public static void applyWeatherAffect(Weather weather) {

    }

    public static void cheatAdvanceTime(Message message) {
        if (message.getFromBody("unit").equals("hour"))
            ClientApp.getCurrentGame().getTime().cheatAdvanceTime(message.getIntFromBody("value"));
        else if (message.getFromBody("unit").equals("day"))
            ClientApp.getCurrentGame().getTime().cheatAdvanceDate(message.getIntFromBody("value"));
    }
}
