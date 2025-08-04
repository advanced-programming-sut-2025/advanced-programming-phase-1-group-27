package org.example.server.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Game;
import org.example.common.models.Message;
import org.example.common.models.Time;
import org.example.server.controller.TimeController;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.*;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Relations.Dialogue;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.Plants.*;
import org.example.server.models.enums.ShopType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.*;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.min;

public class ServerGame implements Game {
    private final Lobby lobby;
    private final FarmMap[] farmMaps = new FarmMap[4];
    private User admin;
    private int currentPlayerIndex = 0;
    private ArrayList<Player> players;
    private NPCMap npcMap;
    private Weather currentWeather = Weather.Sunny, tomorrowWeather = null; //
    private Time time;
    Thread timeThread;
    private ArrayList<NPC> npcs = new ArrayList<>();
    // all dialogues between players
    private ArrayList<Dialogue> dialogues = new ArrayList<>();
    private Shop jojaMart, pierreGeneralStore, carpenterShop, fishShop, marnieRanch, stardropSaloon;
    private BlackSmith blackSmith;
    private NPC Sebastian, Abigail, Harvey, Lia, Robbin, Clint, Pierre, Robin, Willy, Marnie, Morris, Gus;

    public ServerGame(Lobby lobby, ArrayList<Player> players) {
        this.lobby = lobby;
        this.admin = lobby.getAdmin();
        this.players = players;
        this.time = new Time(this);
        this.timeThread = new Thread(new ServerTimeTracker(this.time));
        for (Player player : players) {
            for (Player otherPlayer : players) {
                if (otherPlayer.getUsername().equals(player.getUsername())) {
                    continue;
                }
                player.getRelations().put(otherPlayer, new Relation());
                player.getPlayerMetToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerHuggedToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerGiftToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerTradeToday().put(otherPlayer, Boolean.FALSE);
            }
        }
    }

    // TODO: this constructor should be erased
    public ServerGame(User admin, ArrayList<Player> players) {
        this.admin = admin;
        this.players = players;
        this.time = new Time(this);
        this.lobby = null;
        for (Player player : players) {
            for (Player otherPlayer : players) {
                if (otherPlayer.getUsername().equals(player.getUsername())) {
                    continue;
                }
                player.getRelations().put(otherPlayer, new Relation());
                player.getPlayerMetToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerHuggedToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerGiftToday().put(otherPlayer, Boolean.FALSE);
                player.getPlayerTradeToday().put(otherPlayer, Boolean.FALSE);
            }
        }
    }

    public void init() {

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
            director.buildMap(builder, i, this);
            farmMaps[i] = builder.getFinalProduct();
        }

        if (timeThread != null)
            timeThread.start();
    }

    private void initShops() {
        blackSmith = new BlackSmith(ShopType.Blacksmith, time.getSeason());
        jojaMart = new Shop(ShopType.JojaMart, time.getSeason());
        pierreGeneralStore = new Shop(ShopType.PierreGeneralStore, time.getSeason());
        carpenterShop = new Shop(ShopType.CarpenterShop, time.getSeason());
        fishShop = new Shop(ShopType.FishShop, time.getSeason());
        marnieRanch = new Shop(ShopType.MarnieRanch, time.getSeason());
        stardropSaloon = new Shop(ShopType.StardropSaloon, time.getSeason());
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User user) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(user.getUsername())) {
                admin = players.get(i);
                currentPlayerIndex = i;
                break;
            }
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
        TimeController.passAnHour(lobby);
        updatePlayersBuff();
        updateArtisans();
        // player energies will be automatically updated
    }

    public void newDay() {
        crowsAttack();
        System.out.println("Crows attack checked.");
        updateAnimals(); // TODO: rassa, maybe automatic??
        System.out.println("Animals update checked.");
        updateShippingBin();
        System.out.println("Shipping Bin checked.");
        setNewWeather();
        System.out.println("Weather checked.");
        growPlants();
        System.out.println("Grow plants checked.");
//        // Walking to their houses
//        for (int i = 0; i < players.size(); i++) {
//            Player player = players.get(i);
//
//            Position doorPosition = player.getFarmMap().getHut().getDoor().getPosition();
//            Cell currentCell = player.getCurrentCell();
//            Cell destCell = player.getFarmMap().getCell(doorPosition.getX() + 1, doorPosition.getY());
//            int energy = player.getDayEnergy();
//            if (energy <= 0)
//                continue;
//            if (player.getCurrentCell().getMap() instanceof NPCMap npcMap1) {
//                Cell passageToFarm = (Cell) player.getFarmMap().getPassage().getObject();
//                if (i < 2)
//                    passageToFarm = passageToFarm.getAdjacentCells().get(4);
//                else
//                    passageToFarm = passageToFarm.getAdjacentCells().get(0);
//                Cell newDest = npcMap1.getPlaceInPath(player.getCurrentCell(),
//                        passageToFarm,
//                        energy);
//                energy -= npcMap1.getPathEnergy(currentCell, newDest);
//                player.setCurrentCell(newDest);
//
//                if (energy <= 0) {
//                    System.out.println(player.getUsername() + " passed out in cell(" +
//                            newDest.getPosition().getX() + ", " + newDest.getPosition().getY() +
//                            ") in the NpcValley, on his way home");
//                    player.consumeEnergy(100000);
//
//                    continue;
//                }
//                player.setCurrentCell((Cell) passageToFarm.getObject());
//                player.setCurrentMap(player.getFarmMap());
//            }
//            currentCell = player.getCurrentCell();
//            FarmMap farmMap = player.getFarmMap();
//            Cell newDest = farmMap.getPlaceInPath(currentCell,
//                    destCell,
//                    energy);
//            player.setCurrentCell(newDest);
//            if (newDest != destCell) {
//                System.out.println(player.getUsername() + " passed out in cell(" +
//                        newDest.getPosition().getX() + ", " + newDest.getPosition().getY() +
//                        ") in his Farm, on his way home");
//                player.consumeEnergy(100000);
//            } else {
//                player.setCurrentMenu(Menu.Home);
//            }
//        }

        //refresh relations :
        for (Player player : players) {
            player.refreshNPCThings(this);
            player.refreshPlayerThings();
        }
        // refresh shop stock
        initShops();

        // Apply weather effect
        applyWeatherEffect(currentWeather);
    }

    public void newSeason() {
        initShops();
    }

    private void applyRain() {
        for (Player player : players) {
            FarmMap map = player.getFarmMap();
            Cell[][] cells = map.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j].getObject() instanceof Plant && cells[i][j].getBuilding() == null) {
                        Plant plant = (Plant) cells[i][j].getObject();
                        plant.water();
                    }
                }
            }
        }
    }

    private void applyThor() {
        for (Player player : players) {
            FarmMap map = player.getFarmMap();
            Cell[][] cells = map.getCells();
            for (int i = 0; i < 3; i++) {
                int x = (new Random()).nextInt(cells.length);
                int y = (new Random()).nextInt(cells[0].length);
                cells[x][y].thor();
            }
        }
    }

    public void applyWeatherEffect(Weather weather) {
        if (weather == Weather.Stormy) applyThor();
        if (weather == Weather.Stormy || weather == Weather.Rainy) applyRain();
    }

    private void growPlants() {
        // Grow (and deleting) Plants :
        for (Player player : players) {
            ArrayList<HashMap<String, Object>> info = player.getFarmMap().generateForaging(time.getSeason());
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
            lobby.notifyUser(player, new Message(new HashMap<>() {{
                put("foragingInfo", info);
            }}, Message.Type.foraging_updates));
        }
    }

    private void crowsAttack() {
        // Crows Attacking
        for (Player player : players) {
            ArrayList<Plant> allPlants = player.getFarmMap().getAllPlants();
            int crowsCount = allPlants.size() / 16;
            ArrayList<Integer> attackedPlants = new ArrayList<>();
            for (int i = 0; i < crowsCount; i++) {
                if (new Random().nextInt(4) == 0) {
                    int plantIndex = new Random().nextInt(allPlants.size());
                    attackedPlants.add(plantIndex);
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
            lobby.notifyUser(player, new Message(new HashMap<>() {{
                put("attackedPlants", attackedPlants);
            }}, Message.Type.crows_attack));
        }
    }

    private void updateAnimals() {
        // Updating animals
        for (Player player : players) {
            for (Animal animal : player.getFarmMap().getAnimals()) {
                animal.passADay();
            }
        }
    }

    private void updateShippingBin() {
        // Emptying shipping bin
        for (Player player : players) {
            for (ShippingBin shippingBin : player.getFarmMap().getShippingBins()) {
                shippingBin.refresh();
                // money will automatically be updated when player.addMoney() is called in client
            }
        }
    }

    private void setNewWeather() {
        // Setting Weather :
        if (tomorrowWeather == null) currentWeather = time.getSeason().pickARandomWeather();
        else {
            currentWeather = tomorrowWeather;
            tomorrowWeather = null;
        }
        lobby.notifyAll(new Message(new HashMap<>() {{
            put("weather", currentWeather.name());
        }}, Message.Type.set_weather));
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Time getTime() {
        return time;
    }

    public BlackSmith getBlacksmith() {
        return blackSmith;
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

    public NPC getSebastian() {
        return Sebastian;
    }

    public NPC getAbigail() {
        return Abigail;
    }

    public NPC getHarvey() {
        return Harvey;
    }

    public NPC getLia() {
        return Lia;
    }

    public NPC getRobbin() {
        return Robbin;
    }

    public NPC getClint() {
        return Clint;
    }

    public NPC getPierre() {
        return Pierre;
    }

    public NPC getRobin() {
        return Robin;
    }

    public NPC getWilly() {
        return Willy;
    }

    public NPC getMarnie() {
        return Marnie;
    }

    public NPC getMorris() {
        return Morris;
    }

    public NPC getGus() {
        return Gus;
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public boolean nextPlayer() { // returns true if everyone has played one turn
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex) == admin;
    }

    public NPCMap getNpcMap() {
        return npcMap;
    }

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
    }

    public void addDialogue(Dialogue dialogue) {
        dialogues.add(dialogue);
    }


    private void updatePlayersBuff() {
        for (Player player : players) {
            if (player.getCurrentBuff() != null) {
                player.getCurrentBuff().reduceRemainingTime();
                if (player.getCurrentBuff().getRemainingTime() <= 0) {
                    player.removeBuff();
                }
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

    public String NPCGiftingLevel3() {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        result.append("NPC Gifts : \n");
        for (Player player : players) {
            for (NPC npc : npcs) {
                if (!npc.getRelations().containsKey(player)) {
                    npc.getRelations().put(player, new Relation());
                }
                Relation relation = npc.getRelations().get(player);
                if (relation.getLevel() >= 3) {
                    if (Math.random() < 0.5) {
                        int choice = rand.nextInt(3);
                        if (npc.getName().equals("Sebastian")) {
                            result.append(npc.getName()).append(" gifts to ").append(player.getUsername())
                                    .append(" : ");
                            if (choice == 0) {
                                result.append("1 * Pizza");
                                player.getBackpack().addItems(CookingProduct.Pizza, StackLevel.Basic, 1);
                            } else if (choice == 1) {
                                result.append("1 * Pumpkin pie");
                                player.getBackpack().addItems(CookingProduct.PumpkinPie, StackLevel.Basic, 1);
                            } else if (choice == 2) {
                                result.append("1 * Wool");
                                player.getBackpack().addItems(AnimalProduct.Wool, StackLevel.Basic, 1);
                            }
                            result.append("\n");
                        } else if (npc.getName().equals("Abigail")) {
                            result.append(npc.getName()).append(" gifts to ").append(player.getUsername())
                                    .append(" : ");
                            if (choice == 0) {
                                result.append("1 * Coffee");
                                player.getBackpack().addItems(ProcessedProductType.Coffee, StackLevel.Basic, 1);
                            } else if (choice == 1) {
                                result.append("1 * Iron ore");
                                player.getBackpack().addItems(MineralType.IronOre, StackLevel.Basic, 1);
                            } else if (choice == 2) {
                                result.append("1 * Stone");
                                player.getBackpack().addItems(MineralType.Stone, StackLevel.Basic, 1);
                            }
                            result.append("\n");
                        } else if (npc.getName().equals("Harvey")) {
                            result.append(npc.getName()).append(" gifts to ").append(player.getUsername())
                                    .append(" : ");
                            if (choice == 0) {
                                result.append("1 * Wine");
                                int energy = (int) (1.75 * FruitType.Grape.getEnergy());
                                int price = 3 * FruitType.Grape.getPrice();
                                player.getBackpack().addItems(new ProcessedProduct(ProcessedProductType.Wine,
                                        price, energy), StackLevel.Basic, 1);
                            } else if (choice == 1) {
                                result.append("1 * Pickle");
                                int energy = (int) (1.75 * FruitType.Carrot.getEnergy());
                                int price = 2 * FruitType.Carrot.getPrice() + 50;
                                player.getBackpack().addItems(new ProcessedProduct(ProcessedProductType.Pickle, price
                                        , energy), StackLevel.Basic, 1);
                            } else if (choice == 2) {
                                result.append("1 * Coffee");
                            }
                            result.append("\n");
                        } else if (npc.getName().equals("Lia")) {
                            result.append(npc.getName()).append(" gifts to ").append(player.getUsername())
                                    .append(" : ");
                            if (choice == 0) {
                                result.append("1 * Wine");
                                int energy = (int) (1.75 * FruitType.Blueberry.getEnergy());
                                int price = 3 * FruitType.Blueberry.getPrice();
                                player.getBackpack().addItems(new ProcessedProduct(ProcessedProductType.Wine,
                                        price, energy), StackLevel.Basic, 1);
                            } else if (choice == 1) {
                                result.append("1 * Grape");
                                player.getBackpack().addItems(FruitType.Grape, StackLevel.Basic, 1);
                            } else if (choice == 2) {
                                result.append("1 * Salad");
                                player.getBackpack().addItems(CookingProduct.Salad, StackLevel.Basic, 1);
                            }
                            result.append("\n");
                        } else if (npc.getName().equals("Robbin")) {
                            result.append(npc.getName()).append(" gifts to ").append(player.getUsername())
                                    .append(" : ");
                            if (choice == 0) {
                                result.append("1 * Iron metal bar");
                                player.getBackpack().addItems(ProcessedProductType.IronMetalBar,
                                        StackLevel.Basic, 1);
                            } else if (choice == 1) {
                                result.append("1 * Wood");
                                player.getBackpack().addItems(MineralType.Wood, StackLevel.Basic, 1);
                            } else if (choice == 2) {
                                result.append("1 * Spaghetti");
                                player.getBackpack().canAdd(CookingProduct.Spaghetti, StackLevel.Basic, 1);
                            }
                            result.append("\n");
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    public ArrayList getFarmInfo() {
        ArrayList info = new ArrayList();
        for (int farmId = 0; farmId < 4; farmId++) {
            info.add(farmMaps[farmId].getInitialForagingInfo());
        }
        return info;
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

    public Player getPlayerByUsername(String username) {
        for(Player player : players){
            if(player.getUsername().equals(username)){
                return player;
            }
        }
        return null;
    }
}
