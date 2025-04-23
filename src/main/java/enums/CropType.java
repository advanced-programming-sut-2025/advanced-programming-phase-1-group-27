package enums;

import models.Crop;
import models.Seasons.Season;

public enum CropType implements Item{
    BlueJazz,
    Hay;
    // ...

    private String name;
    private Seed source;
    private int[] stages;
    private int totalHarvestTime, regrowthTime, baseSellPrice, energy;
    private boolean oneTime, isEdible, canBecomeGiant;
    private Season season;
    
}
