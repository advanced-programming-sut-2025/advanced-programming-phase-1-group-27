package org.example.server.controller;

import org.example.client.Main;
import org.example.client.controller.MenuController;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.ForgetPasswordMenuView;
import org.example.client.view.menu.LoginMenuView;
import org.example.client.view.menu.MainMenuView;
import org.example.client.view.menu.WelcomeMenuView;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenuController {

    public static Message login(Message loginMessage , ClientConnectionThread clientConnectionThread) {
        String username = loginMessage.getFromBody("username");
        String password = loginMessage.getFromBody("password");
        boolean stayLoggedIn = loginMessage.getFromBody("stayLoggedIn");

        User user = ServerApp.getUserByUsername(username);
        if (user == null){
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Username not found!"));
            }} , Message.Type.response);
        }

        if(!user.passwordEquals(password)){
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Passwords do not match!"));
            }} , Message.Type.response);
        }
        clientConnectionThread.setUser(user);

        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo("You have successfully logged in."
                    , false));
        }} , Message.Type.response);
    }
}
