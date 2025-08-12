package org.example.server.models.enums.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.example.server.models.Edible;
import org.example.server.models.Item;
import org.example.server.models.enums.Seasons.Season;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public enum FishType implements Item, Edible {
    Salmon(75, Season.Fall, false, 75,"Items/FishType/Salmon.png"),
    Sardine(40, Season.Fall, false, 40, "Items/FishType/Sardine.png"),
    Shad(60, Season.Fall, false, 60, "Items/FishType/Shad.png"),
    BlueDiscus(120, Season.Fall, false, 120, "Items/FishType/Blue_Discus.png"),
    MidnightCarp(150, Season.Winter, false, 150, "Items/FishType/Midnight_Carp.png"),
    Squid(80, Season.Winter, false, 80, "Items/FishType/Squid.png"),
    Tuna(100, Season.Winter, false, 100, "Items/FishType/Tuna.png"),
    Perch(55, Season.Winter, false, 55,  "Items/FishType/Perch.png"),
    Flounder(100, Season.Spring, false, 100, "Items/FishType/Flounder.png"),
    Lionfish(100, Season.Spring, false, 100, "Items/FishType/Lionfish.png"),
    Herring(30, Season.Spring, false, 30, "Items/FishType/Herring.png"),
    Ghostfish(45, Season.Spring, false, 45,  "Items/FishType/Ghostfish.png"),
    Tilapia(75, Season.Summer, false, 75,  "Items/FishType/Tilapia.png"),
    Dorado(100, Season.Summer, false, 100, "Items/FishType/Dorado.png"),
    Sunfish(30, Season.Summer, false, 30, "Items/FishType/Sunfish.png"),
    RainbowTrout(65, Season.Summer, false, 65,  "Items/FishType/Rainbow_Trout.png"),
    Legend(5000, Season.Spring, true, 500, "Items/FishType/Legend.png"),
    Glacierfish(1000, Season.Winter, true, 200, "Items/FishType/Glacierfish.png"),
    Angler(900, Season.Fall, true, 200, "Items/FishType/Angler.png"),
    Crimsonfish(1500, Season.Summer, true, 250, "Items/FishType/Crimsonfish.png"),;

    private static HashMap<Season, FishType> cheapestOfSeason = new HashMap<>() {{
        put(Season.Fall, Sardine);
        put(Season.Winter, Perch);
        put(Season.Summer, Sunfish);
        put(Season.Spring, Herring);
    }};
    private final int price;
    private final int energy;
    private final Season season;
    private final boolean isLegendary;
    private final String address;

    FishType(int price, Season season, boolean isLegendary, int energy, String address) {
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
        this.energy = energy;
        this.address = address;
    }



    public static HashMap<Season, FishType> getCheapestOfSeason() {
        return cheapestOfSeason;
    }

    public static FishType getItem(String itemName) {
        for (FishType fishType : FishType.values()) {
            if (fishType.getName().equalsIgnoreCase(itemName)) {
                return fishType;
            }
        }
        return null;
    }

    public static ArrayList<FishType> getAvailableFish(Season season) {
        ArrayList<FishType> result = new ArrayList<>();
        for (FishType fish : FishType.values()) {
            if (!fish.isLegendary && fish.season == season)
                result.add(fish);
        }
        return result;
    }

    public static ArrayList<FishType> getAvailableLegendaryFish(Season season) {
        ArrayList<FishType> result = new ArrayList<>();
        for (FishType fish : FishType.values()) {
            if (fish.isLegendary && fish.season == season)
                result.add(fish);
        }
        return result;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    @Override
    public boolean isEdible() {
        return true;
    }
}
