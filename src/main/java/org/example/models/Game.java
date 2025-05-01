package org.example.models;

import org.example.models.Map.FarmMap;

import java.util.ArrayList;

public class Game {
    private User admin;
    private int currentPlayerIndex = 0;
    private ArrayList<User> players = new ArrayList<>();
    private FarmMap farmMap;
    private Time time = new Time();

    public User getCurrentUser() {
        return players.get(currentPlayerIndex);
    }

    public Time getTime() {
        return time;
    }
}
