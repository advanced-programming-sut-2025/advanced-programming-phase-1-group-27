package org.example.client.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.InteractionsWithOthers.TradeController;
import org.example.client.model.*;
import org.example.client.view.HomeView;
import org.example.client.view.InteractionMenus.Trade.PreTradeMenuView;
import org.example.client.view.InteractionMenus.Trade.StartTradeView;
import org.example.client.view.InteractionMenus.Trade.TradeView;
import org.example.client.view.InteractionMenus.MarriageRequestView;
import org.example.client.view.OutsideView;
import org.example.client.view.VoteView;
import org.example.common.models.*;
import org.example.common.models.Map.FarmMap;
import org.example.common.models.Map.NPCMap;
import org.example.common.models.Shops.Shop;
import org.example.common.models.AbilityType;
import org.example.common.models.Plants.Plant;
import org.example.common.models.StackLevel;
import org.example.common.models.Weathers.Weather;

import java.util.HashMap;
import java.util.Random;

import org.example.common.models.Plants.Crop;
import org.example.common.models.Plants.Tree;
import org.example.common.models.items.ShopItems;
import org.example.common.models.tools.Backpack;

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

        if ( ClientApp.getCurrentMenu() instanceof OutsideView outsideView) {

            Sprite itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getGiftIcon());
            itemSprite.setSize(72, 62);

            PopUpController.addPopUp(new PopUpTexture(itemSprite
                    ,outsideView.getPlayerController().getX(),outsideView.getPlayerController().getY()+80,
                    outsideView.getPlayerController().getX(), outsideView.getPlayerController().getY()+20, 4
            ));

        }

        ClientApp.getCurrentGame().getCurrentPlayer().addToChatInbox("You have a new gift \n from " + giver);
    }

    private static void handleFlower(Message message) {
        String giver = message.getFromBody("starter");
        ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().addItems(ShopItems.Bouquet , StackLevel.Basic , 1);
        // TODO : sobhan ya parsa, gol bedahid

        if ( ClientApp.getCurrentMenu() instanceof OutsideView outsideView) {

            Sprite itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getBouquet());
            itemSprite.setSize(72, 62);

            PopUpController.addPopUp(new PopUpTexture(itemSprite
                    ,outsideView.getPlayerController().getX(),outsideView.getPlayerController().getY()+80,
                    outsideView.getPlayerController().getX(), outsideView.getPlayerController().getY()+20, 4
            ));

        }

    }

    private static void handleHug(Message message) {
        String giver = message.getFromBody("starter");
        // TODO : sobhan ya parsa, hug konid

        if ( ClientApp.getCurrentMenu() instanceof OutsideView outsideView) {

            Sprite itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getHugIcon());
            itemSprite.setSize(72, 62);

            float giverX = 0, giverY = 0;

            for( MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers() ) {

                if ( miniPlayer.getUsername().equals(giver) ) {
                    giverX = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getX();
                    giverY = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getY();
                }

            }

            PopUpController.addPopUp(new PopUpTexture(itemSprite
                    ,(outsideView.getPlayerController().getX()+giverX)/2f,(outsideView.getPlayerController().getY()+giverY)/2f,
                    (outsideView.getPlayerController().getX()+giverX)/2f, (outsideView.getPlayerController().getY()+giverY)/2f, 4
            ));

        }

    }

    public static void handleVote(Message message) {
        String mode = message.getFromBody("mode");
        if (mode.equals("askToTerminate")) {
            Gdx.app.postRunnable(() -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new VoteView(mode, ""));
            });
        } else if (mode.equals("terminateGame")) {
            handleTerminate();
        } else if (mode.equals("askToKick")) {
            Gdx.app.postRunnable(() -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new VoteView(mode, message.getFromBody("playerName")));
            });
        } else if (mode.equals("kickPlayer")) {
            ClientApp.getCurrentGame().kickPlayer(message.getFromBody("playerName"));
        }
    }

    private static void handleTerminate() {
        User user = ClientApp.getLoggedInUser();
        user.setMaxMoneyEarned(Math.max(user.getMaxMoneyEarned(), ClientApp.getCurrentGame().getCurrentPlayer().getMoney()));
        user.setNumberOfGamesPlayed(user.getNumberOfGamesPlayed() + 1);
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("userInfo", user.getInfo());
        }}, Message.Type.update_user_info));
        ClientApp.terminateGame();
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
            MarriageRequestView marriageRequestView = new MarriageRequestView(proposer);
            ClientApp.setNonMainMenu(marriageRequestView);
            Main.getMain().setScreen(marriageRequestView);
        });
    }

    public static void handleMarriageResponse(Message message) {
        boolean answer = message.getFromBody("answer");
        String username = message.getFromBody("self");
        Sprite itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getBrokenHeart());
        // TODO: parsa, inja javab behet mirese
        if (answer) {
            itemSprite = new Sprite(GameAssetManager.getGameAssetManager().getWeddingRing());
            ClientApp.getCurrentGame().getCurrentPlayer().getBackpack().reduceItems(ShopItems.WeddingRing, 1);
        }
        Gdx.app.postRunnable(() -> {
            OutsideView newOutsideView = new OutsideView();
            Main.getMain().getScreen().dispose();
            ClientApp.setNonMainMenu(newOutsideView);
            Main.getMain().setScreen(newOutsideView);
            // TODO: inja benevis abdi jan kososherato
        });



        itemSprite.setSize(72, 62);


//        if ( !answer ){
//
//            PopUpController.addPopUp(new PopUpTexture(itemSprite
//                    ,newOutsideView.getPlayerController().getX(),newOutsideView.getPlayerController().getY()+80,
//                    newOutsideView.getPlayerController().getX(), newOutsideView.getPlayerController().getY()+20, 4
//            ));
//
//        }
//        else{
//
//            float giverX = 0, giverY = 0;
//
//            for( MiniPlayer miniPlayer : ClientApp.getCurrentGame().getPlayers() ) {
//
//                if ( miniPlayer.getUsername().equals(username) ) {
//                    giverX = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getX();
//                    giverY = OutsideView.getGraphicalPosition(miniPlayer.getPosition()).getY();
//                }
//
//            }
//
//            PopUpController.addPopUp(new PopUpTexture(itemSprite
//                    ,newOutsideView.getPlayerController().getX(),giverY,
//                    giverX, giverY, 4
//            ));
//
//        }



    }

    public static void handleReaction(Message message) {
        String playerName = message.getFromBody("username");
        Reaction reaction = new Reaction(message.<LinkedTreeMap<String, Object>>getFromBody("reaction"));


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
                                Align.left,
                                true
                        );
                        infoWindow.setPosition(otherPlayerController.getX(), otherPlayerController.getY() + 70);
                        infoWindow.setFontScale(0.7f);
                        PopUpController.addInfoWindow(infoWindow);
                    }
                }
            }
            if (ClientApp.getCurrentGame().getCurrentPlayer().getUsername().equals(playerName)) {
                float playerX = 0, playerY = 0;
                if (ClientApp.getCurrentMenu() instanceof OutsideView view) {
                    playerX = view.getPlayerController().getX();
                    playerY = view.getPlayerController().getY();
                } else if (ClientApp.getCurrentMenu() instanceof HomeView view) {
                    playerX = view.getPlayerController().getX();
                    playerY = view.getPlayerController().getY();
                }
                if (reaction.isEmoji()) {
                    PopUpController.addPopUp(
                            new PopUpTexture(
                                    ((TextureRegionDrawable) reaction.getEmoji().getEmojiImage().getDrawable())
                                            .getRegion().getTexture(),
                                    playerX, playerY + 70,
                                    playerX, playerY + 70,
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
                    infoWindow.setPosition(playerX, playerY + 30);
                    infoWindow.setFontScale(0.7f);
                    PopUpController.addInfoWindow(infoWindow);
                }

            }
        });
    }

    public static void handleMiniPlayerUpdate(Message message) {
        if (ClientApp.getCurrentGame() == null)
            return;
        String mode = message.getFromBody("mode");
        if (mode.equals("ask")) {
            sendPlayerMiniInfo(message);
        }
        else if (mode.equals("response")) {
            updateMiniPlayerInfo(message);
        }
    }

    private static void updateMiniPlayerInfo(Message message) {
        MiniPlayer miniPlayer = ClientApp.getCurrentGame().getMiniPlayerByUsername(message.getFromBody("other"));
        miniPlayer.update(message);
    }

    private static void sendPlayerMiniInfo(Message message) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        ClientApp.getServerConnectionThread().sendMessage(new Message(new HashMap<>() {{
            put("mode", "response");
            put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
            put("starter", message.getFromBody("starter"));
            put("other", message.getFromBody("other"));
            put("self", ClientApp.getLoggedInUser().getUsername());
            put("position", player.getPosition().getInfo());
            put("mapIndex", player.getCurrentMap() instanceof NPCMap? 4 : ClientApp.getCurrentGame().getPlayerMapIndex(player.getUsername()));
            put("money", player.getMoney());
            put("numberOfQuestsCompleted", player.getNumberOfQuestsDone());
            put("totalAbility", player.getAbility(AbilityType.Farming).getLevel() +
                    player.getAbility(AbilityType.Fishing).getLevel() +
                    player.getAbility(AbilityType.Foraging).getLevel() +
                    player.getAbility(AbilityType.Mining).getLevel());
        }}, Message.Type.update_mini_player));
    }
}
