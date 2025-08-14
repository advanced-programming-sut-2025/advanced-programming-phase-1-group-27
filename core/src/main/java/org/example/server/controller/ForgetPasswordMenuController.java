package org.example.server.controller;

import org.example.common.database.DataBaseHelper;
import org.example.common.models.Message;
import org.example.common.models.User;
import org.example.server.models.ServerApp;

public class ForgetPasswordMenuController {

    public static void setPassword(Message message) {
        String username = message.getFromBody("username");
        User user = ServerApp.getUserByUsername(username);
        if (user == null)
            return;
        user.setPassword(message.getFromBody("password"));
        DataBaseHelper.changePassword(username, message.getFromBody("password"));
    }
}
