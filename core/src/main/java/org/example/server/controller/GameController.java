package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.GraphicalResult;
import org.example.common.models.ItemManager;
import org.example.common.models.Message;
import org.example.common.models.MusicInfo;
import org.example.server.models.*;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Relations.Trade;
import org.example.server.models.Shops.Shop;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.tools.Backpack;

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

    public static synchronized Message getPlayerInventory(Message message) {
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

    public static Message getPlayerRelation(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        String username1 = message.getFromBody("username1");
        String username2 = message.getFromBody("username2");
        Player currentPLayer = lobby.getGame().getPlayerByUsername(username1);
        Player otherPLayer = lobby.getGame().getPlayerByUsername(username2);
        assert currentPLayer != null;
        assert otherPLayer != null;
        Relation relation = currentPLayer.getRelations().computeIfAbsent(otherPLayer , k->new Relation());
        return new Message(new HashMap<>() {{
            put("Level" , relation.getLevel());
            put("XP" ,  relation.getXp());
        }} , Message.Type.response);
    }

    public static void handleP2P(Message message) {
        String starter = message.getFromBody("starter");
        String other = message.getFromBody("other");
        String self = message.getFromBody("self");
        String mode = message.getFromBody("mode");
        if(mode.equals("confirmTrade")) {
            // TODO : Rassa Trade
            boolean answer = message.getFromBody("answer");
            int lobbyId = message.getIntFromBody("lobbyId");
            if(answer){
                Trade trade = new Trade(message);
                ServerApp.getLobbies().get(lobbyId).getGame().addTrade(trade);
            }
        }
        ClientConnectionThread connection = ServerApp.getClientConnectionThreadByUsername(
                starter.equals(self)? other : starter
        );
        assert connection != null;
        connection.sendMessage(message);
    }

    public static Message getPlayerMusicInfo(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        String playerName = message.getFromBody("playerName");
        MusicInfo musicInfo = lobby.getGame().getPlayerMusicInfo(playerName);
        return new Message(new HashMap<>() {{
            put("songId", musicInfo.getSongId());
            put("startTime", musicInfo.getStartTime());
        }}, Message.Type.response);
    }

    public static void saveAndExit(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.notifyAll(new Message(null, Message.Type.save_and_exit_game));
        lobby.getGame().pause();
    }

    public static void handleVote(Message message) {
        String mode = message.getFromBody("mode");
        if (mode.equals("askToTerminate")) {
            askToTerminateGame(message);
        }
        else if (mode.equals("voteToTerminate")) {
            checkVoteToTerminate(message);
        }
        else if (mode.equals("askToKick")) {
            askToKick(message);
        }
        else if (mode.equals("voteToKick")) {
            checkVoteToKick(message);
        }
    }

    private static void askToTerminateGame(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.startVote();
        lobby.notifyAll(new Message(new HashMap<>() {{
            put("mode", "askToTerminate");
        }}, Message.Type.voting));
    }

    private static void checkVoteToTerminate(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.vote(message.getFromBody("vote"));
        if (lobby.hasPollWon()) {
            lobby.getGame().terminate();
            lobby.notifyAll(new Message(new HashMap<>() {{
                put("mode", "terminateGame");
            }}, Message.Type.voting));
            ServerApp.removeLobby(lobby);
        }
    }

    private static void askToKick(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.startVote();
        lobby.notifyAll(new Message(new HashMap<>() {{
            put("mode", "askToKick");
        }}, Message.Type.voting));
    }

    private static void checkVoteToKick(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        lobby.vote(message.getFromBody("vote"));
        if (lobby.hasPollWon()) {
            lobby.kickPlayer(message.getFromBody("playerName"));
            lobby.notifyAll(new Message(new HashMap<>() {{
                put("mode", "kickPlayer");
                put("playerName", message.getFromBody("playerName"));
            }}, Message.Type.voting));
        }
    }
}
