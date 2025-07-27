package org.example.client.model;

import com.google.gson.internal.LinkedTreeMap;
import org.example.server.models.Lobby;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Map.FarmMapBuilder;
import org.example.server.models.Map.FarmMapDirector;
import org.example.server.models.Map.NPCMap;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;
import org.example.server.models.Time;
import org.example.server.models.User;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.ShopType;
import org.example.server.models.enums.Weathers.Weather;

import java.util.ArrayList;

public class ClientGame {
    private final int lobbyId;
    private User admin;
    private Player player;
    private final FarmMap[] farmMaps = new FarmMap[4];
    private ArrayList<MiniPlayer> players;
    private NPCMap npcMap;
    private Weather currentWeather = Weather.Sunny, tomorrowWeather = null;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private Shop jojaMart, pierreGeneralStore, carpenterShop, fishShop, marnieRanch, stardropSaloon;
    private BlackSmith blackSmith;
    private NPC Sebastian, Abigail, Harvey, Lia, Robbin, Clint, Pierre, Robin, Willy, Marnie, Morris, Gus;

    public ClientGame(Lobby lobby, Player player, ArrayList<MiniPlayer> players) {
        this.lobbyId = lobby.getId();
        this.admin = lobby.getAdmin();
        this.player = player;
        this.players = players;
    }

    public void init(ArrayList<ArrayList<LinkedTreeMap<String, Object>>> info) {
        blackSmith = new BlackSmith(ShopType.Blacksmith);
        jojaMart = new Shop(ShopType.JojaMart);
        pierreGeneralStore = new Shop(ShopType.PierreGeneralStore);
        carpenterShop = new Shop(ShopType.CarpenterShop);
        fishShop = new Shop(ShopType.FishShop);
        marnieRanch = new Shop(ShopType.MarnieRanch);
        stardropSaloon = new Shop(ShopType.StardropSaloon);

        Sebastian = new NPC(NPCType.Sebastian, 10);
        Abigail = new NPC(NPCType.Abigail, 20);
        Harvey = new NPC(NPCType.Harvey, 30);
        Lia = new NPC(NPCType.Lia, 40);
        Robbin = new NPC(NPCType.Robbin, 50);
        Clint = new NPC(NPCType.Clint, 60);
        Pierre = new NPC(NPCType.Pierre, 70);
        Robin = new NPC(NPCType.Robin, 80);
        Willy = new NPC(NPCType.Willy, 90);
        Marnie = new NPC(NPCType.Marnie, 100);
        Morris = new NPC(NPCType.Morris, 110);
        Gus = new NPC(NPCType.Gus, 120);

        npcs.add(Sebastian);
        npcs.add(Abigail);
        npcs.add(Harvey);
        npcs.add(Lia);
        npcs.add(Robbin);
        npcs.add(Clint);
        npcs.add(Pierre);
        npcs.add(Robin);
        npcs.add(Willy);
        npcs.add(Marnie);
        npcs.add(Morris);
        npcs.add(Gus);

        npcMap = new NPCMap();
        for (int i = 0; i < 4; i++) {
            FarmMapBuilder builder = new FarmMapBuilder();
            FarmMapDirector director = new FarmMapDirector();
            director.buildMapWithoutForaging(builder, i);
            farmMaps[i] = builder.getFinalProduct();
            farmMaps[i].addForaging(info.get(i));
        }
    }

    public Player getCurrentPlayer() {
        return player;
    }

    public FarmMap getFarmMap(int mapIndex) {
        return farmMaps[mapIndex];
    }

    public NPCMap getNpcMap() {
        return npcMap;
    }

    public Shop getJojaMart() {
        return jojaMart;
    }

    public Shop getPierreGeneralStore() {
        return pierreGeneralStore;
    }

    public Shop getStardropSaloon() {
        return stardropSaloon;
    }

    public Shop getCarpenterShop() {
        return carpenterShop;
    }

    public Shop getFishShop() {
        return fishShop;
    }

    public Shop getMarnieRanch() {
        return marnieRanch;
    }

    public BlackSmith getBlackSmith() {
        return blackSmith;
    }
}
