package org.example.models;

import java.util.ArrayList;

public class Game {
    private User admin;
    private int currentUserIndex = 0;
    private ArrayList<User> players = new ArrayList<>();
    private Map map;

    public User getCurrentUser() {
        return players.get(currentUserIndex);
    }
}
