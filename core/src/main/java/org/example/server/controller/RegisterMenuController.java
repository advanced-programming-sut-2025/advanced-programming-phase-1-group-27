package org.example.server.controller;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.*;
import org.example.server.models.enums.Gender;
import org.example.server.models.enums.Questions;

import java.util.HashMap;

public class RegisterMenuController {

    public static Message register(Message registerMessage) {
        String username = registerMessage.getFromBody("username");
        String password = registerMessage.getFromBody("password");
        String email = registerMessage.getFromBody("email");

        if (!User.isValidUsername(username))
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Username format is invalid!"));
                put("reRegister", false);
                put("suggestedUsername", null);
            }}, Message.Type.response);

        if (ServerApp.getUserByUsername(username) != null) {
            String suggestedUsername = ServerApp.generateUsername(username);
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo(
                        "Username already exists! Suggested username: " + suggestedUsername,
                        Float.MAX_VALUE
                ));
                put("reRegister", true);
                put("suggestedUsername", suggestedUsername);
            }}, Message.Type.response);
        }

        if (!User.isValidEmail(email)) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo("Email format is invalid!"));
                put("reRegister", false);
                put("suggestedUsername", null);
            }}, Message.Type.response);
        }

        Result passwordCheck = User.checkPassword(password);
        if (!passwordCheck.success()) {
            return new Message(new HashMap<>() {{
                put("GraphicalResult", GraphicalResult.getInfo(passwordCheck.message()));
                put("reRegister", false);
                put("suggestedUsername", null);
            }}, Message.Type.response);
        }

        return new Message(new HashMap<>() {{
            put("GraphicalResult", GraphicalResult.getInfo(
                    "Successful registration!",
                    false
            ));
            put("reRegister", false);
            put("suggestedUsername", null);
        }}, Message.Type.response);
    }

    public static void addUser(Message message) {
        User user = new User(message.getFromBody("userInfo"));
        ServerApp.addUser(user);
    }
}
