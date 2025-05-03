package org.example.models;

import org.example.models.Map.FarmMap;
import org.example.models.Map.NPCMap;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.NPC;
import org.example.models.enums.Weathers.Weather;

import java.util.ArrayList;

public class Game {
    private Player admin;
    private int currentPlayerIndex = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private FarmMap[] farmMaps = new FarmMap[];
    private NPCMap npcMap = new NPCMap();
    private Weather currentWeather;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private static ArrayList<Dialogue> dialogues = new ArrayList<>();

    public void newDay() {
        currentWeather = time.getSeason().pickARandomWeather();
        //TODO




    }


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
