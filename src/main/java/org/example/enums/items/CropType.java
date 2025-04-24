package org.example.enums.items;

import models.Seasons.Season;

public enum CropType implements Item {
    BlueJazz,
    Hay;
    // ...

    private int price;

    private String name;
    private Seed source;
    private int[] stages;
    private int totalHarvestTime, regrowthTime, baseSellPrice, energy;
    private boolean oneTime, isEdible, canBecomeGiant;
    private Season season;

    @Override
    public void use() {

    }

    @Override
    public int getPrice() {
        return 0;
    }
}
