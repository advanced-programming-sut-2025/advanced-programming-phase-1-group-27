package org.example.models.enums.items;

import org.example.models.enums.Seasons.Season;

public enum FishType implements Item{
    Salmon(75, Season.Fall, false),
    Sardine(40, Season.Fall, false),
    Shad(60, Season.Fall, false),
    BlueDiscus(120, Season.Fall, false),
    MidnightCarp(150, Season.Winter, false),
    Squid(80, Season.Winter, false),
    Tuna(100, Season.Winter, false),
    Perch(55, Season.Winter, false),
    Flounder(100, Season.Spring, false),
    Lionfish(100, Season.Spring, false),
    Herring(30, Season.Spring, false),
    Ghostfish(45, Season.Spring, false),
    Tilapia(75, Season.Summer, false),
    Dorado(100, Season.Summer, false),
    Sunfish(30, Season.Summer, false),
    RainbowTrout(65, Season.Summer, false),
    Legend(5000, Season.Spring, true),
    Glacierfish(1000, Season.Winter, true),
    Angler(900, Season.Fall, true),
    Crimsonfish(1500, Season.Summer, true);

    private final int price;
    private final int energy;
    private final Season season;
    private final boolean isLegendary;

    FishType(int price, Season season, boolean isLegendary , int energy) {
        this.price = price;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }
}
