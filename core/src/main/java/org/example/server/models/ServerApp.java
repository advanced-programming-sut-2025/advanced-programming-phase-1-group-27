package org.example.server.models;

import org.example.common.database.DataBaseHelper;
import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.connections.ListenerThread;

import java.util.ArrayList;

public class ServerApp {
    public static final int TIMEOUT_MILLIS = 5000;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static ListenerThread listenerThread;
    private static boolean hasEnded = false;
    private static ArrayList<User> onlineUsers = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
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
    }

    public static void addConnection(ClientConnectionThread clientConnectionThread) {
        if (clientConnectionThread == null || connections.contains(clientConnectionThread))
            return;
        connections.add(clientConnectionThread);
    }

    public static void setUsers(ArrayList<User> users) {
        ServerApp.users = users;
    }

    public static User getUserByUsername(String username) {
//        for (User user : users) {
//            if (user.getUsername().equals(username))
//                return user;
//        }
//        return null;
        return DataBaseHelper.DatabaseHelper.getUserByUsername(username);
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

    public static void addOnline(User user) {
        onlineUsers.add(user);
    }
}
