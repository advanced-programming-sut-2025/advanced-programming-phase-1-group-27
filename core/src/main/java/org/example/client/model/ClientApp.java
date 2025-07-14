package org.example.client.model;

import org.example.server.models.User;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ClientApp {
    private static String ip;
    private static int port;
    private static ServerConnectionThread serverConnectionThread;
    private static User loggedInUser;

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
}
