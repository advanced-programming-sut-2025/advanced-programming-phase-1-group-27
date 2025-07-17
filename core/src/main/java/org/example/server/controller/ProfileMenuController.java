package org.example.server.controller;

import com.badlogic.gdx.utils.Null;
import org.example.client.controller.MenuController;
import org.example.client.model.ClientApp;
import org.example.common.database.DataBaseHelper;
import org.example.common.models.GraphicalResult;
import org.example.common.models.Message;
import org.example.server.models.App;
import org.example.server.models.Result;
import org.example.server.models.ServerApp;
import org.example.server.models.User;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.enums.Menu;
import org.example.client.view.menu.ProfileMenuView;

import java.util.HashMap;
import java.util.Objects;

public class ProfileMenuController{

    public static Message change(Message message , ClientConnectionThread clientConnectionThread){
        String username = message.getFromBody("username");
        String password = message.getFromBody("password");
        String nickname = message.getFromBody("nickname");
        String email = message.getFromBody("email");
        User currentUser = clientConnectionThread.getUser();
        if(!Objects.equals(username, "")){
            Result result = changeUsername(username ,currentUser);
            if(!result.success()){
                return new Message(new HashMap<>() {{
                    put("GraphicalResult" , GraphicalResult.getInfo(result.message()));
                }}, Message.Type.response);
            }
        }
        if(!Objects.equals(password, "")){
            Result result = changePassword(password , currentUser);
            if(!result.success()){
                return new Message(new HashMap<>() {{
                    put("GraphicalResult" , GraphicalResult.getInfo(result.message()));
                }}, Message.Type.response);
            }
        }
        if(!Objects.equals(nickname, "")){
            Result result = changeNickname(nickname, currentUser);
            if(!result.success()){
                return new Message(new HashMap<>() {{
                    put("GraphicalResult" , GraphicalResult.getInfo(result.message()));
                }}, Message.Type.response);
            }
        }
        if(!Objects.equals(email, "")){
            Result result = changeEmail(email , currentUser);
            if(!result.success()){
                return new Message(new HashMap<>() {{
                    put("GraphicalResult" , GraphicalResult.getInfo(result.message()));
                }}, Message.Type.response);
            }
        }
        System.out.println("MOZ");

        StringBuilder res = new StringBuilder();
        if(!Objects.equals(username, "")){
            DataBaseHelper.changeUsername(currentUser.getUsername() , username);
            currentUser.setUsername(username);
            res.append("username changed successfully!\n");
        }
        if(!Objects.equals(password, "")){
            DataBaseHelper.changePassword(currentUser.getUsername() , User.hashPassword(password));
            currentUser.setPassword(User.hashPassword(password));
            res.append("password changed successfully!\n");
        }
        if(!Objects.equals(nickname, "")){
            DataBaseHelper.changeNickname(currentUser.getNickname() , nickname);
            currentUser.setNickname(nickname);
            res.append("nickname changed successfully!\n");
        }
        if(!Objects.equals(email, "")){
            DataBaseHelper.changeEmail(currentUser.getEmail() , email);
            currentUser.setEmail(email);
            res.append("email changed successfully!\n");
        }

        return new Message(new HashMap<>() {{
            put("GraphicalResult" , GraphicalResult.getInfo(res.toString(),
                    false));
        }} , Message.Type.response);
    }

    private static Result changeUsername(String newUsername , User currentUser) {
        if (!User.isValidUsername(newUsername))
            return new Result(false, "Username format is invalid!");
        if (currentUser.getUsername().equals(newUsername))
            return new Result(false, "New username should be different from current username!");
        if (ServerApp.getUserByUsername(newUsername) != null)
            return new Result(false, "Username already exists!");
        return new Result(true, "Username successfully changed!");
    }

    private static Result changeNickname(String nickname , User currentUser) {
        if (currentUser.getNickname().equals(nickname))
            return new Result(false, "New nickname should be different from current nickname!");
        return new Result(true, "Nickname changed successfully!");
    }

    private static Result changeEmail(String email , User currentUser) {
        if (currentUser.getEmail().equals(email))
            return new Result(false, "New email should be different from current email!");
        if (!User.isValidEmail(email))
            return new Result(false, "Email format is invalid!");
        return new Result(true, "Email changed successfully!");
    }

    private static Result changePassword(String newPassword , User currentUSer) {
        if (currentUSer.passwordEquals(newPassword))
            return new Result(false, "New password must be different of old password!");
        Result result = User.checkPassword(newPassword);
        if (!result.success())
            return result;
        return new Result(true, "Password changed successfully!");
    }

    // TODO : nemikhaim?
    public Result showInfo() {
        User user = App.getLoggedInUser();
        String result = "User info:\n" + "username: " + user.getUsername() + "\n" +
                "nickname: " + user.getNickname() + "\n" +
                "maximum money earned in a single game: " + user.getMaxMoneyEarned() + "\n" +
                "number of games played: " + user.getNumberOfGamesPlayed();
        return new Result(true, result);
    }
}
