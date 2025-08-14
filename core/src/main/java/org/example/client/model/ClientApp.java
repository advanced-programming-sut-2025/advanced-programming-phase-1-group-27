package org.example.client.model;

import com.badlogic.gdx.Gdx;
import org.example.client.Main;
import org.example.client.controller.SaveController;
import org.example.client.view.AppMenu;
import org.example.client.view.menu.MainMenuView;
import org.example.common.models.Gender;
import org.example.common.models.Map.NPCMap;
import org.example.common.models.Message;
import org.example.common.models.SecurityQuestion;
import org.example.common.models.User;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class ClientApp {
    public static final int TIMEOUT_MILLIS = 5000;
    private static final String loggedInUserFilePath = "data/login_user.json";
    private static String ip;
    private static int port;
    private static ServerConnectionThread serverConnectionThread;
    private static User loggedInUser = null;
    private static ClientGame currentGame = null;
    private static AppMenu currentMenu = null;
    private static AppMenu nonMainMenu = null;

    public static boolean loadSavedUser() {
        User savedUser = getSavedUser();
        if (savedUser != null) {
            loggedInUser = savedUser;
            serverConnectionThread.sendMessage(new Message(new HashMap<>() {{
                put("userInfo", loggedInUser.getInfo());
            }}, Message.Type.set_online_user));
            return true;
        }
        return false;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static void initFromArgs(String[] args) throws IOException {
        ip = args[0].split(":")[0];
        port = Integer.parseInt(args[0].split(":")[1]);

        String serverIp = args[1].split(":")[0];
        int serverPort = Integer.parseInt(args[1].split(":")[1]);
        Socket socket = new Socket(serverIp, serverPort);
        serverConnectionThread = new ServerConnectionThread(socket);
    }

    public static void connectToServer() {
        if (serverConnectionThread == null || serverConnectionThread.isAlive())
            return;
        serverConnectionThread.start();
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }

    public static ServerConnectionThread getServerConnectionThread() {
        return serverConnectionThread;
    }

    public static void end() {
        if (currentGame != null) {
            serverConnectionThread.sendMessage(new Message(new HashMap<>() {{
                put("lobbyId", ClientApp.getCurrentGame().getLobbyId());
                put("playerName", ClientApp.getCurrentGame().getCurrentPlayer().getUsername());
            }}, Message.Type.dc));
            if (currentGame.getCurrentPlayer().getCurrentMap() instanceof NPCMap)
                serverConnectionThread.sendMessage(new Message(new HashMap<>() {{
                    put("lobbyId", currentGame.getLobbyId());
                    put("username", currentGame.getCurrentPlayer().getUsername());
                }}, Message.Type.leave_npc));
            saveGame();
        }
        ClientApp.getServerConnectionThread().sendAndWaitForResponse(
                new Message(null, Message.Type.leave_app), TIMEOUT_MILLIS
        );
        exit();
    }

    public static void exit() {
        serverConnectionThread.end();
        Gdx.app.exit();
        System.exit(0);
    }

    public static User getUserByUsername(String username) {
        Message response = serverConnectionThread.sendAndWaitForResponse(new Message(new HashMap<>() {{
            put("username", username);
        }}, Message.Type.get_user), TIMEOUT_MILLIS);
        if (response.getFromBody("found"))
            return new User(response.getFromBody("userInfo"));
        return null;
    }

    public static ClientGame getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(ClientGame currentGame) {
        ClientApp.currentGame = currentGame;
    }

    public static AppMenu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(AppMenu currentMenu) {
        ClientApp.currentMenu = currentMenu;
    }

    public static void updateFile(User user) {
        File file = new File(loggedInUserFilePath);
        if (!file.exists() || file.length() == 0)
            return;
        setSavedUser(user);
    }

    public static void deleteLoginUserFile() {
        File file = new File(loggedInUserFilePath);
        if (!file.exists() || file.length() == 0)
            return;
        file.delete();
    }

    public static User getSavedUser() {
        File file = new File(loggedInUserFilePath);
        if (!file.exists() || file.length() == 0)
            return null;

        try (FileReader reader = new FileReader(file)) {
            JSONObject json = new JSONObject(new JSONTokener(reader));
            User result = new User();
            result.setUsername(json.optString("username"));
            result.setPassword(json.optString("password"));
            result.setNickname(json.optString("nickname"));
            result.setEmail(json.optString("email"));
            result.setRecoveryQuestion(new SecurityQuestion(
                    json.optString("recoveryQuestion"),
                    json.optString("recoveryAnswer")
            ));
            result.setGender(Gender.getGender(json.optString("gender")));
            result.setMaxMoneyEarned(json.optInt("maxMoneyEarned"));
            result.setNumberOfGamesPlayed(json.optInt("numberOfGamesPlayed"));
            result.setAvatarId(json.optInt("avatarId"));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setSavedUser(User user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("nickname", user.getNickname());
        jsonObject.put("gender", user.getGender());
        jsonObject.put("recoveryQuestion", user.getRecoveryQuestion().getQuestion());
        jsonObject.put("recoveryAnswer", user.getRecoveryQuestion().getAnswer());
        jsonObject.put("maxMoneyEarned", user.getMaxMoneyEarned());
        jsonObject.put("numberOfGamesPlayed", user.getNumberOfGamesPlayed());
        jsonObject.put("avatarId", user.getAvatarId());

        File file = new File(loggedInUserFilePath);
        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generatePassword() {
        Random random = new Random();
        int passwordLen = 8 + random.nextInt(5);
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String specialCharacters = "?><,\"';:\\/|][}{+=)(*&~%$#!";
        ArrayList<Character> passwordCharacters = new ArrayList<>();
        passwordCharacters.add(lowercase.charAt(random.nextInt(lowercase.length())));
        passwordCharacters.add(uppercase.charAt(random.nextInt(uppercase.length())));
        passwordCharacters.add(numbers.charAt(random.nextInt(numbers.length())));
        passwordCharacters.add(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        String allCharacters = lowercase + uppercase + numbers + specialCharacters;
        for (int i = 4; i < passwordLen; i++)
            passwordCharacters.add(allCharacters.charAt(random.nextInt(allCharacters.length())));

        // shuffling selected characters (the first four characters are not random)
        Collections.shuffle(passwordCharacters, random);

        StringBuilder password = new StringBuilder();
        for (Character c : passwordCharacters) {
            password.append(c);
        }
        return password.toString();
    }

    public static AppMenu getNonMainMenu() {
        return nonMainMenu;
    }

    public static void setNonMainMenu(AppMenu menu) {
        nonMainMenu = menu;
    }


    public static void saveGame() {
        if (currentGame == null)
            return;
        SaveController.sendInfo();
        terminateGame();
    }

    public static void terminateGame() {
        Gdx.app.postRunnable(() -> {
            Main.getMain().getScreen().dispose();
            ClientApp.setCurrentMenu(new MainMenuView());
            Main.getMain().setScreen(ClientApp.getCurrentMenu());
            currentGame = null;
        });
    }
}
