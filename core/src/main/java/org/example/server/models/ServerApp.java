package org.example.server.models;

import org.example.common.database.DataBaseHelper;
import org.example.common.models.Message;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.connections.ListenerThread;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerApp {
    public static final int TIMEOUT_MILLIS = 5000;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static ListenerThread listenerThread;
    private static boolean hasEnded = false;
    private static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static void setListenerThread(ListenerThread listenerThread) {
        ServerApp.listenerThread = listenerThread;
    }

    public static void startListening() {
        if (listenerThread != null && !listenerThread.isAlive())
            listenerThread.start();
        else
            throw new IllegalStateException("Listener thread is already running or not set.");
    }

    public static void end() {
        hasEnded = true;
        for (ClientConnectionThread connection : connections) {
            connection.end();
        }
        connections.clear();
    }

    public static boolean hasEnded() {
        return hasEnded;
    }

    public static void removeConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread == null)
            return;
        connections.remove(clientConnectionThread);
        System.out.println("Number of connectionsss: " + connections.size());
    }

    public static void addConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread == null || connections.contains(clientConnectionThread))
            return;
        connections.add(clientConnectionThread);
        System.out.println("Number of connections: " + connections.size());
    }

    public static User getUserByUsername(String username) {
        return DataBaseHelper.getUserByUsername(username);
    }

    public static Message getUserByUsername(Message message) {
        String username = message.getFromBody("username");
        User user = getUserByUsername(username);
        if (user == null)
            return new Message(new HashMap<>() {{
                put("found", false);
            }}, Message.Type.response);
        return new Message(new HashMap<>() {{
            put("found", true);
            put("userInfo", user.getInfo());
        }}, Message.Type.response);
    }

    public static String generateUsername(String username) {
        String result;
        for (int i = 1; i <= 20; i++) {
            result = username + i;
            if (getUserByUsername(result) == null)
                return result;
        }
        return null;
    }

    public static void addUser(User user) {
        DataBaseHelper.registerUser(user);
    }

    public static ArrayList<User> getUsers() {
        return DataBaseHelper.getAllUsers();
    }

    public static void addLobby(Lobby lobby) {
        lobbies.add(lobby);
    }

    public static ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public static Lobby getLobbyById(int id) {
        for (Lobby lobby : lobbies) {
            if (lobby.getId() == id)
                return lobby;
        }
        return null;
    }
}
