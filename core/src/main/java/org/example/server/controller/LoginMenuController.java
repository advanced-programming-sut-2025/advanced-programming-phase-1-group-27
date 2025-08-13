package org.example.server.controller;

import org.example.common.models.User;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.connections.ClientConnectionThread;

import java.util.HashMap;

public class LoginMenuController {

    public static Message login(Message loginMessage, ClientConnectionThread clientConnectionThread) {
        String username = loginMessage.getFromBody("username");
        String password = loginMessage.getFromBody("password");

        User user = ServerApp.getUserByUsername(username);
        if (user == null) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Username not found!"));
            }} , Message.Type.response);
        }

        if(!user.passwordEquals(password)){
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Passwords does not match!"));
            }} , Message.Type.response);
        }
        clientConnectionThread.setUser(user);
        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo("You have successfully logged in."
                    , false));
        }} , Message.Type.response);
    }
}
