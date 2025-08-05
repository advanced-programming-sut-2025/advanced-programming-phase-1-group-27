package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.view.HomeView;
import org.example.client.view.InteractionMenus.PreTradeMenuView;
import org.example.client.view.InteractionMenus.StartTradeView;
import org.example.client.view.InteractionMenus.TradeView;
import org.example.client.view.OutsideView;
import org.example.common.models.Direction;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.server.models.Cell;
import org.example.server.models.Item;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.Weathers.Weather;

import java.util.HashMap;
import java.util.Random;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.Tree;

import java.util.ArrayList;

import static java.lang.Math.min;
import static java.lang.Math.scalb;

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
            if ( ClientApp.getCurrentMenu() instanceof OutsideView outsideView
                    && !(ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap npcMap)) {

                outsideView.displayThorAnimation(x,y);

            }
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
        if (weather == Weather.Stormy) applyThor();
        if (weather == Weather.Stormy || weather == Weather.Rainy) applyRain();
    }

    public static void cheatAdvanceTime(Message message) {
        if (message.getFromBody("unit").equals("hour"))
            ClientApp.getCurrentGame().getTime().cheatAdvanceTime(message.getIntFromBody("value"));
        else if (message.getFromBody("unit").equals("day"))
            ClientApp.getCurrentGame().getTime().cheatAdvanceDate(message.getIntFromBody("value"));
    }

    public static void setWeather(Message message) {
        Weather weather = Weather.getWeather(message.getFromBody("weather"));
        assert weather != null;
        ClientApp.getCurrentGame().setWeather(weather);
        applyWeatherAffect(weather);
    }

    public static void crowsAttack(Message message) {
        FarmMap farmMap = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap();
        ArrayList<Double> attackedPlants = message.getFromBody("attackedPlants");
        ArrayList<Plant> allPlants = farmMap.getAllPlants();
        for (Double plantDouble : attackedPlants) {
            int plantIndex = plantDouble.intValue();
            Plant plant = allPlants.get(plantIndex);
            if (plant.getCell().isProtected())
                continue;
            if (plant instanceof Crop crop) {
                crop.getCell().setObject(null);
            } else if (plant instanceof Tree tree) {
                tree.setTillNextHarvest(min(1, tree.getTillNextHarvest()));
            }
        }
    }

    public static void updateForaging(Message message) {
        FarmMap farmMap = ClientApp.getCurrentGame().getCurrentPlayer().getFarmMap();
        farmMap.addForaging(message.getFromBody("foragingInfo"));
    }

    public static void otherPlayerEnteredNpcMap(Message message) {
        String username = message.getFromBody("username");
        int i = message.getIntFromBody("x"), j = message.getIntFromBody("y");
        ClientApp.getCurrentGame().addOtherPlayer(username, i, j);
    }

    public static void otherPlayerLeftNpcMap(Message message) {
        String username = message.getFromBody("username");
        ClientApp.getCurrentGame().removeOtherPlayer(username);
    }

    public static void otherPlayerWalking(Message message) {
        String username = message.getFromBody("username");
        Direction direction = Direction.getDirection(message.getFromBody("direction"));
        ClientApp.getCurrentGame().walkPlayer(username, direction);
    }

    public static Message getInventory() {
        return new Message(new HashMap<>() {{
            put("inventoryInfo", ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().getInfo());
        }}, Message.Type.response);
    }

    public static void handleP2P(Message message) {
        String mode = message.getFromBody("mode");
        if (mode.equals("startTrade")) {
            if( ClientApp.getCurrentMenu() instanceof OutsideView) {
                String username = message.getFromBody("starter");
                Gdx.app.postRunnable(() -> {
                    Main.getMain().getScreen().dispose();
                    ClientApp.setCurrentMenu(new StartTradeView(username , ClientApp.getCurrentMenu()));
                    Main.getMain().setScreen(ClientApp.getCurrentMenu());
                });
            } else {
                // TODO : Auto decline
            }
        }
        else if (mode.equals("respondToStartTrade")) {
            if (ClientApp.getCurrentMenu() instanceof PreTradeMenuView preTradeMenuView)
                preTradeMenuView.getController().checkRespondToStart(message);
        }
        else if (mode.equals("updateSelected")) {
            if (ClientApp.getCurrentMenu() instanceof TradeView tradeView)
                tradeView.getController().updateSelected(message);
        }
        else if (mode.equals("suggestedTrade")) {
            if (ClientApp.getCurrentMenu() instanceof TradeView tradeView)
                tradeView.getController().getSuggestedTrade(message);
        }
        else if (mode.equals("confirmTrade")) {
            if (ClientApp.getCurrentMenu() instanceof TradeView tradeView)
                tradeView.getController().checkConfirmation(message);
        }
        else {
            throw new UnsupportedOperationException(mode + " hasn't been handled");
        }
    }
}
