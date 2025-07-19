package org.example.server.models;

import java.util.ArrayList;

public class Lobby {
    private User admin;
    private ArrayList<User> users = new ArrayList<>();
    private boolean isPrivate;
    private boolean isVisible;
    private String id, name, password;
    private Game game = null;

    public String toString() {
        // Add a lock icon for private lobbies
        String privateIndicator = isPrivate ? " 🔒" : "";

        // Format: "Lobby Name 🔒 (Players: X/Y)"
        return name + privateIndicator + " (Players: " + users.size() + "/" + 4 + ")";
    }
}
