package org.example.models;

import org.example.models.Map.*;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.NPC;
import org.example.models.Relations.Relation;
import org.example.models.enums.CellType;
import org.example.models.enums.NPCType;
import org.example.models.enums.Plants.CropType;
import org.example.models.enums.Plants.Plant;
import org.example.models.enums.Plants.SaplingType;
import org.example.models.enums.Plants.SeedType;
import org.example.models.enums.Seasons.Season;
import org.example.models.enums.ShopType;
import org.example.models.enums.Weathers.Weather;
import org.example.models.enums.items.*;
import org.example.models.enums.items.products.AnimalProduct;
import org.example.models.enums.items.products.CookingProduct;
import org.example.models.enums.items.products.CraftingProduct;
import org.example.models.enums.items.products.ProcessedProductType;

import java.util.ArrayList;

public class Game {
    private Player admin;
    private int currentPlayerIndex = 0;
    private ArrayList<Player> players;
    private final FarmMap[] farmMaps = new FarmMap[4];
    private  NPCMap npcMap;
    private Weather currentWeather, tomorrowWeather = null;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    // dialogoue ro ki niaz dare?
    private ArrayList<Dialogue> dialogues = new ArrayList<>();
    private Shop blacksmith, jojaMart, pierreGeneralStore, carpenterShop, fishShop, marnieRanch, stardropSaloon;
    private NPC Sebastian , Abigail , Harvey , Lia , Robbin , Clint , Pierre , Robin , Willy , Marnie , Morris , Gus;

    public Game(ArrayList<Player> players) {
        this.admin = players.getFirst();
        this.players = players;
    }

    public void init() {

        blacksmith = new Shop(ShopType.Blacksmith);
        jojaMart = new Shop(ShopType.JojaMart);
        pierreGeneralStore = new Shop(ShopType.PierreGeneralStore);
        carpenterShop = new Shop(ShopType.CarpenterShop);
        fishShop = new Shop(ShopType.FishShop);
        marnieRanch = new Shop(ShopType.MarnieRanch);
        stardropSaloon = new Shop(ShopType.StardropSaloon);

        Sebastian = new NPC(NPCType.Sebastian , 10);
        Abigail = new NPC(NPCType.Abigail , 20);
        Harvey = new NPC(NPCType.Harvey , 30);
        Lia = new NPC(NPCType.Lia , 40);
        Robbin = new NPC(NPCType.Robbin , 50);
        Clint = new NPC(NPCType.Clint , 60);
        Pierre = new NPC(NPCType.Pierre , 70);
        Robin = new NPC(NPCType.Robin , 80);
        Willy = new NPC(NPCType.Willy , 90);
        Marnie = new NPC(NPCType.Marnie , 100);
        Morris = new NPC(NPCType.Morris , 110);
        Gus = new NPC(NPCType.Gus , 120);

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
            director.BuildMap(builder, i);
            farmMaps[i] = builder.getFinalProduct();
        }

        // TODO: initialize npc and shops enums
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
        updatePlayersBuff();
        updateArtisans();
        // TODO: sobhan. update all artisans in map. chejoori bokonam?
        // TODO
        
        
        
    }

    public void newDay() {
        // Setting Energies :
        for (Player player : players) {
            player.setDayEnergy(player.getMaxEnergy());
        }
        // Setting Weather :
        if (tomorrowWeather == null) currentWeather = time.getSeason().pickARandomWeather();
        else {
            currentWeather = tomorrowWeather;
            tomorrowWeather = null;
        }
        // Grow (and deleting) Plants :
        for (Player player : players) {
            Cell[][] cells = player.getFarmMap().getCells();
            for (int i = 0; i < player.getFarmMap().getHeight(); i++) {
                for (int j = 0; j < player.getFarmMap().getWidth(); j++) {
                    if (cells[i][j].getObject() instanceof Plant plant) {
                        if (!plant.getWateredYesterday() && !plant.getWateredToday()) {
                            cells[i][j].setObject(null);
                        } else if (!plant.isGiant()){
                            plant.grow();
                        } else {
                            if ((cells[i][j].getAdjacentCells().get(4) != null &&
                                    cells[i][j].getAdjacentCells().get(4).getObject() == plant) ||
                                    (cells[i][j].getAdjacentCells().get(6) != null &&
                                            cells[i][j].getAdjacentCells().get(6).getObject() == plant)) {

                            } else if (cells[i][j].getBuilding() != null && cells[i][j].getBuilding() instanceof GreenHouse) {
                                plant.grow();
                            } else {
                                for (Season season: plant.getType().getSeasons())
                                    if (season == App.getCurrentGame().getTime().getSeason())
                                        plant.grow();

                            }
                        }
                    }
                }
            }
        }
        //refresh relations :
        for (Player player : players) {
            player.refreshNPCThings();
            player.refreshPlayerThings();
        }
        //TODO : refresh shop stocks
        currentWeather.applyWeatherEffect();
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Time getTime() {
        return time;
    }

    public Shop getBlacksmith() {
        return blacksmith;
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
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        return players.get(currentPlayerIndex) == admin;
    }

    public NPCMap getNpcMap() {
        return npcMap;
    }

    public static Item getItemByName(String itemName) {// for cheat commands
        itemName = itemName.replace(" ", "");
        Item result = AnimalProduct.getItem(itemName);
        if (result != null)
            return result;

        result = CookingProduct.getItem(itemName);
        if (result != null)
            return result;

        result = CraftingProduct.getItem(itemName);
        if (result != null)
            return result;

        result = ProcessedProductType.getItem(itemName);
        if (result != null) {
            if (result.getPrice() == null)
                return new ProcessedProduct((ProcessedProductType) result, 0, 0);
            return new ProcessedProduct(
                    (ProcessedProductType) result,
                    result.getPrice(),
                    ((ProcessedProductType) result).getEnergy()
            );
        }

        result = AnimalType.getItem(itemName);
        if (result != null)
            return result;

        result = BuildingType.getItem(itemName);
        if (result != null)
            return result;

        result = FishType.getItem(itemName);
        if (result != null)
            return result;

        result = MineralType.getItem(itemName);
        if (result != null)
            return result;

        result = ShopItems.getItem(itemName);
        if (result != null)
            return result;

        result = ToolType.getItem(itemName);
        if (result != null)
            return result;

        result = SeedType.getItem(itemName);
        if (result != null)
            return result;

        result = FishType.getItem(itemName);
        if (result != null)
            return result;

        result = SaplingType.getItem(itemName);
        if (result != null)
            return result;

        return null;
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
        for (FarmMap map: farmMaps) {
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    Cell cell = map.getCell(i, j);
                    if (cell.getObject() instanceof Artisan)
                        ((Artisan) cell.getObject()).passHour();
                }
            }
        }
    }
}
