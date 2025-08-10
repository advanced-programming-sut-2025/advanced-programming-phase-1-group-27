package org.example.client.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.controller.OtherPlayerController;
import org.example.client.view.OutsideView;
import org.example.client.view.menu.MainMenuView;
import org.example.common.models.*;
import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.Map.*;
import org.example.server.models.NPCs.NPC;
import org.example.server.models.Player;
import org.example.server.models.Relations.Relation;
import org.example.server.models.Shops.BlackSmith;
import org.example.server.models.Shops.Shop;
import org.example.server.models.User;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.NPCType;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.FruitType;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.Plants.Tree;
import org.example.server.models.enums.ShopType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.Weathers.Weather;
import org.example.server.models.enums.items.MineralType;
import org.example.server.models.enums.items.products.AnimalProduct;
import org.example.server.models.enums.items.products.CookingProduct;
import org.example.server.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.min;

public class ClientGame implements Game {
    private final int lobbyId;
    private final HashMap<String, Integer> usernameToMap;
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
    private HashMap<String, String> songNameToId = new HashMap<>();
    private Music currentMusic = null;
    private String currentMusicName = null;

    public ClientGame(Lobby lobby, Player player, ArrayList<MiniPlayer> players) {
        this.lobbyId = lobby.getId();
        this.usernameToMap = lobby.getUsernameToMap();
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

        generateNPCDialogues();
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

    public MiniPlayer getMiniPlayerByUsername(String username) {
        for (MiniPlayer miniPlayer : players) {
            if (miniPlayer.getUsername().equals(username))
                return miniPlayer;
        }
        return null;
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
        generateNPCDialogues();
    }

    @Override
    public void newSeason() {
        initShops();
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    private void generateNPCDialogues() {
        for (NPC npc : npcs) {
            if (!npc.isThinking() && npc.getType().getJob() == null) {
                new Thread(new NPCDialogueGenerator(npc, player)).start();
            }
        }
    }

    private void refreshRelations() {
        player.refreshNPCThings(this);
        // TODO: parsa, check kon
        player.addToChatInbox(npcGiftingLevel3());
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

                if (ClientApp.getCurrentMenu() instanceof OutsideView outsideView && ClientApp.getNonMainMenu() == null){
                    outsideView.displayCrowAttack(plant.getCell().getPosition().getX(),plant.getCell().getPosition().getY());
                }

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
                        System.out.println("shashidam!");
                    } else if (cells[i][j].getBuilding() instanceof GreenHouse) {
                        plant.grow();
                    } else if (!plant.getType().getSeasons().contains(time.getSeason())) {
                        cells[i][j].setObject(null);
                        System.out.println("shashidam22!");
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
        FarmMap map = player.getFarmMap();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getObject() instanceof Artisan)
                    ((Artisan) cell.getObject()).passHour();
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

    public OtherPlayerController getOtherPlayerController(String playerName) {
        for (OtherPlayerController otherPlayerController : otherPlayerControllers) {
            if (otherPlayerController.getUsername().equals(playerName))
                return otherPlayerController;
        }
        return null;
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

    private String npcGiftingLevel3() {
        Random rand = new Random();
        StringBuilder result = new StringBuilder();
        result.append("NPC Gifts : \n");
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
        return result.toString();
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

    public synchronized void addSong(String songName, String songId) {
        songNameToId.put(songName, songId);
    }

    public HashMap<String, String> getSongList() {
        return songNameToId;
    }

    public String getSongIdByName(String songName) {
        return songNameToId.get(songName);
    }

    public synchronized void setCurrentMusic(Music music, float offset) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = music;
        Gdx.app.postRunnable(() -> {
            currentMusic.play();
            currentMusic.setPosition(offset);
        });
    }

    public void stopMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
            currentMusic.dispose();
        }
    }

    public void pauseMusic() {
        if (currentMusic != null && currentMusic.isPlaying())
            currentMusic.pause();
    }

    public void resumeMusic() {
        if (currentMusic != null && !currentMusic.isPlaying())
            currentMusic.play();
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public String getCurrentMusicName() {
        return currentMusicName;
    }

    public void setCurrentMusicName(String currentMusicName) {
        this.currentMusicName = currentMusicName;
    }

    public void kickPlayer(String playerName) {
        if (ClientApp.getLoggedInUser().getUsername().equals(playerName)) {
            Gdx.app.postRunnable(() -> {
                Main.getMain().getScreen().dispose();
                ClientApp.setCurrentMenu(new MainMenuView());
                Main.getMain().setScreen(ClientApp.getCurrentMenu());
                ClientApp.setCurrentGame(null);
            });
            return;
        }
        MiniPlayer miniPlayer = getMiniPlayerByUsername(playerName);
        players.remove(miniPlayer);
        if (admin.getUsername().equals(playerName)) {
            MiniPlayer newAdmin = players.getFirst();
            admin = new User(
                    newAdmin.getUsername(), newAdmin.getPassword(),
                    newAdmin.getNickname(), newAdmin.getEmail(),
                    newAdmin.getGender()
            );
        }
        OtherPlayerController otherPlayerController = getOtherPlayerController(playerName);
        otherPlayerControllers.remove(otherPlayerController);
    }

    public int getPlayerMapIndex(String playerName) {
        return usernameToMap.get(playerName);
    }

    public Message getPlayerInfo() {
        return new Message(new HashMap<>() {{
            put("position", player.getPosition().getInfo());
            put("isInNPCMap", player.getCurrentMap() instanceof NPCMap);
            put("money", player.getMoney());
            put("numberOfQuestsCompleted", 0); // TODO: rassa, dorostesh kon
            put("totalAbility", player.getAbility(AbilityType.Farming).getLevel() +
                                player.getAbility(AbilityType.Fishing).getLevel() +
                                player.getAbility(AbilityType.Foraging).getLevel() +
                                player.getAbility(AbilityType.Mining).getLevel());
        }}, Message.Type.response);
    }
}
