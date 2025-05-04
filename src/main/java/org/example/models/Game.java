package org.example.models;

import org.example.models.Map.FarmMap;
import org.example.models.Map.FarmMapBuilder;
import org.example.models.Map.FarmMapDirector;
import org.example.models.Map.NPCMap;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.NPC;
import org.example.models.enums.Weathers.Weather;

import java.util.ArrayList;

public class Game {
    private Player admin;
    private int currentPlayerIndex = 0;
    private ArrayList<Player> players;
    private FarmMap[] farmMaps = new FarmMap[4];
    private NPCMap npcMap = new NPCMap();
    private Weather currentWeather;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private static ArrayList<Dialogue> dialogues = new ArrayList<>();

    public Game(ArrayList<Player> players) {
        this.admin = players.getFirst();
        this.players = players;
        for (int i = 0; i < 4; i++) {
            FarmMapBuilder builder = new FarmMapBuilder();
            FarmMapDirector director = new FarmMapDirector();
            director.BuildMap(builder, i);
            farmMaps[i] = builder.getFinalProduct();
        }
    }

    public Player getAdmin() {
        return admin;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public FarmMap getFarmMap(int index) {
        return farmMaps[index];
    }

    public void passAnHour() {
        time.passAnHour();
        // TODO
    }

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
