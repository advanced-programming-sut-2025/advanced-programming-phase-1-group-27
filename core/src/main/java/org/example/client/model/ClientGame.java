package org.example.client.model;

import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;
import org.example.server.models.Time;
import org.example.server.models.User;
import org.example.server.models.enums.Weathers.Weather;

import java.util.ArrayList;

public class ClientGame {
    private final int lobbyId;
    private User admin;
    private Player player;
    private final FarmMap[] farmMaps = new FarmMap[4];
    private ArrayList<miniPlayer> players;
    private NPCMap npcMap;
    private Weather currentWeather = Weather.Sunny, tomorrowWeather = null;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private Shop jojaMart, pierreGeneralStore, carpenterShop, fishShop, marnieRanch, stardropSaloon;
    private BlackSmith blackSmith;
    private NPC Sebastian, Abigail, Harvey, Lia, Robbin, Clint, Pierre, Robin, Willy, Marnie, Morris, Gus;

    public ClientGame(int lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Player getCurrentPlayer() {
        return player;
    }
}
