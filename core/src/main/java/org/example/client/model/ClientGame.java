package org.example.client.model;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.controller.OtherPlayerController;
import org.example.common.models.Direction;
import org.example.common.models.Game;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.*;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;
import org.example.common.models.Time;
import org.example.server.models.User;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.Plants.Tree;
import org.example.server.models.enums.ShopType;
import org.example.server.models.enums.Weathers.Weather;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;

public class ClientGame implements Game {
    private final int lobbyId;
    private User admin;
    private Player player;
    private final FarmMap[] farmMaps = new FarmMap[4];
    private ArrayList<MiniPlayer> players;
    private NPCMap npcMap;
    private Weather currentWeather = Weather.Sunny;
    private Time time;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private Shop jojaMart, pierreGeneralStore, carpenterShop, fishShop, marnieRanch, stardropSaloon;
    private BlackSmith blackSmith;
    private NPC Sebastian, Abigail, Harvey, Lia, Robbin, Clint, Pierre, Robin, Willy, Marnie, Morris, Gus;
    private ArrayList<OtherPlayerController> otherPlayerControllers = new ArrayList<>();

    public ClientGame(Lobby lobby, Player player, ArrayList<MiniPlayer> players) {
        this.lobbyId = lobby.getId();
        this.admin = lobby.getAdmin();
        this.player = player;
        this.players = players;
        this.time = new Time(this);
    }

    public void init(ArrayList<ArrayList<LinkedTreeMap<String, Object>>> info) {
        initShops();
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

        npcMap = new NPCMap(this);
        for (int i = 0; i < 4; i++) {
            FarmMapBuilder builder = new FarmMapBuilder();
            FarmMapDirector director = new FarmMapDirector();
            director.buildMapWithoutForaging(builder, i, this);
            farmMaps[i] = builder.getFinalProduct();
            farmMaps[i].addForaging(info.get(i));
        }

        generateNPCDialouges();
    }

    public Player getCurrentPlayer() {
        return player;
    }

    public User getAdmin() {
        return admin;
    }

    public FarmMap getFarmMap(int mapIndex) {
        return farmMaps[mapIndex];
    }

    public NPCMap getNpcMap() {
        return npcMap;
    }

    public ArrayList<MiniPlayer> getPlayers() {
        return players;
    }

    @Override
    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public void setWeather(Weather weather) {
        currentWeather = weather;
    }

    public Time getTime() {
        return time;
    }

    public Shop getShop(String shopName) {
        if (shopName.equalsIgnoreCase(ShopType.Blacksmith.name()))
            return blackSmith;
        if (shopName.equalsIgnoreCase(ShopType.JojaMart.name()))
            return jojaMart;
        if (shopName.equalsIgnoreCase(ShopType.PierreGeneralStore.name()))
            return pierreGeneralStore;
        if (shopName.equalsIgnoreCase(ShopType.CarpenterShop.name()))
            return carpenterShop;
        if (shopName.equalsIgnoreCase(ShopType.FishShop.name()))
            return fishShop;
        if (shopName.equalsIgnoreCase(ShopType.MarnieRanch.name()))
            return marnieRanch;
        if (shopName.equalsIgnoreCase(ShopType.StardropSaloon.name()))
            return stardropSaloon;
        return null;
    }

    @Override
    public void passAnHour() {
        updateBuff();
        updateArtisans();
    }

    @Override
    public void newDay() {
        crowsAttack();
        updateAnimals();
        updateShippingBin();
        setPlayerEnergy();
        growPlants();
        initShops();
        refreshRelations(); // refreshing relationships between players and between player and npcs
        generateNPCDialouges();
    }

    @Override
    public void newSeason() {
        initShops();
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    private void generateNPCDialouges() {
        for (NPC npc : npcs) {
            if (!npc.isThinking() && npc.getType().getJob() == null) {
                new Thread(new NPCDialogueGenerator(npc, player)).start();
            }
        }
    }

    private void refreshRelations() {
        player.refreshNPCThings(this);
        player.refreshPlayerThings();
    }

    private void crowsAttack() {
        // Crows Attacking
        ArrayList<Plant> allPlants = player.getFarmMap().getAllPlants();
        int crowsCount = allPlants.size() / 16;
        for (int i = 0; i < crowsCount; i++) {
            if (new Random().nextInt(4) == 0) {
                int plantIndex = new Random().nextInt(allPlants.size());
                Plant plant = allPlants.get(plantIndex);
                if (plant.getCell().isProtected())
                    continue;
                if (plant instanceof Crop crop) {
                    crop.getCell().setObject(null);
                } else if (plant instanceof Tree tree) {
                    tree.setTillNextHarvest(min(1, tree.getTillNextHarvest()));
                }
            }
        }
    }

    private void growPlants() {
        // Grow (and deleting) Plants :
        player.getFarmMap().generateForaging(time.getSeason());
        Cell[][] cells = player.getFarmMap().getCells();
        for (int i = 0; i < player.getFarmMap().getHeight(); i++) {
            for (int j = 0; j < player.getFarmMap().getWidth(); j++) {
                if (cells[i][j].getObject() instanceof Plant plant && !plant.isForaging()) {
                    if (plant.isGiant() && ((cells[i][j].getAdjacentCells().get(6) != null &&
                            cells[i][j].getAdjacentCells().get(6).getObject() == plant) ||
                            (cells[i][j].getAdjacentCells().get(4) != null &&
                                    cells[i][j].getAdjacentCells().get(4).getObject() == plant))) {
                        continue;
                    }
                    if (!plant.getWateredYesterday() && !plant.getWateredToday()) {
                        cells[i][j].setObject(null);
                    } else if (cells[i][j].getBuilding() instanceof GreenHouse) {
                        plant.grow();
                    } else if (!plant.getType().getSeasons().contains(currentWeather)) {
                        cells[i][j].setObject(null);
                    } else {
                        plant.grow();
                    }
                }
            }
        }
    }

    private void setPlayerEnergy() {
        // Setting Energies :
        if (player.hasPassedOut())
            player.setEnergy(player.getMaxEnergy() * 3 / 4);

        else
            player.setEnergy(player.getMaxEnergy());
    }

    private void updateShippingBin() {
        // Emptying shipping bin
        for (ShippingBin shippingBin : player.getFarmMap().getShippingBins()) {
            player.addMoney(shippingBin.refresh());
        }
    }

    private void updateAnimals() {
        for (Animal animal : player.getFarmMap().getAnimals()) {
            animal.passADay();
        }
    }

    private void updateBuff() {
        if (player.getCurrentBuff() != null) {
            player.getCurrentBuff().reduceRemainingTime();
            if (player.getCurrentBuff().getRemainingTime() <= 0) {
                player.removeBuff();
            }
        }
    }

    private void updateArtisans() {
        for (FarmMap map : farmMaps) {
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    Cell cell = map.getCell(i, j);
                    if (cell.getObject() instanceof Artisan)
                        ((Artisan) cell.getObject()).passHour();
                }
            }
        }
    }

    public void initShops() {
        blackSmith = new BlackSmith(ShopType.Blacksmith, time.getSeason());
        jojaMart = new Shop(ShopType.JojaMart, time.getSeason());
        pierreGeneralStore = new Shop(ShopType.PierreGeneralStore, time.getSeason());
        carpenterShop = new Shop(ShopType.CarpenterShop, time.getSeason());
        fishShop = new Shop(ShopType.FishShop, time.getSeason());
        marnieRanch = new Shop(ShopType.MarnieRanch, time.getSeason());
        stardropSaloon = new Shop(ShopType.StardropSaloon, time.getSeason());
    }

    public void addOtherPlayer(String username, int i, int j) {
        otherPlayerControllers.add(new OtherPlayerController(username, i, j));
        System.out.println("Adding " + username + " : " + i + ", " + j);
    }

    public void updateOtherPlayers() {
        for (OtherPlayerController otherPlayerController : otherPlayerControllers)
            otherPlayerController.update();
    }

    public void renderOtherPlayers() {
        for (OtherPlayerController otherPlayerController : otherPlayerControllers)
            otherPlayerController.render();
    }

    public void removeOtherPlayer(String username) {
//        for (OtherPlayerController otherPlayerController : otherPlayerControllers) {
//            if (otherPlayerController.getUsername().equals(username)) {
//                otherPlayerControllers.remove(otherPlayerController);
//                break;
//            }
//        }
        otherPlayerControllers.removeIf(otherPlayerController ->
                otherPlayerController.getUsername().equals(username));
        System.out.println("Removing " + username);
    }

    public void walkPlayer(String username, Direction direction) {
        for (OtherPlayerController otherPlayerController : otherPlayerControllers) {
            if (otherPlayerController.getUsername().equals(username)) {
                otherPlayerController.walk(direction);
            }
        }
        System.out.println(username + direction);
    }

    public ArrayList<OtherPlayerController> getOtherPlayerControllers() {
        return otherPlayerControllers;
    }

    public NPC getGus() {
        return Gus;
    }

    public NPC getMorris() {
        return Morris;
    }

    public NPC getMarnie() {
        return Marnie;
    }

    public NPC getWilly() {
        return Willy;
    }

    public NPC getRobin() {
        return Robin;
    }

    public NPC getPierre() {
        return Pierre;
    }

    public NPC getClint() {
        return Clint;
    }

    public NPC getRobbin() {
        return Robbin;
    }

    public NPC getLia() {
        return Lia;
    }

    public NPC getHarvey() {
        return Harvey;
    }

    public NPC getAbigail() {
        return Abigail;
    }

    public NPC getSebastian() {
        return Sebastian;
    }

    public NPC getNPCByType(NPCType type) {
        for ( NPC npc : npcs ) {
            if ( npc.getType() == type ){
                return npc;
            }
        }
        return null;
    }

    public Shop getJojaMart() {
        return jojaMart;
    }

    public Shop getPierreGeneralStore() {
        return pierreGeneralStore;
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

    public Shop getStardropSaloon() {
        return stardropSaloon;
    }

    public BlackSmith getBlacksmith() {
        return blackSmith;
    }

    public int getLobbyId() {
        return lobbyId;
    }

}
