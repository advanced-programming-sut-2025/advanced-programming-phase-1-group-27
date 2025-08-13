package org.example.server.controller;

import org.example.common.models.Message;
import org.example.common.models.Lobby;
import org.example.server.models.ServerApp;

public class TimeController {
    public static void passAnHour(Lobby lobby) {
        lobby.notifyAll(new Message(null, Message.Type.pass_an_hour));
    }

    public static void cheatAdvanceTime(Message message) {
        Lobby lobby = ServerApp.getLobbyById(message.getIntFromBody("lobbyId"));
        assert lobby != null;
        if (message.getFromBody("unit").equals("hour"))
            lobby.getGame().getTime().cheatAdvanceTime(message.getIntFromBody("value"));
        else if (message.getFromBody("unit").equals("day"))
            lobby.getGame().getTime().cheatAdvanceDate(message.getIntFromBody("value"));
    }
}
