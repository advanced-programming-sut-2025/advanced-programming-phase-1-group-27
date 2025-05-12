package org.example.models;

import org.example.models.Map.*;
import org.example.models.Relations.Dialogue;
import org.example.models.NPCs.NPC;
import org.example.models.enums.CellType;
import org.example.models.enums.Plants.CropType;
import org.example.models.enums.Plants.Plant;
import org.example.models.enums.Plants.SaplingType;
import org.example.models.enums.Plants.SeedType;
import org.example.models.enums.Seasons.Season;
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
    private final NPCMap npcMap = new NPCMap();
    private Weather currentWeather, tomorrowWeather = null;
    private Time time = new Time();
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<Dialogue> dialogues = new ArrayList<>();

    public Game(ArrayList<Player> players) {
        this.admin = players.getFirst();
        this.players = players;
    }

    public void init() {
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
        updatePlayersBuff();
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
        //TODO
        currentWeather.applyWeatherEffect();
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

    public ArrayList<Dialogue> getDialogues() {
        return dialogues;
    }

    public void addDialogue(Dialogue dialogue) {
        dialogues.add(dialogue);
    }
}
