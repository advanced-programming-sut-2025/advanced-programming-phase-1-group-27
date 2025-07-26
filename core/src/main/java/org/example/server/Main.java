package org.example.server;

import org.example.common.database.DataBaseHelper;
import org.example.server.models.ServerApp;
import org.example.server.models.connections.ListenerThread;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar YourServer.jar <port>");
            return;
        }
        try {
            int port = Integer.parseInt(args[0]);
            DataBaseHelper.createDatabase();
            ServerApp.setListenerThread(new ListenerThread(port));
            ServerApp.startListening();

        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
        }

        while (!ServerApp.hasEnded()) {
            // maybe cli commands?
        }
    }
}