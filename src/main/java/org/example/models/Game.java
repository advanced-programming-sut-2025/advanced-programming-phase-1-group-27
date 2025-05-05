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
    private Weather currentWeather, tomorrowWeather = null;
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

    public void setAdmin(User user) {
        for (Player player : players) {
            if (player.getUsername().equals(user.getUsername()))
                admin = player;
        }
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

    public Weather getTomorrowWeather() {
        return tomorrowWeather;
    }

    public void setTomorrowWeather(Weather tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }

    public FarmMap getFarmMap(int index) {
        return farmMaps[index];
    }

    public void passAnHour() {
        time.passAnHour();
        // TODO
        
        
        
    }

    public void newDay() {
        for (Player player : players) {
            player.setDayEnergy(player.getMaxEnergy());
        }
        if (tomorrowWeather == null) currentWeather = time.getSeason().pickARandomWeather();
        else {
            currentWeather = tomorrowWeather;
            tomorrowWeather = null;
        }

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

    public boolean nextPlayer() { // returns true if everyone has played one turn
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        return players.get(currentPlayerIndex) == admin;
    }

}
