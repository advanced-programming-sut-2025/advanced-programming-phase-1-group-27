package org.example.models.enums.items;

import org.example.models.Item;
import org.example.models.enums.Seasons.Season;

import java.util.ArrayList;
import java.util.HashMap;

public enum FishType implements Item {
    Salmon(75, Season.Fall, false , 75),
    Sardine(40, Season.Fall, false , 40),
    Shad(60, Season.Fall, false , 60),
    BlueDiscus(120, Season.Fall, false , 120),
    MidnightCarp(150, Season.Winter, false , 150),
    Squid(80, Season.Winter, false , 80),
    Tuna(100, Season.Winter, false , 100),
    Perch(55, Season.Winter, false , 55),
    Flounder(100, Season.Spring, false , 100),
    Lionfish(100, Season.Spring, false , 100),
    Herring(30, Season.Spring, false , 30),
    Ghostfish(45, Season.Spring, false , 45),
    Tilapia(75, Season.Summer, false , 75),
    Dorado(100, Season.Summer, false , 100),
    Sunfish(30, Season.Summer, false , 30),
    RainbowTrout(65, Season.Summer, false , 65),
    Legend(5000, Season.Spring, true , 5000),
    Glacierfish(1000, Season.Winter, true , 1000),
    Angler(900, Season.Fall, true , 900),
    Crimsonfish(1500, Season.Summer, true , 1500),;

    private final int price;
    private final int energy;
    private final Season season;
    private final boolean isLegendary;

    private static HashMap<Season, FishType> cheapestOfSeason = new HashMap<>() {{
        put(Season.Fall, Sardine);
        put(Season.Winter, Perch);
        put(Season.Summer, Sunfish);
        put(Season.Spring, Herring);
    }};

    FishType(int price, Season season, boolean isLegendary , int energy) {
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
        this.energy = energy;
    }

    public static HashMap<Season, FishType> getCheapestOfSeason() {
        return cheapestOfSeason;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
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
}
