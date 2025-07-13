package org.example.server.models;

import org.example.server.models.connections.ClientConnectionThread;
import org.example.server.models.connections.ListenerThread;

import java.util.ArrayList;

public class ServerApp {
    private static final int TIMEOUT_MILLIS = 500;
    private static final ArrayList<ClientConnectionThread> connections = new ArrayList<>();
    private static ListenerThread listenerThread;
    private static boolean hasEnded = false;
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
}
