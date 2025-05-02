package org.example.models;

import org.example.models.Map.FarmMap;
import org.example.models.NPCs.NPC;

import java.util.ArrayList;

public class Game {
    private Player admin;
    private int currentPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private FarmMap farmMap;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Time getTime() {
        return time;
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }
}
