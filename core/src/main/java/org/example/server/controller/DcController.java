package org.example.server.controller;

import org.example.common.database.DataBaseHelper;
import org.example.common.models.Lobby;
import org.example.common.models.Message;
import org.example.common.models.User;
import org.example.server.models.ServerApp;
import org.example.server.models.connections.ClientConnectionThread;

import java.util.HashMap;

public class DcController {
    public static void handleDC(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        String playerName = message.getFromBody("playerName");
        lobby.addDcPlayer(playerName);
        new Thread(() -> {
            try {
                boolean reconnect = false;
                for (int i = 0; i < 60; i++) {
                    Thread.sleep(2000);
                    if (!lobby.isPlayerDC(playerName)) {
                        reconnect = true;
                        break;
                    }
                }
                if (reconnect) {
                    SaveController.sendInfo(lobby, playerName);
                }
                else {
                    DataBaseHelper.saveTimeAndWeather(lobby, lobby.getGame().getTime(), lobby.getGame().getCurrentWeather());
                    DataBaseHelper.saveGiftsAndTrades(lobby, lobby.getGame().getGifts(), lobby.getGame().getTrades());
                    lobby.getGame().pause();
                    lobby.clearDCPlayers();
                    lobby.notifyAll(new Message(null, Message.Type.save_and_exit_game));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    public static void handleNewLogin(User user) {
        for (Lobby lobby : ServerApp.getLobbies()) {
            if (lobby.isPlayerDC(user.getUsername()))
                lobby.reconnectPlayer(user.getUsername());
        }
    }
}
