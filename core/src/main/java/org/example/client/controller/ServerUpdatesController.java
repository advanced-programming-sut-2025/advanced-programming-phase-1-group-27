package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.ClientApp;
import org.example.client.model.PopUpTexture;
import org.example.client.model.Reaction;
import org.example.client.view.InteractionMenus.PreTradeMenuView;
import org.example.client.view.InteractionMenus.StartTradeView;
import org.example.client.view.InteractionMenus.TradeView;
import org.example.client.view.InteractionMenus.MarriageRequestView;
import org.example.client.view.OutsideView;
import org.example.client.view.VoteView;
import org.example.common.models.*;
import org.example.server.models.Cell;
import org.example.server.models.Item;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.Shops.Shop;
import org.example.server.models.Stacks;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.ShopType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;

import java.util.HashMap;
import java.util.Random;

import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.Tree;
import org.example.server.models.enums.items.ShopItems;
import org.example.server.models.tools.Backpack;

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
            if (ClientApp.getCurrentMenu() instanceof OutsideView outsideView
                    && !(ClientApp.getCurrentGame().getCurrentPlayer().getCurrentMap() instanceof NPCMap)
                    && ClientApp.getNonMainMenu() == null) {

                outsideView.displayThorAnimation(x, y);

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
            if (ClientApp.getCurrentMenu() instanceof OutsideView) {
                String username = message.getFromBody("starter");
                Gdx.app.postRunnable(() -> {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new StartTradeView(username));
                });
            } else {
                TradeController controller = new TradeController();
                controller.decline(message);
            }
        } else if (mode.equals("respondToStartTrade")) {
            if (ClientApp.getNonMainMenu() instanceof PreTradeMenuView preTradeMenuView)
                preTradeMenuView.getController().checkRespondToStart(message);
        } else if (mode.equals("updateSelected")) {
            if (ClientApp.getNonMainMenu() instanceof TradeView tradeView)
                tradeView.setSelectedOther(tradeView.getController().updateSelected(message));
        } else if (mode.equals("suggestTrade")) {
            if (ClientApp.getNonMainMenu() instanceof TradeView tradeView)
                tradeView.setTradeDoneByStarterSide(true);
        } else if (mode.equals("confirmTrade")) {
            if (ClientApp.getNonMainMenu() instanceof TradeView tradeView) {
                tradeView.getController().checkConfirmation(message);
            }
        } else if (mode.equals("sendInventory")) {
            if (ClientApp.getNonMainMenu() instanceof TradeView tradeView) {
                tradeView.setOnScreenItems(new Backpack(message.<LinkedTreeMap<String, Object>>getFromBody("inventoryInfo")).getItems());
            }
        } else if (mode.equals("gift")) {
            handleGift(message);
        } else if (mode.equals("flower")) {
            handleFlower(message);
        } else if (mode.equals("hug")) {
            handleHug(message);
        } else {
            throw new UnsupportedOperationException(mode + " hasn't been handled");
        }
    }

    private static void handleGift(Message message) {
        String giver = message.getFromBody("starter");
        Stacks gift = new Stacks(message.getFromBody("gift"));
        ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(gift.getItem(), gift.getStackLevel(), gift.getQuantity());
        // TODO : sobhan ya parsa, gift ro handle konid
    }

    private static void handleFlower(Message message) {
        String giver = message.getFromBody("starter");
        ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(ShopItems.Bouquet , StackLevel.Basic , 1);
        // TODO : sobhan ya parsa, gol bedahid
    }

    private static void handleHug(Message message) {
        String giver = message.getFromBody("starter");
        // TODO : sobhan ya parsa, hug konid
    }

    public static void handleVote(Message message) {
        String mode = message.getFromBody("mode");
        if (mode.equals("askToTerminate")) {
            Gdx.app.postRunnable(() -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new VoteView(mode, ""));
            });
        } else if (mode.equals("terminateGame")) {
            ClientApp.terminateGame();
        } else if (mode.equals("askToKick")) {
            Gdx.app.postRunnable(() -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new VoteView(mode, message.getFromBody("playerName")));
            });
        } else if (mode.equals("kickPlayer")) {
            ClientApp.getCurrentGame().kickPlayer(message.getFromBody("playerName"));
        }
    }

    public static void handleChat(Message message) {
        String messageText = message.getFromBody("message");
        ClientApp.getCurrentGame().getCurrentPlayer().addToChatInbox(messageText);
    }

    public static Message getMusicOffset() {
        Music music = ClientApp.getCurrentGame().getCurrentMusic();
        float offset = 0f;
        if (music == null)
            offset = Float.MAX_VALUE;
        else
            offset = music.getPosition();
        float finalOffset = offset;
        return new Message(new HashMap<>() {{
            put("offset", finalOffset);
        }}, Message.Type.response);
    }

    public static void handleMarriageRequest(Message message) {
        String proposer = message.getFromBody("self");
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MarriageRequestView(proposer));
        });
    }

    public static void handleMarriageResponse(Message message) {
        boolean answer = message.getFromBody("answer");
        // TODO: parsa, inja javab behet mirese
        if (answer) {
            //animation for marriage
            ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().reduceItems(ShopItems.WeddingRing, 1);
        } else {
            //animation for reject
        }
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            OutsideView newOutsideView = new OutsideView();
            ClientApp.setNonMainMenu(newOutsideView);
            Main.getMain().setScreen(newOutsideView);
        });
    }

    public static void handleReaction(Message message) {
        String playerName = message.getFromBody("username");
        Reaction reaction = new Reaction(message.<LinkedTreeMap<String, Object>>getFromBody("reaction"));
        // TODO : sobhan, in gooy o in meydan

        Gdx.app.postRunnable(() -> {
            for (OtherPlayerController otherPlayerController : ClientApp.getCurrentGame().getOtherPlayerControllers()) {
                if (otherPlayerController.getUsername().equals(playerName)) {
                    if (reaction.isEmoji()) {
                        PopUpController.addPopUp(
                                new PopUpTexture(
                                        ((TextureRegionDrawable) reaction.getEmoji().getEmojiImage().getDrawable())
                                                .getRegion().getTexture(),
                                        otherPlayerController.getX(), otherPlayerController.getY() + 70,
                                        otherPlayerController.getX(), otherPlayerController.getY() + 70,
                                        2
                                ));
                    } else {
                        InfoWindow infoWindow = new InfoWindow(
                                GameAssetManager.getGameAssetManager().getSkin().getFont("font"),
                                reaction.getText(),
                                Color.BLACK,
                                200,
                                Align.center,
                                true
                        );
                        infoWindow.setPosition(otherPlayerController.getX(), otherPlayerController.getY() + 70);
                        infoWindow.setFontScale(0.7f);
                        PopUpController.addInfoWindow(infoWindow);
                    }
                }
            }
            if (ClientApp.getCurrentGame().getCurrentPlayer().getUsername().equals(playerName)) {
                if (ClientApp.getCurrentMenu() instanceof OutsideView view) {
                    if (reaction.isEmoji()) {
                        PopUpController.addPopUp(
                                new PopUpTexture(
                                        ((TextureRegionDrawable) reaction.getEmoji().getEmojiImage().getDrawable())
                                                .getRegion().getTexture(),
                                        view.getPlayerController().getX(), view.getPlayerController().getY() + 70,
                                        view.getPlayerController().getX(), view.getPlayerController().getY() + 70,
                                        2
                                ));
                    }
                    else {
                        InfoWindow infoWindow = new InfoWindow(
                                GameAssetManager.getGameAssetManager().getSkin().getFont("font"),
                                reaction.getText(),
                                Color.BLACK,
                                200,
                                Align.left,
                                true
                        );
                        infoWindow.setPosition(view.getPlayerController().getX(), view.getPlayerController().getY() + 30);
                        infoWindow.setFontScale(0.7f);
                        PopUpController.addInfoWindow(infoWindow);
                    }
                }
            }
        });
    }
}
